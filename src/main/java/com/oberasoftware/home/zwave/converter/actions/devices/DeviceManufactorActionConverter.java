package com.oberasoftware.home.zwave.converter.actions.devices;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.actions.devices.DeviceManufactorAction;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import org.slf4j.Logger;

import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
public class DeviceManufactorActionConverter implements ZWaveConverter<DeviceManufactorAction, ZWaveRawMessage> {
    private static final Logger LOG = getLogger(DeviceManufactorActionConverter.class);

    private static final int MANUFACTURER_SPECIFIC_GET = 0x04;

    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(DeviceManufactorAction.class.getSimpleName());
    }

    @Override
    public ZWaveRawMessage convert(DeviceManufactorAction source) throws HomeAutomationException {
        LOG.debug("Request manufacturer device information for node: {}", source.getNodeId());
        int nodeId = source.getNodeId();

        return new ZWaveRawMessage(nodeId, ControllerMessageType.SendData, MessageType.Request,
                new byte[] {(byte) nodeId,
                        2,
                        (byte)CommandClass.MANUFACTURER_SPECIFIC.getClassCode(),
                        MANUFACTURER_SPECIFIC_GET
                });
    }
}
