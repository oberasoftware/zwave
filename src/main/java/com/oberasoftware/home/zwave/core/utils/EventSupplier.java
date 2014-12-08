package com.oberasoftware.home.zwave.core.utils;

import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;

/**
 * @author renarj
 */
@FunctionalInterface
public interface EventSupplier<T> {
    T get() throws HomeAutomationException;
}
