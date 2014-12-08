package com.oberasoftware.home.zwave.threading;

import com.oberasoftware.home.zwave.api.events.EventBus;
import com.oberasoftware.home.zwave.connector.SerialZWaveConnector;
import com.oberasoftware.home.zwave.messages.ByteMessage;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.oberasoftware.home.zwave.ZWAVE_CONSTANTS.*;
import static com.oberasoftware.home.zwave.messages.ZWaveRawMessage.bb2hex;

/**
 * @author Renze de Vries
 */
@Component
public class ReceiverThread extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(ReceiverThread.class);

    @Autowired
    private EventBus eventBus;

    @Autowired
    private SerialZWaveConnector serialZWaveConnector;

    /**
     * Sends 1 byte frame response.
     * @param response the response code to send.
     */
    private void sendResponse(int response) {
        try {
            OutputStream outputStream = serialZWaveConnector.getOutputStream();
            outputStream.write(response);
            outputStream.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Processes incoming message and notifies event handlers.
     * @param buffer the buffer to process.
     */
    private void processIncomingMessage(byte[] buffer) {
        ZWaveRawMessage serialMessage = new ZWaveRawMessage(buffer);
        if (serialMessage.isValid) {
            LOG.debug("Message is valid, sending ACK");
            sendResponse(ACK);
        } else {
            LOG.error("Message is not valid, discarding");
            return;
        }

        eventBus.pushAsync(new MessageReceivedEvent(serialMessage));
    }

    /**
     * Run method. Runs the actual receiving process.
     */
    @Override
    public void run() {
        LOG.debug("Starting Z-Wave receive thread");

        InputStream inputStream = serialZWaveConnector.getInputStream();

        // Send a NAK to resynchronise communications
        sendResponse(NAK);

        while (!interrupted()) {
            int nextByte;

            try {
                nextByte = inputStream.read();

                if (nextByte == -1)
                    continue;

            } catch (IOException e) {
                LOG.error("Got I/O exception {} during receiving. exiting thread.", e.getLocalizedMessage());
                break;
            }

            switch (nextByte) {
                case SOF:
                    int messageLength;

                    try {
                        messageLength = inputStream.read();

                    } catch (IOException e) {
                        LOG.error("Got I/O exception {} during receiving. exiting thread.", e.getLocalizedMessage());
                        break;
                    }

                    byte[] buffer = new byte[messageLength + 2];
                    buffer[0] = SOF;
                    buffer[1] = (byte)messageLength;
                    int total = 0;

                    while (total < messageLength) {
                        try {
                            int read = inputStream.read(buffer, total + 2, messageLength - total);
                            total += (read > 0 ? read : 0);
                        } catch (IOException e) {
                            LOG.error("Got I/O exception {} during receiving. exiting thread.", e.getLocalizedMessage());
                            return;
                        }
                    }

                    LOG.trace("Reading message finished");
                    LOG.debug("Receive Message = {}", bb2hex(buffer));
                    processIncomingMessage(buffer);
                    break;
                case ACK:
                case NAK:
                case CAN:
                    LOG.debug("Received a raw byte message: {}", nextByte);
                    eventBus.pushAsync(new ByteMessage(nextByte));
                    break;
                default:
                    LOG.warn(String.format("Unexpected message 0x%02X, sending NAK", nextByte));
                    sendResponse(NAK);
                    break;
            }
        }
        LOG.debug("Stopped Z-Wave receive thread");

    }
}

