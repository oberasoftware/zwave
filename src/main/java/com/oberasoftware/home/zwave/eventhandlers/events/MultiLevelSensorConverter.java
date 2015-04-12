package com.oberasoftware.home.zwave.eventhandlers.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.DeviceEvent;
import com.oberasoftware.home.zwave.api.events.devices.DeviceSensorEvent;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

import static com.oberasoftware.home.zwave.core.utils.MessageUtil.extractValue;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class MultiLevelSensorConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(MultiLevelSensorConverter.class);

    private static final int SENSOR_MULTI_LEVEL_SUPPORTED_GET = 0x01;
    private static final int SENSOR_MULTI_LEVEL_SUPPORTED_REPORT = 0x02;
    private static final int SENSOR_MULTI_LEVEL_GET = 0x04;
    private static final int SENSOR_MULTI_LEVEL_REPORT = 0x05;



    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.SENSOR_MULTILEVEL)
    public DeviceEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        LOG.debug("Handling multi level sensor request: {} from node: {}", source, source.getNodeId());
        byte[] payload = source.getPayload();
        int commandType = payload[0];
        int nodeId = source.getNodeId();

        switch (commandType) {
            case SENSOR_MULTI_LEVEL_GET:
            case SENSOR_MULTI_LEVEL_SUPPORTED_GET:
                LOG.warn(String.format("Command 0x%02X not implemented.",
                        commandType));
                return null;
            case SENSOR_MULTI_LEVEL_SUPPORTED_REPORT:
                LOG.debug("Process Multi Level Supported Sensor Report");

                break;
            case SENSOR_MULTI_LEVEL_REPORT:
                LOG.debug("Sensor Multi Level report received for node: {}", nodeId);

                int sensorTypeCode = payload[1];
                int sensorScale = (payload[2] >> 3) & 0x03;
                LOG.debug(String.format("NODE %d: Sensor Type = (0x%02x), Scale = %d", nodeId, sensorTypeCode, sensorScale));

                Optional<SensorType> sensorType = SensorType.getSensorType(sensorTypeCode);
                if (sensorType.isPresent()) {
                    try {
                        BigDecimal value = extractValue(payload, 2);

                        LOG.debug("NODE: {} Sensor Value: {}", nodeId, value);

                        return new DeviceSensorEvent(nodeId, source.getEndpointId(), sensorType.get(), value);
                    } catch (NumberFormatException e) {
                        LOG.error("", e);
                    }
                } else {
                    LOG.error("Node {}: Unknown Sensor Type = {}", nodeId, sensorTypeCode);
                }
                break;
            default:
                LOG.warn("Unsupported Command type: {} for command class: {}", commandType, source.getCommandClass());
        }


        return null;
    }



}
