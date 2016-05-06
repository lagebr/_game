/**
 * 
 */
package com.nameless.nameless_game.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
	
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;

		float w = ScreenRenderer.pixelToMeter(Gdx.graphics.getWidth());
		float h = ScreenRenderer.pixelToMeter(Gdx.graphics.getWidth());

		bodyDef.position.set(0, 0);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.filter.categoryBits = NPC_ENTITY;
		fixtureDef.filter.maskBits = PLAYER_ENTITY;

		EdgeShape border = new EdgeShape();
		border.set(0, 0, w, 0);
		fixtureDef.shape = border;

		bodyEdgeScreen = world.createBody(bodyDef);
		bodyEdgeScreen.createFixture(fixtureDef);

		border.dispose(); // openGL stuff
	}

	@Override
	public Body getBody() {
		return bodyEdgeScreen;
	}
}
