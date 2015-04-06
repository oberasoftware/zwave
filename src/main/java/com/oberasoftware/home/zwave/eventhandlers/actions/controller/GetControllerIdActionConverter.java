package com.oberasoftware.home.zwave.eventhandlers.actions.controller;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.controller.GetControllerIdAction;
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
public class GetControllerIdActionConverter implements ZWaveConverter {

    @EventSubscribe
    public ActionConvertedEvent convert(GetControllerIdAction source) throws HomeAutomationException {
        return ActionConverterBuilder.create(ControllerMessageType.MemoryGetId, MessageType.Request).construct();
    }
}
