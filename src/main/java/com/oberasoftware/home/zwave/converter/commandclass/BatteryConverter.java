package com.oberasoftware.home.zwave.converter.commandclass;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.BatteryEvent;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.slf4j.Logger;

import java.util.Set;

import static java.lang.Integer.toHexString;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
public class BatteryConverter implements ZWaveConverter<ApplicationCommandEvent, BatteryEvent> {
    private static final Logger LOG = getLogger(BatteryConverter.class);

    private static final int BATTERY_GET = 0x02;
    private static final int BATTERY_REPORT = 0x03;

    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(CommandClass.BATTERY.getLabel());
    }

    @Override
    public BatteryEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        LOG.debug("Received battery information from node: {}", source.getNodeId());
        byte[] payload = source.getPayload();

        int command = payload[0];
        switch (command) {
            case BATTERY_REPORT:
                int batteryLevel = payload[1];

                LOG.debug("Node: {} battery level report: {}", source.getNodeId(), batteryLevel);
                return new BatteryEvent(source.getNodeId(), batteryLevel);
            case BATTERY_GET:
            default:
                LOG.warn("Battery command: {} not implemented reported by node: {}", toHexString(command), source.getNodeId());
        }

        return null;
    }
}
