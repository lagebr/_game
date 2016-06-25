package com.nameless.nameless_game.model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * ChargeHostile spins and if Player is within the direction of ChargeHostile it
 * will charge towards after a slight delay.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-09
 */
public class ChargeHostile extends Hostile {
	private double chargeDist = 0;
	private double angle = 0;

	boolean isSleeping = false;
	private float timeSlept = 0;
	private float napTime = 2.0f;
	private float angVelocity = 2f;

	private float width = Gdx.graphics.getWidth();
	private float height = Gdx.graphics.getHeight();

	public ChargeHostile(float x, float y, float radius, Texture texture, World world, Entity target) {
		super(texture);

		this.target = target;

		type = HostileType.CHARGE;

		body = PhysicsHelper.createDynamicCircleBody(this, ScreenGameRenderer.pixelToMeter(x),
				ScreenGameRenderer.pixelToMeter(y), ScreenGameRenderer.pixelToMeter(radius), world);
		Random rnd = new Random();
		body.setTransform(body.getPosition(), rnd.nextFloat() * 2 * MathUtils.PI);

		body.setFixedRotation(false);

		updateSprite();
	}

	@Override
	public void update(float deltaTime) {
		updateSprite();

		body.setAngularVelocity(angVelocity);

		if (!isSleeping) {
			// Cartesian with origo in bottom left of Borders.
			double dx = body.getPosition().x - target.getBody().getPosition().x;
			double dy = body.getPosition().y - target.getBody().getPosition().y;

			angle = Math.atan2(dy, dx) + Math.PI;
			chargeDist = Math.sqrt(dx * dx + dy * dy);
			// Check (radians) if target is in within field of view
			if (Math.abs(angle - body.getAngle() % (2 * MathUtils.PI)) < 6 * MathUtils.PI / 180) {
				// When target is in sight delay, then charge
				isSleeping = true;

				napTime = 1 / 4 * (target.getBody().getPosition().dst(body.getPosition()) * -1.9f
						+ (float) Math.sqrt(width * width + height * height));
				angVelocity = 0.01f;
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
		if (chargeDist > Gdx.graphics.getWidth() / 2)
			chargeDist = Gdx.graphics.getWidth() / 2; // added ceiling

		float c = .8f * (float) Math.log((chargeDist));
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

	/**
	 * Updates the sprite position to correspond to the physics body position.
	 */
	@Override
	public void updateSprite() {
		float x = ScreenGameRenderer.meterToPixel(body.getPosition().x) - sprite.getWidth() / 2;
		float y = ScreenGameRenderer.meterToPixel(body.getPosition().y) - sprite.getHeight() / 2;

		sprite.setRotation(body.getAngle() * 180.0f / (float) Math.PI);
		sprite.setPosition(x, y);
	}
}
