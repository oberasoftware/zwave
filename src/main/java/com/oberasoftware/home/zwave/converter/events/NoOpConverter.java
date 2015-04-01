package com.oberasoftware.home.zwave.converter.events;

import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.PingEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.springframework.stereotype.Component;

/**
 * @author renarj
 */
@Component
public class NoOpConverter implements ZWaveConverter {

    @SupportsConversion(commandClass = CommandClass.NO_OPERATION)
    public PingEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        return new PingEvent(source.getNodeId());
    }
}
