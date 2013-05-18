package com.android.webscreen;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.android.chimpchat.ChimpManager;
import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.core.IChimpDevice;
import com.android.chimpchat.core.TouchPressType;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.ServerRunner;
import fi.iki.elonen.NanoHTTPD.Response.Status;

/**
 * An example of subclassing NanoHTTPD to make a custom HTTP server.
 */
public class ScreenServer extends NanoHTTPD {
    private static final String INDEX = "resources/index.html";
    
    private IChimpDevice device;

	private String staticIndex;

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

        if (uri.startsWith("/click")) {
        	int x = Integer.parseInt(parms.get("x"));
        	int y = Integer.parseInt(parms.get("y"));
        	device.touch(x, y, TouchPressType.DOWN_AND_UP);
        	return new NanoHTTPD.Response("OK");
        } else if (uri.startsWith("/key")) {
        	int keyCode = Integer.parseInt(parms.get("key"));
        	System.out.println("Key pressed: " + keyCode);
        	String keyName = KeyCodes.map(keyCode);
        	if (keyName != null) {
        		if (KeyCodes.shift) {
        			device.press("KEYCODE_SHIFT_LEFT", TouchPressType.DOWN);
        			device.press(keyName, TouchPressType.DOWN_AND_UP);
        			device.press("KEYCODE_SHIFT_LEFT", TouchPressType.UP);
        		} else {
        			device.press(keyName, TouchPressType.DOWN_AND_UP);
        		}
        	}
        	return new NanoHTTPD.Response("OK");
        } else if (uri.startsWith("/screen")) {
            byte[] imagedata = device.takeSnapshot().convertToBytes("png");
            return new NanoHTTPD.Response(Status.OK, "image/png", new ByteArrayInputStream(imagedata));
        } else {
			return new NanoHTTPD.Response(staticIndex);
        }
    }

    public static void main(String[] args) {
        ServerRunner.run(ScreenServer.class);
    }
}
