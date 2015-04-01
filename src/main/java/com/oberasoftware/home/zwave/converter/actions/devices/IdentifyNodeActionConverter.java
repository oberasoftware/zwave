package com.oberasoftware.home.zwave.converter.actions.devices;

import com.oberasoftware.home.zwave.api.actions.devices.IdentifyNodeAction;
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
public class IdentifyNodeActionConverter implements ZWaveConverter {
    @SupportsConversion
    public ZWaveRawMessage convert(IdentifyNodeAction source) throws HomeAutomationException {
        return new ZWaveRawMessage(source.getNodeId(), ControllerMessageType.IdentifyNode, MessageType.Request, new byte[] {(byte)source.getNodeId()});
    }
}
