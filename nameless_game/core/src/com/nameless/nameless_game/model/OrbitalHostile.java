package com.nameless.nameless_game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A hostile that orbits around the center of the screen.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 */
public class OrbitalHostile extends Hostile {
	Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());

	/**
	 * 
	 */
	public OrbitalHostile(float x, float y, float width, float height, Texture texture, World world) {
		super(x, y, width, height, texture, world);
	}

	/**
	 * 
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float G = 9.82f;
		float m1 = 2f;
		float m2 = 4f; // "planet"
		Vector2 m1_pos = body.getPosition();
		float r = m1_pos.dst(center); // calculate distance
		float a = (G * m1 * m2) / (r * r);
		// body.setLinearVelocity(v);
	}
}
