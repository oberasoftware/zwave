package com.oberasoftware.home.zwave.eventhandlers;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.ZWaveController;
import com.oberasoftware.home.zwave.api.ZWaveAction;
import com.oberasoftware.home.zwave.api.ZWaveIntervalAction;
import com.oberasoftware.home.zwave.api.ZWaveScheduler;
import com.oberasoftware.home.zwave.api.actions.devices.DeviceManufactorAction;
import com.oberasoftware.home.zwave.api.actions.devices.GenerateCommandClassPollAction;
import com.oberasoftware.home.zwave.api.actions.devices.IdentifyNodeAction;
import com.oberasoftware.home.zwave.api.actions.devices.MultiInstanceEndpointAction;
import com.oberasoftware.home.zwave.api.actions.devices.RequestNodeInfoAction;
import com.oberasoftware.home.zwave.api.events.controller.ControllerInitialDataEvent;
import com.oberasoftware.home.zwave.api.events.controller.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.events.controller.SendDataEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.api.events.devices.MultiInstanceDiscoveryEvent;
import com.oberasoftware.home.zwave.api.events.devices.NodeInfoReceivedEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.core.NodeStatus;
import com.oberasoftware.home.zwave.core.utils.EventSupplier;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.oberasoftware.home.zwave.core.NodeAvailability.AVAILABLE;
import static com.oberasoftware.home.zwave.core.NodeAvailability.AWAKE;
import static com.oberasoftware.home.zwave.core.NodeStatus.ACTIVE;
import static com.oberasoftware.home.zwave.core.NodeStatus.INITIALIZED;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class NodeEventHandler implements EventHandler {
    private static final Logger LOG = getLogger(NodeEventHandler.class);

    @Autowired
    private NodeManager nodeManager;

    @Autowired
    private ZWaveController zWaveController;

    @Autowired
    private ZWaveScheduler scheduler;

    private Map<Integer, Integer> outstandingNodeActions = new ConcurrentHashMap<>();

    private Map<String, ScheduledFuture<?>> pollingActions = new ConcurrentHashMap<>();

    @EventSubscribe
    public void receive(ControllerInitialDataEvent event) {
        LOG.debug("Received a initial controller data with node information: {}", event);

        event.getNodeIds().forEach(n -> {
            LOG.debug("Registering node: {}", n);
            nodeManager.registerNode(n);

            send(() -> new IdentifyNodeAction(n));
        });
    }

    @EventSubscribe
    public void receive(MultiInstanceDiscoveryEvent multiInstanceDiscoveryEvent) {
        LOG.info("Received multi instance device information: {}", multiInstanceDiscoveryEvent);

        int nodeId = multiInstanceDiscoveryEvent.getNodeId();
        nodeManager.registerEndpoints(multiInstanceDiscoveryEvent.getNodeId(), multiInstanceDiscoveryEvent.getEndpoints());
        Set<CommandClass> commandClasses = nodeManager.getNode(nodeId).getCommandClasses();

        multiInstanceDiscoveryEvent.getEndpoints().forEach(e -> {
            LOG.info("Registering node: {} endpoint: {} commandClasses: {}", nodeId, e, commandClasses);
            scheduleCommandClassPolling(multiInstanceDiscoveryEvent.getNodeId(), e, commandClasses);
        });
    }

    @EventSubscribe
    public void receiveNodeInformation(NodeIdentifyEvent nodeIdentifyEvent) {
        LOG.debug("Received node information: {}", nodeIdentifyEvent);

        int nodeId = nodeIdentifyEvent.getNodeId();

        nodeManager.setNodeInformation(nodeId, nodeIdentifyEvent);
        nodeManager.setNodeStatus(nodeId, NodeStatus.IDENTIFIED);
        LOG.debug("Received identity information for node: {}", nodeId);

        if(nodeId != zWaveController.getControllerId()) {
            int callbackId = send(() -> new RequestNodeInfoAction(nodeId));

            outstandingNodeActions.put(callbackId, nodeId);
        } else {
            LOG.debug("Received information for Controller: {}, advancing stage to: {}", nodeId, INITIALIZED);
            nodeManager.setNodeStatus(nodeId, INITIALIZED);
            nodeManager.setNodeAvailability(nodeId, AVAILABLE);
        }
    }

    @EventSubscribe
    public void receivedManufactorInformation(ManufactorInfoEvent manufactorInfoEvent) {
        LOG.debug("Received device manufacturer info: {} for node: {}", manufactorInfoEvent, manufactorInfoEvent.getNodeId());

        nodeManager.setNodeInformation(manufactorInfoEvent.getNodeId(), manufactorInfoEvent);
        nodeManager.setNodeStatus(manufactorInfoEvent.getNodeId(), NodeStatus.INITIALIZED);
    }

    @EventSubscribe
    public void receivePing(SendDataEvent sendDataEvent) {
        if(outstandingNodeActions.containsKey(sendDataEvent.getCallbackId())) {
            int nodeId = outstandingNodeActions.remove(sendDataEvent.getCallbackId());

            LOG.debug("Received a callback from node: {} for callback: {}", nodeId, sendDataEvent.getCallbackId());

            LOG.debug("Setting node: {} status to: {}", nodeId, ACTIVE);
            nodeManager.setNodeStatus(nodeId, ACTIVE);

            if(!nodeManager.isBatteryDevice(nodeId)) {
                nodeManager.setNodeAvailability(nodeId, AVAILABLE);
            } else {
                nodeManager.setNodeAvailability(nodeId, AWAKE);
            }

            send(() -> new DeviceManufactorAction(nodeId));
        }
    }

    @EventSubscribe
    public void receiveNodeInformation(NodeInfoReceivedEvent event) {
        LOG.info("Received node: {} command classes information: {}", event.getNodeId(), event.getCommandClasses());
        nodeManager.registerCommandClasses(event.getNodeId(), event.getCommandClasses());

        if(event.getCommandClasses().stream().anyMatch(c -> c == CommandClass.MULTI_INSTANCE)) {
            LOG.debug("Multi instance device, request multi instance endpoints");
            send(() -> new MultiInstanceEndpointAction(event.getNodeId()));
        } else {
            LOG.debug("Scheduling polling tasks for node: {}", event.getNodeId());
            scheduleCommandClassPolling(event.getNodeId(), 0, nodeManager.getNode(event.getNodeId()).getCommandClasses());
        }

        int nodeId = event.getNodeId();
//        if(nodeId != zWaveController.getControllerId()) {
//            int callbackId = send(() -> new RequestNodeInfoAction(nodeId));
//            outstandingNodeActions.put(callbackId, nodeId);
//
//            send(() -> new NodeNoOpAction(event.getNodeId()));
//        }
    }

    private void scheduleCommandClassPolling(int nodeId, int endpointId, Set<CommandClass> commandClassList) {
        commandClassList.forEach(c -> {
            if(c.isPollingSupported()) {
                String key = nodeId + ":" + endpointId + ":" + c.getLabel();
                if(!pollingActions.containsKey(key)) {
                    int refreshTime = c.getSecondsRefreshInterval();
                    ZWaveIntervalAction action = new GenerateCommandClassPollAction(nodeId, endpointId, c);
                    ScheduledFuture<?> future;
                    if (refreshTime > 0) {
                        LOG.info("Scheduling regular check for node: {} endpoint: {} commandClass: {}", nodeId, endpointId, c);
                        future = scheduler.schedule(action, TimeUnit.SECONDS, refreshTime);
                    } else {
                        LOG.info("Retrieving initial value from zwave device: {} endpoint: {} for command Class: {}", nodeId, endpointId, c);
                        future = scheduler.scheduleOnce(action);
                    }

                    pollingActions.put(key, future);
                }
            }
        });
    }

    public int getNodeId(int callbackId) {
        return outstandingNodeActions.getOrDefault(callbackId, -1);
    }

    private int send(EventSupplier<ZWaveAction> delegate) {
        try {
            return zWaveController.send(delegate.get());
        } catch(HomeAutomationException e) {
            LOG.error("Could not submit ZWaveController event", e);
        }

        return -1;
    }
}
