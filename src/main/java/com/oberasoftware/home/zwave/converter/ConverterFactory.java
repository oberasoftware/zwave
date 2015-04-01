package com.oberasoftware.home.zwave.converter;

import java.util.Optional;

/**
 * @author renarj
 */
public interface ConverterFactory {
    Optional<ZWaveConverter> createConverter(String requiredType);
}
