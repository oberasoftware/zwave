package com.oberasoftware.home.zwave.converter.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.IdentifyNodeAction;
import com.oberasoftware.home.zwave.converter.ActionConverterBuilder;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import com.oberasoftware.home.zwave.threading.ActionConvertedEvent;
import org.springframework.stereotype.Component;

/**
 * @author renarj
 */
@Component
public class IdentifyNodeActionConverter implements ZWaveConverter {

    @EventSubscribe
    public ActionConvertedEvent convert(IdentifyNodeAction source) throws HomeAutomationException {
//        return new ZWaveRawMessage(source.getNodeId(), ControllerMessageType.IdentifyNode, MessageType.Request, new byte[] {(byte)source.getNodeId()});

        return ActionConverterBuilder.create(ControllerMessageType.IdentifyNode, MessageType.Request, source.getNodeId()).construct();
    }
}
