package com.oberasoftware.home.zwave.converter.commandclass;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.BasicEvent;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.slf4j.Logger;

import java.util.Set;

import static java.lang.Integer.toHexString;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
public class BasicConverter implements ZWaveConverter<ApplicationCommandEvent, BasicEvent> {
    private static final Logger LOG = getLogger(BasicConverter.class);

    private static final int BASIC_SET = 0x01;
    private static final int BASIC_GET = 0x02;
    private static final int BASIC_REPORT = 0x03;

    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(CommandClass.BASIC.getLabel());
    }

    @Override
    public BasicEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        LOG.debug("Basic command received for node: {}", source.getNodeId());
        byte[] payload = source.getPayload();

        int command = payload[0];
        switch (command) {
            case BASIC_SET:
            case BASIC_REPORT:
                int value = payload[1];

                return new BasicEvent(source.getNodeId(), value);
            case BASIC_GET:
            default:
                LOG.debug("Basic command: {} not implemented from node: {}", toHexString(command), source.getNodeId());
        }

        return null;
    }
}
