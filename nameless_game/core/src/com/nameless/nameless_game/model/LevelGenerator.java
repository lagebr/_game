package com.nameless.nameless_game.model;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

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
		Player player = createPlayer(width / 2, height / 2, world);
		Level level = new Level(width, height, player, world);

		ArrayList<Vector2> locations = new ArrayList<Vector2>();

		for (int i = 0; i < numHostiles; i++) {
			/// Add spawn expections
			Vector2 location = createRandomLocation(50, 50, width - 50, height - 50);
			while (isLocationValid(location, locations) == false) {
				location = createRandomLocation(50, 50, width - 50, height - 50);
			}

			Texture texture = new Texture(Gdx.files.internal("GreenSquare50x50.png"));
			Hostile hostile = new PanicHostileWithTarget(location.x, location.y, 50, 50, texture, world, player);
			level.addEntity(hostile);
		}
		Texture texture = new Texture(Gdx.files.internal("PlayerCircle120x120.png"));
		Hostile charger = new ChargeHostileWithTarget(100, 400, 60, texture, world, player);
		level.addEntity(charger);

		return level;
	}

	private static Player createPlayer(float x, float y, World world) {
		Texture texture = new Texture(Gdx.files.internal("PlayerCircle120x120.png"));
		return new Player(x, y, 60, texture, world);
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
	 * Checks if the location is at least 50 pixels away from the other
	 * locations.
	 * 
	 * @param location
	 *            The location to check.
	 * @param locations
	 *            The locations to compare against.
	 * @return
	 *         <ul>
	 *         <li>True - location is at least 50 pixels away from every other
	 *         location.</li>
	 *         <li>False - location is too close to a different location.</li>
	 *         </ul>
	 */
	private static boolean isLocationValid(Vector2 location, ArrayList<Vector2> locations) {
		for (Vector2 otherLocation : locations) {
			if (location.dst(otherLocation) < 50) {
				return false;
			}
		}

		return true;
	}
}
