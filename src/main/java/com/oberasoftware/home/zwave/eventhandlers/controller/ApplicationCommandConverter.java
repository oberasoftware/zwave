package com.oberasoftware.home.zwave.eventhandlers.controller;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.core.utils.MessageUtil;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import org.springframework.stereotype.Component;

import static java.util.Arrays.copyOfRange;

/**
 * @author renarj
 */
@Component
public class ApplicationCommandConverter implements ZWaveConverter {

    private static final int DEFAULT_ENDPOINT_ID = 1;
    private static final int MESSAGE_OFFSET = 4;
    private static final int NODE_ID_INDEX = 1;
    private static final int COMMAND_CLASS_INDEX = 3;

    @EventSubscribe
    @SupportsConversion(controllerMessage=ControllerMessageType.ApplicationCommandHandler)
    public ApplicationCommandEvent convert(ZWaveRawMessage source) throws HomeAutomationException {
        int nodeId = source.getMessageByte(NODE_ID_INDEX);
        int commandClassCode = source.getMessageByte(COMMAND_CLASS_INDEX);
        CommandClass commandClass = MessageUtil.getCommandClass(commandClassCode);

        byte[] message = source.getMessage();

        return new ApplicationCommandEvent(nodeId, DEFAULT_ENDPOINT_ID, commandClass, copyOfRange(message, MESSAGE_OFFSET, message.length));
    }
}
