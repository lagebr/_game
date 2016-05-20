package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * Charger spins and if Player is within the direction of Charger it will charge
 * towards after a slight delay.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-09
 */
public class ChargeHostileWithTarget extends HostileWithTarget {
	private double chargeDist = 0;
	private double v = 0;

	boolean isSleeping = false;
	private float timeSlept = 0;
	private float napTime = 5f;
	private float angVelocity = 1f;

	public ChargeHostileWithTarget(float x, float y, float radius, Texture texture, World world, Entity target) {
		super(texture);

		type = HostileType.CHARGE;
		body = createDynamicCircleBody(ScreenRenderer.pixelToMeter(x), ScreenRenderer.pixelToMeter(y),
				ScreenRenderer.pixelToMeter(radius), world);
		body.setFixedRotation(false);
		this.target = target;

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		body.setAngularVelocity(angVelocity);
		if (!isSleeping) {
			// Cartesian with origo in bottom left of Borders.
			double dx = body.getPosition().x - target.getBody().getPosition().x;
			double dy = body.getPosition().y - target.getBody().getPosition().y;

			v = Math.atan2(dy, dx) + Math.PI;
			chargeDist = Math.sqrt(dx * dx + dy * dy);
			// Check (radians) if target is in within field of view
			if (Math.abs(v - (float) (body.getAngle() % (2 * MathUtils.PI))) < 6 * MathUtils.PI / 180) {
				// When target is in sight delay, then charge
				isSleeping = true;
				angVelocity = 0.1f;
				// TODO removing testing code below
				System.out.println((body.getAngle() * 180 / Math.PI) % 360);
				System.out.println((v * 180 / Math.PI) % 360);
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

	/**
	 * Charges hostile with impulse in current direction, with a delay
	 * afterwards.
	 */
	private void charge() {
		float c = 1.7f * (float) (chargeDist);
		float xImpulse = c * MathUtils.cos(body.getAngle());
		float yImpulse = c * MathUtils.sin(body.getAngle());
		body.applyLinearImpulse(new Vector2(xImpulse, yImpulse), body.getWorldCenter(), true);

		float delay = 1f;
		Timer.schedule(new Task() {
			@Override
			public void run() {
				// Waiting.
			}
		}, delay);
	}
}
