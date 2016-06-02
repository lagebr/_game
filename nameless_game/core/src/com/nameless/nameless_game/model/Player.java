package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * The entity model for the player character.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-05
 */
public class Player extends Entity {

	private boolean leftRotate = false;
	private boolean rightRotate = false;

	private boolean isBoosting = false;

	/**
	 * The player entity model creates a player entity in the world, with a
	 * dynamic physics body.
	 * 
	 * @param x
	 *            The x center of the body in number of pixels.
	 * @param y
	 *            The y center of the body in number of pixels.
	 * @param radius
	 *            The radius of the body in number of pixels.
	 * @param texture
	 *            The texture of the entity
	 * @param world
	 *            The world to add the body to
	 * @return the physics body
	 */
	public Player(float x, float y, float radius, Texture texture, World world) {
		super(texture);

		body = PhysicsHelper.createDynamicBody(ScreenGameRenderer.pixelToMeter(x), ScreenGameRenderer.pixelToMeter(y),
				ScreenGameRenderer.pixelToMeter(radius), world);
		
		updateSprite();
	}

	@Override
	public void update(float deltaTime) {
		updateSprite();
		
		if (leftRotate) {
			body.applyTorque(3.0f, true);
		}

		if (rightRotate) {
			body.applyTorque(-3.0f, true);
		}

		if (isBoosting) {
			float xForce = 10 * (float) Math.cos((double) body.getAngle());
			float yForce = 10 * (float) Math.sin((double) body.getAngle());

			body.applyForceToCenter(xForce, yForce, true);
		}
	}

	/**
	 * Applies a linear impulse to move the player forward. Only applies impulse
	 * if player is not in boost state.
	 * 
	 * @return
	 *         <ul>
	 *         <li>True - impulse was successfully applied.
	 *         <li>False - impulse was not added, player was in boost state.
	 *         </ul>
	 */
	public boolean impulseForward() {
		if (isBoosting == false) {
			float xImpulse = (float) Math.cos((double) body.getAngle());
			float yImpulse = (float) Math.sin((double) body.getAngle());

			body.applyLinearImpulse(new Vector2(xImpulse, yImpulse), body.getWorldCenter(), true);
			return true;
		} else {
			return false;
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

	public boolean leftRotate() {
		return leftRotate;
	}

	public void setLeftRotate(boolean leftRotate) {
		this.leftRotate = leftRotate;
	}

	public boolean rightRotate() {
		return rightRotate;
	}

	public void setRightRotate(boolean rightRotate) {
		this.rightRotate = rightRotate;
	}

	public boolean isBoosting() {
		return isBoosting;
	}

	public void setBoosting(boolean isBoosting) {
		this.isBoosting = isBoosting;
	}
}
