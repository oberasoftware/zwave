Oberasoftware ZWave Library for Java
=====

We have created an opensource ZWave library supporting controller devices based on the Sigma Designs Serial API. If you have a device
supporting this Serial API protocol most likely this library will work for you.

This ZWave library has been tested on the following controllers:

* Aeon Labs Z-Stick II
* RaZberry for Raspberry PI

Most likely but not confirmed works also on:

* Vision USB stick Z-wave
* Z-Wave.me Z-StickC
* Sigma UZB ZWave-Plus


Code Example
====
The following code example shows how to switch on a Switchable item using the local API. Once the light is on, in case it is dimmable we set the level to 50%. Finally we shut the switchable item back off again.

```java
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
            s.connect();

            while(!s.isNetworkReady()) {
                LOG.info("Network not ready yet, sleeping");
                sleepUninterruptibly(1, TimeUnit.SECONDS);
            }

            LOG.info("Wait over, sending message");

            int nodeId = 4;
            int dimmerLevel = 50;
            s.doAction(new SwitchAction(nodeId, SwitchAction.STATE.ON));
            s.doAction(new SwitchAction(nodeId, dimmerLevel));

            LOG.info("Waiting a bit to switch off so we can see some visual effect");
            sleepUninterruptibly(10, TimeUnit.SECONDS);

            LOG.info("Wait over, sending Off message");
            s.doAction(new SwitchAction(nodeId, SwitchAction.STATE.OFF));

            LOG.info("Light off, preparing for shutdown in a bit");
            sleepUninterruptibly(3, TimeUnit.SECONDS);

            s.shutdown();
        } catch (HomeAutomationException e) {
            LOG.error("Error occurred in ZWave processing", e);
        }
    }
}
```

With the following code you can for example receive generic events or with the annotated event subscriber we receive sensor events:
```java
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
            s.connect();
            s.subscribe(new MyEventListener());
        } catch (HomeAutomationException e) {
            LOG.error("Error occurred in ZWave processing", e);
        }
    }

    private static class MyEventListener implements EventHandler {

        @EventSubcribe
        public void receive(ZWaveEvent event) throws Exception {
            LOG.debug("Received an event: {}", event);
        }

        @EventSubscribe
        public void handleSensorEvent(DeviceSensorEvent sensorEvent) {
            LOG.debug("Received a sensor: {} value: {} for node: {}", sensorEvent.getSensorType(), sensorEvent.getValue().doubleValue(), sensorEvent.getNodeId());
        }
    }
}
```
