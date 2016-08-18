package com.nameless.nameless_game.model;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * RightAngleHostile is a hostile which moves randomly but is restricted to move
 * in right angles.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-25
 */
public class RightAngleHostile extends Hostile {

	private Random random = new Random();

	private int currentDirection;

	/**
	 * Constructs a RightAngleHostile at a certain position with a certain size
	 * and certain in a certain physics world
	 * 
	 * @param x
	 *            The x-position.
	 * @param y
	 *            The y-position.
	 * @param width
	 *            The width of the hostile.
	 * @param height
	 *            The height of the hostile.
	 * @param texture
	 *            The texture of the hostile.
	 * @param world
	 *            The physics world to place the hostile in.
	 */
	public RightAngleHostile(float x, float y, float width, float height, Texture texture, World world) {
		super(x, y, width, height, texture, world);

		type = HostileType.RIGHT_ANGLE;

		updateSprite();
	}

	@Override
	public void update(float deltaTime) {
		updateSprite();

		if (random.nextInt(100) < 5) {
			// 10% chance of changing direction
			currentDirection = random.nextInt(4);
		}

		float magnitude = 100;
		switch (currentDirection) {
		case 0:
			body.applyForceToCenter(new Vector2(-magnitude, 0), true);
			break;
		case 1:
			body.applyForceToCenter(new Vector2(0, magnitude), true);
			break;
		case 2:
			body.applyForceToCenter(new Vector2(magnitude, 0), true);
			break;
		case 3:
			body.applyForceToCenter(new Vector2(0, -magnitude), true);
			break;
		}
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
