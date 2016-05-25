package com.nameless.nameless_game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * A procedural level generator to add seemingly infinite variety. Included in
 * this generating is all the hostile spawn & type, player spawn, fuel cells,
 * key-hole for level progression, static elements etc.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 *
 */
public class LevelGenerator {

	private static Random random = new Random();

	public static Level generateLevel(float width, float height, World world, int numHostiles) {
		ArrayList<Vector2> locations = new ArrayList<Vector2>();

		float playerX = width / 2;
		float playerY = height / 2;
		Player player = createPlayer(playerX, playerY, world);
		locations.add(new Vector2(playerX, playerY));

		Level level = new Level(width, height, player, world);

		for (int i = 0; i < numHostiles; i++) {
			Vector2 location = getValidLocation(50, 50, width - 100, height - 100, locations);
			locations.add(location);

			Hostile hostile = createRandomHostile(location, world, player);
			level.addHostile(hostile);
		}

		HashMap<HostileType, Texture> textureLookUp;
		textureLookUp = new HashMap<HostileType, Texture>();
		textureLookUp.put(HostileType.PANIC, new Texture(Gdx.files.internal("simple_square.png")));
		textureLookUp.put(HostileType.CHARGE, new Texture(Gdx.files.internal("charge_hostile.png")));
		textureLookUp.put(HostileType.ORBITAL, new Texture(Gdx.files.internal("orbital_hostile.png")));
		textureLookUp.put(HostileType.TRIANGLE, new Texture(Gdx.files.internal("triangle_hostile.png")));
		textureLookUp.put(HostileType.RIGHT_ANGLE, new Texture(Gdx.files.internal("BlueSquare100x100.png")));
		level.setKeyTextureLookUp(textureLookUp);

		level.generateNewKey();

		return level;
	}

	private static Player createPlayer(float x, float y, World world) {
		Texture texture = new Texture(Gdx.files.internal("player.png"));
		return new Player(x, y, 45, texture, world);
	}

	/**
	 * Creates a random hostile at a given location.
	 * 
	 * <li>
	 * <ul>
	 * PanicHostileWithTarget - 70% probability
	 * </ul>
	 * <ul>
	 * TriangleHostileWithTarget - 20% probability
	 * </ul>
	 * <ul>
	 * ChargeHostileWithTarget - 10% probability
	 * </ul>
	 * </li>
	 * 
	 * @param location
	 *            The location.
	 * @param world
	 *            The physics world to add the hostile to.
	 * @param player
	 *            The target, if hostile needs a target.
	 * @return The created hostile.
	 */
	public static Hostile createRandomHostile(Vector2 location, World world, Player player) {
		int number = random.nextInt(100);

		Hostile hostile;
		if (number < 60) { // 60% probability
			Texture texture = new Texture(Gdx.files.internal("simple_square.png"));
			hostile = new PanicHostileWithTarget(location.x, location.y, 50, 50, texture, world, player);
		} else if (number < 80) { // 80% - 60% = 20% probability
			Texture texture = new Texture(Gdx.files.internal("triangle_hostile.png"));
			hostile = new TriangleHostileWithTarget(location.x, location.y, texture, world, player);
		} else if (number < 90) { // 90% - 80% = 10% probability 
			Texture texture = new Texture(Gdx.files.internal("BlueSquare100x100.png"));
			hostile = new RightAngleHostile(location.x, location.y, 100f, 100f, texture, world);
		} else { // 100% - 90% = 10% probability
			Texture texture = new Texture(Gdx.files.internal("charge_hostile.png"));
			hostile = new ChargeHostileWithTarget(location.x, location.y, 60, texture, world, player);
		}
		
		return hostile;
	}

	/**
	 * Tries to find a valid location by calling
	 * <code>createRandomLocation</code> until a valid location is found. If a
	 * location is valid is defined by <code>isLocationValid</code>.
	 * 
	 * Location will be inside the rectangle formed by x, y, width and height
	 * where x and y are in the lower left corner.
	 * 
	 * If 50 iterations go by without finding a valid location, the location is
	 * returned even though it is not valid.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param locations
	 *            The locations to compare with.
	 * @return The created location.
	 */
	private static Vector2 getValidLocation(float x, float y, float width, float height, ArrayList<Vector2> locations) {
		int iterations = 0;

		Vector2 location = createRandomLocation(x, y, width, height);
		while (isLocationValid(location, locations) == false && iterations < 50) {
			location = createRandomLocation(x, y, width, height);

			iterations += 1;
		}

		return location;
	}

	/**
	 * Tries to find a valid location where valid is defined as any location
	 * more than 300 pixels away from the player. If the method fails to find a
	 * valid location 50 times, a non-valid location will be returned.
	 * 
	 * Location will be inside the rectangle formed by x, y, width and height
	 * where x and y are in the lower left corner.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param player
	 * @return The created location.
	 */
	public static Vector2 getValidNewLocation(float x, float y, float width, float height, Player player) {
		Vector2 location = createRandomLocation(x, y, width, height);

		float playerX = ScreenGameRenderer.meterToPixel(player.getBody().getPosition().x);
		float playerY = ScreenGameRenderer.meterToPixel(player.getBody().getPosition().y);
		Vector2 playerLocation = new Vector2(playerX, playerY);

		int iterations = 0;

		while (playerLocation.dst(location) < 300 && iterations < 50) {
			location = createRandomLocation(x, y, width, height);

			iterations += 1;
		}

		return location;
	}

	/**
	 * Creates a random location inside the rectangle formed by x, y, width and
	 * height where x and y are in the lower left corner.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return The created location.
	 */
	private static Vector2 createRandomLocation(float x, float y, float width, float height) {
		float randomX = random.nextInt((int) (width - x)) + x;
		float randomY = random.nextInt((int) (height - y)) + y;

		return new Vector2(randomX, randomY);
	}

	/**
	 * Checks if the location is at least 100 pixels away from the other
	 * locations.
	 * 
	 * @param location
	 *            The location to check.
	 * @param locations
	 *            The locations to compare against.
	 * @return
	 *         <ul>
	 *         <li>True - location is at least 100 pixels away from every other
	 *         location.</li>
	 *         <li>False - location is too close to a different location.</li>
	 *         </ul>
	 */
	private static boolean isLocationValid(Vector2 location, ArrayList<Vector2> locations) {
		for (Vector2 otherLocation : locations) {
			if (location.dst(otherLocation) < 100) {
				return false;
			}
		}

		return true;
	}
}
