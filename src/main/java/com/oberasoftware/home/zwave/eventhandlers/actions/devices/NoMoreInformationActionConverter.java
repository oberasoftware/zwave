package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.actions.devices.NoMoreInformationAction;
import com.oberasoftware.home.zwave.eventhandlers.ActionConverterBuilder;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
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

        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, nodeId)
                .addCommandClass(CommandClass.WAKE_UP)
                .addInt(WAKE_UP_NO_MORE_INFORMATION)
                .callback(callbackId)
                .construct();
    }
}
