package com.nameless.nameless_game.model;

import java.util.Random;

import org.junit.experimental.theories.Theories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * The entity model for the hostile entities in the game world.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2015-05-06
 *
 */
public class Hostile extends Entity {
	private Random random = new Random(); // TODO Use LibGDX own random.
	
	protected HostileType type;
	
	public HostileType getType() {
		return type;
	}

	public void setType(HostileType type) {
		this.type = type;
	}

	public Hostile(float x, float y, float width, float height, Texture texture, World world) {
		super(texture);

		//body = createDynamicBody(ScreenRenderer.pixelToMeter(x), ScreenRenderer.pixelToMeter(y),
				//ScreenRenderer.pixelToMeter(width), ScreenRenderer.pixelToMeter(height), world);
	}

	/**
	 * The empty constructor.
	 */
	public Hostile() {
		// Intentionally left empty.
	}

	public Hostile(Texture texture) {
		sprite = new Sprite(texture);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		float impulseX = ((float) random.nextInt(2000) - 1000) / 50000;
		float impulseY = ((float) random.nextInt(2000) - 1000) / 50000;

		body.applyLinearImpulse(new Vector2(impulseX, impulseY), body.getLocalCenter(), true);
	}

	/**
	 * createBody creates a rectangular, dynamic physics body, adds it to the
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

	/**
	 * createBody creates a circular, dynamic physics body, adds it to the
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
	public Body createDynamicBody(float x, float y, float radius, World world) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y, BodyType.DynamicBody, false);
		Body physicsBody = world.createBody(bodyDef);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(radius);

		FixtureDef fixtureDef = PhysicsHelper.createFixture(circle, 1.0f);
				
		// Collision masks
		fixtureDef.filter.categoryBits = Entity.NPC_ENTITY;
		fixtureDef.filter.maskBits = Entity.PLAYER_ENTITY | Entity.NPC_ENTITY;
		
		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(this);
		
		circle.dispose(); // openGL

		return physicsBody;
	}
}
