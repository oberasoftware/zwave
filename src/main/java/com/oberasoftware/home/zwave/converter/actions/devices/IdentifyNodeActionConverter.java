package com.oberasoftware.home.zwave.converter.actions.devices;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.actions.devices.IdentifyNodeAction;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;

import java.util.Set;

/**
 * @author renarj
 */
public class IdentifyNodeActionConverter implements ZWaveConverter<IdentifyNodeAction, ZWaveRawMessage> {
    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(IdentifyNodeAction.class.getSimpleName());
    }

    @Override
    public ZWaveRawMessage convert(IdentifyNodeAction source) throws HomeAutomationException {
        return new ZWaveRawMessage(source.getNodeId(), ControllerMessageType.IdentifyNode, MessageType.Request, new byte[] {(byte)source.getNodeId()});
    }
}
