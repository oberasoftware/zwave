package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.GenerateCommandClassPollAction;
import com.oberasoftware.home.zwave.api.actions.devices.SwitchMultiLevelGetAction;
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
public class SwitchMultiLevelGetActionConverter implements EventHandler {
    private static final Logger LOG = getLogger(SwitchMultiLevelGetActionConverter.class);

    private static final int SWITCH_MULTILEVEL_GET = 0x02;

    @EventSubscribe
    public ActionConvertedEvent convert(SwitchMultiLevelGetAction action) {
        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, action.getNodeId())
                .addCommandClass(CommandClass.SWITCH_MULTILEVEL)
                .addInt(SWITCH_MULTILEVEL_GET)
                .construct();
    }

    @SupportsConversion(commandClass = CommandClass.SWITCH_MULTILEVEL)
    @EventSubscribe
    public SwitchMultiLevelGetAction generate(GenerateCommandClassPollAction action) {
        LOG.info("Generating switch level get action for node: {}", action.getNodeId());
        return new SwitchMultiLevelGetAction(action.getNodeId());
    }
}
