package com.oberasoftware.home.zwave.converter.controller;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.SEND_STATE;
import com.oberasoftware.home.zwave.api.events.controller.SendDataEvent;
import com.oberasoftware.home.zwave.api.events.controller.SendDataStateEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.exceptions.ZWaveConverterException;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.TransmissionState;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static com.oberasoftware.home.zwave.api.events.controller.SEND_STATE.FAILED;
import static com.oberasoftware.home.zwave.api.events.controller.SEND_STATE.SUCCESS;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class SendDataConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(SendDataConverter.class);

    @EventSubscribe
    @SupportsConversion(controllerMessage = ControllerMessageType.SendData)
    public SendDataStateEvent convert(ZWaveRawMessage source) throws HomeAutomationException {
        switch(source.getMessageType()) {
            case Response:
                SEND_STATE state = source.getMessageByte(0) != 0x00 ? SUCCESS : FAILED;

                LOG.debug("Received a response message with status: {}", state);
                return new SendDataStateEvent(state);
            case Request:
                int callbackId = source.getMessageByte(0);
                TransmissionState transmissionState = TransmissionState.getTransmissionState(source.getMessageByte(1));

                LOG.debug("Received a request message for callback: {}", callbackId);
                return new SendDataEvent(source.getNodeId(), callbackId, transmissionState == TransmissionState.COMPLETE_OK ? SUCCESS : FAILED);
            default:
                throw new ZWaveConverterException("Unable to convert message of SendData type: " + source);
        }
    }
}
