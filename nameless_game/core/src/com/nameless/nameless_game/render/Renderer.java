package com.nameless.nameless_game.render;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;
import com.nameless.nameless_game.model.Entity;

/**
 * Renderer is an abstract class which describes how a renderer should look
 * like.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-04
 */
public abstract class Renderer {

	/**
	 * Draws all entities to screen.
	 * 
	 * @param entities
	 *            The entities to be drawn.
	 */
	public abstract void render(ArrayList<Entity> entities);
}
