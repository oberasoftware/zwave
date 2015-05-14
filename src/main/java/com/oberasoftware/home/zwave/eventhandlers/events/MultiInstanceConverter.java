package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.Event;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.MultiInstanceDiscoveryEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.core.utils.MessageUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MultiInstanceConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(MultiInstanceConverter.class);

    private static final int MULTI_CHANNEL_ENCAP = 0x0d;
    private static final int MULTI_CHANNEL_ENDPOINT_REPORT = 0x08;

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.MULTI_INSTANCE)
    public Event convert(ApplicationCommandEvent event) {
        int nodeId = event.getNodeId();
        byte[] payload = event.getPayload();

        int command = payload[0];
        switch(command) {
            case MULTI_CHANNEL_ENCAP:
                int endpointId = payload[1];
                CommandClass commandClass = MessageUtil.getCommandClass(payload[3]);

                LOG.debug("Received multi instance command for node: {} and endpoint: {} for commandClass: {}", nodeId, endpointId, commandClass);

                return new ApplicationCommandEvent(nodeId, endpointId, commandClass, Arrays.copyOfRange(payload, 4, payload.length + 1));
            case MULTI_CHANNEL_ENDPOINT_REPORT:
                boolean endpointsAreTheSameDeviceClass = (payload[1] & 0x40) != 0;
                int endpoints = payload[2] & 0x7F;

                LOG.debug("Endpoints being reported amount: {} for node: {}", endpoints, nodeId);

                List<Integer> foundEndpoints = new ArrayList<>();
                for (int i=1; i <= endpoints; i++) {
                    LOG.debug("Endpoint reported: {} for node: {}", i, nodeId);
                    foundEndpoints.add(i);
                }

                LOG.debug("Received a multi channel endpoint report from node: {}", nodeId);
                return new MultiInstanceDiscoveryEvent(nodeId, foundEndpoints);
            default:
                LOG.debug("Command: {} not supported for multi instance command from node: {}", command, nodeId);
                return null;
        }

    }
}
