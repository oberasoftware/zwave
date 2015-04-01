package com.oberasoftware.home.zwave.converter.actions.controller;

import com.oberasoftware.home.zwave.api.actions.controller.GetControllerIdAction;
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
public class GetControllerIdActionConverter implements ZWaveConverter {

    @SupportsConversion
    public ZWaveRawMessage convert(GetControllerIdAction source) throws HomeAutomationException {
        return new ZWaveRawMessage(ControllerMessageType.MemoryGetId, MessageType.Request);
    }
}
