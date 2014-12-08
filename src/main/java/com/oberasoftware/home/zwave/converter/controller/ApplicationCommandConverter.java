package com.oberasoftware.home.zwave.converter.controller;

import com.google.common.collect.Sets;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.core.utils.MessageUtil;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;

import java.util.Arrays;
import java.util.Set;

/**
 * @author renarj
 */
public class ApplicationCommandConverter implements ZWaveConverter<ZWaveRawMessage, ApplicationCommandEvent> {

    private static final int DEFAULT_ENDPOINT_ID = 1;
    private static final int MESSAGE_OFFSET = 4;
    private static final int NODE_ID_INDEX = 1;
    private static final int COMMAND_CLASS_INDEX = 3;

    @Override
    public Set<String> getSupportedTypeNames() {
        return Sets.newHashSet(ControllerMessageType.ApplicationCommandHandler.getLabel());
    }

    @Override
    public ApplicationCommandEvent convert(ZWaveRawMessage source) throws HomeAutomationException {
        int nodeId = source.getMessageByte(NODE_ID_INDEX);
        int commandClassCode = source.getMessageByte(COMMAND_CLASS_INDEX);
        CommandClass commandClass = MessageUtil.getCommandClass(commandClassCode);

        byte[] message = source.getMessage();

        return new ApplicationCommandEvent(nodeId, DEFAULT_ENDPOINT_ID, commandClass, Arrays.copyOfRange(message, MESSAGE_OFFSET, message.length));
    }
}
