package com.redpois0n.gscrot.keys;

import java.util.ArrayList;
import java.util.List;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.redpois0n.gscrot.Config;

public class KeyListener implements NativeKeyListener {
	
	private static final List<Integer> pressed = new ArrayList<Integer>();
	
	public static boolean isPressed(int keycode) {
		return pressed.contains(keycode);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		pressed.add(e.getKeyCode());
		
		for (KeyBinding kb : Config.KEYBINDINGS) {
			boolean trigger = false;
			for (int i : kb.getKeys()) {
				if (pressed.contains(i)) {
					trigger = true;
				} else {
					trigger = false;
					break;
				}
			}
			
			if (trigger) {
				kb.trigger();
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		for (int i = 0; i < pressed.size(); i++) {
			if (pressed.get(i).equals(e.getKeyCode())) {
				pressed.remove(i);
			}
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

	}

}
