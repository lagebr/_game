/**
 * 
 */
package com.nameless.nameless_game.model;

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
		// TODO Auto-generated constructor stub
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;

		float w = ScreenRenderer.meterToPixel(Gdx.graphics.getWidth());
		float h = ScreenRenderer.meterToPixel(Gdx.graphics.getWidth());
		
		bodyDef.position.set(0, 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = NPC_ENTITY;
        fixtureDef.filter.maskBits = PLAYER_ENTITY;
        
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2,-h/2,w/2,-h/2);
        fixtureDef.shape = edgeShape;
        
        bodyEdgeScreen = world.createBody(bodyDef);
        bodyEdgeScreen.createFixture(fixtureDef);
        
        edgeShape.dispose(); // openGL stuff
	}
}
