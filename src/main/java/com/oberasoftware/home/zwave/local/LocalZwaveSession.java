package com.oberasoftware.home.zwave.local;

import com.oberasoftware.home.zwave.ZWaveController;
import com.oberasoftware.home.zwave.api.ZWaveAction;
import com.oberasoftware.home.zwave.api.ZWaveSession;
import com.oberasoftware.home.zwave.api.events.EventListener;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;

/**
 * @author renarj
 */
public class LocalZwaveSession implements ZWaveSession {

    public LocalZwaveSession() {

    }

    @Override
    public boolean isNetworkReady() {
        return LocalSpringContainer.getBean(ZWaveController.class).isNetworkReady();
    }

    @Override
    public void subscribe(EventListener eventListener) {
        LocalSpringContainer.getBean(ZWaveController.class).subscribe(eventListener);
    }

    @Override
    public NodeManager getDeviceManager() {
        return LocalSpringContainer.getBean(NodeManager.class);
    }

    @Override
    public void doAction(ZWaveAction action) throws HomeAutomationException {
        LocalSpringContainer.getBean(ZWaveController.class).send(action);
    }

    @Override
    public void shutdown() {
        LocalSpringContainer.destroy();
    }
}
