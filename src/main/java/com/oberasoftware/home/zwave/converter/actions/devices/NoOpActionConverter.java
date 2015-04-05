package com.oberasoftware.home.zwave.converter.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.NodeNoOpAction;
import com.oberasoftware.home.zwave.converter.ActionConverterBuilder;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import com.oberasoftware.home.zwave.threading.ActionConvertedEvent;
import org.springframework.stereotype.Component;

/**
 * @author renarj
 */
@Component
public class NoOpActionConverter implements ZWaveConverter {

    @EventSubscribe
    public ActionConvertedEvent convert(NodeNoOpAction source, int callbackId) throws HomeAutomationException {
//        return new ZWaveRawMessage(source.getNodeId(), ControllerMessageType.SendData,
//                MessageType.Request, new byte[] {(byte)source.getNodeId(), 1, (byte)CommandClass.NO_OPERATION.getClassCode()});

        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, source.getNodeId())
                .addCommandClass(CommandClass.NO_OPERATION)
                .callback(callbackId)
                .construct();
    }
}
