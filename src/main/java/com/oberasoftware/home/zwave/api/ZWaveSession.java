package com.oberasoftware.home.zwave.api;

import com.oberasoftware.home.zwave.api.events.EventListener;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;

/**
 * @author renarj
 */
public interface ZWaveSession {

    boolean isNetworkReady();

    void subscribe(EventListener eventListener);

    NodeManager getDeviceManager();

    void shutdown();

    void doAction(ZWaveAction action) throws HomeAutomationException;
}
