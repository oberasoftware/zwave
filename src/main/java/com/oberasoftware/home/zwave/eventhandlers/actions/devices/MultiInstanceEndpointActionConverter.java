package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.api.actions.devices.MultiInstanceEndpointAction;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.eventhandlers.ActionConverterBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MultiInstanceEndpointActionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(MultiInstanceEndpointActionConverter.class);

    private static final int MULTI_INSTANCE_GET = 0x04;
    private static final int MULTI_CHANNEL_ENDPOINT_GET = 0x07;

    @Autowired
    private NodeManager nodeManager;

    @EventSubscribe
    public ActionConvertedEvent convert(MultiInstanceEndpointAction action, int callbackId) {
        LOG.debug("Converting multi instance endpoint action: {}", action);

        int nodeId = action.getNodeId();

        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, nodeId)
                .addCommandClass(CommandClass.MULTI_INSTANCE)
                .addInt(MULTI_CHANNEL_ENDPOINT_GET)
                .callback(callbackId)
                .construct();
    }
}
