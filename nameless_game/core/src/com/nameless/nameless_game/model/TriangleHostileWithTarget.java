package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * TriangleHostileWithTarget is a Hostile which constantly follows the player.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-24
 */
public class TriangleHostileWithTarget extends Hostile {

	public TriangleHostileWithTarget(float x, float y, Texture texture,
			World world, Entity target) {
		super(texture);

		this.target = target;

		type = HostileType.TRIANGLE;

		body = createDynamicTriangleBody(ScreenGameRenderer.pixelToMeter(x),
				ScreenGameRenderer.pixelToMeter(y), world);

		updateSpritePosition();
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		double dx = body.getPosition().x - target.getBody().getPosition().x;
		double dy = body.getPosition().y - target.getBody().getPosition().y;

		float x = (float) (-dx / Math.abs(dx) * 0.05);
		float y = (float) (-dy / Math.abs(dy) * 0.05);

		body.applyLinearImpulse(new Vector2(x, y), body.getWorldCenter(), true);

		updateSpritePosition();
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
	public Body createDynamicTriangleBody(float x, float y, World world) {
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
		fixture.setUserData(this);

		triangle.dispose(); // LibGDX

		return physicsBody;
	}

	/**
	 * Updates the sprite position to correspond to the physics body position.
	 */
	protected void updateSpritePosition() {
		float x = ScreenGameRenderer.meterToPixel(body.getPosition().x);
		float y = ScreenGameRenderer.meterToPixel(body.getPosition().y);

		sprite.setRotation(body.getAngle() * 180.0f / (float) Math.PI);
		sprite.setPosition(x, y);
	}
}
