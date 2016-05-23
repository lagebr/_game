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
	
	private Random random;

	private GameInputProcessor inputProcessor;
	private ScreenGameRenderer renderer;

	private Level level;

	private ArrayList<Texture> keySeqTextureList;

	private NamelessGame game;

	private ArrayList<HostileType> keySeqProgression;

	private ArrayList<HostileType> keyTypesLevelList;

	private boolean isPreparing;
	private float timeCount;

	/**
	 * Creates a game controller with a reference to NamelessGame. The reference
	 * is used when game should pause or exit to main menu. Initializes game.
	 * 
	 * @param game
	 */
	public GameController(NamelessGame game) {
		random = new Random();
		
		isPreparing = true;
		timeCount = 4;

		this.game = game;

		inputProcessor = new GameInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		renderer = new ScreenGameRenderer(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		World world = new World(new Vector2(0, 0), false);

		float levelWidth = (float) Gdx.graphics.getWidth();
		float levelHeight = (float) Gdx.graphics.getHeight();

		level = LevelGenerator.generateLevel(levelWidth, levelHeight, world,
				10);

		createCollisionListener();
		
		keyTypesLevelList = level.getKeyTypes();
		while (keyTypesLevelList.size() < 5) {
			keyTypesLevelList.add(keyTypesLevelList.get(random.nextInt(keyTypesLevelList.size())));
		}
		
		Collections.shuffle(keyTypesLevelList);
		
		keySeqTextureList = new ArrayList<Texture>(5);
		for (HostileType keyValue : keyTypesLevelList) {
			Texture texture;
			switch (keyValue) {
				case CHARGE :
					texture = new Texture(
							Gdx.files.internal("PlayerCircle120x120.png"));
					break;
				case PANIC :
					texture = new Texture(
							Gdx.files.internal("GreenSquare50x50.png"));
					break;
				default :
					texture = null;
					break;
			}
			keySeqTextureList.add(texture);
		}
		
		keySeqProgression = (ArrayList<HostileType>) keyTypesLevelList
				.clone();
	}

	/**
	 * Renders the graphics, bodies and handles input.
	 */
	@Override
	public void render(float delta) {
		if (!isPreparing) {
			level.getPlayer().update(Gdx.graphics.getDeltaTime());
			for (Entity entity : level.getEntities()) {
				entity.update(Gdx.graphics.getDeltaTime());
			}
		}

		renderer.prepare(Color.BLACK);
		renderer.renderEntities(level.getEntities());
		renderer.render(level.getPlayer());
		renderer.renderKeySeq(keySeqTextureList);
		renderer.renderDebug(level.getWorld());

		if (!isPreparing) {
			handleInput();
			// @see {@link} https://github.com/libgdx/libgdx/wiki/Box2d
			level.getWorld().step(1f / 60f, 6, 2);

			for (int i = 0; i < level.getEntities().size() - 1; i++) {
				Entity entity = level.getEntities().get(i);
				if (entity.isFlaggedForDeletion()) {
					level.getWorld().destroyBody(entity.getBody());
					entity.getBody().setUserData(null);
					entity.setBody(null);
					entity.setSprite(null);
					level.getEntities().remove(i);
					entity = null;
				}
			}
		} else {
			timeCount -= delta;
			renderer.renderCountDown((int) timeCount);
			if (timeCount < 0) {
				isPreparing = false;
			}
		}
	}

	private void createCollisionListener() {
		level.getWorld().setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (contact.getFixtureA()
						.getFilterData().categoryBits == Entity.PLAYER_ENTITY) {
					if (contact.getFixtureB()
							.getUserData() instanceof Hostile) {
						if (level.getPlayer().isBoosting() == false) {
							game.startGameOver();
						} else {
							Hostile hostileB = (Hostile) contact.getFixtureB()
									.getUserData();
							hostileB.setFlaggedForDeletion(true);
							keySeqListener(hostileB);
						}
					}
				} else if (contact.getFixtureB()
						.getFilterData().categoryBits == Entity.PLAYER_ENTITY) {
					if (contact.getFixtureA()
							.getUserData() instanceof Hostile) {
						if (level.getPlayer().isBoosting() == false) {
							game.startGameOver();
						} else {
							Hostile hostileA = (Hostile) contact.getFixtureA()
									.getUserData();
							hostileA.setFlaggedForDeletion(true);
							keySeqListener(hostileA);
						}
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

	/**
	 * Listens and reacts to player boosting enemies to death.
	 */
	private void keySeqListener(Hostile hostile) {
		if (keySeqProgression.size() != 0) {
			if (hostile.getType() == keySeqProgression.get(0)) {
				System.out.println("ENEMY SLAIN");
				keySeqProgression.remove(0);
				keySeqTextureList.remove(0);
				if (keySeqProgression.size() == 0) {
					game.startMainMenu();
				}
			} else {
				System.out.println("FAILURE");
			}
		}
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
