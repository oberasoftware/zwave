package com.oberasoftware.home.zwave.converter.actions.controller;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.actions.controller.GetControllerIdAction;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;

import java.util.Set;

/**
 * @author renarj
 */
public class GetControllerIdActionConverter implements ZWaveConverter<GetControllerIdAction, ZWaveRawMessage> {
    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(GetControllerIdAction.class.getSimpleName());
    }

    @Override
    public ZWaveRawMessage convert(GetControllerIdAction source) throws HomeAutomationException {
        return new ZWaveRawMessage(ControllerMessageType.MemoryGetId, MessageType.Request);
    }
}
