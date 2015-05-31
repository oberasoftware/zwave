package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.BatteryGetAction;
import com.oberasoftware.home.zwave.api.actions.devices.GenerateCommandClassPollAction;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.eventhandlers.ActionConverterBuilder;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType.SendData;
import static com.oberasoftware.home.zwave.api.messages.types.MessageType.Request;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class BatteryActionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(BatteryActionConverter.class);

    private static final int BATTERY_GET = 0x02;

    @EventSubscribe
    public ActionConvertedEvent convert(BatteryGetAction source) throws HomeAutomationException {
        LOG.debug("Converting battery action: {}", source);
        int nodeId = source.getNodeId();

        return ActionConverterBuilder.create(SendData, Request, nodeId)
                .addCommandClass(CommandClass.BATTERY)
                .addInt(BATTERY_GET)
        .construct();
    }

    @SupportsConversion(commandClass = CommandClass.BATTERY)
    @EventSubscribe
    public BatteryGetAction generate(GenerateCommandClassPollAction action) {
        return new BatteryGetAction(action.getNodeId());
    }
}
