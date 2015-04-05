package com.oberasoftware.home.zwave.converter.controller;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.ControllerInitialDataEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
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
public class ControllerInitialDataConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(ControllerInitialDataConverter.class);

    private static final int NODE_BYTES_REQUIRED_LENGTH = 29; // 29 bytes = 232 bits, one for each supported node by Z-Wave;

    @SupportsConversion(controllerMessage = ControllerMessageType.SerialApiGetInitData)
    @EventSubscribe
    public ControllerInitialDataEvent convert(ZWaveRawMessage message) throws HomeAutomationException {
        LOG.debug("Got MessageSerialApiGetInitData response: {}", message);
        int nodeByteLength = message.getMessageByte(2);

        if (nodeByteLength == NODE_BYTES_REQUIRED_LENGTH) {
            int nodeId = 1;

            List<Integer> nodeIds = new ArrayList<>();
            // loop bytes
            for (int i = 3; i < 3 + nodeByteLength;i++) {
                int incomingByte = message.getMessageByte(i);
                // loop bits in byte
                for (int j=0;j<8;j++) {
                    int b1 = incomingByte & (int)Math.pow(2.0D, j);
                    int b2 = (int)Math.pow(2.0D, j);
                    if (b1 == b2) {
                        LOG.info("Node was found: {}", nodeId);

                        nodeIds.add(nodeId);
                    }
                    nodeId++;
                }
            }

            ControllerInitialDataEvent.MODE controllerMode = ((message.getMessageByte(1) & 0x01) == 1) ? ControllerInitialDataEvent.MODE.SLAVE : ControllerInitialDataEvent.MODE.CONTROLLER;
            ControllerInitialDataEvent.CONTROLLER_TYPE controllerType = ((message.getMessageByte(1) & 0x04) == 1) ? ControllerInitialDataEvent.CONTROLLER_TYPE.SECONDARY : ControllerInitialDataEvent.CONTROLLER_TYPE.PRIMARY;


            return new ControllerInitialDataEvent(controllerMode, controllerType, nodeIds);
        } else {
            LOG.error("Invalid number of node bytes: {}", nodeByteLength);
            return null;
        }

    }
}
