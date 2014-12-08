package com.oberasoftware.home.zwave.converter.actions.controller;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.actions.controller.ControllerInitialDataAction;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;

import java.util.Set;

/**
 * @author renarj
 */
public class ControllerInitialDataActionConverter implements ZWaveConverter<ControllerInitialDataAction, ZWaveRawMessage> {
    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(ControllerInitialDataAction.class.getSimpleName());
    }

    @Override
    public ZWaveRawMessage convert(ControllerInitialDataAction source) throws HomeAutomationException {
        return new ZWaveRawMessage(ControllerMessageType.SerialApiGetInitData, MessageType.Request);
    }
}
