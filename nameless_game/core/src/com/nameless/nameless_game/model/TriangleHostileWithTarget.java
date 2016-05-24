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
public class TriangleHostileWithTarget extends HostileWithTarget {

	public TriangleHostileWithTarget(float x, float y, Texture texture, World world, Entity target) {
		super(texture);

		this.target = target;

		type = HostileType.TRIANGLE;

		body = createDynamicTriangleBody(ScreenGameRenderer.pixelToMeter(x), ScreenGameRenderer.pixelToMeter(y), world);
		
		updateSpritePosition();
	}

	public Body createDynamicTriangleBody(float x, float y, World world) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y, BodyType.DynamicBody, false);
		Body physicsBody = world.createBody(bodyDef);

		Vector2 p1 = new Vector2(0, 0);
		Vector2 p2 = new Vector2(ScreenGameRenderer.pixelToMeter(70), 0);
		Vector2 p3 = new Vector2(ScreenGameRenderer.pixelToMeter(35), ScreenGameRenderer.pixelToMeter(60));

		PolygonShape triangle = new PolygonShape();
		triangle.set(new Vector2[] { p1, p2, p3 });

		FixtureDef fixtureDef = PhysicsHelper.createFixture(triangle, 10.0f);
		// Collision masks
		fixtureDef.filter.categoryBits = Entity.NPC_ENTITY;
		fixtureDef.filter.maskBits = Entity.PLAYER_ENTITY | Entity.NPC_ENTITY;

		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(this);

		triangle.dispose(); // LibGDX

		return physicsBody;
	}
}
