/**
 * 
 */
package com.nameless.nameless_game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * Spins and if Player is in it's path after a delay it'll launch after Player.
 * TODO
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-09
 *
 */
public class ChargeHostileWithTarget extends HostileWithTarget {
	float screenWidth = Gdx.graphics.getWidth();
	float screenHeight = Gdx.graphics.getHeight();

	boolean isSleeping = false;

	private float timeSlept = 0;
	private float napTime = 5f;
	private float angVelocity = .5f;

	public ChargeHostileWithTarget(float x, float y, float radius, Texture texture, World world, Entity target) {
		super(texture);
		body = createDynamicBody(ScreenRenderer.pixelToMeter(x), ScreenRenderer.pixelToMeter(y),
				ScreenRenderer.pixelToMeter(radius), world);
		body.setFixedRotation(false);
		this.target = target;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		body.setAngularVelocity(angVelocity);
		if (!isSleeping) {
			double dx = target.getBody().getPosition().x - body.getPosition().x;
			double dy = target.getBody().getPosition().y - body.getPosition().y;
			double v = Math.atan(dy / dx);
			if (Math.abs(v - (float)(body.getAngle() % 2*MathUtils.PI)) < 4 * MathUtils.PI/180) {
				// When target is in sight delay then charge
				isSleeping = true;
				angVelocity = 0;
				}

		} else {
			timeSlept += deltaTime;
			if (timeSlept > napTime) {
				charge();
				// After charge return to start state
				isSleeping = false;
				angVelocity = 1;
				timeSlept = 0;
			}
		}

	}
	
	/** Charges hostile with impulse in current direction. */
	private void charge() {
		float c = 10;
		float xImpulse = c * MathUtils.cos(body.getAngle());
		float yImpulse = c * MathUtils.sin(body.getAngle());
		body.applyLinearImpulse(new Vector2(xImpulse, yImpulse), body.getWorldCenter(), true);
	}
}
