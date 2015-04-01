package com.oberasoftware.home.zwave.converter.actions.devices;

import com.oberasoftware.home.zwave.api.actions.devices.RequestNodeInfoAction;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import org.springframework.stereotype.Component;

/**
 * @author renarj
 */
@Component
public class RequestNodeInfoConverter implements ZWaveConverter {

    @SupportsConversion
    public ZWaveRawMessage convert(RequestNodeInfoAction nodeInfoAction) throws HomeAutomationException {
        int nodeId = nodeInfoAction.getNodeId();

        ZWaveRawMessage message = new ZWaveRawMessage(nodeId,
                ControllerMessageType.RequestNodeInfo, MessageType.Request);

        byte[] newPayload = { (byte) nodeId };
        message.setMessage(newPayload);
        return message;
    }
}
