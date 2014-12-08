package com.oberasoftware.home.zwave.core.impl;

import com.oberasoftware.home.zwave.core.NodeAvailability;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.core.NodeStatus;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author renarj
 */
public class NodeManagerTest {
    @Test
    public void testMinimalStatus() {
        NodeManager nodeManager = new NodeManagerImpl();
        nodeManager.registerNode(new ZWaveNodeImpl(1, NodeStatus.IDENTIFIED, NodeAvailability.AVAILABLE, Optional.empty(), Optional.empty()));
        nodeManager.registerNode(new ZWaveNodeImpl(2, NodeStatus.IDENTIFIED, NodeAvailability.AVAILABLE, Optional.empty(), Optional.empty()));
        nodeManager.registerNode(new ZWaveNodeImpl(3, NodeStatus.IDENTIFIED, NodeAvailability.AVAILABLE, Optional.empty(), Optional.empty()));

        assertThat(nodeManager.haveNodeMinimalStatus(NodeStatus.IDENTIFIED), is(true));

        nodeManager.registerNode(new ZWaveNodeImpl(4, NodeStatus.INITIALIZING, NodeAvailability.AVAILABLE, Optional.empty(), Optional.empty()));
        assertThat(nodeManager.haveNodeMinimalStatus(NodeStatus.IDENTIFIED), is(false));

        assertThat(nodeManager.haveNodeMinimalStatus(NodeStatus.ACTIVE), is(false));
        assertThat(nodeManager.haveNodeMinimalStatus(NodeStatus.IDENTIFIED), is(false));
    }
}
