package com.oberasoftware.home.zwave.eventhandlers;

import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;
import com.oberasoftware.home.zwave.api.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
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

    private static final int MULTI_CHANNEL_ENCAP = 0x0d;

    private final List<Byte> payload = new ArrayList<>();
    private final Optional<Integer> nodeId;
    private final Optional<Integer> endpointId;
    private final ControllerMessageType controllerMessageType;
    private final MessageType messageType;

    private Optional<Integer> callbackId = Optional.empty();

    public ActionConverterBuilder(Optional<Integer> nodeId, Optional<Integer> endpointId, ControllerMessageType controllerMessageType, MessageType messageType) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.controllerMessageType = controllerMessageType;
        this.messageType = messageType;
    }

    public static ActionConverterBuilder create(ControllerMessageType controllerMessageType, MessageType messageType) {
        return new ActionConverterBuilder(Optional.empty(), Optional.empty(), controllerMessageType, messageType);
    }

    public static ActionConverterBuilder create(ControllerMessageType controllerMessageType, MessageType messageType, int nodeId) {
        return new ActionConverterBuilder(Optional.of(nodeId), Optional.empty(), controllerMessageType, messageType);
    }

    public static ActionConverterBuilder create(ControllerMessageType controllerMessageType, MessageType messageType, ZWaveDeviceAction deviceAction) {
        int nodeId = deviceAction.getNodeId();
        int endpointId = deviceAction.getEndpointId();

        if(endpointId != ZWaveDeviceAction.DEFAULT_ENDPOINTID) {
            return new ActionConverterBuilder(Optional.of(nodeId), Optional.of(endpointId), controllerMessageType, messageType);
        } else {
            return create(controllerMessageType, messageType, nodeId);
        }
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
        payload.add((byte) i);
        return this;
    }

    public ActionConverterBuilder addCommandClass(CommandClass commandClass) {
        addInt(commandClass.getClassCode());
        return this;
    }

    public ActionConvertedEvent construct() {
        if(endpointId.isPresent()) {
            return constructMultiInstance();
        }
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

    public ActionConvertedEvent constructMultiInstance() {
        LOG.debug("Constructing multi instance message for node: {} endpoint: {}", nodeId, endpointId);

        //we get the initial payload lenght without any potential nodeId
        int payloadLenght = payload.size() + 4;

        ZWaveRawMessage message = new ZWaveRawMessage(nodeId.get(), controllerMessageType, messageType);
        //nodeId is first payload byte
        payload.add(0, nodeId.get().byteValue());
        payload.add(1, (byte)payloadLenght); //this will be length
        payload.add(2, (byte)CommandClass.MULTI_INSTANCE.getClassCode());
        payload.add(3, (byte) MULTI_CHANNEL_ENCAP);
        payload.add(4, (byte)0x01);
        payload.add(5, endpointId.get().byteValue());


        if(callbackId.isPresent()) {
            message.setCallbackId(callbackId.get());
        }

//        if(payloadLenght != 0) {
//            //when there is a payload we specify the length, this is always the 2nd byte
//            payload.add(1, (byte) payloadLenght);
//        }

        //It could be an empty payload in case of controller messages
//        if(!payload.isEmpty()) {
            byte[] buffer = new byte[payload.size()];
            for (int i = 0; i < payload.size(); i++) {
                buffer[i] = payload.get(i);
            }
            message.setMessage(buffer);
//        }

        LOG.trace("Message converted: {}", message);

        return new ActionConvertedEvent(message);
    }
}
