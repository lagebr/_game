/**
 * 
 */
package com.nameless.nameless_game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * @author lagebr
 *
 */
public class Border extends Entity {
	Body bodyEdgeScreen;

	public Border(World world) {
		super();

		// Adjust borders according to screen size.
		float w = ScreenRenderer.pixelToMeter(Gdx.graphics.getWidth());
		float h = ScreenRenderer.pixelToMeter(Gdx.graphics.getHeight()); // never forget

		//
		//createBoxBorder(world, ScreenRenderer.pixelToMeter(-5), ScreenRenderer.pixelToMeter(h / 2),
		//		ScreenRenderer.pixelToMeter(10), ScreenRenderer.pixelToMeter(h + 20));

		 createBorder(0f, 0f, 0f, 0f, w, 0f, world);
		 createBorder(0f, 0f, 0f, 0f, 0f, h, world);
	//	createBorder(0f, 0f, ScreenRenderer.meterToPixel(0f), ScreenRenderer.pixelToMeter(h),
	//			ScreenRenderer.meterToPixel(w), ScreenRenderer.pixelToMeter(h), world);
		 createBorder(0f, 0f, w, 0f, w, h, world);
		 createBorder(0f, 0f, 0f, h, w, h, world);
	}

	@Override
	public Body getBody() {
		return bodyEdgeScreen;
	}

	private void createBoxBorder(World world, float xBody, float yBody, float height, float width) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(xBody, yBody);
		bodyDef.fixedRotation = true;
		Body physicsBody = world.createBody(bodyDef);

		PolygonShape rectShape = new PolygonShape();
		rectShape.setAsBox(width / 2, height / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectShape;
		fixtureDef.density = 0.5f; // hardy
		fixtureDef.friction = 0.4f; // frit
		fixtureDef.restitution = 0.6f; // bounce
		fixtureDef.filter.categoryBits = Entity.NPC_ENTITY; // this what I am
		fixtureDef.filter.maskBits = Entity.PLAYER_ENTITY; // this is what I
															// collide with
		physicsBody.createFixture(fixtureDef);

		rectShape.dispose(); // openGL
	}

	private void createBorder(float xBody, float yBody, float x1, float y1, float x2, float y2, World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;

		bodyDef.position.set(xBody, yBody);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.filter.categoryBits = NPC_ENTITY;
		fixtureDef.filter.maskBits = PLAYER_ENTITY;

		EdgeShape border = new EdgeShape();
		border.set(x1, y1, x2, y2);
		fixtureDef.shape = border;

		bodyEdgeScreen = world.createBody(bodyDef);
		bodyEdgeScreen.createFixture(fixtureDef);

		border.dispose(); // openGL stuff
	}
}
