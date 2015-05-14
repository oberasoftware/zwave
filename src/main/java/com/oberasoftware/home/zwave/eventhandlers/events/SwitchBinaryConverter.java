package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.DeviceEvent;
import com.oberasoftware.home.zwave.api.events.devices.SwitchEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class SwitchBinaryConverter implements EventHandler {
    private static final Logger LOG = getLogger(SwitchMultiLevelReportConverter.class);

    private static final int SWITCH_BINARY_SET = 0x01;
    private static final int SWITCH_BINARY_GET = 0x02;
    private static final int SWITCH_BINARY_REPORT = 0x03;

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.SWITCH_MULTILEVEL)
    public DeviceEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        LOG.debug("Received binary switch report from node: {}", source.getNodeId());
        byte[] payload = source.getPayload();
        int command = payload[0];
        switch (command) {
            case SWITCH_BINARY_SET:
            case SWITCH_BINARY_REPORT:
                int value = payload[1];

                LOG.debug("Processing binary switch report for node: {} found value: {}", source.getNodeId(), value);

                boolean state = value > 0;
                return new SwitchEvent(source.getNodeId(), source.getEndpointId(), state);
            case SWITCH_BINARY_GET:
            default:
                LOG.debug("Command: {} not implemented from node: {}", command, source.getNodeId());
        }

        return null;
    }

}
