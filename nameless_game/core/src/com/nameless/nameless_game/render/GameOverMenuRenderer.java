package com.nameless.nameless_game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverMenuRenderer {

	private SpriteBatch batch;
	private Texture background;
	
	/**
	 * Creates a new GameOverMenuRenderer.
	 */
	public GameOverMenuRenderer() {
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.local("simple_game_over_menu.png"));
	}
	
	/**
	 * Renders the main menu.
	 */
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
	}
}
