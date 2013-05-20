package com.android.webscreen;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.android.chimpchat.ChimpManager;
import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.core.IChimpDevice;
import com.android.chimpchat.core.IChimpImage;
import com.android.chimpchat.core.TouchPressType;
import com.android.webscreen.KeyCodes.AndroidKey;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.ServerRunner;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class ScreenServer extends NanoHTTPD {
    private static final String INDEX = "resources/index.html";
    
    private IChimpDevice device;

	private String staticIndex;

	private byte[] imagedata;

	public ScreenServer() {
        super(8080);
		URL url = Resources.getResource(INDEX);
		try {
			staticIndex = Resources.toString(url, Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Logger.getLogger(ChimpManager.class.getName()).setLevel(Level.OFF);
		System.out.println("Waiting for device...");
		device = new AdbBackend().waitForConnection();
		System.out.println("...done");		
    }

    @Override
    public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms, Map<String, String> files) {
        System.out.println(method + " '" + uri + "' ");

		if (uri.startsWith("/mousedown")) {
			sendTouch(parms, TouchPressType.DOWN);
		} else if (uri.startsWith("/mousemove")) {
			sendTouch(parms, TouchPressType.MOVE);
		} else if (uri.startsWith("/mouseup")) {
			sendTouch(parms, TouchPressType.UP);
        } else if (uri.startsWith("/key")) {
        	int keyCode = Integer.parseInt(parms.get("key"));
        	System.out.println("Key pressed: " + keyCode);
        	sendKey(keyCode);
        } else if (uri.startsWith("/screen")) {
        	updateSnapshot();
            return new NanoHTTPD.Response(Status.OK, "image/png", new ByteArrayInputStream(imagedata));
        } else {
			return new NanoHTTPD.Response(staticIndex);
        }
    	return new NanoHTTPD.Response("OK");
    }
    
    private void updateSnapshot() {
		IChimpImage img = device.takeSnapshot();
		if (img != null) {
			imagedata = img.convertToBytes("png");
		}
	}

	private void sendTouch(Map<String, String> parms, TouchPressType type) {
    	int x = Integer.parseInt(parms.get("x"));
    	int y = Integer.parseInt(parms.get("y"));
    	device.touch(x, y, type);
	}

	private void sendKey(int keyCode) {
    	AndroidKey key = KeyCodes.map(keyCode);
    	if (key != null) {
    		if (key.shift()) {
    			System.out.println("MAJ");
    			device.press("KEYCODE_SHIFT_LEFT", TouchPressType.DOWN);
    			device.press(key.getName(), TouchPressType.DOWN_AND_UP);
    			device.press("KEYCODE_SHIFT_LEFT", TouchPressType.UP);
    		} else {
    			device.press(key.getName(), TouchPressType.DOWN_AND_UP);
    		}
    	}
    }

    public static void main(String[] args) {
        ServerRunner.run(ScreenServer.class);
    }
}
