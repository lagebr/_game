package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.nameless.nameless_game.render.ScreenGameRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

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
	public Player(float x, float y, float radius, Texture texture,
			World world) {
		super(texture);

		body = createDynamicBody(ScreenGameRenderer.pixelToMeter(x),
				ScreenGameRenderer.pixelToMeter(y),
				ScreenGameRenderer.pixelToMeter(radius), world);
		updateSpritePosition();
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

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

			body.applyLinearImpulse(new Vector2(xImpulse, yImpulse),
					body.getWorldCenter(), true);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * createBody creates a rectangular, static physics body, adds it to the
	 * physics world and returns it.
	 * 
	 * @param x
	 *            The x center of the body in physics meters.
	 * @param y
	 *            The y center of the body in physics meters.
	 * @param radius
	 *            The radius of the circle in physics meters.
	 * @param world
	 *            the world to add the body to
	 * @return the physics body
	 */
	public static Body createDynamicBody(float x, float y, float radius,
			World world) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y,
				BodyType.DynamicBody, false);
		bodyDef.angularDamping = 5.0f;
		bodyDef.linearDamping = 1.0f;

		Body physicsBody = world.createBody(bodyDef);

		CircleShape circle = new CircleShape();
		circle.setRadius(radius);

		FixtureDef fixtureDef = PhysicsHelper.createFixture(circle, 0.85f);
		fixtureDef.friction = 0.0f;
		// collision masks
		fixtureDef.filter.categoryBits = Entity.PLAYER_ENTITY;
		fixtureDef.filter.maskBits = Entity.NPC_ENTITY;

		physicsBody.createFixture(fixtureDef);

		circle.dispose();

		return physicsBody;
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
