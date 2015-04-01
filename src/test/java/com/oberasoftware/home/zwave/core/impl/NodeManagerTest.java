package com.oberasoftware.home.zwave.core.impl;

import com.oberasoftware.home.zwave.api.events.EventBus;
import com.oberasoftware.home.zwave.api.events.devices.NodeRegisteredEvent;
import com.oberasoftware.home.zwave.core.NodeAvailability;
import com.oberasoftware.home.zwave.core.NodeStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author renarj
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeManagerTest {

    @Mock
    private EventBus bus;

    @InjectMocks
    private NodeManagerImpl nodeManager = new NodeManagerImpl();

    @Test
    public void testMinimalStatus() {
        nodeManager.registerNode(new ZWaveNodeImpl(1, NodeStatus.IDENTIFIED, NodeAvailability.AVAILABLE, Optional.empty(), Optional.empty()));
        nodeManager.registerNode(new ZWaveNodeImpl(2, NodeStatus.IDENTIFIED, NodeAvailability.AVAILABLE, Optional.empty(), Optional.empty()));
        nodeManager.registerNode(new ZWaveNodeImpl(3, NodeStatus.IDENTIFIED, NodeAvailability.AVAILABLE, Optional.empty(), Optional.empty()));

        assertThat(nodeManager.haveNodeMinimalStatus(NodeStatus.IDENTIFIED), is(true));

        nodeManager.registerNode(new ZWaveNodeImpl(4, NodeStatus.INITIALIZING, NodeAvailability.AVAILABLE, Optional.empty(), Optional.empty()));
        assertThat(nodeManager.haveNodeMinimalStatus(NodeStatus.IDENTIFIED), is(false));

        assertThat(nodeManager.haveNodeMinimalStatus(NodeStatus.ACTIVE), is(false));
        assertThat(nodeManager.haveNodeMinimalStatus(NodeStatus.IDENTIFIED), is(false));
    }

    @Test
    public void testNotifications() {
        nodeManager.registerNode(new ZWaveNodeImpl(1, NodeStatus.IDENTIFIED, NodeAvailability.AVAILABLE, Optional.empty(), Optional.empty()));

        ArgumentCaptor<NodeRegisteredEvent> newDeviceEventCaptor = ArgumentCaptor.forClass(NodeRegisteredEvent.class);
        verify(bus, times(1)).pushAsync(newDeviceEventCaptor.capture());

        NodeRegisteredEvent event = newDeviceEventCaptor.getValue();
        assertThat(event.getNodeId(), is(1));
        assertThat(event.getNode().getAvailability(), is(NodeAvailability.AVAILABLE));
    }
}
