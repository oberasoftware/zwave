package com.oberasoftware.home.zwave.converter.events;

import com.oberasoftware.home.zwave.api.events.EventBus;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.handlers.ApplicationCommandHandler;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@RunWith(MockitoJUnitRunner.class)
public class MultiLevelSensorConverterTest {
    private static final Logger LOG = getLogger(MultiLevelSensorConverterTest.class);

    @Mock
    private EventBus eventBus;

    @InjectMocks
    private ApplicationCommandHandler applicationCommandHandler;

    @Test
    /**
     * We are trying to support this raw serial message:
     * ApplicationCommandEvent{nodeId=4, endpointId=1, commandClass=SENSOR_MULTILEVEL, payload=05 04 22 00 03 }
     */
    public void converterSensorReport() {
        ApplicationCommandEvent event = new ApplicationCommandEvent(4, 1, CommandClass.SENSOR_MULTILEVEL, new byte[] {0x05, 0x04, 0x22, 0x00, 0x6B});
        LOG.debug("Event: {}", event);

        applicationCommandHandler.receive(event);
    }

    @Test
    public void extractValue() {
        LOG.debug(new MultiLevelSensorConverter().extractValue(new byte[]{0x22, 0x02, 0x10}, 0).toString());

        LOG.debug(new MultiLevelSensorConverter().extractValue(new byte[]{0x22, 0x01, 0x04}, 0).toString());
    }
}


