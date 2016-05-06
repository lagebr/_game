package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.nameless.nameless_game.render.ScreenRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The entity model for the player character.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-05
 *
 */
public class Player extends Entity {

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
		super(texture); // calls alternative super-constructor from Entity

		body = createDynamicBody(ScreenRenderer.pixelToMeter(x), ScreenRenderer.pixelToMeter(y),
				ScreenRenderer.pixelToMeter(radius), world);
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
	private static Body createDynamicBody(float x, float y, float radius, World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		Body physicsBody = world.createBody(bodyDef);

		CircleShape circle = new CircleShape();
		circle.setRadius(radius);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; // hardy
		fixtureDef.friction = 0.4f; // frit
		fixtureDef.restitution = 0.6f; // bounce
		fixtureDef.filter.categoryBits = Entity.PLAYER_ENTITY; // this what I am
		fixtureDef.filter.maskBits = Entity.NPC_ENTITY; // this is what I
														// collide with
		physicsBody.createFixture(fixtureDef); // if activate makes Player run
												// out of screen

		circle.dispose(); // openGL

		return physicsBody;
	}
}
