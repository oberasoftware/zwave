package com.oberasoftware.home.zwave.filters;

import com.oberasoftware.base.event.EventFilter;
import com.oberasoftware.base.event.HandlerEntry;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class ControllerMessageTypeFilter implements EventFilter {
    private static final Logger LOG = getLogger(ControllerMessageTypeFilter.class);

    @Override
    public boolean isFiltered(Object event, HandlerEntry handler) {
        Method method = handler.getEventMethod();
        if(event instanceof ZWaveRawMessage) {
            if(method.isAnnotationPresent(SupportsConversion.class)) {
                return checkFilter((ZWaveRawMessage) event, method);
            }
        }

        LOG.debug("Event: {} is not filtered for method: {} on class: {}", event, method, method.getDeclaringClass());
        return false;
    }

    private boolean checkFilter(ZWaveRawMessage message, Method method) {
        LOG.debug("Checking message filter for: {} on method: {} in class: {}", message, method, method.getDeclaringClass());
        SupportsConversion supportsConversion = method.getAnnotation(SupportsConversion.class);

        //if the handler does not support this controller message it is filtered
        return !supportsControllerMessageType(supportsConversion.controllerMessage(), message);
    }

    private boolean supportsControllerMessageType(ControllerMessageType controllerMessageType, ZWaveRawMessage message) {
        return controllerMessageType == ControllerMessageType.ALL || message.getControllerMessageType() == controllerMessageType;
    }

}
