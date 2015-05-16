package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.SwitchBinaryGetAction;
import com.oberasoftware.home.zwave.api.actions.devices.GenerateCommandClassPollAction;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.eventhandlers.ActionConverterBuilder;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class SwitchBinaryActionGetConverter implements EventHandler {
    private static final Logger LOG = getLogger(SwitchBinaryActionGetConverter.class);

    private static final int SWITCH_BINARY_GET = 0x02;

    @EventSubscribe
    public ActionConvertedEvent convert(SwitchBinaryGetAction action) {
        LOG.debug("Generating serial message for binary switch GET for node: {} and endpoint: {}", action.getNodeId(), action.getEndpointId());
        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, action)
                .addCommandClass(CommandClass.SWITCH_BINARY)
                .addInt(SWITCH_BINARY_GET)
                .construct();
    }

    @SupportsConversion(commandClass = CommandClass.SWITCH_BINARY)
    @EventSubscribe
    public SwitchBinaryGetAction generate(GenerateCommandClassPollAction action) {
        LOG.info("Generating switch get action for node: {} endpoint: {}", action.getNodeId(), action.getEndpointId());
        return new SwitchBinaryGetAction(action.getNodeId(), action.getEndpointId());
    }
}
