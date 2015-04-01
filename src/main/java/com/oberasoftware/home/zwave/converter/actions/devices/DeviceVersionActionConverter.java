package com.oberasoftware.home.zwave.converter.actions.devices;

import com.oberasoftware.home.zwave.api.actions.devices.DeviceVersionAction;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class DeviceVersionActionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(DeviceVersionActionConverter.class);

    public static final int VERSION_COMMAND_CLASS_GET = 0x13;

    @SupportsConversion
    public ZWaveRawMessage convert(DeviceVersionAction source) throws HomeAutomationException {
        LOG.info("Sending action: {}", source);
        int nodeId = source.getNodeId();

        return new ZWaveRawMessage(nodeId, ControllerMessageType.SendData, MessageType.Request,
                new byte[] {
                        (byte)nodeId,
                        3,
                        (byte) CommandClass.VERSION.getClassCode(),
                        VERSION_COMMAND_CLASS_GET,
                        (byte)source.getCommandClass().getClassCode()
                });
    }
}
