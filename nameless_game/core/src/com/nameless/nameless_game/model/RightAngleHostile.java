package com.nameless.nameless_game.model;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

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

	public RightAngleHostile(float x, float y, float width, float height, Texture texture, World world) {
		super(x, y, width, height, texture, world);
		
		type = HostileType.RIGHT_ANGLE;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		// 10% chance of changing direction
		if (random.nextInt(100) < 5) {
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
	 * createDynamicBody creates a rectangular, dynamic physics body, adds it to
	 * the physics world and returns it.
	 * 
	 * @param x
	 *            The x center of the body in physics meters.
	 * @param y
	 *            The y center of the body in physics meters.
	 * @param width
	 *            The width of the body in physics meters.
	 * @param height
	 *            The height of the body in physics meters.
	 * @param world
	 *            the world to add the body to
	 * @return the physics body
	 */
	public Body createDynamicBody(float x, float y, float width, float height, World world) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y, BodyType.DynamicBody, false);
		bodyDef.fixedRotation = true;
		bodyDef.linearDamping = 5f;
		Body physicsBody = world.createBody(bodyDef);

		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);

		FixtureDef fixtureDef = PhysicsHelper.createFixture(rectangle, 10.0f);
		// Collision masks
		fixtureDef.filter.categoryBits = Entity.NPC_ENTITY;
		fixtureDef.filter.maskBits = Entity.PLAYER_ENTITY | Entity.NPC_ENTITY;

		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(this);

		rectangle.dispose(); // LibGDX

		return physicsBody;
	}
}
