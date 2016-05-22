package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A hostile that orbits around the center of the screen.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 */
public class OrbitalHostile extends Hostile {

	/**
	 * 
	 */
	public OrbitalHostile(float x, float y, float width, float height,
			Texture texture, World world) {
		super(x, y, width, height, texture, world);
	}

	/**
	 * 
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		
	}
}
