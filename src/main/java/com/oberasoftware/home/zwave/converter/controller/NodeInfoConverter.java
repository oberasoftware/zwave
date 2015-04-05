package com.oberasoftware.home.zwave.converter.controller;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.NodeInfoRequestEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class NodeInfoConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(NodeInfoConverter.class);

    @SupportsConversion(controllerMessage = ControllerMessageType.RequestNodeInfo)
    @EventSubscribe
    public NodeInfoRequestEvent convert(ZWaveRawMessage source) throws HomeAutomationException {
        LOG.debug("Received confirmation on requesting node information");
        return new NodeInfoRequestEvent();
    }
}
