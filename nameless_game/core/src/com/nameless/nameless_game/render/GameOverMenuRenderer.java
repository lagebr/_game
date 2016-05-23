package com.nameless.nameless_game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverMenuRenderer {

	private SpriteBatch batch;
	private BitmapFont font;
	
	/**
	 * Creates a new GameOverMenuRenderer.
	 */
	public GameOverMenuRenderer() {
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
		font.draw(batch, "Press <P> to play again", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		font.draw(batch, "Press <S> to go to main menu", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 100);
		batch.end();
	}
}
