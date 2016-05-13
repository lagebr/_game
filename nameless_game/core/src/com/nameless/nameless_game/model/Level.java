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

	/**
	 * Constructs a level with a player and a physics world.
	 * 
	 * @param player
	 *            The player.
	 * @param world
	 *            The physics world.
	 */
	public Level(Player player, World world) {
		this.player = player;
		this.world = world;
		
		border = new Border(world);
		
		hostiles = new ArrayList<Hostile>();
	}

	public World getWorld() {
		return world;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ArrayList<Hostile> getHostiles() {
		return hostiles;
	}

	/**
	 * Attempts to add hostile to level. Hostile is only added if its' physics
	 * world is the same as the level's physics world.
	 * 
	 * @param hostile
	 *            The hostile to be added.
	 * @return
	 *         <ul>
	 *         <li>True - hostile was successfully added.</li>
	 *         <li>False - hostile was not added.</li>
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
}
