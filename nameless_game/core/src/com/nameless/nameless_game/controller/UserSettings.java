package com.nameless.nameless_game.controller;

import com.badlogic.gdx.utils.Json;

/**
 * Responsible for writing and reading in user settings, using LibGDX's JSON
 * functionality.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2015-05-08
 *
 */
public class UserSettings {
	private static Json parser = new Json();
	
	public void read() {
		// TODO
	}

	public static void write() {
		// TODO
		System.out.println(parser.prettyPrint(GameInputProcessor.actionLookUp));

	}

}
