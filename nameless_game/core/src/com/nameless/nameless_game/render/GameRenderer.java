package com.nameless.nameless_game.render;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.nameless.nameless_game.model.Hostile;

/**
 * Renderer is an abstract class which describes how a renderer should look
 * like.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-04
 * 
 */
public abstract class GameRenderer {

	/**
	 * Draws all entities to screen.
	 * 
	 * @param entities
	 *            The entities to be drawn.
	 */
	public abstract void renderHostiles(ArrayList<Hostile> hostiles);

	/**
	 * Draws all sprites to screen.
	 * 
	 * @param entities
	 *            The entities to be drawn.
	 */
	public abstract void renderKeySeq(ArrayList<Texture> keySeqTextureList);
}
