package com.oberasoftware.home.zwave.converter.actions.devices;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.actions.devices.RequestNodeInfoAction;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;

import java.util.Set;

/**
 * @author renarj
 */
public class RequestNodeInfoConverter implements ZWaveConverter<RequestNodeInfoAction, ZWaveRawMessage> {
    @Override
    public ZWaveRawMessage convert(RequestNodeInfoAction nodeInfoAction) throws HomeAutomationException {
        int nodeId = nodeInfoAction.getNodeId();

        ZWaveRawMessage message = new ZWaveRawMessage(nodeId,
                ControllerMessageType.RequestNodeInfo, MessageType.Request);

        byte[] newPayload = { (byte) nodeId };
        message.setMessage(newPayload);
        return message;
    }

    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(RequestNodeInfoAction.class.getSimpleName());
    }


}
