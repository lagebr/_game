package com.nameless.nameless_game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * Charger spins and if Player is within the direction of Charger it will charge
 * towards after a slight delay.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-09
 */
public class ChargeHostileWithTarget extends HostileWithTarget {
	private double chargeDist = 0;
	private double angle = 0;

	boolean isSleeping = false;
	private float timeSlept = 0;
	private float napTime = 2.0f;
	private float angVelocity = 2f;

	private float width = Gdx.graphics.getWidth();
	private float height = Gdx.graphics.getHeight();

	public ChargeHostileWithTarget(float x, float y, float radius,
			Texture texture, World world, Entity target) {
		super(texture);
		this.target = target;
		type = HostileType.CHARGE;

		body = createDynamicCircleBody(ScreenGameRenderer.pixelToMeter(x),
				ScreenGameRenderer.pixelToMeter(y),
				ScreenGameRenderer.pixelToMeter(radius), world);

		body.setFixedRotation(false);

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		body.setAngularVelocity(angVelocity);
		if (!isSleeping) {
			// Cartesian with origo in bottom left of Borders.
			double dx = body.getPosition().x - target.getBody().getPosition().x;
			double dy = body.getPosition().y - target.getBody().getPosition().y;

			angle = Math.atan2(dy, dx) + Math.PI;
			chargeDist = Math.sqrt(dx * dx + dy * dy);
			// Check (radians) if target is in within field of view
			if (Math.abs(
					angle - (float) (body.getAngle() % (2 * MathUtils.PI))) < 6
							* MathUtils.PI / 180) {
				// When target is in sight delay, then charge
				isSleeping = true;

				napTime = 1/4*(target.getBody().getPosition()
						.dst(body.getPosition()) * -2.8f
						+ (float) Math.sqrt(width * width + height * height));
				angVelocity = 0.01f;
				// TODO removing testing code below
				// System.out.println((body.getAngle() * 180 / Math.PI) % 360);
				// System.out.println((angle * 180 / Math.PI) % 360);
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
		body.applyLinearImpulse(new Vector2(xImpulse, yImpulse),
				body.getWorldCenter(), true);

		float delay = 1f;
		Timer.schedule(new Task() {
			@Override
			public void run() {
				// Waiting.
			}
		}, delay);
	}
}
