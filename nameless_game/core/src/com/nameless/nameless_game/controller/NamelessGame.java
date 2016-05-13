package com.nameless.nameless_game.controller;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.model.ChargeHostileWithTarget;
import com.nameless.nameless_game.model.Entity;
import com.nameless.nameless_game.model.Hostile;
import com.nameless.nameless_game.model.Level;
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
	private GameInputProcessor inputProcessor;
	private ScreenRenderer renderer;

	private Level level;

	@Override
	public void create() {
		inputProcessor = new GameInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		renderer = new ScreenRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		World world = new World(new Vector2(0, 0), false);

		Texture playerTexture = new Texture(Gdx.files.internal("PlayerCircle120x120.png"));
		Player player = new Player(75, 75, 60, playerTexture, world);

		level = new Level(player, world);

		Random random = new Random();
		for (int i = 0; i < 0; i++) {
			Texture hostileTexture = new Texture(Gdx.files.internal("GreenSquare50x50.png"));
			Hostile hostile = new PanicHostileWithTarget(random.nextInt(600) + 100, random.nextInt(400) + 100, 50, 50,
					hostileTexture, world, player);
			level.addEntity(hostile);
		}
		Hostile hostileC = new ChargeHostileWithTarget(400, 400, 60, playerTexture, world, player);
		level.addEntity(hostileC);
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
		renderer.render(level.getEntities());
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
			} else if (event.action == InputAction.UP && event.keyPressed == true) {
				float xImpulse = (float) Math.cos((double) level.getPlayer().getBody().getAngle());
				float yImpulse = (float) Math.sin((double) level.getPlayer().getBody().getAngle());

				level.getPlayer().getBody().applyLinearImpulse(new Vector2(xImpulse, yImpulse),
						level.getPlayer().getBody().getWorldCenter(), true);
			}
		}
		inputProcessor.getActionQueue().clear();
	}
}
