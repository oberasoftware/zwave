package com.oberasoftware.home.zwave.api.actions.devices;

import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;
import com.oberasoftware.home.zwave.api.ZWaveIntervalAction;
import com.oberasoftware.home.zwave.api.messages.types.MeterScale;

import java.util.Optional;

/**
 * @author renarj
 */
public class MeterGetAction implements ZWaveDeviceAction, ZWaveIntervalAction {
    private final int nodeId;
    private final int endpointId;
    private final Optional<MeterScale> scale;

    public MeterGetAction(int nodeId, int endpointId, MeterScale scale) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.scale = Optional.of(scale);
    }

    public MeterGetAction(int nodeId, MeterScale scale) {
        this.nodeId = nodeId;
        this.scale = Optional.of(scale);
        this.endpointId = DEFAULT_ENDPOINTID;
    }

    public MeterGetAction(int nodeId, int endpointId) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.scale = Optional.empty();
    }

    public MeterGetAction(int nodeId) {
        this.nodeId = nodeId;
        this.endpointId = DEFAULT_ENDPOINTID;
        this.scale = Optional.empty();
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public Optional<MeterScale> getScale() {
        return scale;
    }

    public int getEndpointId() {
        return endpointId;
    }

    @Override
    public String toString() {
        return "MeterGetAction{" +
                "nodeId=" + nodeId +
                ", endpointId=" + endpointId +
                ", scale=" + scale +
                '}';
    }
}
