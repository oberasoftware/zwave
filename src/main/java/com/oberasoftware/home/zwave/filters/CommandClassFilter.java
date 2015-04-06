package com.oberasoftware.home.zwave.filters;

import com.oberasoftware.base.event.EventFilter;
import com.oberasoftware.base.event.HandlerEntry;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.SupportsConversion;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class CommandClassFilter implements EventFilter {
    private static final Logger LOG = getLogger(CommandClassFilter.class);

    @Override
    public boolean isFiltered(Object event, HandlerEntry handler) {
        Method method = handler.getEventMethod();
        if(event instanceof ApplicationCommandEvent) {
            if(method.isAnnotationPresent(SupportsConversion.class)) {
                return checkFilter((ApplicationCommandEvent) event, method);
            }
        }

        LOG.debug("Event: {} is not filtered for method: {} on class: {}", event, method.getName(), method.getDeclaringClass());
        return false;
    }

    private boolean checkFilter(ApplicationCommandEvent message, Method method) {
        LOG.debug("Checking message filter for: {} on method: {} in class: {}", message, method.getName(), method.getDeclaringClass());
        SupportsConversion supportsConversion = method.getAnnotation(SupportsConversion.class);

        //if the handler does not support this command class message it is filtered
        return !supportsCommandClass(supportsConversion.commandClass(), message);
    }

    private boolean supportsCommandClass(CommandClass commandClass, ApplicationCommandEvent message) {
        return commandClass == CommandClass.ALL || message.getCommandClass() == commandClass;
    }
}
