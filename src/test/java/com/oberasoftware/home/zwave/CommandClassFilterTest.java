package com.oberasoftware.home.zwave;

import com.oberasoftware.base.event.impl.HandlerEntryImpl;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.filters.CommandClassFilter;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author renarj
 */
public class CommandClassFilterTest {
    @Test
    public void testCommandClassFilter() throws Exception {
        CommandClassFilter filter = new CommandClassFilter();

        Method handlerMethod = TestHandler.class.getMethod("receive", ZWaveRawMessage.class);

        ApplicationCommandEvent meterCommand = new ApplicationCommandEvent(-1, -1, CommandClass.METER, new byte[0]);
        ApplicationCommandEvent basicCommand = new ApplicationCommandEvent(-1, -1, CommandClass.BASIC, new byte[0]);

        assertThat(filter.isFiltered(meterCommand, new HandlerEntryImpl(null, handlerMethod)), is(false));

        assertThat(filter.isFiltered(basicCommand, new HandlerEntryImpl(null, handlerMethod)), is(true));
    }

    private class TestHandler {
        @SupportsConversion(commandClass = CommandClass.METER)
        public void receive(ZWaveRawMessage message) {

        }
    }

}
