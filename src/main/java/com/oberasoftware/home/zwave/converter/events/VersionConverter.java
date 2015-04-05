package com.oberasoftware.home.zwave.converter.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.VersionEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.core.utils.MessageUtil;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class VersionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(VersionConverter.class);

    private static final int VERSION_GET = 0x11;
    private static final int VERSION_REPORT = 0x12;
    private static final int VERSION_COMMAND_CLASS_GET = 0x13;
    private static final int VERSION_COMMAND_CLASS_REPORT = 0x14;

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.VERSION)
    public VersionEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        int nodeId = source.getNodeId();
        LOG.debug("NODE {}: Received Version Request", nodeId);

        byte[] payload = source.getPayload();
        int command = payload[0];
        switch (command) {
            case VERSION_REPORT:
                LOG.debug("Process Version Report");

                //TODO: Support
                break;
            case VERSION_COMMAND_CLASS_REPORT:
                LOG.debug("Process Version Command Class Report");
                int commandClassCode = payload[1];
                int commandClassVersion = payload[2];

                CommandClass commandClass = MessageUtil.getCommandClass(commandClassCode);

                LOG.debug("Node: {} requested command class: {} version: {}", nodeId, commandClass, commandClassVersion);
                break;
            case VERSION_GET:
            case VERSION_COMMAND_CLASS_GET:
            default:
                LOG.warn("Unsupported command received: {} for node: {}", command, nodeId);
        }

        return null;
    }
}
