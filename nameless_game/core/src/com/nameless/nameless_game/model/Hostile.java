package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The entity model for the hostile entities in the game world. 
 * 
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2015-05-06
 *
 */
public class Hostile extends Entity {

	public Hostile(float x, float y, float width, float height, Texture texture, World world) {
		super(x, y, width, height, texture, world);
		// TODO Auto-generated constructor stub
	}
}
