package com.oberasoftware.home.zwave.eventhandlers;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.MultiInstanceEndpointAction;
import com.oberasoftware.home.zwave.api.events.devices.NodeUpdatedEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MultiInstanceHandler implements EventHandler {
    private static final Logger LOG = getLogger(MultiInstanceHandler.class);

    @EventSubscribe
    public MultiInstanceEndpointAction receive(NodeUpdatedEvent nodeUpdatedEvent) {
        List<CommandClass> commandClasses = nodeUpdatedEvent.getNode().getCommandClasses();
        boolean isMultiInstance = commandClasses.stream().anyMatch(c -> c == CommandClass.MULTI_INSTANCE);

        LOG.debug("Node updated: {}, checking for multi instance support: {}", nodeUpdatedEvent.getNodeId(), isMultiInstance);
        if(isMultiInstance) {
            return new MultiInstanceEndpointAction(nodeUpdatedEvent.getNodeId());
        }
        return null;
    }
}
