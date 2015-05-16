package com.oberasoftware.home.zwave.eventhandlers.actions.devices;

import com.oberasoftware.base.event.Event;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.api.actions.devices.GenerateCommandClassPollAction;
import com.oberasoftware.home.zwave.api.actions.devices.MeterGetAction;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.MeterEvent;
import com.oberasoftware.home.zwave.api.events.devices.MeterScalesEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.api.messages.types.MessageType;
import com.oberasoftware.home.zwave.api.messages.types.MeterScale;
import com.oberasoftware.home.zwave.api.messages.types.MeterType;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.core.ZWaveNode;
import com.oberasoftware.home.zwave.eventhandlers.ActionConverterBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.oberasoftware.home.zwave.core.utils.MessageUtil.extractValue;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MeterCommandClassHandler implements ZWaveConverter {
    private static final Logger LOG = getLogger(MeterCommandClassHandler.class);

    private static final int METER_GET = 0x01;
    private static final int METER_REPORT = 0x02;
    private static final int METER_SUPPORTED_GET = 0x03;
    private static final int METER_SUPPORTED_REPORT = 0x04;
    private static final int METER_RESET = 0x05;


    @Autowired
    private NodeManager nodeManager;

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.METER)
    public Object convert(ApplicationCommandEvent event) {
        LOG.debug("Converting meter command: {}", event);

        int nodeId = event.getNodeId();
        byte[] payload = event.getPayload();
        int command = payload[0];

        switch (command) {
            case METER_GET:
            case METER_SUPPORTED_GET:
            case METER_RESET:
                LOG.warn("Command: {} not implemented from node: {}", command, event.getNodeId());
                break;
            case METER_REPORT:
                return handleMeterReport(payload, nodeId, event.getEndpointId());
            case METER_SUPPORTED_REPORT:
                return handleMeterSupportReport(nodeId, event.getEndpointId(), payload);
            default:
                LOG.warn("Unsupport command: {} received from node: {}", command, nodeId);
        }

        return null;
    }

    private List<Event> handleMeterSupportReport(int nodeId, int endpointId, byte[] payload) {
        LOG.debug("Processing meter support report for node: {}", nodeId);

        boolean canReset = (payload[1] & 0x80) != 0;
        int meterTypeIndex = payload[1] & 0x1F;
        int supportedScales = payload[2];

        MeterType meterType = MeterType.getMeterType(meterTypeIndex);
        LOG.debug("Detecting meter scales for node: {} endpoint: {} for meterType: {}", nodeId, endpointId, meterType.getLabel());

        List<MeterScale> meterScales = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            // scale is supported
            if ((supportedScales & (1 << i)) == (1 << i)) {
                MeterScale scale = MeterScale.getMeterScale(meterType, i);

                if (scale == null) {
                    LOG.debug("Node: {} detected invalid scale: {}", nodeId, i);
                    continue;
                }

                LOG.debug("Detected Meter scale: {} for Node: {}:{}", scale.getUnit(), nodeId,endpointId);

                // add scale to the list of supported scales.
                if (!meterScales.contains(scale)) {
                    meterScales.add(scale);
                }
            }
        }
        String key = nodeId + ":" + endpointId + ":meterTypes";
        nodeManager.setNodeProperty(nodeId, key, meterScales);

        List<Event> events =  meterScales.stream().map(m -> new MeterGetAction(nodeId, endpointId, m)).collect(Collectors.toCollection(ArrayList::new));
        events.add(0, new MeterScalesEvent(nodeId, endpointId, meterScales));
        return events;
    }

    private MeterEvent handleMeterReport(byte[] payload, int nodeId, int endpointId) {
        LOG.debug("Meter report received for node: {}", nodeId);

        int meterTypeIndex = payload[1] & 0x1f;
        MeterType meterType = MeterType.getMeterType(meterTypeIndex);

        //int scaleIndex = (serialMessage.getMessagePayloadByte(offset + 2) & 0x18) >> 0x03;
        int scaleIndex = (payload[2] & 0x18) >> 0x03;
        // In version 3, an extra scale bit is stored in the meter type byte, fix in the future to be based on version reporting
        scaleIndex |= ((payload[1] & 0x80) >> 0x05);

        MeterScale scale = MeterScale.getMeterScale(meterType, scaleIndex);
        LOG.debug("Meter report for node: {} type: {} scale: {}", nodeId, meterType, scale);

        try {
            BigDecimal value = extractValue(payload, 2);
            LOG.debug("Meter value: {} for node: {} and endpoint: {}", value, nodeId, endpointId);

            return new MeterEvent(nodeId, endpointId, value, meterType, scale);
        } catch (NumberFormatException ignored) {
            LOG.debug("Invalid number found in payload of meter event for node: {}", nodeId);
            return null;
        }
    }

    @EventSubscribe
    public ActionConvertedEvent convert(MeterGetAction action) {
        ActionConverterBuilder builder = ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, action)
                .addCommandClass(CommandClass.METER)
                .addInt(METER_GET);

        Optional<MeterScale> scaleOptional = action.getScale();
        if(scaleOptional.isPresent()) {
            LOG.debug("Scale configured: {} for node: {}", scaleOptional.get(), action.getNodeId());
            int scale = scaleOptional.get().getScale() << 3;
            builder.addInt(scale);
        }

        return builder.construct();
    }

    @SupportsConversion(commandClass = CommandClass.METER)
    @EventSubscribe
    public Object generate(GenerateCommandClassPollAction action) {
        ZWaveNode node = nodeManager.getNode(action.getNodeId());
        String key = action.getNodeId() + ":" + action.getEndpointId() + ":meterTypes";
        List<MeterScale> meterTypes = (List<MeterScale>) node.getProperty(key);
        if(meterTypes != null) {
            LOG.info("We have known supported meter types: {} for node: {} endpoint: {}", meterTypes, action.getNodeId(), action.getEndpointId());

            LOG.info("Generating meter actions for node: {} and metertypes: {}", action.getNodeId(), meterTypes);
            return meterTypes.stream().map(m -> new MeterGetAction(action.getNodeId(), action.getEndpointId(), m)).collect(Collectors.toList());
        } else {
            LOG.info("Retrieving meter types for node: {} endpoint: {}", action.getNodeId(), action.getEndpointId());
            return generateGetSupportedTypes(action);
        }

    }

    public ActionConvertedEvent generateGetSupportedTypes(GenerateCommandClassPollAction action) {
        return ActionConverterBuilder.create(ControllerMessageType.SendData, MessageType.Request, action)
                .addCommandClass(CommandClass.METER)
                .addInt(METER_SUPPORTED_GET)
                .construct();
    }
}
