package com.oberasoftware.home.zwave;

/**
 * @author renarj
 */
public interface ZWAVE_CONSTANTS {
    int SOF = 0x01;
    int ACK = 0x06;
    int NAK = 0x15;
    int CAN = 0x18;

    /**
     * BINARY SWITCH CONSTANTS
     */
    int SWITCH_BINARY_SET = 0x01; //set a value


    int ZWAVE_RESPONSE_TIMEOUT = 5000;		// 5000 ms ZWAVE_RESPONSE TIMEOUT

    int ZWAVE_RECEIVE_TIMEOUT = 1000;		// 1000 ms ZWAVE_RECEIVE_TIMEOUT
}
