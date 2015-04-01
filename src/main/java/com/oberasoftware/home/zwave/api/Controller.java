package com.oberasoftware.home.zwave.api;

import com.oberasoftware.home.zwave.api.events.EventListener;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;

/**
 * @author renarj
 */
public interface Controller {
    void initializeNetwork();

    int getControllerId();

    <T> void subscribe(EventListener<T> topicListener);

    int send(ZWaveAction message) throws HomeAutomationException;

    boolean isNetworkReady();
}
