package com.oberasoftware.home.zwave.eventhandlers.controller;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.api.messages.types.BasicDeviceClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.GenericDeviceClass;
import com.oberasoftware.home.zwave.api.messages.types.SpecificDeviceClass;
import com.oberasoftware.home.zwave.threading.SenderThread;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class IdentifyNodeConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(IdentifyNodeConverter.class);

    @Autowired
    private SenderThread senderThread;

    @SupportsConversion(controllerMessage = ControllerMessageType.IdentifyNode)
    @EventSubscribe
    public NodeIdentifyEvent convert(ZWaveRawMessage source) throws HomeAutomationException {
        LOG.debug("Handling incoming node information event: {}", source);

        ZWaveRawMessage lastSentMessage = senderThread.getLastMessage();
        int nodeId = lastSentMessage.getMessageByte(0);
        LOG.debug("Received node information from node: {} based on last message: {}", nodeId, lastSentMessage);

        byte[] message = source.getMessage();

        boolean listening = (message[0] & 0x80) != 0;
        boolean routing = (message[0] & 0x40) != 0;
        int version = (message[0] & 0x07) + 1;
        boolean frequentlyListening = (message[1] & 0x60) != 0;

        BasicDeviceClass basicDeviceClass = BasicDeviceClass.getType(message[3]).get();
        GenericDeviceClass genericDeviceClass = GenericDeviceClass.getType(message[4]).get();
        Optional<SpecificDeviceClass> optionalSpecificClass = SpecificDeviceClass.getType(genericDeviceClass, message[5]);

        LOG.debug("Basic device class: {}", basicDeviceClass);
        LOG.debug("Generic device class: {}", genericDeviceClass);
        LOG.debug("Specific device class: {}", optionalSpecificClass);

        SpecificDeviceClass specificDeviceClass = optionalSpecificClass.isPresent() ? optionalSpecificClass.get() : null;

        return new NodeIdentifyEvent(nodeId, listening, frequentlyListening, routing, version, basicDeviceClass, genericDeviceClass, specificDeviceClass);
    }
}
