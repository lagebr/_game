package com.nameless.nameless_game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * MainMenuRenderer is responsible for rendering the main menu.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-23
 */
public class MainMenuRenderer {

	private SpriteBatch batch;
	private Texture background;
	private Texture marker;

	/**
	 * Creates a new MainMenuRenderer.
	 */
	public MainMenuRenderer() {
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.local("simple_start_menu.png"));
		marker = new Texture(Gdx.files.local("simple_circle_marker.png"));
	}

	/**
	 * Clears the screen and prepares for rendering.
	 */
	public void prepare() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Renders the main menu.
	 */
	public void render() {
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
	}

	/**
	 * Renders a marker at one of the menu options "Start" or "Quit".
	 * 
	 * @param index
	 *            The index of the marker. 0 = "Start", 1 = "Quit". If index > 1
	 *            or index < 0, marker is not drawn.
	 */
	public void renderMarker(int index) {
		batch.begin();
		switch (index) {
		case 0:
			batch.draw(marker, 390, 768-350);
			break;
		case 1:
			batch.draw(marker, 390, 768-495);
			break;
		}
		batch.end();
	}
}
