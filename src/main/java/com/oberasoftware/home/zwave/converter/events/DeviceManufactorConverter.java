package com.oberasoftware.home.zwave.converter.events;

import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static java.lang.Integer.toHexString;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class DeviceManufactorConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(DeviceManufactorConverter.class);

    private static final int MANUFACTURER_SPECIFIC_GET = 0x04;
    private static final int MANUFACTURER_SPECIFIC_REPORT = 0x05;

    @SupportsConversion(commandClass = CommandClass.MANUFACTURER_SPECIFIC)
    public ManufactorInfoEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        LOG.debug("Received manufacturer info for node: {}", source.getNodeId());

        int nodeId = source.getNodeId();
        byte[] payload = source.getPayload();

        int command = payload[0];

        switch (command) {
            case MANUFACTURER_SPECIFIC_REPORT:

                int manufactorId = (payload[1] << 8) | (payload[2]);
                int deviceType = (payload[3] << 8) | (payload[4]);
                int deviceId = (payload[5] << 8) | (payload[6]);

                LOG.debug("Received manufacturer: {} type: {} and deviceId: {} for node: {}", manufactorId, deviceType, deviceId, nodeId);

                return new ManufactorInfoEvent(nodeId, manufactorId, deviceType, deviceId);
            case MANUFACTURER_SPECIFIC_GET:
            default:
                LOG.warn("Received manufacturer info for node: {} unknown command: {}", nodeId, toHexString(command));
        }

        return null;
    }

}
