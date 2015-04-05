package com.oberasoftware.home.zwave.converter.events;

import com.oberasoftware.base.event.EventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void extractValue() {
        LOG.debug(new MultiLevelSensorConverter().extractValue(new byte[]{0x22, 0x02, 0x10}, 0).toString());

        LOG.debug(new MultiLevelSensorConverter().extractValue(new byte[]{0x22, 0x01, 0x04}, 0).toString());
    }
}


