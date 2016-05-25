package com.nameless.nameless_game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * RightAngleHostile is a hostile which moves randomly but is restricted to move in right angles. 
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-25
 */
public class RightAngleHostile extends Hostile {

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
	}
	
	/**
	 * createDynamicBody creates a rectangular, dynamic physics body, adds it to the
	 * physics world and returns it.
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
