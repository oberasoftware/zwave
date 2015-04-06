/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.oberasoftware.home.zwave.api.messages.types;

/**
 * Transmission state enumeration. Indicates the transmission state of the message to the node.
 */
public enum TransmissionState {
	COMPLETE_OK(0x00, "Transmission complete and ACK received"),
	COMPLETE_NO_ACK(0x01, "Transmission complete, no ACK received"),
	COMPLETE_FAIL(0x02, "Transmission failed"),
	COMPLETE_NOT_IDLE(0x03, "Transmission failed, network busy"),
	COMPLETE_NOROUTE(0x04, "Tranmission complete, no return route");
	
	private int key;
	private String label;

	private TransmissionState(int key, String label) {
		this.key = key;
		this.label = label;
	}

	/**
	 * Lookup function based on the transmission state code.
	 * Returns null when there is no transmission state with code i.
	 * @param i the code to lookup
	 * @return enumeration value of the transmission state.
	 */
	public static TransmissionState getTransmissionState(int i) {
		for(TransmissionState state : values()) {
			if(state.getKey() == i) {
				return state;
			}
		}

		return COMPLETE_FAIL;
	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
}
