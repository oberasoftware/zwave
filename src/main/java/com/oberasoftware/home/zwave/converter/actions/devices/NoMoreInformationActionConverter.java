package com.oberasoftware.home.zwave.converter.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.NoMoreInformationAction;
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
public class NoMoreInformationActionConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(NoMoreInformationActionConverter.class);

    private static final int WAKE_UP_NO_MORE_INFORMATION = 0x08;

    @EventSubscribe
    public ActionConvertedEvent convert(NoMoreInformationAction source, int callbackId) throws HomeAutomationException {
        int nodeId = source.getNodeId();
        LOG.debug("Converting action: {} for node: {}", source, nodeId);

//        return new ZWaveRawMessage(nodeId, ControllerMessageType.SendData, MessageType.Request,
//                new byte[] {(byte)nodeId, 2, (byte)CommandClass.WAKE_UP.getClassCode(), WAKE_UP_NO_MORE_INFORMATION});

        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, nodeId)
                .addCommandClass(CommandClass.WAKE_UP)
                .addInt(WAKE_UP_NO_MORE_INFORMATION)
                .callback(callbackId)
                .construct();
    }
}
