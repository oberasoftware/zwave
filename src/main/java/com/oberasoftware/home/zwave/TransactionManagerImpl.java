package com.oberasoftware.home.zwave;

import com.oberasoftware.base.event.EventBus;
import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.ZWaveAction;
import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;
import com.oberasoftware.home.zwave.api.events.controller.TransactionEvent;
import com.oberasoftware.home.zwave.api.actions.devices.WaitForWakeUpAction;
import com.oberasoftware.home.zwave.api.ControllerConnector;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.core.ZWaveNode;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.api.events.ActionConvertedEvent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import static com.oberasoftware.home.zwave.core.NodeAvailability.AWAKE;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
@Component
public class TransactionManagerImpl implements TransactionManager, EventHandler {
    private static final Logger LOG = getLogger(TransactionManagerImpl.class);

    @Autowired
    private ControllerConnector connector;

    @Autowired
    private NodeManager nodeManager;

    @Autowired
    private EventBus eventBus;

    private AtomicInteger callbackGenerator = new AtomicInteger(1);

    @Override
    public int startAction(ZWaveAction action) throws HomeAutomationException {
        if (action instanceof WaitForWakeUpAction) {
            WaitForWakeUpAction wakeUpEventAction = (WaitForWakeUpAction) action;
            LOG.debug("Starting a parked wake up event action: {} with callback: {}", action, wakeUpEventAction.getCallbackId());

            eventBus.publish(wakeUpEventAction.getDeviceAction(), wakeUpEventAction.getCallbackId());

            return wakeUpEventAction.getCallbackId();
        } else {
            int callbackId = getCallbackId();

            if (isDeviceReady(action)) {
                eventBus.publish(action, callbackId);
            } else {
                LOG.debug("Starting action on battery device: {} that could be asleep", action);
                eventBus.publish(new WaitForWakeUpAction((ZWaveDeviceAction) action, callbackId));
            }

            return callbackId;
        }
    }

    @EventSubscribe
    public void receiveSendEvent(ActionConvertedEvent convertedAction) throws HomeAutomationException {
        LOG.debug("Converted action: {} sending to controller", convertedAction);
        ZWaveRawMessage rawMessage = convertedAction.getRawMessage();
        rawMessage.setTransmitOptions(0x01 | 0x04 | 0x20);

        connector.send(rawMessage);
    }

    private boolean isDeviceReady(ZWaveAction action) {
        if(action instanceof ZWaveDeviceAction) {
            ZWaveNode node = nodeManager.getNode(((ZWaveDeviceAction) action).getNodeId());

            if(node != null) {
                boolean batteryDevice = nodeManager.isBatteryDevice(node.getNodeId());

                LOG.debug("This device: {} is a battery device: {}", ((ZWaveDeviceAction) action).getNodeId(), batteryDevice);

                //we can send if the device is a battery device that is awake, or if not a battery device at all
                return (batteryDevice && node.getAvailability() == AWAKE) || !batteryDevice;
            }
        }

        return true;
    }

    @EventSubscribe
    public void completeTransaction(TransactionEvent transactionEvent) throws HomeAutomationException {
        if(transactionEvent.isTransactionCompleted()) {
            LOG.debug("Received an event: {} that completes a transaction", transactionEvent);
            connector.completeTransaction();
        }
    }

    @Override
    public void cancelTransaction() throws HomeAutomationException {
        connector.completeTransaction();
    }

    private int getCallbackId() {
        int callbackId = callbackGenerator.incrementAndGet();
        if(callbackId > 0xFF) {
            callbackId = callbackGenerator.getAndSet(1);
        }
        return callbackId;
    }
}
