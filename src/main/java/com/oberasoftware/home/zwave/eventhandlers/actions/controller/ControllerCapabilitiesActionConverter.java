package com.oberasoftware.home.zwave.eventhandlers.actions.controller;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.controller.ControllerCapabilitiesAction;
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
public class ControllerCapabilitiesActionConverter implements ZWaveConverter {

    @EventSubscribe
    public ActionConvertedEvent convert(ControllerCapabilitiesAction source) throws HomeAutomationException {
        return ActionConverterBuilder.create(ControllerMessageType.GetCapabilities, MessageType.Request).construct();
    }
}
