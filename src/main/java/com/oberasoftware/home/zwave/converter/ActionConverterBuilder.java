package com.oberasoftware.home.zwave.converter;

import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;
import com.oberasoftware.home.zwave.threading.ActionConvertedEvent;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
public class ActionConverterBuilder {
    private static final Logger LOG = getLogger(ActionConverterBuilder.class);

    private final List<Byte> payload = new ArrayList<>();
    private final Optional<Integer> nodeId;
    private final ControllerMessageType controllerMessageType;
    private final MessageType messageType;

    private Optional<Integer> callbackId = Optional.empty();

    public ActionConverterBuilder(Optional<Integer> nodeId, ControllerMessageType controllerMessageType, MessageType messageType) {
        this.nodeId = nodeId;
        this.controllerMessageType = controllerMessageType;
        this.messageType = messageType;
    }

    public static ActionConverterBuilder create(ControllerMessageType controllerMessageType, MessageType messageType) {
        return new ActionConverterBuilder(Optional.empty(), controllerMessageType, messageType);
    }

    public static ActionConverterBuilder create(ControllerMessageType controllerMessageType, MessageType messageType, int nodeId) {
        return new ActionConverterBuilder(Optional.of(nodeId), controllerMessageType, messageType);
    }

    public ActionConverterBuilder callback(int callbackId) {
        this.callbackId = Optional.of(callbackId);
        return this;
    }

    public ActionConverterBuilder addByte(byte b) {
        payload.add(b);

        return this;
    }

    public ActionConverterBuilder addInt(int i) {
        payload.add((byte)i);
        return this;
    }

    public ActionConverterBuilder addCommandClass(CommandClass commandClass) {
        addInt(commandClass.getClassCode());
        return this;
    }

    public ActionConvertedEvent construct() {
        ZWaveRawMessage message;
        //we get the initial payload lenght without any potential nodeId
        int payloadLenght = payload.size();

        if(nodeId.isPresent()) {
            message = new ZWaveRawMessage(nodeId.get(), controllerMessageType, messageType);
            //in case a nodeId is known this will always be the first byte
            payload.add(0, nodeId.get().byteValue());
        } else {
            message = new ZWaveRawMessage(controllerMessageType, messageType);
        }
        if(callbackId.isPresent()) {
            message.setCallbackId(callbackId.get());
        }

        if(payloadLenght != 0) {
            //when there is a payload we specify the length, this is always the 2nd byte
            payload.add(1, (byte) payloadLenght);
        }

        //It could be an empty payload in case of controller messages
        if(!payload.isEmpty()) {
            byte[] buffer = new byte[payload.size()];
            for (int i = 0; i < payload.size(); i++) {
                buffer[i] = payload.get(i);
            }
            message.setMessage(buffer);
        }

        LOG.trace("Message converted: {}", message);

        return new ActionConvertedEvent(message);
    }
}
