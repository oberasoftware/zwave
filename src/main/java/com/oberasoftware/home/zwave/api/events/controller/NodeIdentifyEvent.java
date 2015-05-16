package com.oberasoftware.home.zwave.api.events.controller;

import com.oberasoftware.home.zwave.api.messages.types.BasicDeviceClass;
import com.oberasoftware.home.zwave.api.messages.types.GenericDeviceClass;
import com.oberasoftware.home.zwave.api.messages.types.SpecificDeviceClass;

/**
 * @author renarj
 */
public class NodeIdentifyEvent implements TransactionEvent {
    private final boolean listening;
    private final boolean frequentListener;
    private final boolean routing;
    private final int version;
    private final int nodeId;

    private final BasicDeviceClass basicDeviceClass;
    private final GenericDeviceClass genericDeviceClass;
    private final SpecificDeviceClass specificDeviceClass;

    public NodeIdentifyEvent(int nodeId, boolean listening, boolean frequentListener, boolean routing, int version, BasicDeviceClass basicDeviceClass, GenericDeviceClass genericDeviceClass, SpecificDeviceClass specificDeviceClass) {
        this.nodeId = nodeId;
        this.listening = listening;
        this.frequentListener = frequentListener;
        this.routing = routing;
        this.version = version;
        this.basicDeviceClass = basicDeviceClass;
        this.genericDeviceClass = genericDeviceClass;
        this.specificDeviceClass = specificDeviceClass;
    }

    public int getNodeId() {
        return nodeId;
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
        return "NodeIdentifyEvent{" +
                "listening=" + listening +
                ", frequentListener=" + frequentListener +
                ", routing=" + routing +
                ", version=" + version +
                ", nodeId=" + nodeId +
                ", basicDeviceClass=" + basicDeviceClass +
                ", genericDeviceClass=" + genericDeviceClass +
                ", specificDeviceClass=" + specificDeviceClass +
                '}';
    }
}
