package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The entity model for the player character.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-05
 *
 */
public class Player extends Entity {

	public Player(float x, float y, float width, float height, Texture texture, World world) {
		super(x, y, width, height, texture, world);
		
	}
	

}
