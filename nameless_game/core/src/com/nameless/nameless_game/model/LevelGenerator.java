package com.nameless.nameless_game.model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

		for (int i = 0; i < numHostiles; i++) {
			/// Add spawn expections
			float x = random.nextInt((int) width - 100) + 50;
			float y = random.nextInt((int) height - 100) + 50;
			
			Texture texture = new Texture(Gdx.files.internal("GreenSquare50x50.png"));
			Hostile hostile = new PanicHostileWithTarget(x, y, 50, 50, texture, world, player);
			level.addEntity(hostile);
		}

		return level;
	}

	private static Player createPlayer(float x, float y, World world) {
		Texture texture = new Texture(Gdx.files.internal("PlayerCircle120x120.png"));
		return new Player(x, y, 60, texture, world);
	}
}
