package com.nameless.nameless_game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nameless.nameless_game.controller.NamelessGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		config.foregroundFPS = 60;
		config.resizable = false;
		new LwjglApplication(new NamelessGame(), config);
	}
}
