package com.oberasoftware.home.zwave.converter;

import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author renarj
 */
@Component
public class ConverterHandler<S, T> {
    private static final Logger LOG = LoggerFactory.getLogger(ConverterHandler.class);

    private static final ConverterFactory converterFactory = new ConverterFactoryImpl();

    public T convert(Function<S, String> sourceTypeselector, S source) throws HomeAutomationException {
        String sourceConverterType = sourceTypeselector.apply(source);
        Optional<ZWaveConverter<S, T>> converter = converterFactory.createConverter(sourceConverterType);

        LOG.debug("Converting message of type: {} converter available for this type: {}", source.getClass().getSimpleName(), converter);

        Optional<T> convertedMessage = convert(converter, source);
        if(convertedMessage.isPresent()) {
            LOG.debug("Message converted to message: {}", convertedMessage.get());
            return convertedMessage.get();
        } else {
            LOG.error("There is no specific converter for action: {} finding a generic converter", source);
            //TODO: implement basic converter
            return null;
        }
    }

    private Optional<T> convert(Optional<ZWaveConverter<S, T>> actionConverter, S source) throws HomeAutomationException {
        if(actionConverter.isPresent()) {
            ZWaveConverter<S, T> c = actionConverter.get();
            LOG.debug("Converting message: {} using converter: {}", source, c);
            return Optional.of(c.convert(source));
        }
        return Optional.empty();
    }
}
