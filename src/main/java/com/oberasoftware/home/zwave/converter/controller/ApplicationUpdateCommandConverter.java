package com.oberasoftware.home.zwave.converter.controller;

import com.google.common.collect.Lists;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.devices.NodeInfoReceivedEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.core.utils.MessageUtil;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class ApplicationUpdateCommandConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(ApplicationUpdateCommandConverter.class);

    private static final int NODE_INFO_RECEIVED = 0x84;

    @SupportsConversion(controllerMessage = ControllerMessageType.ApplicationUpdate)
    @EventSubscribe
    public NodeInfoReceivedEvent convert(ZWaveRawMessage source) throws HomeAutomationException {

        byte[] message = source.getMessage();

        int command = message[0] & 0xff;
        int nodeId = message[1];

        switch(command) {
            case NODE_INFO_RECEIVED:
                LOG.debug("Received node information for node: {}", nodeId);

                int length = message[2];

                List<CommandClass> commandClassList = new ArrayList<>();
                for (int i = 6; i < length + 3; i++) {
                    int data = message[i]  & 0xff;
                    if(data != 0xef) {
                        LOG.debug("Received command class: {} for node: {}", data, nodeId);

                        CommandClass commandClass = MessageUtil.getCommandClass(data);
                        if (commandClass != null) {
                            commandClassList.add(commandClass);
                        }
                    }
                }

                return new NodeInfoReceivedEvent(nodeId, commandClassList);
            default:
                LOG.warn("Unsupported application update command received: {} for node: {}", command, nodeId);
        }

        return new NodeInfoReceivedEvent(nodeId, Lists.newArrayList());
    }
}
