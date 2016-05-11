/**
 * 
 */
package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * A type of Hostile with a sine-like move pattern.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-09
 *
 */
public class HostileCircle extends Hostile {
	private double progression = 0f;

	public HostileCircle(float x, float y, float radius, Texture texture, World world) {
		super(texture);
		body = createDynamicBody(ScreenRenderer.pixelToMeter(x), ScreenRenderer.pixelToMeter(y),
				ScreenRenderer.pixelToMeter(radius), world);
		body.setFixedRotation(false);

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		// body.setLinearVelocity(1, 1);
		progression += deltaTime;

		float forceY = (float) (Math.sin((progression))) / 10;
		float forceX = (float) (Math.cos((progression))) / 10;
		body.applyLinearImpulse(new Vector2(forceX, 0), body.getLocalCenter(), true);
		if (progression > 360)
			progression = 0;
	}
}
