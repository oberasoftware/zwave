package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.PingEvent;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import org.springframework.stereotype.Component;

/**
 * @author renarj
 */
@Component
public class NoOpConverter implements ZWaveConverter {

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.NO_OPERATION)
    public PingEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        return new PingEvent(source.getNodeId());
    }
}
