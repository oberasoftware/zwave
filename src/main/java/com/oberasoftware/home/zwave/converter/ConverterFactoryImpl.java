package com.oberasoftware.home.zwave.converter;

import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.stream;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class ConverterFactoryImpl implements ConverterFactory {
    private static final Logger LOG = getLogger(ConverterFactoryImpl.class);

    private Map<String, ZWaveConverter> converterMap = new HashMap<>();

    @Autowired
    private List<ZWaveConverter> converters;

    @PostConstruct
    public void mapConverters() {
        converters.forEach(c -> stream(c.getClass().getMethods())
                .filter(m -> !m.isBridge() && m.isAnnotationPresent(SupportsConversion.class))
                .forEach(m -> addSupportedType(c, m)));
    }

    private void addSupportedType(ZWaveConverter c, Method m) {
        SupportsConversion conversionInfo = m.getAnnotation(SupportsConversion.class);

        if(conversionInfo.commandClass() != CommandClass.NONE) {
            addSupportedType(c, conversionInfo.commandClass().getLabel());
        } else if(conversionInfo.controllerMessage() != ControllerMessageType.NONE) {
            addSupportedType(c, conversionInfo.controllerMessage().getLabel());
        } else if(conversionInfo.convertsActions()) {
            Class<?>[] parameterTypes = m.getParameterTypes();
            if(parameterTypes.length == 1) {
                addSupportedType(c, parameterTypes[0].getSimpleName());
            }
        }
    }

    private void addSupportedType(ZWaveConverter c, String typeName) {
        LOG.debug("Added message converter: {} for type: {}", c, typeName);
        converterMap.put(typeName, c);
    }

    @Override
    public Optional<ZWaveConverter> createConverter(String requiredType) {
        return Optional.ofNullable(converterMap.get(requiredType));
    }
}
