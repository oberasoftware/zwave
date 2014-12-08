package com.oberasoftware.home.zwave.converter;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author renarj
 */
public interface ConverterFactory {
    <S, T> Optional<ZWaveConverter<S, T>> createConverter(String requiredType);
}
