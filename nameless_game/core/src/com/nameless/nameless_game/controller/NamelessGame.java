package com.nameless.nameless_game.controller;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.model.Entity;
import com.nameless.nameless_game.model.HostileType;
import com.nameless.nameless_game.model.Level;
import com.nameless.nameless_game.model.LevelGenerator;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * NamelessGame is the main controller for the game. Handles input and updates.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-04
 */
public class NamelessGame extends ApplicationAdapter {
	private GameInputProcessor inputProcessor;
	private ScreenRenderer renderer;

	private Level level;
	
	private ArrayList<Texture> keySeqTextureList;

	@Override
	public void create() {
		inputProcessor = new GameInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		renderer = new ScreenRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		World world = new World(new Vector2(0, 0), false);

		float levelWidth = (float) Gdx.graphics.getWidth();
		float levelHeight = (float) Gdx.graphics.getHeight();
		
		level = LevelGenerator.generateLevel(levelWidth, levelHeight, world, 10);
		
		keySeqTextureList = new ArrayList<Texture>(5);
		for (HostileType keyValue : level.getKeyTypes()) {
			Texture texture;
			switch (keyValue) {
				case CHARGE:
					texture = new Texture(Gdx.files.internal("PlayerCircle120x120.png"));
					break;
				case PANIC:
					texture = new Texture(Gdx.files.internal("GreenSquare50x50.png"));
					break;
				default :
					texture = null;
					break;
			}
			keySeqTextureList.add(texture);
		}
	}

	/**
	 * Renders the graphics, bodies and handles input.
	 */
	@Override
	public void render() {
		handleInput();

		level.getPlayer().update(Gdx.graphics.getDeltaTime());
		for (Entity entity : level.getEntities()) {
			entity.update(Gdx.graphics.getDeltaTime());
		}

		renderer.prepare(Color.BLACK);
		renderer.renderEntities(level.getEntities());
		renderer.render(level.getPlayer());
		
		renderer.renderDebug(level.getWorld());

		// @see {@link} https://github.com/libgdx/libgdx/wiki/Box2d
		level.getWorld().step(1f / 60f, 6, 2);
	}

	/**
	 * Receives all input events in the input processors event queue, processes
	 * them and then empties the event queue.
	 */
	private void handleInput() {
		for (InputEvent event : inputProcessor.getActionQueue()) {
			if (event.action == InputAction.LEFT) {
				level.getPlayer().setLeftRotate(event.keyPressed);
			} else if (event.action == InputAction.RIGHT) {
				level.getPlayer().setRightRotate(event.keyPressed);
			} else if (event.action == InputAction.UP
					&& event.keyPressed == true) {
				level.getPlayer().impulseForward();
			} else if (event.action == InputAction.BOOST) {
				if (event.keyPressed == true) {
					level.getPlayer().setBoosting(true);
				} else {
					level.getPlayer().setBoosting(false);
				}
			}
		}
		inputProcessor.getActionQueue().clear();
	}
}
