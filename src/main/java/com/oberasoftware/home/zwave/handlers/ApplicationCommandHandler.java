package com.oberasoftware.home.zwave.handlers;

import com.oberasoftware.home.zwave.api.events.EventListener;
import com.oberasoftware.home.zwave.api.events.EventBus;
import com.oberasoftware.home.zwave.api.events.Subscribe;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.DeviceEvent;
import com.oberasoftware.home.zwave.api.events.controller.SendDataEvent;
import com.oberasoftware.home.zwave.converter.ConverterFactory;
import com.oberasoftware.home.zwave.converter.ConverterFactoryImpl;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class ApplicationCommandHandler implements EventListener<ApplicationCommandEvent> {
    private static final Logger LOG = getLogger(ApplicationCommandHandler.class);


    private ConverterFactory converterFactory = new ConverterFactoryImpl();

    @Autowired
    private EventBus eventBus;


    @Override
    public void receive(ApplicationCommandEvent event) {
        LOG.debug("Received application command: {}", event);

        Optional<ZWaveConverter<ApplicationCommandEvent, DeviceEvent>> deviceEventConverter = converterFactory.createConverter(event.getCommandClass().getLabel());
        if(deviceEventConverter.isPresent()) {
            ZWaveConverter<ApplicationCommandEvent, DeviceEvent> converter = deviceEventConverter.get();
            LOG.debug("Converting application command to device event using converter: {}", converter);
            try {
                DeviceEvent deviceEvent = converter.convert(event);
                LOG.debug("We have a device event: {}", deviceEvent);

                eventBus.pushAsync(deviceEvent);
            } catch (HomeAutomationException e) {
                LOG.error("Unable to convert message", e);
            }

        }
    }

    @Subscribe
    public void handleSendDataEvent(SendDataEvent sendDataEvent) {
        LOG.debug("Received send data event: {}", sendDataEvent);
    }
}
