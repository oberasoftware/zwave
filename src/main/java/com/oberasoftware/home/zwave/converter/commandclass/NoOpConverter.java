package com.oberasoftware.home.zwave.converter.commandclass;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.PingEvent;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.types.CommandClass;

import java.util.Set;

/**
 * @author renarj
 */
public class NoOpConverter implements ZWaveConverter<ApplicationCommandEvent, PingEvent> {
    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(CommandClass.NO_OPERATION.getLabel());
    }

    @Override
    public PingEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        return new PingEvent(source.getNodeId());
    }
}
