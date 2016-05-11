package com.nameless.nameless_game.controller;

import com.badlogic.gdx.utils.JsonReader;

/**
 * Parses the stored levels from their JSON format to turn them into Java Objects.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-11
 *
 */
public class LevelsParser {
	JsonReader reader = new JsonReader();
	String fileHandle;
	
	/**
	 * Setup the file to read the levels from.
	 * @param fileHandle 
	 */
	public LevelsParser(String fileHandle) {
		this.fileHandle = fileHandle;
	}

}
