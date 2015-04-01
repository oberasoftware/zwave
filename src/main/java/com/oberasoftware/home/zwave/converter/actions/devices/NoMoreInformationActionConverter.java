package com.oberasoftware.home.zwave.converter.actions.devices;

import com.oberasoftware.home.zwave.api.actions.devices.NoMoreInformationAction;
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
public class NoMoreInformationActionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(NoMoreInformationActionConverter.class);

    private static final int WAKE_UP_NO_MORE_INFORMATION = 0x08;

    @SupportsConversion
    public ZWaveRawMessage convert(NoMoreInformationAction source) throws HomeAutomationException {
        int nodeId = source.getNodeId();
        LOG.debug("Converting action: {} for node: {}", source, nodeId);

        return new ZWaveRawMessage(nodeId, ControllerMessageType.SendData, MessageType.Request,
                new byte[] {(byte)nodeId, 2, (byte)CommandClass.WAKE_UP.getClassCode(), WAKE_UP_NO_MORE_INFORMATION});
    }
}
