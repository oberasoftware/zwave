package com.oberasoftware.home.zwave.converter.controller;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.events.controller.NodeInformationEvent;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import org.slf4j.Logger;

import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
public class IdentifyNodeConverter implements ZWaveConverter<ZWaveRawMessage, NodeInformationEvent> {
    private static final Logger LOG = getLogger(IdentifyNodeConverter.class);

    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(ControllerMessageType.IdentifyNode.getLabel());
    }

    @Override
    public NodeInformationEvent convert(ZWaveRawMessage source) throws HomeAutomationException {
        LOG.debug("Handling incoming node information event: {}", source);

        byte[] message = source.getMessage();

        boolean listening = (message[0] & 0x80) != 0;
        boolean routing = (message[0] & 0x40) != 0;
        int version = (message[0] & 0x07) + 1;
        boolean frequentlyListening = (message[1] & 0x60) != 0;

//        ZWaveDeviceClass.Basic basic = ZWaveDeviceClass.Basic.getBasic(incomingMessage.getMessagePayloadByte(3));
//        if (basic == null) {
//            LOG.error(String.format("NODE %d: Basic device class 0x%02x not found", nodeId, incomingMessage.getMessagePayloadByte(3)));
//            return false;
//        }
//        LOG.debug(String.format("NODE %d: Basic = %s 0x%02x", nodeId, basic.getLabel(), basic.getKey()));
//
//        ZWaveDeviceClass.Generic generic = ZWaveDeviceClass.Generic.getGeneric(incomingMessage.getMessagePayloadByte(4));
//        if (generic == null) {
//            LOG.error(String.format("NODE %d: Generic device class 0x%02x not found", nodeId, incomingMessage.getMessagePayloadByte(4)));
//            return false;
//        }
//        LOG.debug(String.format("NODE %d: Generic = %s 0x%02x", nodeId, generic.getLabel(), generic.getKey()));
//
//        ZWaveDeviceClass.Specific specific = ZWaveDeviceClass.Specific.getSpecific(generic, incomingMessage.getMessagePayloadByte(5));
//        if (specific == null) {
//            LOG.error(String.format("NODE %d: Specific device class 0x%02x not found", nodeId, incomingMessage.getMessagePayloadByte(5)));
//            return false;
//        }
//        LOG.debug(String.format("NODE %d: Specific = %s 0x%02x", nodeId, specific.getLabel(), specific.getKey()));
//
//        ZWaveDeviceClass deviceClass = node.getDeviceClass();
//        deviceClass.setBasicDeviceClass(basic);
//        deviceClass.setGenericDeviceClass(generic);
//        deviceClass.setSpecificDeviceClass(specific);
//
//        // advance node stage of the current node.
//        node.advanceNodeStage(NodeStage.PING);

        return new NodeInformationEvent(listening, frequentlyListening, routing, version);
    }
}
