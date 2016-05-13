/**
 * 
 */
package com.nameless.nameless_game.model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

	Random random = new Random();
	private float timeSlept = 0;
	private float napTime = 1;

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
		if (!isSleeping) {
			body.setAngularVelocity(1);

			float v1 = 12;
			float v2 = 12;

			double dx = body.getPosition().x - target.getBody().getPosition().x;
			double dy = body.getPosition().y - target.getBody().getPosition().y;
			double v = Math.atan(dy / dx);

			if (Math.abs(v - (double) body.getAngle()) < 5 * Math.PI / 180) {

			}
		} else {
			timeSlept += deltaTime;
			if (timeSlept > napTime) {
				charge();
				timeSlept = 0;
				isSleeping = false;
			}
		}

	}

	private void charge() {
		
		
	}
}
