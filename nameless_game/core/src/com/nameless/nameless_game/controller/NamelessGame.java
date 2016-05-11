package com.nameless.nameless_game.controller;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.model.Border;
import com.nameless.nameless_game.model.Entity;
import com.nameless.nameless_game.model.Hostile;
import com.nameless.nameless_game.model.PanicHostileWithTarget;
import com.nameless.nameless_game.model.Player;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * NamelessGame is the main controller for the game. Handles input and updates.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-04
 */
public class NamelessGame extends ApplicationAdapter {
	GameInputProcessor inputProcessor;
	ScreenRenderer renderer;
	World world;

	Border border;
	ArrayList<Entity> entities;
	Player player;

	@Override
	public void create() {
		inputProcessor = new GameInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		renderer = new ScreenRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		world = new World(new Vector2(0, 0), false);
		entities = new ArrayList<Entity>();

		border = new Border(world);

		Texture entityTexture = new Texture(Gdx.files.internal("BlueSquare100x100.png"));
		Entity entity = new Entity(200, 400, 100, 100, entityTexture, world);
		entities.add(entity);

		Texture playerTexture = new Texture(Gdx.files.internal("PlayerCircle120x120.png"));
		player = new Player(75, 75, 60, playerTexture, world);

		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			Texture hostileTexture = new Texture(Gdx.files.internal("GreenSquare50x50.png"));
			Hostile hostile = new PanicHostileWithTarget(random.nextInt(600) + 100, random.nextInt(400) + 100, 50, 50, hostileTexture,
					world, player);
			entities.add(hostile);
		}
	}
	
	/**
	 * Renders the graphics, bodies and handles input.
	 */
	@Override
	public void render() {
		handleInput();

		player.update(Gdx.graphics.getDeltaTime());
		for (Entity entity : entities) {
			entity.update(Gdx.graphics.getDeltaTime());
		}

		renderer.prepare(Color.BLACK);
		renderer.render(entities);
		renderer.render(player);
		renderer.renderDebug(world);

		// @see {@link} https://github.com/libgdx/libgdx/wiki/Box2d
		world.step(1f / 60f, 6, 2); 
	}

	/**
	 * Receives all input events in the input processors event queue, processes
	 * them and then empties the event queue.
	 */
	private void handleInput() {
		for (InputEvent event : inputProcessor.getActionQueue()) {
			if (event.action == InputAction.LEFT && event.keyPressed == true) {
				player.getBody().applyLinearImpulse(new Vector2(-1.0f, 0), player.getBody().getLocalCenter(), true);
			} else if (event.action == InputAction.RIGHT && event.keyPressed == true) {
				player.getBody().applyLinearImpulse(new Vector2(1.0f, 0), player.getBody().getLocalCenter(), true);
			} else if (event.action == InputAction.UP && event.keyPressed == true) {
				player.getBody().applyLinearImpulse(new Vector2(0, 1.0f), player.getBody().getLocalCenter(), true);
			} else if (event.action == InputAction.DOWN && event.keyPressed == true) {
				player.getBody().applyLinearImpulse(new Vector2(0, -1.0f), player.getBody().getLocalCenter(), true);
			}
		}
		inputProcessor.getActionQueue().clear();
	}
}
