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
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class IdentifyNodeConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(IdentifyNodeConverter.class);

    @SupportsConversion(controllerMessage = ControllerMessageType.IdentifyNode)
    @EventSubscribe
    public NodeIdentifyEvent convert(ZWaveRawMessage source) throws HomeAutomationException {
        LOG.debug("Handling incoming node information event: {}", source);

        byte[] message = source.getMessage();

        boolean listening = (message[0] & 0x80) != 0;
        boolean routing = (message[0] & 0x40) != 0;
        int version = (message[0] & 0x07) + 1;
        boolean frequentlyListening = (message[1] & 0x60) != 0;

        BasicDeviceClass basicDeviceClass = BasicDeviceClass.getType(message[3]).get();
        GenericDeviceClass genericDeviceClass = GenericDeviceClass.getType(message[4]).get();
        Optional<SpecificDeviceClass> optionalSpecificClass = SpecificDeviceClass.getType(genericDeviceClass, message[5]);

        LOG.info("Basic device class: {}", basicDeviceClass);
        LOG.info("Generic device class: {}", genericDeviceClass);
        LOG.info("Specific device class: {}", optionalSpecificClass);

        SpecificDeviceClass specificDeviceClass = optionalSpecificClass.isPresent() ? optionalSpecificClass.get() : null;

        return new NodeIdentifyEvent(listening, frequentlyListening, routing, version, basicDeviceClass, genericDeviceClass, specificDeviceClass);
    }
}
