package com.oberasoftware.home.zwave.handlers;

import com.oberasoftware.home.zwave.api.events.EventListener;
import com.oberasoftware.home.zwave.api.events.EventBus;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.TransactionManager;
import com.oberasoftware.home.zwave.api.events.controller.ControllerEvent;
import com.oberasoftware.home.zwave.converter.ConverterHandler;
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
public class MainEventHandler implements EventListener<MessageReceivedEvent> {
    private static final Logger LOG = getLogger(MainEventHandler.class);

    @Autowired
    private TransactionManager transactionManager;

    @Autowired
    private ConverterHandler<ZWaveRawMessage, ControllerEvent> converterHandler;

    @Autowired
    private EventBus eventBus;

    @Override
    public void receive(MessageReceivedEvent receivedMessage) {
        ZWaveRawMessage zWaveRawMessage = receivedMessage.getReceivedMessage();
        LOG.debug("Received a raw message: {}", zWaveRawMessage);

        try {
            ControllerEvent event = converterHandler.convert(r -> r.getControllerMessageType().getLabel(), zWaveRawMessage);
            LOG.debug("Event received: {}", event);

            if(event != null && event.isTransactionCompleted()) {
                transactionManager.completeTransaction(event);
            }

            eventBus.push(event);
        } catch (HomeAutomationException e) {
            LOG.error("", e);
        }
    }
}
