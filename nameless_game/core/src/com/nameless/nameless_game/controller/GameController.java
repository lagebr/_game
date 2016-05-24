package com.nameless.nameless_game.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.model.Entity;
import com.nameless.nameless_game.model.Hostile;
import com.nameless.nameless_game.model.HostileType;
import com.nameless.nameless_game.model.Level;
import com.nameless.nameless_game.model.LevelGenerator;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * GameController is responsible for updating the game and drawing it to screen
 * using Renderer/ScreenRenderer.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-22
 */
public class GameController implements Screen {

	private static final Color BACKGROUND_COLOR = new Color(0f, 178f / 256f, 72f / 256f, 1f);
	private Random random;

	private GameInputProcessor inputProcessor;
	private ScreenGameRenderer renderer;

	private Level level;

	private int numberOfHostilesToCreate = 0;

	private NamelessGame game;

	private boolean isPreparing;
	private float timeCount;

	private int numWins;

	/**
	 * Creates a game controller with a reference to NamelessGame. The reference
	 * is used when game should pause or exit to main menu. Initializes game.
	 * 
	 * @param game
	 */
	public GameController(NamelessGame game) {
		numWins = 0;

		random = new Random();

		isPreparing = true;
		timeCount = 4;

		this.game = game;

		inputProcessor = new GameInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		renderer = new ScreenGameRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		World world = new World(new Vector2(0, 0), false);

		float levelWidth = (float) Gdx.graphics.getWidth();
		float levelHeight = (float) Gdx.graphics.getHeight();

		level = LevelGenerator.generateLevel(levelWidth, levelHeight, world, 2);

		createCollisionListener();
	}

	private Texture getKeyTexture(HostileType key) {
		return level.getKeyTexture(key);
	}

	/**
	 * Renders the graphics, bodies and handles input.
	 */
	@Override
	public void render(float delta) {
		if (!isPreparing) {
			level.getPlayer().update(Gdx.graphics.getDeltaTime());
			for (Entity entity : level.getHostiles()) {
				entity.update(Gdx.graphics.getDeltaTime());
			}
		}

		renderer.prepare(BACKGROUND_COLOR);
		renderer.renderBackground();
		renderer.renderHostiles(level.getHostiles());
		renderer.render(level.getPlayer());
		renderer.renderKey(getKeyTexture(level.getKey()));
		renderer.renderWinCount(numWins);
		// renderer.renderDebug(level.getWorld());

		if (!isPreparing) {
			handleInput();
			// @see {@link} https://github.com/libgdx/libgdx/wiki/Box2d
			level.getWorld().step(1f / 60f, 6, 2);

			for (int i = 0; i < level.getHostiles().size(); i++) {
				Entity entity = level.getHostiles().get(i);
				if (entity.isFlaggedForDeletion()) {
					level.getWorld().destroyBody(entity.getBody());
					entity.getBody().setUserData(null);
					entity.setBody(null);
					entity.setSprite(null);
					level.getHostiles().remove(i);
					entity = null;
				}
			}

			// Create new Hostiles if necessary.
			for (int i = 0; i < numberOfHostilesToCreate; i++) {
				Vector2 location = LevelGenerator.getValidNewLocation(50, 50, Gdx.graphics.getWidth() - 100,
						Gdx.graphics.getHeight() - 100, level.getPlayer());
				Hostile hostile = LevelGenerator.createRandomHostile(location, level.getWorld(), level.getPlayer());
				level.addHostile(hostile);
			}

			numberOfHostilesToCreate = 0;
		} else {
			timeCount -= delta;
			renderer.renderCountDown((int) timeCount);
			if (timeCount < 0) {
				isPreparing = false;
				// Clear InputEvents from during the preparation
				inputProcessor.getActionQueue().clear();
			}
		}
	}

	private void createCollisionListener() {
		level.getWorld().setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (contact.getFixtureA().getFilterData().categoryBits != Entity.PLAYER_ENTITY
						&& contact.getFixtureB().getFilterData().categoryBits != Entity.PLAYER_ENTITY) {
					return; // If neither entity in collision is player we can
							// ignore the collision.
				}

				Hostile hostile = null;
				if (contact.getFixtureA().getUserData() instanceof Hostile) {
					hostile = (Hostile) contact.getFixtureA().getUserData();
				} else if (contact.getFixtureB().getUserData() instanceof Hostile) {
					hostile = (Hostile) contact.getFixtureB().getUserData();
				}

				if (hostile != null) {
					if (hostile.getType().equals(level.getKey())) {
						hostile.setFlaggedForDeletion(true);

						numWins += 1;
						numberOfHostilesToCreate += 1;
						level.generateNewKey();
					} else {
						game.startGameOver(numWins);
					}
				}
			}

			@Override
			public void endContact(Contact contact) {
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}
		});
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

	@Override
	public void show() {
		// TODO Auto-generated method stub
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
