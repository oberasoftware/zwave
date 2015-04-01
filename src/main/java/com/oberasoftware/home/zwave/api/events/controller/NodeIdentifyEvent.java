package com.oberasoftware.home.zwave.api.events.controller;

import com.oberasoftware.home.zwave.messages.types.BasicDeviceClass;
import com.oberasoftware.home.zwave.messages.types.GenericDeviceClass;
import com.oberasoftware.home.zwave.messages.types.SpecificDeviceClass;

/**
 * @author renarj
 */
public class NodeIdentifyEvent implements ControllerEvent {
    private final boolean listening;
    private final boolean frequentListener;
    private final boolean routing;
    private final int version;

    private final BasicDeviceClass basicDeviceClass;
    private final GenericDeviceClass genericDeviceClass;
    private final SpecificDeviceClass specificDeviceClass;

    public NodeIdentifyEvent(boolean listening, boolean frequentListener, boolean routing, int version, BasicDeviceClass basicDeviceClass, GenericDeviceClass genericDeviceClass, SpecificDeviceClass specificDeviceClass) {
        this.listening = listening;
        this.frequentListener = frequentListener;
        this.routing = routing;
        this.version = version;
        this.basicDeviceClass = basicDeviceClass;
        this.genericDeviceClass = genericDeviceClass;
        this.specificDeviceClass = specificDeviceClass;
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

    public BasicDeviceClass getBasicDeviceClass() {
        return basicDeviceClass;
    }

    public GenericDeviceClass getGenericDeviceClass() {
        return genericDeviceClass;
    }

    public SpecificDeviceClass getSpecificDeviceClass() {
        return specificDeviceClass;
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
