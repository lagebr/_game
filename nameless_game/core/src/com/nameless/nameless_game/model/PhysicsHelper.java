package com.nameless.nameless_game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.nameless.nameless_game.render.ScreenGameRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * PhysicsHelper provides static helper methods for creating Box2D related
 * objects. The aim is to centralize commonly set values such as linear damping,
 * friction and restitution.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-13
 * 
 */
public class PhysicsHelper {

	private static final float LINEAR_DAMPING = 0.75f;
	private static final float ANGULAR_DAMPING = 0.75f;
	private static final float FRICTION = 0.4f;
	private static final float RESTITUTION = 0.6f;

	/**
	 * Creates a fixture definition with a given shape and density. Friction is
	 * automatically set to 0.4f and restitution is set to 0.6f. No collision
	 * masks are set.
	 * 
	 * @param shape
	 *            The shape of the fixture definition.
	 * @param density
	 *            The density of the fixture definition.
	 * @return The fixture definition.
	 */
	public static FixtureDef createFixture(Shape shape, float density) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = FRICTION;
		fixtureDef.restitution = RESTITUTION;

		return fixtureDef;
	}

	/**
	 * Creates a ent.getBody() definition with a given position and
	 * ent.getBody() type. Linear damping is automatically set to 0.75f. Angular
	 * damping is also set to 0.75f.
	 * 
	 * @param x
	 *            The x position.
	 * @param y
	 *            The y position.
	 * @param type
	 *            The ent.getBody() type.
	 * @param fixedRotation
	 *            <ul>
<<<<<<< HEAD
	 *            <li>True - ent.getBody() can not rotate</li>
	 *            <li>False - ent.getBody() can rotate</li>
=======
	 *            <li>True - body can not rotate</li>
	 *            <li>False - body can rotate</li>
>>>>>>> f7e2e8e7c84caaa3355eef5f52f592123357f552
	 *            </ul>
	 * @return The ent.getBody() definition.
	 */
	public static BodyDef createBodyDef(float x, float y, BodyType type,
			boolean fixedRotation) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = fixedRotation;
		bodyDef.linearDamping = LINEAR_DAMPING;
		bodyDef.angularDamping = ANGULAR_DAMPING;

		return bodyDef;
	}

	/**
	 * createBody creates a rectangular, static physics ent.getBody(), adds it
	 * to the physics world and returns it.
	 * 
	 * @param x
	 *            The x center of the ent.getBody() in physics meters.
	 * @param y
	 *            The y center of the ent.getBody() in physics meters.
	 * @param radius
	 *            The radius of the circle in physics meters.
	 * @param world
	 *            the world to add the ent.getBody() to
	 * @return the physics ent.getBody()
	 */
	public static Body createDynamicBody(float x, float y, float radius,
			World world) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y,
				BodyType.DynamicBody, false);
		bodyDef.angularDamping = 10.0f;
		bodyDef.linearDamping = 2.0f;

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
	public static Body createDynamicCircleBody(Entity objRef, float x, float y,
			float radius, World world) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y,
				BodyType.DynamicBody, false);
		Body physicsBody = world.createBody(bodyDef);

		CircleShape circle = new CircleShape();
		circle.setRadius(radius);

		FixtureDef fixtureDef = PhysicsHelper.createFixture(circle, 1.0f);

		// Collision masks
		fixtureDef.filter.categoryBits = Entity.NPC_ENTITY;
		fixtureDef.filter.maskBits = Entity.PLAYER_ENTITY | Entity.NPC_ENTITY;

		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(objRef); // this = method caller

		circle.dispose(); // openGL

		return physicsBody;
	}

	/**
	 * createStaticBody creates a rectangular, static physics body, adds it to
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
	public static Body createStaticBody(float x, float y, float width,
			float height, World world) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y, BodyType.StaticBody,
				true);

		Body physicsBody = world.createBody(bodyDef);

		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);

		FixtureDef fixtureDef = PhysicsHelper.createFixture(rectangle, 0.5f);

		// collision masks
		fixtureDef.filter.categoryBits = Entity.NPC_ENTITY;
		fixtureDef.filter.maskBits = Entity.PLAYER_ENTITY | Entity.NPC_ENTITY;

		physicsBody.createFixture(fixtureDef);

		rectangle.dispose();

		return physicsBody;
	}

	/**
	 * createBody creates a rectangular, dynamic physics ent.getBody(), adds it
	 * to the physics world and returns it.
	 * 
	 * @param x
	 *            The x center of the ent.getBody() in physics meters.
	 * @param y
	 *            The y center of the ent.getBody() in physics meters.
	 * @param width
	 *            The width of the ent.getBody() in physics meters.
	 * @param height
	 *            The height of the ent.getBody() in physics meters.
	 * @param world
	 *            the world to add the ent.getBody() to
	 * @return the physics ent.getBody()
	 */
	public static Body createDynamicBody(Hostile objRef, float x, float y,
			float width, float height, World world) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y,
				BodyType.DynamicBody, false);
		Body physicsBody = world.createBody(bodyDef);

		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);

		FixtureDef fixtureDef = PhysicsHelper.createFixture(rectangle, 10.0f);
		// Collision masks
		fixtureDef.filter.categoryBits = Entity.NPC_ENTITY;
		fixtureDef.filter.maskBits = Entity.PLAYER_ENTITY | Entity.NPC_ENTITY;

		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(objRef);

		rectangle.dispose(); // LibGDX

		return physicsBody;
	}
	
	/**
	 * Creates a dynamic triangle body.
	 * 
	 * @param x
	 *            Center x-coordinate of triangle in pixels.
	 * @param y
	 *            Center y-coordinate of triangle in pixels.
	 * @param world
	 *            The physics world to add it to.
	 * @return The create physics body.
	 */
	public static Body createDynamicTriangleBody(float x, float y, World world, Hostile self) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y,
				BodyType.DynamicBody, false);
		Body physicsBody = world.createBody(bodyDef);
		physicsBody.setFixedRotation(true);

		Vector2 p1 = new Vector2(0, 0);
		Vector2 p2 = new Vector2(ScreenGameRenderer.pixelToMeter(70), 0);
		Vector2 p3 = new Vector2(ScreenGameRenderer.pixelToMeter(35),
				ScreenGameRenderer.pixelToMeter(60));

		PolygonShape triangle = new PolygonShape();
		triangle.set(new Vector2[]{p1, p2, p3});

		FixtureDef fixtureDef = PhysicsHelper.createFixture(triangle, 10.0f);
		// Collision masks
		fixtureDef.filter.categoryBits = Entity.NPC_ENTITY;
		fixtureDef.filter.maskBits = Entity.PLAYER_ENTITY | Entity.NPC_ENTITY;

		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(self);

		triangle.dispose(); // LibGDX

		return physicsBody;
	}
}
