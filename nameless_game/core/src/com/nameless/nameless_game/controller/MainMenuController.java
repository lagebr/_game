package com.nameless.nameless_game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nameless.nameless_game.render.MainMenuRenderer;

/**
 * MenuController is responsible for updating the main menu and drawing it to
 * screen using Renderer/MenuRenderer.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-22
 */
public class MainMenuController implements Screen {

	private NamelessGame game;

	private MainMenuRenderer renderer;

	/**
	 * Creates a menu controller with a reference to NamelessGame. The reference
	 * is used when game should start.
	 * 
	 * @param game
	 */
	public MainMenuController(NamelessGame game) {
		this.game = game;

		renderer = new MainMenuRenderer();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		renderer.render();
		
		if (Gdx.app.getInput().isKeyPressed(Input.Keys.P)) {
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
