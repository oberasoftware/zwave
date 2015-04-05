package com.oberasoftware.home.zwave.converter.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.DeviceManufactorAction;
import com.oberasoftware.home.zwave.converter.ActionConverterBuilder;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import com.oberasoftware.home.zwave.threading.ActionConvertedEvent;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class DeviceManufactorActionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(DeviceManufactorActionConverter.class);

    private static final int MANUFACTURER_SPECIFIC_GET = 0x04;

    @EventSubscribe
    public ActionConvertedEvent convert(DeviceManufactorAction source, int callbackId) throws HomeAutomationException {
        LOG.debug("Request manufacturer device information for node: {}", source.getNodeId());
        int nodeId = source.getNodeId();

//        return new ZWaveRawMessage(nodeId, ControllerMessageType.SendData, MessageType.Request,
//                new byte[] {(byte) nodeId,
//                        2,
//                        (byte)CommandClass.MANUFACTURER_SPECIFIC.getClassCode(),
//                        MANUFACTURER_SPECIFIC_GET
//                });

        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, nodeId)
                .addCommandClass(CommandClass.MANUFACTURER_SPECIFIC)
                .addInt(MANUFACTURER_SPECIFIC_GET)
                .callback(callbackId)
                .construct();
    }
}
