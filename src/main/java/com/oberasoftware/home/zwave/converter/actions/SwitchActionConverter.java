package com.oberasoftware.home.zwave.converter.actions;

import com.oberasoftware.home.zwave.ZWAVE_CONSTANTS;
import com.oberasoftware.home.zwave.api.actions.SwitchAction;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.springframework.stereotype.Component;

import static com.oberasoftware.home.zwave.messages.types.ControllerMessageType.SendData;
import static com.oberasoftware.home.zwave.messages.types.MessageType.Request;

/**
 * @author renarj
 */
@Component
public class SwitchActionConverter implements ZWaveConverter {

    private static final int SWITCH_BINARY = 0x25;
    private static final int SWITCH_MULTILEVEL_SET = 0x01;

    @SupportsConversion
    public ZWaveRawMessage convert(SwitchAction switchAction) throws HomeAutomationException {
        int nodeId = switchAction.getDevice().getNodeId();
        int level = switchAction.getLevel();

        if(level >0 && level < SwitchAction.MAX_LEVEL) {
            //we will set a dimmer level

            return new ZWaveRawMessage(nodeId, SendData, Request, new byte[] {
                    (byte) nodeId,
                    3,
                    (byte) CommandClass.SWITCH_MULTILEVEL.getClassCode(),
                    SWITCH_MULTILEVEL_SET,
                    (byte) level
            });
        } else {
            int command = switchAction.getDesiredState() == SwitchAction.STATE.ON ? 0xFF : 0x00;

            return new ZWaveRawMessage(nodeId, SendData, Request, new byte[] {
                    (byte) nodeId,
                    3, //message length
                    (byte) SWITCH_BINARY,
                    (byte) ZWAVE_CONSTANTS.SWITCH_BINARY_SET,
                    (byte) command
            });
        }
    }
}
