package com.oberasoftware.home.zwave.handlers;

import com.oberasoftware.home.zwave.api.events.EventListener;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.TransactionManager;
import com.oberasoftware.home.zwave.messages.ByteMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.oberasoftware.home.zwave.ZWAVE_CONSTANTS.ACK;
import static com.oberasoftware.home.zwave.ZWAVE_CONSTANTS.CAN;
import static com.oberasoftware.home.zwave.ZWAVE_CONSTANTS.NAK;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class ByteMessageListener implements EventListener<ByteMessage> {
    private static final Logger LOG = getLogger(ByteMessageListener.class);

    @Autowired
    private TransactionManager transactionManager;

    @Override
    public void receive(ByteMessage byteMessage) throws HomeAutomationException {
        LOG.debug("Received a byte message: {}", byteMessage);

        int receivedByte = byteMessage.getSingleByte();
        switch (receivedByte) {
            case ACK:
                break;
            case CAN:
            case NAK:
                LOG.debug("Got a ACK/CAN/NAK response, cancelling transaction: {}", receivedByte);
                transactionManager.cancelTransaction();
        }
    }
}
