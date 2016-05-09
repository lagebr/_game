package com.nameless.nameless_game.render;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.model.Entity;

public class ScreenRenderer extends Renderer {
	public static final int METER_TO_PIXEL = 100;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Box2DDebugRenderer debugRenderer;

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

		debugRenderer = new Box2DDebugRenderer();
	}

	/**
	 * Prepare clears the screen and updates the camera to prepare for a new
	 * render frame.
	 * 
	 * @param color
	 *            The background color.
	 */
	public void prepare(Color color) {
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
	}

	/**
	 * Draws entities. All entities are drawn in the same batch.
	 * 
	 * @param entities
	 *            The entities to be drawn.
	 */
	@Override
	public void render(ArrayList<Entity> entities) {
		batch.begin();
		for (Entity entity : entities) {
			entity.getSprite().draw(batch);
		}
		batch.end();
	}

	/**
	 * Draws all physics bodies.
	 * 
	 * @param world
	 *            The world that the physics bodies belongs to.
	 */
	public void renderDebug(World world) {
		debugRenderer.render(world, camera.combined.scale(meterToPixel(1), meterToPixel(1), meterToPixel(1)));
	}

	/**
	 * Converts screen pixels to physics meters.
	 * 
	 * @param pixels
	 * @return meters
	 */
	public static float pixelToMeter(float pixels) {
		return pixels / (float) METER_TO_PIXEL;
	}

	/**
	 * Converts physics meters to screen pixels
	 * 
	 * @param meters
	 * @return pixels
	 */
	public static float meterToPixel(float meters) {
		return meters * (float) METER_TO_PIXEL;
	}

	/**
	 * Returns the camera of the renderer.
	 * 
	 * @return camera
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}
}
