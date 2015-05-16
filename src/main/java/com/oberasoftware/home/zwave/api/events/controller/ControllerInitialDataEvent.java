package com.oberasoftware.home.zwave.api.events.controller;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author renarj
 */
public class ControllerInitialDataEvent implements TransactionEvent {
    public enum MODE {
        SLAVE,
        CONTROLLER
    }

    public enum CONTROLLER_TYPE {
        PRIMARY,
        SECONDARY
    }

    private final MODE mode;
    private final CONTROLLER_TYPE type;
    private final List<Integer> nodeIds;

    public ControllerInitialDataEvent(MODE mode, CONTROLLER_TYPE type, List<Integer> nodeIds) {
        this.mode = mode;
        this.type = type;
        this.nodeIds = Lists.newArrayList(nodeIds);
    }

    public MODE getMode() {
        return mode;
    }

    public CONTROLLER_TYPE getType() {
        return type;
    }

    public List<Integer> getNodeIds() {
        return Lists.newArrayList(nodeIds);
    }

    @Override
    public boolean isTransactionCompleted() {
        return true;
    }

    @Override
    public String toString() {
        return "ControllerInitialDataEvent{" +
                "mode=" + mode +
                ", type=" + type +
                ", nodeIds=" + nodeIds +
                '}';
    }
}
