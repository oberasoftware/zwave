package com.oberasoftware.home.zwave.converter;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class ConverterFactoryImpl implements ConverterFactory {
    private static final Logger LOG = getLogger(ConverterFactoryImpl.class);

    private Map<String, ZWaveConverter> converterMap = new HashMap<>();

    public ConverterFactoryImpl() {
        ServiceLoader<ZWaveConverter> l = ServiceLoader.load(ZWaveConverter.class);
        l.forEach(m -> addSupportedTypes(m, m.getSupportedTypeNames()));
    }

    private void addSupportedTypes(ZWaveConverter c, Set<String> supportedTypes) {
        supportedTypes.forEach(s -> addSupportedType(c, s));
    }

    private void addSupportedType(ZWaveConverter c, String typeName) {
        LOG.debug("Added message converter: {} for type: {}", c, typeName);
        converterMap.put(typeName, c);
    }

    @Override
    public <S, T> Optional<ZWaveConverter<S, T>> createConverter(String requiredType) {
        return Optional.ofNullable(converterMap.get(requiredType));
    }
}
