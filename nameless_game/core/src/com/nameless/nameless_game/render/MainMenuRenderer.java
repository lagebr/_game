package com.nameless.nameless_game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * MainMenuRenderer is responsible for rendering the main menu.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-23
 */
public class MainMenuRenderer {

	private SpriteBatch batch;
	private BitmapFont font;
	
	/**
	 * Creates a new MainMenuRenderer.
	 */
	public MainMenuRenderer() {
		batch = new SpriteBatch();
		font = new BitmapFont();

	}
	
	/**
	 * Renders the main menu.
	 */
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();	
		font.setColor(Color.WHITE);
		font.draw(batch, "Welcome to <nameless game>.", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 1/2 + 50);
		font.draw(batch, "Press <P> to begin", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		batch.end();
	}
}
