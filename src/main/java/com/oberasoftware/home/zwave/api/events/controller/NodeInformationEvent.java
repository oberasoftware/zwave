package com.oberasoftware.home.zwave.api.events.controller;

/**
 * @author renarj
 */
public class NodeInformationEvent implements ControllerEvent {
    private final boolean listening;
    private final boolean frequentListener;
    private final boolean routing;
    private final int version;

    public NodeInformationEvent(boolean listening, boolean frequentListener, boolean routing, int version) {
        this.listening = listening;
        this.frequentListener = frequentListener;
        this.routing = routing;
        this.version = version;
    }

    public boolean isListening() {
        return listening;
    }

    public boolean isFrequentListener() {
        return frequentListener;
    }

    public boolean isRouting() {
        return routing;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean isTransactionCompleted() {
        return true;
    }

    @Override
    public String toString() {
        return "NodeInformationEvent{" +
                "listening=" + listening +
                ", frequentListener=" + frequentListener +
                ", routing=" + routing +
                ", version=" + version +
                '}';
    }
}
