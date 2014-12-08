package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class WakeUpIntervalReportEvent extends WakeUpEvent {
    private final int minInterval;
    private final int maxInterval;
    private final int defaultInterval;
    private final int stepInterval;

    public WakeUpIntervalReportEvent(int nodeId, int minInterval, int maxInterval, int defaultInterval, int stepInterval) {
        super(nodeId);
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
        this.defaultInterval = defaultInterval;
        this.stepInterval = stepInterval;
    }

    public int getMinInterval() {
        return minInterval;
    }

    public int getMaxInterval() {
        return maxInterval;
    }

    public int getDefaultInterval() {
        return defaultInterval;
    }

    public int getStepInterval() {
        return stepInterval;
    }

    @Override
    public String toString() {
        return "WakeUpIntervalReportEvent{" +
                "minInterval=" + minInterval +
                ", maxInterval=" + maxInterval +
                ", defaultInterval=" + defaultInterval +
                ", stepInterval=" + stepInterval +
                '}';
    }
}
