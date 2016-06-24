package com.nameless.nameless_game.model;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * PanicHostileWithTarget is a hostile which moves slowly and randomly around
 * the map. If the hostile is within a certain range of the target however, it
 * will panic and move fast in the opposite direction.
 * 
 * @author Isaac Arvestad
 * @version 2016-05-11
 */
public class PanicHostileWithTarget extends Hostile {

	private Random random = new Random();

	private float panicDistance;

	public PanicHostileWithTarget(float x, float y, float width, float height,
			Texture texture, World world, Entity target) {
		super(texture);

		this.target = target;

		type = HostileType.PANIC;

		body = PhysicsHelper.createDynamicBody(this,
				ScreenGameRenderer.pixelToMeter(x),
				ScreenGameRenderer.pixelToMeter(y),
				ScreenGameRenderer.pixelToMeter(width),
				ScreenGameRenderer.pixelToMeter(height), world);

		panicDistance = ScreenGameRenderer.pixelToMeter(200);
		
		updateSprite();

	}

	@Override
	public void update(float deltaTime) {
		updateSprite();

		double dx = body.getPosition().x - target.getBody().getPosition().x;
		double dy = body.getPosition().y - target.getBody().getPosition().y;
		float distance = (float) Math.sqrt(dx * dx + dy * dy);

		if (distance < panicDistance) {
			// Normalize and multiply by an arbitrary scalar
			float x = (float) (dx / Math.abs(dx) * 0.2);
			float y = (float) (dy / Math.abs(dy) * 0.2);

			body.applyLinearImpulse(new Vector2(x, y), body.getWorldCenter(),
					true);
		} else {
			float x = (float) (random.nextInt(2000) - 1000) / 5000f;
			float y = (float) (random.nextInt(2000) - 1000) / 5000f;
			body.applyLinearImpulse(new Vector2(x, y), body.getWorldCenter(),
					true);
		}
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
