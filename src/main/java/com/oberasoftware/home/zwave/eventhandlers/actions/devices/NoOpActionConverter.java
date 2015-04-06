package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.NodeNoOpAction;
import com.oberasoftware.home.zwave.eventhandlers.ActionConverterBuilder;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
import org.springframework.stereotype.Component;

/**
 * @author renarj
 */
@Component
public class NoOpActionConverter implements ZWaveConverter {

    @EventSubscribe
    public ActionConvertedEvent convert(NodeNoOpAction source, int callbackId) throws HomeAutomationException {
        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, source.getNodeId())
                .addCommandClass(CommandClass.NO_OPERATION)
                .callback(callbackId)
                .construct();
    }
}
