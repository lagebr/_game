package com.nameless.nameless_game.model;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * PhysicsHelper provides static helper methods for creating Box2D related
 * objects. The aim is to centralize commonly set values such as linear damping,
 * friction and restitution.
 * 
 * @author Isaac Arvestad
 * @version 2016-05-13
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
	 * Creates a body definition with a given position and body type. Linear
	 * damping is automatically set to 0.75f. Angular damping is also set to
	 * 0.75f.
	 * 
	 * @param x
	 *            The x position.
	 * @param y
	 *            The y position.
	 * @param type
	 *            The body type.
	 * @param fixedRotation
	 *            <ul>
	 *            	<li>True - body can not rotate</li>
	 *            	<li>False - body can rotate</li>
	 *            </ul>
	 * @return The body definition.
	 */
	public static BodyDef createBodyDef(float x, float y, BodyType type, boolean fixedRotation) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = fixedRotation;
		bodyDef.linearDamping = LINEAR_DAMPING;
		bodyDef.angularDamping = ANGULAR_DAMPING;

		return bodyDef;
	}
}
