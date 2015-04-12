package com.oberasoftware.home.zwave.core.utils;

import com.oberasoftware.base.event.EventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageUtilTest {
    private static final Logger LOG = getLogger(MessageUtilTest.class);

    @Mock
    private EventBus eventBus;

    @Test
    public void extractValue() {
        assertThat(MessageUtil.extractValue(new byte[]{0x22, 0x02, 0x10}, 0).longValue(), is(52l));

        assertThat(MessageUtil.extractValue(new byte[]{0x22, 0x01, 0x04}, 0).longValue(), is(26l));
    }
}


