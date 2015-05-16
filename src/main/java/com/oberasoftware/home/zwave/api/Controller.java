package com.oberasoftware.home.zwave.api;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;

/**
 * @author renarj
 */
public interface Controller {
    void initializeNetwork();

    int getControllerId();

    void subscribe(EventHandler topicListener);

    int send(ZWaveAction message) throws HomeAutomationException;

    boolean isNetworkReady();
}
