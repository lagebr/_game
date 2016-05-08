package com.nameless.nameless_game.render;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.model.Border;
import com.nameless.nameless_game.model.Entity;

public class ScreenRenderer extends Renderer {
	public static final int METER_TO_PIXEL = 100;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite sprite;

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
			batch.draw(entity.getTexture(),
					meterToPixel(entity.getBody().getPosition().x) - entity.getTexture().getWidth() / 2,
					meterToPixel(entity.getBody().getPosition().y) - entity.getTexture().getHeight() / 2);
		}
		batch.end(); // openGL stuff
	}

	/**
	 * Draws all physics bodies to a black screen.
	 * 
	 * @param world
	 *            The world that the physics bodies belongs to.
	 */
	public void renderDebug(World world) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		debugRenderer.render(world, camera.combined.scale(meterToPixel(1), meterToPixel(1), meterToPixel(1)));
	}

	/**
	 * NOT IN USE 2016-05-06 Depracted method to render border. Might be reused
	 * later.
	 * 
	 * @param border
	 */
	public void renderBorder(Border border) {
		Pixmap pixmap = new Pixmap(800, 600, Format.RGBA8888);
		pixmap.setColor(0, 1, 0, 0.75f);
		pixmap.drawLine(0, 0, 0, Gdx.graphics.getHeight()); // Left border
		pixmap.drawLine(0, Gdx.graphics.getWidth(), 0, 0); // Down border
		pixmap.drawLine(Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Right
																										// border
		pixmap.drawLine(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Up
																											// border
		Texture pixMapTex = new Texture(pixmap);
		pixmap.dispose();

		sprite = new Sprite(pixMapTex);
		sprite.setPosition(0, 0);
		batch.begin();
		sprite.draw(batch);
		batch.end();

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
