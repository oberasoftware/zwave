package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.MeterEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.api.messages.types.MeterScale;
import com.oberasoftware.home.zwave.api.messages.types.MeterType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.oberasoftware.home.zwave.core.utils.MessageUtil.extractValue;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MeterConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(MeterConverter.class);

    private static final int METER_GET = 0x01;
    private static final int METER_REPORT = 0x02;
    private static final int METER_SUPPORTED_GET = 0x03;
    private static final int METER_SUPPORTED_REPORT = 0x04;
    private static final int METER_RESET = 0x05;


    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.METER)
    public MeterEvent convert(ApplicationCommandEvent event) {
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
                LOG.debug("Meter report received for node: {}", event.getNodeId());

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
                    LOG.debug("Meter value: {} for node: {} and endpoint: {}", value, nodeId, event.getEndpointId());

                    return new MeterEvent(nodeId, event.getEndpointId(), value, meterType, scale);
                } catch (NumberFormatException ignored) {
                }

                break;
            case METER_SUPPORTED_REPORT:
                LOG.debug("Meter supported report not implemented currently");
                break;
            default:
                LOG.warn("Unsupport command: {} received from node: {}", command, nodeId);
        }

        return null;
    }
}
