package com.oberasoftware.home.zwave.local;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.home.zwave.ZWaveController;
import com.oberasoftware.home.zwave.api.ZWaveAction;
import com.oberasoftware.home.zwave.api.ZWaveIntervalAction;
import com.oberasoftware.home.zwave.api.ZWaveScheduler;
import com.oberasoftware.home.zwave.api.ZWaveSession;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;

import java.util.concurrent.TimeUnit;

/**
 * @author renarj
 */
public class LocalZwaveSession implements ZWaveSession {

    public LocalZwaveSession() {
//        LocalSpringContainer.getBean(ZWaveController.class).initializeNetwork();
    }

    @Override
    public boolean isNetworkReady() {
        return getController().isNetworkReady();
    }

    @Override
    public void subscribe(EventHandler eventListener) {
        getController().subscribe(eventListener);
    }

    @Override
    public NodeManager getDeviceManager() {
        return LocalSpringContainer.getBean(NodeManager.class);
    }

    @Override
    public void doAction(ZWaveAction action) throws HomeAutomationException {
        getController().send(action);
    }

    @Override
    public void schedule(ZWaveIntervalAction action, TimeUnit timeUnit, long interval) {
        LocalSpringContainer.getBean(ZWaveScheduler.class).schedule(action, timeUnit, interval);
    }

    @Override
    public void shutdown() {
        LocalSpringContainer.destroy();
    }

    private ZWaveController getController() {
        return LocalSpringContainer.getBean(ZWaveController.class);
    }
}
