package com.android.webscreen;

import java.util.HashMap;
import java.util.Map;

public class KeyCodes {
	private static final String[] KEY_NAMES = {
		"KEYCODE_UNKNOWN",
		"KEYCODE_SOFT_LEFT",
		"KEYCODE_SOFT_RIGHT",
		"KEYCODE_HOME",
		"KEYCODE_BACK",
		"KEYCODE_CALL",
		"KEYCODE_ENDCALL",
		"KEYCODE_0",
		"KEYCODE_1",
		"KEYCODE_2",
		"KEYCODE_3",
		"KEYCODE_4",
		"KEYCODE_5",
		"KEYCODE_6",
		"KEYCODE_7",
		"KEYCODE_8",
		"KEYCODE_9",
		"KEYCODE_STAR",
		"KEYCODE_POUND",
		"KEYCODE_DPAD_UP",
		"KEYCODE_DPAD_DOWN",
		"KEYCODE_DPAD_LEFT",
		"KEYCODE_DPAD_RIGHT",
		"KEYCODE_DPAD_CENTER",
		"KEYCODE_VOLUME_UP",
		"KEYCODE_VOLUME_DOWN",
		"KEYCODE_POWER",
		"KEYCODE_CAMERA",
		"KEYCODE_CLEAR",
		"KEYCODE_A",
		"KEYCODE_B",
		"KEYCODE_C",
		"KEYCODE_D",
		"KEYCODE_E",
		"KEYCODE_F",
		"KEYCODE_G",
		"KEYCODE_H",
		"KEYCODE_I",
		"KEYCODE_J",
		"KEYCODE_K",
		"KEYCODE_L",
		"KEYCODE_M",
		"KEYCODE_N",
		"KEYCODE_O",
		"KEYCODE_P",
		"KEYCODE_Q",
		"KEYCODE_R",
		"KEYCODE_S",
		"KEYCODE_T",
		"KEYCODE_U",
		"KEYCODE_V",
		"KEYCODE_W",
		"KEYCODE_X",
		"KEYCODE_Y",
		"KEYCODE_Z",
		"KEYCODE_COMMA",
		"KEYCODE_PERIOD",
		"KEYCODE_ALT_LEFT",
		"KEYCODE_ALT_RIGHT",
		"KEYCODE_SHIFT_LEFT",
		"KEYCODE_SHIFT_RIGHT",
		"KEYCODE_TAB",
		"KEYCODE_SPACE",
		"KEYCODE_SYM",
		"KEYCODE_EXPLORER",
		"KEYCODE_ENVELOPE",
		"KEYCODE_ENTER",
		"KEYCODE_DEL",
		"KEYCODE_GRAVE",
		"KEYCODE_MINUS",
		"KEYCODE_EQUALS",
		"KEYCODE_LEFT_BRACKET",
		"KEYCODE_RIGHT_BRACKET",
		"KEYCODE_BACKSLASH",
		"KEYCODE_SEMICOLON",
		"KEYCODE_APOSTROPHE",
		"KEYCODE_SLASH",
		"KEYCODE_AT",
		"KEYCODE_NUM",
		"KEYCODE_HEADSETHOOK",
		"KEYCODE_FOCUS",
		"KEYCODE_PLUS",
		"KEYCODE_MENU",
		"KEYCODE_NOTIFICATION",
		"KEYCODE_SEARCH",
		"KEYCODE_MEDIA_PLAY_PAUSE",
		"KEYCODE_MEDIA_STOP",
		"KEYCODE_MEDIA_NEXT",
		"KEYCODE_MEDIA_PREVIOUS",
		"KEYCODE_MEDIA_REWIND",
		"KEYCODE_MEDIA_FAST_FORWARD",
		"KEYCODE_MUTE",
	};
	
	private static final Map<String, Integer> NAME_TO_CODE = new HashMap<String, Integer>() {{
		for (int i = 0; i < KEY_NAMES.length; i++) {
			put(KEY_NAMES[i], i);
		}
	}};
	
	private static final Map<Integer, String> TRANSLATE = new HashMap<Integer, String>() {{
		put(8, "KEYCODE_DEL");
		put(13, "KEYCODE_ENTER");
		put(27, "KEYCODE_BACK");
		put(36, "KEYCODE_HOME");
		put(37, "KEYCODE_DPAD_LEFT");
		put(38, "KEYCODE_DPAD_UP");
		put(39, "KEYCODE_DPAD_RIGHT");
		put(40, "KEYCODE_DPAD_DOWN");
		put(32, "KEYCODE_SPACE");
		put(44, "KEYCODE_COMMA");
		put(46, "KEYCODE_PERIOD");
	}};

	public static AndroidKey map(int keyCode) {
		String keyName;
		boolean shift = false;
		if (keyCode >= '0' && keyCode <= '9') {
			keyName = KEY_NAMES[keyCode - '0' + NAME_TO_CODE.get("KEYCODE_0")];
		} else if (keyCode >= 'a' && keyCode <= 'z') {
    		keyName = KEY_NAMES[keyCode - 'a' + NAME_TO_CODE.get("KEYCODE_A")];
		} else if (keyCode >= 'A' && keyCode <= 'Z') {
    		keyName = KEY_NAMES[keyCode - 'A' + NAME_TO_CODE.get("KEYCODE_A")];
    		shift = true;
    	} else {
    		keyName = TRANSLATE.get(keyCode);
    	}
    	if (keyName == null) {
    		return null;
    	} else {
    		return new AndroidKey(keyName, shift);
    	}
	}
	
	public static class AndroidKey {
		private final String name;
		private final boolean shift;
		
		private AndroidKey(String name, boolean shift) {
			this.name = name;
			this.shift = shift;
		}

		public String getName() {
			return name;
		}

		public boolean shift() {
			return shift;
		}
	}
}
