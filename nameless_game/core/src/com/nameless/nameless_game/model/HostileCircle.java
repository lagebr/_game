/**
 * 
 */
package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A type of Hostile with a sine-like move pattern.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-09
 *
 */
public class HostileCircle extends Hostile {
	private double progress = 0f;

	public HostileCircle(float x, float y, float width, float height, Texture texture, World world) {
		super(x, y, width, height, texture, world);

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		progress += 15.5 * deltaTime;
		// X := originX + cos(angle)*radius;
		// Y := originY + sin(angle)*radius;

		float forceY = (float)Math.sin((progress ) );
		float forceX = (float)Math.cos((progress ));
		body.applyLinearImpulse(new Vector2(forceX/100, forceY/100), body.getLocalCenter(), true);
	}
}