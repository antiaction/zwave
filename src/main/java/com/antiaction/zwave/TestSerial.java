package com.antiaction.zwave;

import gnu.io.NRSerialPort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TestSerial {

	public static void main(String[] args) {
		for(String s:NRSerialPort.getAvailableSerialPorts()){
			System.out.println("Availible port: "+s);
		}

		String port = "COM3";
		int baudRate = 115200;
		NRSerialPort serial = new NRSerialPort(port, baudRate);
		serial.connect();

		DataInputStream ins = new DataInputStream(serial.getInputStream());
		DataOutputStream outs = new DataOutputStream(serial.getOutputStream());

		try {
			int b = ins.read();
			outs.write(b);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		serial.disconnect();
	}

}
