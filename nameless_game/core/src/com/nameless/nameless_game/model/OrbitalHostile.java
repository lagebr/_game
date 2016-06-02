package com.nameless.nameless_game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * A hostile that orbits around the center of the screen.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 */
public class OrbitalHostile extends Hostile {
	Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2,
			Gdx.graphics.getHeight());

	/**
	 * 
	 */
	public OrbitalHostile(float x, float y, float width, float height,
			Texture texture, World world) {
		super(x, y, width, height, texture, world);
		
		updateSprite();
	}

	/**
	 * 
	 */
	@Override
	public void update(float deltaTime) {
		float G = 9.82f;
		float m1 = 2f;
		float m2 = 4f; // "planet"
		Vector2 m1_pos = body.getPosition();
		float r = m1_pos.dst(center); // calculate distance
		float a = (G * m1 * m2) / (r * r);
		// body.setLinearVelocity(v);
	}
	
	/**
	 * Updates the sprite position to correspond to the physics body position.
	 */
	@Override
	public void updateSprite() {
			float x = ScreenGameRenderer.meterToPixel(body.getPosition().x)
					- sprite.getWidth() / 2;
			float y = ScreenGameRenderer.meterToPixel(body.getPosition().y)
					- sprite.getHeight() / 2;

			sprite.setRotation(body.getAngle() * 180.0f / (float) Math.PI);
			sprite.setPosition(x, y);
		
	}
}
