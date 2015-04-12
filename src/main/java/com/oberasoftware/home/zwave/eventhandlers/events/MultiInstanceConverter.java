package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.core.utils.MessageUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MultiInstanceConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(MultiInstanceConverter.class);

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.MULTI_INSTANCE)
    public ApplicationCommandEvent convert(ApplicationCommandEvent event) {
        int nodeId = event.getNodeId();
        byte[] payload = event.getPayload();

        int endpointId = payload[1];
        CommandClass commandClass = MessageUtil.getCommandClass(payload[3]);

        LOG.debug("Received multi instance command for node: {} and endpoint: {} for commandClass: {}", nodeId, endpointId, commandClass);

        return new ApplicationCommandEvent(nodeId, endpointId, commandClass, Arrays.copyOfRange(payload, 4, payload.length + 1));
    }
}
