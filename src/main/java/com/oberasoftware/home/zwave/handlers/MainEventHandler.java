package com.oberasoftware.home.zwave.handlers;

import com.oberasoftware.base.event.EventBus;
import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.TransactionManager;
import com.oberasoftware.home.zwave.api.events.controller.ControllerEvent;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.threading.MessageReceivedEvent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MainEventHandler implements EventHandler {
    private static final Logger LOG = getLogger(MainEventHandler.class);

    @Autowired
    private TransactionManager transactionManager;

    @Autowired
    private ConversionManager<ZWaveRawMessage, ControllerEvent> conversionManager;

    @Autowired
    private EventBus eventBus;

    @EventSubscribe
    public void receive(MessageReceivedEvent receivedMessage) {
        ZWaveRawMessage zWaveRawMessage = receivedMessage.getReceivedMessage();
        LOG.debug("Received a raw message: {}", zWaveRawMessage);

        try {
            ControllerEvent event = conversionManager.convert(r -> r.getControllerMessageType().getLabel(), zWaveRawMessage);
            LOG.debug("Event received: {}", event);

            if(event != null && event.isTransactionCompleted()) {
                transactionManager.completeTransaction(event);
            }

            if(event != null) {
                eventBus.push(event);
            } else {
                LOG.error("Could not convert incoming message: {}", zWaveRawMessage);
            }
        } catch (HomeAutomationException e) {
            LOG.error("", e);
        }
    }
}
