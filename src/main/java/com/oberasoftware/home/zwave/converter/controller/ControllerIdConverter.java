package com.oberasoftware.home.zwave.converter.controller;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.events.controller.ControllerIdEvent;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;

import java.util.Set;

/**
 * @author renarj
 */
public class ControllerIdConverter implements ZWaveConverter<ZWaveRawMessage, ControllerIdEvent> {
    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(ControllerMessageType.MemoryGetId.getLabel());
    }

    @Override
    public ControllerIdEvent convert(ZWaveRawMessage source) throws HomeAutomationException {
        byte[] payload = source.getMessage();

        int homeId = ((payload[0]) << 24) |
                ((payload[1]) << 16) |
                ((payload[2]) << 8) |
                (payload[3]);
        int controllerId = payload[4];

        return new ControllerIdEvent(homeId, controllerId);
    }
}
