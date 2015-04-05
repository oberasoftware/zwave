package com.oberasoftware.home.zwave.converter.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.BinarySensorEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class BinarySensorConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(BinarySensorConverter.class);

    private static final int SENSOR_BINARY_REPORT = 0x03;

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.SENSOR_BINARY)
    public BinarySensorEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        LOG.debug("Node: {} received a binary sensor request: {}", source.getNodeId(), source);

        byte[] payload = source.getPayload();
        int command = payload[0];

        switch (command) {
            case SENSOR_BINARY_REPORT:
                LOG.trace("Process Sensor Binary Report");
                int value = payload[1];

                LOG.debug("Node: {} got a binary sensor report: {}", source.getNodeId(), value);

                return new BinarySensorEvent(source.getNodeId(), value != 0x00);
            default:
                LOG.debug("Node: {} send an unsupported command: {}", source.getNodeId(), command);
        }
        return null;
    }
}
