package com.oberasoftware.home.zwave;

import com.oberasoftware.home.zwave.api.ZWaveIntervalAction;
import com.oberasoftware.home.zwave.api.ZWaveScheduler;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class ZWaveSchedulerImpl implements ZWaveScheduler {
    private static final Logger LOG = getLogger(ZWaveSchedulerImpl.class);

    @Autowired
    private ZWaveController zWaveController;

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void schedule(final ZWaveIntervalAction action, TimeUnit timeUnit, long interval) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                LOG.info("Triggering interval action: {} next interval: {} s.", action, TimeUnit.SECONDS.convert(interval, timeUnit));
                zWaveController.send(action);
            } catch (HomeAutomationException e) {
                LOG.error("", e);
            }
        }, interval, interval, timeUnit);
    }
}
