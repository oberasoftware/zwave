package com.oberasoftware.home.zwave.converter;

import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;

import java.util.Set;

/**
 * @author renarj
 */
public interface ZWaveConverter<S, T> {
    Set<String> getSupportedTypeNames();

    T convert(S source) throws HomeAutomationException;
}
