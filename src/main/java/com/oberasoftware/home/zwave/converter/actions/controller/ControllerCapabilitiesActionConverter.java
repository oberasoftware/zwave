package com.oberasoftware.home.zwave.converter.actions.controller;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.controller.ControllerCapabilitiesAction;
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
public class ControllerCapabilitiesActionConverter implements ZWaveConverter {

    @EventSubscribe
    public ActionConvertedEvent convert(ControllerCapabilitiesAction source) throws HomeAutomationException {
//        return new ZWaveRawMessage(ControllerMessageType.GetCapabilities, MessageType.Request);

        return ActionConverterBuilder.create(ControllerMessageType.GetCapabilities, MessageType.Request).construct();
    }
}
