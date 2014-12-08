package com.oberasoftware.home.zwave.api.events;

import com.oberasoftware.home.zwave.exceptions.EventException;

/**
 * @author renarj
 */
public interface EventBus {
    void push(Object object) throws EventException;

    void pushAsync(Object object);

    void addListener(EventListener eventListener);
}
