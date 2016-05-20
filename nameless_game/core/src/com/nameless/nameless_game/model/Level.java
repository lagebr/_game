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

	private ArrayList<Entity> entities;

	private ArrayList<HostileType> keyTypes;

	public ArrayList<HostileType> getKeyTypes() {
		return keyTypes;
	}

	public void setKeyTypes(ArrayList<HostileType> keyTypes) {
		this.keyTypes = keyTypes;
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
		entities = new ArrayList<Entity>();
		keyTypes = new ArrayList<HostileType>(5);;
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

	public ArrayList<Entity> getEntities() {
		return entities;
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
	public boolean addEntity(Entity entity) {
		if (entity.getBody().getWorld().equals(world)) {
			entities.add(entity);
			addTypes(entity);
			return true;
		} else {
			return false;
		}
	}
	
	/* Helper method. Keeps a list of unique hostile types.*/
	private void addTypes(Entity entity) {
		if (entity instanceof Hostile)
			if (!keyTypes.contains(((Hostile) entity).getType())) {
				keyTypes.add(((Hostile) entity).getType());
			}
	}
}
