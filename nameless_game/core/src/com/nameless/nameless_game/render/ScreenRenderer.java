package com.nameless.nameless_game.render;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nameless.nameless_game.model.Entity;

public class ScreenRenderer extends Renderer {
	public static final int METER_TO_PIXEL = 100;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	/**
	 * Draws all entities on screen using an ortho-camera.
	 * 
	 * @param width
	 *            The width of the screen.
	 * @param height
	 *            The height of the screen.
	 */
	public ScreenRenderer(int width, int height) {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		batch = new SpriteBatch();
	}

	/**
	 * Draws all entities to a black screen. All entities are drawn in the same
	 * batch.
	 * 
	 * @param entities
	 *            The entities to be drawn.
	 */
	@Override
	public void render(ArrayList<Entity> entities) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		for (Entity entity : entities) {
			batch.draw(entity.getTexture(), entity.getBody().getPosition().x * (float) METER_TO_PIXEL,
					entity.getBody().getPosition().y * (float) METER_TO_PIXEL);
		}
		batch.end(); // openGL stuff
	}
}
