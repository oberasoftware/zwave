package com.oberasoftware.home.zwave;

import com.oberasoftware.base.event.EventHandler;
import com.oberasoftware.base.event.EventSubscribe;
import com.oberasoftware.home.zwave.api.ZWaveSession;
import com.oberasoftware.home.zwave.api.actions.devices.GenerateCommandClassPollAction;
import com.oberasoftware.home.zwave.api.events.devices.DeviceSensorEvent;
import com.oberasoftware.home.zwave.api.events.devices.SwitchEvent;
import com.oberasoftware.home.zwave.api.events.devices.SwitchLevelEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.local.LocalZwaveSession;
import org.slf4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.SECONDS;
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
                        .forEach(n -> LOG.info("Node found: {} status: {} availability: {} endpoints: {}", n.getNodeId(), n.getNodeStatus(),
                                n.getAvailability(), n.getEndpoints()))
                , 30, 60, SECONDS);


//        while(!s.isNetworkReady()) {
//            LOG.info("Network not ready yet, sleeping");
//            sleepUninterruptibly(1, TimeUnit.SECONDS);
//        }

//        s.schedule(new MeterGetAction(14, 6, Electric_WATT), SECONDS, 10);

        LOG.info("Network is ready, sending message");
        sleepUninterruptibly(5, SECONDS);

//        s.doAction(new GenerateCommandClassPollAction(3, 0, CommandClass.SWITCH_BINARY));
//        s.doAction(new GenerateCommandClassPollAction(14, 1, CommandClass.METER));
        s.doAction(new GenerateCommandClassPollAction(3, 0, CommandClass.METER));

//        s.doAction(new MeterGetAction(14, 1, MeterScale.Electric_WATT));

//        s.doAction(new MultiInstanceEndpointAction(14));

//        s.doAction(new SwitchAction(7, SwitchAction.STATE.OFF));
//        s.doAction(new MeterGetAction(3, MeterScale.Electric_KWH));
//        s.doAction(new SwitchAction(14, 2, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(14, 3, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(14, 4, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(14, 5, SwitchAction.STATE.OFF));
////        s.doAction(new SwitchAction(15, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(14, 6, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(7, 50));
//        s.doAction(new SwitchAction(8, 50));


//        s.doAction(new SwitchMultiLevelGetAction(7));

//        s.doAction(new RequestNodeInfoAction(14));

        int nodeId = 13;
        int dimmerLevel = 50;
//        s.doAction(new SwitchAction(() -> nodeId, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(() -> 7, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(() -> 7, dimmerLevel));

        LOG.info("Waiting a bit to switch off so we can see some visual effect");
        sleepUninterruptibly(10, SECONDS);

//        s.doAction(new SwitchAction(3, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(15, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(14, 6, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(14, 1, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(14, 2, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(14, 3, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(14, 4, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(14, 5, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(14, 6, SwitchAction.STATE.ON));
//        s.doAction(new SwitchAction(7, 100));



//        s.doAction(new SwitchAction(() -> nodeId, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(() -> 7, 0));
//        s.doAction(new SwitchAction(() -> nodeId, SwitchAction.STATE.OFF));
//        s.doAction(new SwitchAction(() -> 7, SwitchAction.STATE.OFF));

        LOG.info("Actions done");
        sleepUninterruptibly(3, SECONDS);


    }

    public static class MyEventListener implements EventHandler {

//        @EventSubscribe
//        public void receive(ZWaveEvent event) throws Exception {
//            LOG.info("Received an event: {}", event);
//        }

        @EventSubscribe
        public void handleSensorEvent(DeviceSensorEvent sensorEvent) {
            LOG.info("Received a sensor: {} value: {} for node: {}", sensorEvent.getSensorType(), sensorEvent.getValue().doubleValue(), sensorEvent.getNodeId());
        }

        @EventSubscribe
        public void handleSwitchLevelEvent(SwitchLevelEvent event) {
            LOG.info("Received a switch level: {}", event);
        }

        @EventSubscribe
        public void handleBinarySwitch(SwitchEvent event) {
            LOG.info("Received a switch event: {}", event);
        }
    }
}
