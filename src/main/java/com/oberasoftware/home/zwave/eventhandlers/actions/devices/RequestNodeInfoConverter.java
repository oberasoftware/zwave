package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.RequestNodeInfoAction;
import com.oberasoftware.home.zwave.eventhandlers.ActionConverterBuilder;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
import org.springframework.stereotype.Component;

/**
 * @author renarj
 */
@Component
public class RequestNodeInfoConverter implements ZWaveConverter {

    @EventSubscribe
    public ActionConvertedEvent convert(RequestNodeInfoAction nodeInfoAction, int callbackId) throws HomeAutomationException {
        int nodeId = nodeInfoAction.getNodeId();

        return ActionConverterBuilder.create(ControllerMessageType.RequestNodeInfo, MessageType.Request, nodeId).construct();
    }
}
