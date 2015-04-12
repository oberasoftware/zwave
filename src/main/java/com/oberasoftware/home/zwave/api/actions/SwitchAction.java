package com.oberasoftware.home.zwave.api.actions;

import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;

/**
 * @author renarj
 */
public class SwitchAction implements ZWaveDeviceAction {

    public static final int MAX_LEVEL = 100;

    public enum STATE {
        ON,
        OFF
    }

    private final STATE desiredState;
    private final int nodeId;
    private final int endpointId;

    private final int level;

    public SwitchAction(int nodeId, STATE desiredState) {
        this(nodeId, DEFAULT_ENDPOINTID, desiredState, MAX_LEVEL);
    }

    public SwitchAction(int nodeId, int level) {
        this(nodeId, DEFAULT_ENDPOINTID, STATE.ON, level);
    }

    public SwitchAction(int nodeId, int endpointId, int level) {
        this(nodeId, endpointId, STATE.ON, level);
    }

    public SwitchAction(int nodeId, int endpointId, STATE desiredState) {
        this(nodeId, endpointId, desiredState, MAX_LEVEL);
    }

    private SwitchAction(int nodeId, int endpointId, STATE desiredState, int level) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.desiredState = desiredState;
        this.level = level;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public int getEndpointId() {
        return endpointId;
    }

    public STATE getDesiredState() {
        return desiredState;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "SwitchAction{" +
                "desiredState=" + desiredState +
                ", nodeId=" + nodeId +
                '}';
    }
}
