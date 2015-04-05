package com.oberasoftware.home.zwave.messages.types;

/**
 * The enum was copied from the openhab zwave addon as it was functionally mostly complete
 *
 * @author Renze de Vries
 */
public enum ControllerMessageType {
    SerialApiGetInitData(0x02,"SerialApiGetInitData"),									// Request initial information about devices in network
    SerialApiApplicationNodeInfo(0x03,"SerialApiApplicationNodeInfo"),					// Set controller node information
    ApplicationCommandHandler(0x04,"ApplicationCommandHandler"),						// Handle application command
    GetControllerCapabilities(0x05,"GetControllerCapabilities"),						// Request controller capabilities (primary role, SUC/SIS availability)
    SerialApiSetTimeouts(0x06,"SerialApiSetTimeouts"),									// Set Serial API timeouts
    GetCapabilities(0x07,"GetCapabilities"),							                // Request Serial API capabilities from the controller
    SerialApiSoftReset(0x08,"SerialApiSoftReset"),										// Soft reset. Restarts Z-Wave chip
    RfReceiveMode(0x10,"RfReceiveMode"),												// Power down the RF section of the stick
    SetSleepMode(0x11,"SetSleepMode"),													// Set the CPU into sleep mode
    SendNodeInfo(0x12,"SendNodeInfo"),													// Send Node Information Frame of the stick
    SendData(0x13,"SendData"),															// Send data.
    SendDataMulti(0x14, "SendDataMulti"),
    GetVersion(0x15,"GetVersion"),														// Request controller hardware version
    SendDataAbort(0x16,"SendDataAbort"),												// Abort Send data.
    RfPowerLevelSet(0x17,"RfPowerLevelSet"),											// Set RF Power level
    SendDataMeta(0x18, "SendDataMeta"),
    GetRandom(0x1c,"GetRandom"),														// ???
    MemoryGetId(0x20,"MemoryGetId"),													// ???
    MemoryGetByte(0x21,"MemoryGetByte"),												// Get a byte of memory.
    MemoryPutByte(0x22, "MemoryPutByte"),
    ReadMemory(0x23,"ReadMemory"),														// Read memory.
    WriteMemory(0x24, "WriteMemory"),
    SetLearnNodeState(0x40,"SetLearnNodeState"),    									// ???
    IdentifyNode(0x41,"IdentifyNode"),    												// Get protocol info (baud rate, listening, etc.) for a given node
    SetDefault(0x42,"SetDefault"),    													// Reset controller and node info to default (original) values
    NewController(0x43,"NewController"),												// ???
    ReplicationCommandComplete(0x44,"ReplicationCommandComplete"),						// Replication send data complete
    ReplicationSendData(0x45,"ReplicationSendData"),									// Replication send data
    AssignReturnRoute(0x46,"AssignReturnRoute"),										// Assign a return route from the specified node to the controller
    DeleteReturnRoute(0x47,"DeleteReturnRoute"),										// Delete all return routes from the specified node
    RequestNodeNeighborUpdate(0x48,"RequestNodeNeighborUpdate"),						// Ask the specified node to update its neighbors (then read them from the controller)
    ApplicationUpdate(0x49,"ApplicationUpdate"),										// Get a list of supported (and controller) command classes
    AddNodeToNetwork(0x4a,"AddNodeToNetwork"),											// Control the addnode (or addcontroller) process...start, stop, etc.
    RemoveNodeFromNetwork(0x4b,"RemoveNodeFromNetwork"),								// Control the removenode (or removecontroller) process...start, stop, etc.
    CreateNewPrimary(0x4c,"CreateNewPrimary"),											// Control the createnewprimary process...start, stop, etc.
    ControllerChange(0x4d,"ControllerChange"),    										// Control the transferprimary process...start, stop, etc.
    SetLearnMode(0x50,"SetLearnMode"),													// Put a controller into learn mode for replication/ receipt of configuration info
    AssignSucReturnRoute(0x51,"AssignSucReturnRoute"),									// Assign a return route to the SUC
    EnableSuc(0x52,"EnableSuc"),														// Make a controller a Static Update Controller
    RequestNetworkUpdate(0x53,"RequestNetworkUpdate"),									// Network update for a SUC(?)
    SetSucNodeID(0x54,"SetSucNodeID"),													// Identify a Static Update Controller node id
    DeleteSUCReturnRoute(0x55,"DeleteSUCReturnRoute"),									// Remove return routes to the SUC
    GetSucNodeId(0x56,"GetSucNodeId"),													// Try to retrieve a Static Update Controller node id (zero if no SUC present)
    SendSucId(0x57, "SendSucId"),
    RequestNodeNeighborUpdateOptions(0x5a,"RequestNodeNeighborUpdateOptions"),   		// Allow options for request node neighbor update
    RequestNodeInfo(0x60,"RequestNodeInfo"),											// Get info (supported command classes) for the specified node
    RemoveFailedNodeID(0x61,"RemoveFailedNodeID"),										// Mark a specified node id as failed
    IsFailedNodeID(0x62,"IsFailedNodeID"),												// Check to see if a specified node has failed
    ReplaceFailedNode(0x63,"ReplaceFailedNode"),										// Remove a failed node from the controller's list (?)
    GetRoutingInfo(0x80,"GetRoutingInfo"),												// Get a specified node's neighbor information from the controller
    LockRoute(0x90, "LockRoute"),
    SerialApiSlaveNodeInfo(0xA0,"SerialApiSlaveNodeInfo"),								// Set application virtual slave node information
    ApplicationSlaveCommandHandler(0xA1,"ApplicationSlaveCommandHandler"),				// Slave command handler
    SendSlaveNodeInfo(0xA2,"ApplicationSlaveCommandHandler"),							// Send a slave node information frame
    SendSlaveData(0xA3,"SendSlaveData"),												// Send data from slave
    SetSlaveLearnMode(0xA4,"SetSlaveLearnMode"),										// Enter slave learn mode
    GetVirtualNodes(0xA5,"GetVirtualNodes"),											// Return all virtual nodes
    IsVirtualNode(0xA6,"IsVirtualNode"),												// Virtual node test
    WatchDogEnable(0xB6, "WatchDogEnable"),
    WatchDogDisable(0xB7, "WatchDogDisable"),
    WatchDogKick(0xB6, "WatchDogKick"),
    RfPowerLevelGet(0xBA,"RfPowerLevelSet"),											// Get RF Power level
    GetLibraryType(0xBD, "GetLibraryType"),												// Gets the type of ZWave library on the stick
    SendTestFrame(0xBE, "SendTestFrame"),												// Send a test frame to a node
    GetProtocolStatus(0xBF, "GetProtocolStatus"),
    SetPromiscuousMode(0xD0,"SetPromiscuousMode"),										// Set controller into promiscuous mode to listen to all frames
    PromiscuousApplicationCommandHandler(0xD1,"PromiscuousApplicationCommandHandler"),
    ALL(-1, null);

    private int key;
    private String label;

    private ControllerMessageType(int key, String label) {
        this.key = key;
        this.label = label;
    }

    /**
     * Returns the serjal hex key.
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * Returns the functional label.
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}