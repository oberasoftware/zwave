package com.oberasoftware.home.zwave;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.ZWaveSession;
import com.oberasoftware.home.zwave.api.actions.SwitchAction;
import com.oberasoftware.home.zwave.api.events.ZWaveEvent;
import com.oberasoftware.home.zwave.api.events.devices.DeviceSensorEvent;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.local.LocalZwaveSession;
import org.slf4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
public class LocalZwaveSessionTest {
    private static final Logger LOG = getLogger(LocalZwaveSessionTest.class);

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        LOG.info("Starting Local ZWAVE App");
        try {
            doZwaveStuff();
        } catch (HomeAutomationException e) {
            LOG.error("", e);
        }
    }

    /**
     * Initialises the binding. This is called after the 'updated' method
     * has been called and all configuration has been passed.
     */
    public static void doZwaveStuff() throws HomeAutomationException {
        LOG.debug("Application startup");
        ZWaveSession s = new LocalZwaveSession();
        s.subscribe(new MyEventListener());

        scheduledExecutorService.scheduleAtFixedRate(() ->
                s.getDeviceManager().getNodes()
                        .forEach(n -> LOG.info("Node found: {} status: {} availability: {}", n.getNodeId(), n.getNodeStatus(),
                n.getAvailability()))
                , 30, 10, TimeUnit.SECONDS);


        while(!s.isNetworkReady()) {
            LOG.info("Network not ready yet, sleeping");
            sleepUninterruptibly(1, TimeUnit.SECONDS);
        }

        LOG.info("Network is ready, sending message");
        sleepUninterruptibly(5, TimeUnit.SECONDS);

        int nodeId = 13;
        int dimmerLevel = 50;
        s.doAction(new SwitchAction(() -> nodeId, SwitchAction.STATE.ON));
        s.doAction(new SwitchAction(() -> 7, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(() -> 7, dimmerLevel));

        LOG.info("Waiting a bit to switch off so we can see some visual effect");
        sleepUninterruptibly(10, TimeUnit.SECONDS);

        LOG.info("Wait over, sending Off message");
//        s.doAction(new SwitchAction(() -> nodeId, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(() -> 7, 0));
        s.doAction(new SwitchAction(() -> nodeId, SwitchAction.STATE.OFF));
        s.doAction(new SwitchAction(() -> 7, SwitchAction.STATE.OFF));

        LOG.info("Light off, preparing for shutdown in a bit");
        sleepUninterruptibly(3, TimeUnit.SECONDS);



//            s.schedule(new BatteryGetAction(6), TimeUnit.HOURS, 6);

//            s.shutdown();
    }

    public static class MyEventListener implements EventHandler {

        @EventSubscribe
        public void receive(ZWaveEvent event) throws Exception {
            LOG.debug("Received an event: {}", event);
        }

        @EventSubscribe
        public void handleSensorEvent(DeviceSensorEvent sensorEvent) {
            LOG.debug("Received a sensor: {} value: {} for node: {}", sensorEvent.getSensorType(), sensorEvent.getValue().doubleValue(), sensorEvent.getNodeId());
        }
    }
}
