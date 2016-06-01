package com.nameless.nameless_game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.nameless.nameless_game.render.GameOverMenuRenderer;

/**
 * GameOverMenuController is responsible for the game over screen.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-23
 */
public class GameOverMenuController implements Screen {

	private NamelessGame game;

	private int score;

	private GameOverMenuRenderer renderer;

	/**
	 * Creates a GameOverMenuController with a reference to game and the score
	 * achieved during the most recently played game.
	 * 
	 * @param game
	 * @param score
	 */
	public GameOverMenuController(NamelessGame game, int score) {
		this.game = game;
		this.score = score;

		renderer = new GameOverMenuRenderer();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		renderer.render(score);

		if (Gdx.app.getInput().isKeyJustPressed(Input.Keys.SPACE)) {
			game.startMainMenu();
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
