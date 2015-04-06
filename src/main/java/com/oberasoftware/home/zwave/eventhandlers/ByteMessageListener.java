package com.oberasoftware.home.zwave.eventhandlers;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.TransactionManager;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.ByteMessage;
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
public class ByteMessageListener implements EventHandler {
    private static final Logger LOG = getLogger(ByteMessageListener.class);

    @Autowired
    private TransactionManager transactionManager;

    @EventSubscribe
    public void receive(ByteMessage byteMessage) throws HomeAutomationException {
        LOG.debug("Received a byte message: {}", byteMessage);

        int receivedByte = byteMessage.getSingleByte();
        switch (receivedByte) {
            case ACK:
                break;
            case CAN:
            case NAK:
            default:
                LOG.debug("Got a CAN/NAK or unknown response, cancelling transaction: {}", receivedByte);
                transactionManager.cancelTransaction();
        }
    }
}
