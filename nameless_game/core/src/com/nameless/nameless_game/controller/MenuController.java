package com.nameless.nameless_game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * MenuController is responsible for updating the main menu and drawing it to
 * screen using Renderer/MenuRenderer.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-22
 */
public class MenuController implements Screen {

	private NamelessGame game;

	private SpriteBatch batch;
	private BitmapFont font;

	/**
	 * Creates a menu controller with a reference to NamelessGame. The reference
	 * is used when game should start.
	 * 
	 * @param game
	 */
	public MenuController(NamelessGame game) {
		this.game = game;

		batch = new SpriteBatch();
		font = new BitmapFont();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		font.setColor(Color.WHITE);
		font.draw(batch, "Press <SPACE> to begin", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		batch.end();

		if (Gdx.app.getInput().isKeyPressed(Input.Keys.SPACE)) {
			game.startGame();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
