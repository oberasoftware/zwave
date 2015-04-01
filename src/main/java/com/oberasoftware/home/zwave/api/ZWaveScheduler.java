package com.oberasoftware.home.zwave.api;

import java.util.concurrent.TimeUnit;

/**
 * @author renarj
 */
public interface ZWaveScheduler {
    void schedule(ZWaveIntervalAction action, TimeUnit timeUnit, long interval);
}
