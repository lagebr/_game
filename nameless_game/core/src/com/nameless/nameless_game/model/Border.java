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
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2015-05-09
 *
 */
public class Border extends Entity {
	private Body bodyEdgeScreen;

	/**
	 * Creates a border with the dimensions of the LibGDX screen.
	 * 
	 * @param width
	 *            The width in pixels.
	 * @param height
	 *            The height in pixels.
	 * @param world
	 *            The physics world to add the borders to.
	 */
	public Border(float width, float height, World world) {
		// Adjust borders according to screen size
		float w = ScreenRenderer.pixelToMeter(width);
		float h = ScreenRenderer.pixelToMeter(height);

		// The four walls in the world
		createBorder(0f, 0f, 0f, 0f, w, 0f, world);
		createBorder(0f, 0f, 0f, 0f, 0f, h, world);
		createBorder(0f, 0f, w, 0f, w, h, world);
		createBorder(0f, 0f, 0f, h, w, h, world);
	}

	@Override
	public Body getBody() {
		return bodyEdgeScreen;
	}

	/**
	 * Creates an EdgeShape for the four walls of the level.
	 * 
	 * @param xBody
	 * @param yBody
	 * @param x1
	 *            Starting x-value
	 * @param y1
	 *            Starting y-value
	 * @param x2
	 *            Ending x-value
	 * @param y2
	 *            Ending y-value
	 * @param world
	 */
	private void createBorder(float xBody, float yBody, float x1, float y1, float x2, float y2, World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;

		bodyDef.position.set(xBody, yBody);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.filter.categoryBits = NPC_ENTITY;
		fixtureDef.filter.maskBits = PLAYER_ENTITY | NPC_ENTITY;

		EdgeShape border = new EdgeShape();
		border.set(x1, y1, x2, y2);
		fixtureDef.shape = border;

		bodyEdgeScreen = world.createBody(bodyDef);
		bodyEdgeScreen.createFixture(fixtureDef);

		border.dispose(); // openGL stuff
	}
}
