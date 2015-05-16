package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.DeviceEvent;
import com.oberasoftware.home.zwave.api.events.devices.SwitchLevelEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class SwitchMultiLevelReportConverter implements EventHandler {
    private static final Logger LOG = getLogger(SwitchMultiLevelReportConverter.class);

    private static final int SWITCH_MULTILEVEL_SET = 0x01;
    private static final int SWITCH_MULTILEVEL_GET = 0x02;
    private static final int SWITCH_MULTILEVEL_REPORT = 0x03;
    private static final int SWITCH_MULTILEVEL_START_LEVEL_CHANGE = 0x04;
    private static final int SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE = 0x05;
    private static final int SWITCH_MULTILEVEL_SUPPORTED_GET = 0x06;
    private static final int SWITCH_MULTILEVEL_SUPPORTED_REPORT = 0x07;

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.SWITCH_MULTILEVEL)
    public DeviceEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        LOG.debug("Received multi level switch report from node: {}", source.getNodeId());
        byte[] payload = source.getPayload();
        int command = payload[0];
        switch (command) {
            case SWITCH_MULTILEVEL_REPORT:
                int value = payload[1];

                LOG.debug("Processing multi level report for node: {} found value: {}", source.getNodeId(), value);

                return new SwitchLevelEvent(source.getNodeId(), value);
            case SWITCH_MULTILEVEL_SET:
            case SWITCH_MULTILEVEL_GET:
            case SWITCH_MULTILEVEL_SUPPORTED_GET:
            case SWITCH_MULTILEVEL_SUPPORTED_REPORT:
            case SWITCH_MULTILEVEL_START_LEVEL_CHANGE:
            case SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE:
            default:
                LOG.debug("Command: {} not implemented from node: {}", command, source.getNodeId());
        }

        return null;
    }
}
