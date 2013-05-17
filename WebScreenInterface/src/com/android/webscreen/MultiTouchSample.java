package com.android.webscreen;

import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.core.IChimpDevice;
import com.android.chimpchat.core.TouchPressType;

public class MultiTouchSample {
	public static void main(String[] args) {
		// sdk/platform-tools has to be in PATH env variable in order to find adb
		IChimpDevice device = new AdbBackend().waitForConnection();
		
		// Print Device Name
		System.out.println(device.getProperty("build.model"));
		
		// Take a snapshot and save to out.png
		device.takeSnapshot().writeToFile("out.png", null);
		
		for (int i = 0; i < 10; i++) {
			device.touch(90, 265, TouchPressType.DOWN);
			sleep(50);
			device.touch(150, 265, TouchPressType.MOVE);
			sleep(50);
			device.touch(150, 365, TouchPressType.MOVE);
			sleep(50);
			device.touch(150, 365, TouchPressType.UP);
			sleep(1000);
		}
		
		device.dispose();
	}
	
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
}
