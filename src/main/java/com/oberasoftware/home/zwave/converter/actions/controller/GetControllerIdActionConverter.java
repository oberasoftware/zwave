package com.oberasoftware.home.zwave.converter.actions.controller;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.controller.GetControllerIdAction;
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
public class GetControllerIdActionConverter implements ZWaveConverter {

    @EventSubscribe
    public ActionConvertedEvent convert(GetControllerIdAction source) throws HomeAutomationException {
//        return new ZWaveRawMessage(ControllerMessageType.MemoryGetId, MessageType.Request);

        return ActionConverterBuilder.create(ControllerMessageType.MemoryGetId, MessageType.Request).construct();
    }
}
