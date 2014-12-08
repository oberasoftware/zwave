package com.oberasoftware.home.zwave.core;

/**
 * @author renarj
 */
public enum NodeStatus {
    UNKNOWN(-1),
    INITIALIZING(0),
    IDENTIFIED(1),
    ACTIVE(2),
    INITIALIZED(3);

    private final int sequence;

    NodeStatus(int sequence) {
        this.sequence = sequence;
    }

    public boolean hasMinimalStatus(NodeStatus status) {
        return this.sequence >= status.sequence;
    }
}
