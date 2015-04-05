package com.oberasoftware.home.zwave.converter.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.BatteryGetAction;
import com.oberasoftware.home.zwave.converter.ActionConverterBuilder;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.threading.ActionConvertedEvent;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static com.oberasoftware.home.zwave.messages.types.ControllerMessageType.SendData;
import static com.oberasoftware.home.zwave.messages.types.MessageType.Request;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class BatteryActionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(BatteryActionConverter.class);

    private static final int BATTERY_GET = 0x02;

    @EventSubscribe
    public ActionConvertedEvent convert(BatteryGetAction source, int callbackId) throws HomeAutomationException {
        LOG.debug("Converting battery action: {}", source);
        int nodeId = source.getNodeId();

        return ActionConverterBuilder.create(SendData, Request, nodeId)
                .addCommandClass(CommandClass.BATTERY)
                .addInt(BATTERY_GET)
                .callback(callbackId)
        .construct();

//        return new ZWaveRawMessage(nodeId, SendData, Request, new byte[] {
//                (byte)nodeId,
//                2,
//                (byte)CommandClass.BATTERY.getClassCode(),
//                BATTERY_GET
//        }, callbackId);
    }
}
