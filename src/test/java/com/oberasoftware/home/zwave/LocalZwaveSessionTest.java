package com.oberasoftware.home.zwave;

import com.oberasoftware.home.zwave.api.ZWaveSession;
import com.oberasoftware.home.zwave.api.actions.devices.RequestNodeInfoAction;
import com.oberasoftware.home.zwave.api.events.EventListener;
import com.oberasoftware.home.zwave.api.events.Subscribe;
import com.oberasoftware.home.zwave.api.events.ZWaveEvent;
import com.oberasoftware.home.zwave.api.events.devices.DeviceSensorEvent;
import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.local.LocalZwaveSession;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author renarj
 */
public class LocalZwaveSessionTest {
    private static final Logger LOG = getLogger(LocalZwaveSessionTest.class);

    public static void main(String[] args) {
        LOG.info("Starting Local ZWAVE App");
        doZwaveStuff();
    }

    /**
     * Initialises the binding. This is called after the 'updated' method
     * has been called and all configuration has been passed.
     */
    public static void doZwaveStuff() {
        LOG.debug("Application startup");
        try {
            ZWaveSession s = new LocalZwaveSession();
            s.subscribe(new MyEventListener());


        s.doAction(new RequestNodeInfoAction(14));

            while(!s.isNetworkReady()) {
                LOG.info("Network not ready yet, sleeping");
                sleepUninterruptibly(1, TimeUnit.SECONDS);
            }

            LOG.info("Wait over, sending message");

            int nodeId = 7;
            int dimmerLevel = 50;
//            s.doAction(new SwitchAction(() -> nodeId, SwitchAction.STATE.ON));
//            s.doAction(new SwitchAction(() -> nodeId, dimmerLevel));

            LOG.info("Waiting a bit to switch off so we can see some visual effect");
            sleepUninterruptibly(10, TimeUnit.SECONDS);

            LOG.info("Wait over, sending Off message");
//            s.doAction(new SwitchAction(() -> nodeId, SwitchAction.STATE.OFF));

            LOG.info("Light off, preparing for shutdown in a bit");
            sleepUninterruptibly(3, TimeUnit.SECONDS);

            s.getDeviceManager().getNodes().forEach(n -> LOG.info("Node found: {}", n.getNodeId()));

//            s.schedule(new BatteryGetAction(6), TimeUnit.HOURS, 6);

//            s.shutdown();
        } catch (HomeAutomationException e) {
            LOG.error("Error occurred in ZWave processing", e);
        }
    }

    public static class MyEventListener implements EventListener<ZWaveEvent> {

        @Override
        public void receive(ZWaveEvent event) throws Exception {
            LOG.debug("Received an event: {}", event);
        }

        @Subscribe
        public void handleSensorEvent(DeviceSensorEvent sensorEvent) {
            LOG.debug("Received a sensor: {} value: {} for node: {}", sensorEvent.getSensorType(), sensorEvent.getValue().doubleValue(), sensorEvent.getNodeId());
        }
    }
}
