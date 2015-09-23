package com.oberasoftware.home.zwave.threading;

import com.oberasoftware.home.zwave.SerialZWaveConnector;
import com.oberasoftware.home.zwave.api.messages.ZWaveRawMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static com.oberasoftware.home.zwave.ZWAVE_CONSTANTS.ZWAVE_RESPONSE_TIMEOUT;

/**
 * @author Renze de Vries
 */
@Component
public class SenderThread extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(SenderThread.class);

    private int zWaveResponseTimeout = ZWAVE_RESPONSE_TIMEOUT;

    private final Semaphore barrier = new Semaphore(1);

    private Queue<ZWaveRawMessage> sendQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private SerialZWaveConnector serialZWaveConnector;

    private AtomicReference<ZWaveRawMessage> messageAtomicReference = new AtomicReference<>(null);

    /**
     * Run method. Runs the actual sending process.
     */
    @Override
    public void run() {
        LOG.debug("Starting Z-Wave send thread");
        while (!isInterrupted()) {
            try {
                LOG.trace("Message queue size: {}", sendQueue.size());

                if(sendQueue.peek() != null) {
                    ZWaveRawMessage sendMessage = sendQueue.poll();

                    long messageTimeStart = System.currentTimeMillis();
                    messageAtomicReference.set(sendMessage);
                    sendRawMessage(sendMessage);

                    // Clear the semaphore used to acknowledge the response.
                    barrier.drainPermits();

                    if (barrier.tryAcquire(1, zWaveResponseTimeout, TimeUnit.MILLISECONDS)) {
                        long responseTime = System.currentTimeMillis() - messageTimeStart;
                        LOG.debug("Response processed after {} ms. for message: {}", responseTime, sendMessage);
                    } else {
                        if(sendMessage.getRetries() == 0) {
                            sendMessage.incrementRetry();

                            LOG.error("Unable to send message: {}, re-adding to queue", sendMessage);
                            sendQueue.add(sendMessage);
                        } else {
                            LOG.error("Could not send messages, exceeded retry attempt: {}", sendMessage);
                        }
                    }
                } else {
                    LOG.trace("No topics to send, sleeping");
                    sleepUninterruptibly(1, TimeUnit.SECONDS);
                }
            } catch (NoSuchElementException e1) {
                LOG.debug("Sleep no queue elements");
                sleepUninterruptibly(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOG.error("Thread was interrupted, stopping");
            }
        }
        LOG.debug("Stopped Z-Wave send thread");
    }

    private void sendRawMessage(ZWaveRawMessage zWaveRawMessage) {
        byte[] buffer = zWaveRawMessage.getMessageBuffer();
        LOG.debug("Sending Message: {}", zWaveRawMessage);
        try {
            OutputStream outputStream = serialZWaveConnector.getOutputStream();
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException e) {
            LOG.error("Got I/O exception {} during sending. exiting thread.", e.getLocalizedMessage());
        }
    }

    public void completeTransaction() {
        LOG.debug("Completing send transaction");
        barrier.release();
    }

    public ZWaveRawMessage getLastMessage() {
        return messageAtomicReference.get();
    }

    public void queueMessage(ZWaveRawMessage message) {
        sendQueue.add(message);
    }
}
