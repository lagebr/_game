package com.nameless.nameless_game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Level describes a level in the game.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-13
 */
public class Level {
	private Random random = new Random();

	private World world;
	private Player player;
	private Border border;

	private ArrayList<Hostile> hostiles;

	private HostileType key;

	private HashMap<HostileType, Texture> keyTextureLookUp;

	public Texture getKeyTexture(HostileType key) {
		return keyTextureLookUp.get(key);
	}

	public void setKeyTextureLookUp(HashMap<HostileType, Texture> keyTextureLookUp) {
		this.keyTextureLookUp = keyTextureLookUp;
	}

	public HostileType getKey() {
		return key;
	}

	public void setKey(HostileType key) {
		this.key = key;
	}

	/**
	 * Attempts to generate a new key. If the hostile chosen is flagged for
	 * deletion, a new hostile is chosen recursively.
	 */
	public void generateNewKey() {
		int index = random.nextInt(hostiles.size());
		if (hostiles.get(index).isFlaggedForDeletion() == false) {
			key = hostiles.get(index).type;
		} else {
			generateNewKey();
		}
	}

	/**
	 * Attempts to add an entity to level. Entity is only added if its' physics
	 * world is the same as the level's physics world.
	 * 
	 * @param entity
	 *            The entity to be added.
	 * @return
	 *         <ul>
	 *         <li>True - entity was successfully added.</li>
	 *         <li>False - entity was not added.</li>
	 *         </ul>
	 */
	public boolean addHostile(Hostile hostile) {
		if (hostile.getBody().getWorld().equals(world)) {
			hostiles.add(hostile);

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Constructs a level with a player and a physics world.
	 * 
	 * @param width
	 *            The width of the level in pixels.
	 * @param height
	 *            The height of the level in pixels.
	 * @param player
	 *            The player.
	 * @param world
	 *            The physics world.
	 */
	public Level(float width, float height, Player player, World world) {
		this.player = player;
		this.world = world;

		border = new Border(width, height, world);
		hostiles = new ArrayList<Hostile>();
	}

	public World getWorld() {
		return world;
	}

	public Player getPlayer() {
		return player;
	}

	public Border getBorder() {
		return border;
	}

	public ArrayList<Hostile> getHostiles() {
		return hostiles;
	}

}
