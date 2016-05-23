package com.nameless.nameless_game.controller;

import com.badlogic.gdx.Game;

/**
 * NamelessGame is the main controller for the game. Handles input and updates.
 * 
 * Used {@link https://github.com/libgdx/libgdx/wiki/Extending-the-simple-game}
 * as a reference when creating menu system.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-04
 */
public class NamelessGame extends Game {

	@Override
	public void create() {
		startMainMenu();
	}

	@Override
	public void render() {
		super.render(); // This renders currently selected screen.
	}

	/**
	 * Goes to main menu by switching the screen to a new instance of
	 * MenuController.
	 */
	public void startMainMenu() {
		this.setScreen(new MenuController(this));
	}

	/**
	 * Starts game by switching the screen to a new instance of GameController.
	 */
	public void startGame() {
		this.setScreen(new GameController(this));
	}
	
	/**
	 * Goes to game over menu by switching the screen to a new instance of
	 * GameOverMenuController.
	 */
	public void startGameOver() {
		this.setScreen(new GameOverMenuController(this));
	}
}
