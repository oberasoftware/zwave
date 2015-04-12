package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.api.actions.devices.MeterGetAction;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.api.messages.types.MeterScale;
import com.oberasoftware.home.zwave.eventhandlers.ActionConverterBuilder;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MeterGetActionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(MeterGetActionConverter.class);

    private static final int METER_GET = 0x01;

    @EventSubscribe
    public ActionConvertedEvent convert(MeterGetAction action, int callbackId) {

        ActionConverterBuilder builder = ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, action)
                .addCommandClass(CommandClass.METER)
                .addInt(METER_GET);

        Optional<MeterScale> scaleOptional = action.getScale();
        if(scaleOptional.isPresent()) {
            LOG.debug("Scale configured: {} for node: {}", scaleOptional.get(), action.getNodeId());
            int scale = scaleOptional.get().getScale() << 3;
            builder.addInt(scale);
        }

        return builder.callback(callbackId).construct();
    }
}
