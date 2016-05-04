package com.nameless.nameless_game.render;

/**
 * Renderer is an abstract class which describes how a renderer should look
 * like.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-04
 */
public abstract class Renderer {

	/**
	 * Draws objects to screen. Once model is created, all entities should be
	 * passed as a parameter to renderer.
	 */
	public abstract void render();
}
