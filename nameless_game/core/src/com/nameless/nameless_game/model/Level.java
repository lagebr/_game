package com.nameless.nameless_game.model;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Level describes a level in the game.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-13
 */
public class Level {
	private World world;
	private Player player;
	private Border border;

	private ArrayList<Hostile> hostiles;

	private ArrayList<HostileType> keyTypes;

	public ArrayList<HostileType> getKeyTypes() {
		return keyTypes;
	}

	public void setKeyTypes(ArrayList<HostileType> keyTypes) {
		this.keyTypes = keyTypes;
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
	public boolean addEntity(Hostile hostile) {
		if (hostile.getBody().getWorld().equals(world)) {
			hostiles.add(hostile);
			addTypes(hostile);
			return true;
		} else {
			return false;
		}
	}

	/* Helper method. Keeps a list of unique hostile types. */
	private void addTypes(Hostile hostile) {
		if (!keyTypes.contains((hostile).getType())) {
			keyTypes.add(( hostile).getType());
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
		keyTypes = new ArrayList<HostileType>(5);
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
