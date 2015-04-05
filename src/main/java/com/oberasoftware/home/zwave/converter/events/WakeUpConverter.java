package com.oberasoftware.home.zwave.converter.events;

import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.events.controller.ApplicationCommandEvent;
import com.oberasoftware.home.zwave.api.events.devices.WakeUpEvent;
import com.oberasoftware.home.zwave.api.events.devices.WakeUpIntervalReportEvent;
import com.oberasoftware.home.zwave.api.events.devices.WakeUpNoMoreInformationEvent;
import com.oberasoftware.home.zwave.api.events.devices.WakeUpReceivedEvent;
import com.oberasoftware.home.zwave.converter.SupportsConversion;
import com.oberasoftware.home.zwave.converter.ZWaveConverter;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static java.lang.Integer.toHexString;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class WakeUpConverter implements ZWaveConverter {
    private static final Logger LOG = getLogger(WakeUpConverter.class);

    private static final int WAKE_UP_INTERVAL_SET = 0x04;
    private static final int WAKE_UP_INTERVAL_GET = 0x05;
    private static final int WAKE_UP_INTERVAL_REPORT = 0x06;
    private static final int WAKE_UP_NOTIFICATION = 0x07;
    private static final int WAKE_UP_NO_MORE_INFORMATION = 0x08;
    private static final int WAKE_UP_INTERVAL_CAPABILITIES_GET = 0x09;
    private static final int WAKE_UP_INTERVAL_CAPABILITIES_REPORT = 0x0A;

    @EventSubscribe
    @SupportsConversion(commandClass = CommandClass.WAKE_UP)
    public WakeUpEvent convert(ApplicationCommandEvent source) throws HomeAutomationException {
        final byte[] payload = source.getPayload();
        final int command = payload[0];
        final int nodeId = source.getNodeId();

        LOG.debug("Received a wakeup command: {} for node: {}", source, nodeId);

        switch (command) {
            case WAKE_UP_INTERVAL_SET:
            case WAKE_UP_INTERVAL_GET:
            case WAKE_UP_INTERVAL_CAPABILITIES_GET:
            case WAKE_UP_NO_MORE_INFORMATION:
                LOG.debug("Wake-up command: {} not implemented for node: {}", toHexString(command), nodeId);
                return new WakeUpNoMoreInformationEvent(nodeId);
            case WAKE_UP_INTERVAL_REPORT:
                LOG.debug("Processing wake-up interval report for node: {} ignoring report, generating wake-up event", nodeId);

            case WAKE_UP_NOTIFICATION:
                LOG.debug("Processing wake-up notification for node: {}", nodeId);

                return new WakeUpReceivedEvent(nodeId);
            case WAKE_UP_INTERVAL_CAPABILITIES_REPORT:
                LOG.debug("Processing wake-up notification with capabilities report for node: {}", nodeId);

                int minInterval = (payload[1] << 16) | (payload[2] << 8) | (payload[3]);
                int maxInterval = (payload[4] << 16) | (payload[5] << 8) | (payload[6]);
                int defaultInterval = (payload[7] << 16) | (payload[8] << 8) | (payload[9]);
                int intervalStep = (payload[10] << 16) | (payload[11] << 8) | (payload[12]);

                LOG.debug("Node: {} interval report minimal interval: {} maximum interval: {} default interval: {} interval step: {}",
                        nodeId, minInterval, maxInterval, defaultInterval, intervalStep);

                return new WakeUpIntervalReportEvent(nodeId, minInterval, maxInterval, defaultInterval, intervalStep);
            default:
                LOG.debug("Unsupported wakeup command: {} was sent by node: {}", toHexString(command), nodeId);
        }


        return null;
    }
}
