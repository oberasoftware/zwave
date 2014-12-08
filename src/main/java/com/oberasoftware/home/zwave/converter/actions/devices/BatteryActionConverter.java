package com.oberasoftware.home.zwave.converter.actions.devices;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.actions.devices.BatteryGetAction;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.CommandClass;

import java.util.Set;

import static com.oberasoftware.home.zwave.messages.types.ControllerMessageType.SendData;
import static com.oberasoftware.home.zwave.messages.types.MessageType.Request;

/**
 * @author renarj
 */
public class BatteryActionConverter implements ZWaveConverter<BatteryGetAction, ZWaveRawMessage> {
    private static final int BATTERY_GET = 0x02;

    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(BatteryGetAction.class.getSimpleName());
    }

    @Override
    public ZWaveRawMessage convert(BatteryGetAction source) throws HomeAutomationException {
        int nodeId = source.getNodeId();

        return new ZWaveRawMessage(nodeId, SendData, Request, new byte[] {
                (byte)nodeId,
                2,
                (byte)CommandClass.BATTERY.getClassCode(),
                BATTERY_GET
        });
    }
}
