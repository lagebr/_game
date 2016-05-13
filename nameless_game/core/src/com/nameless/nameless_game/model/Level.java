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

	private ArrayList<Entity> hostiles;

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

		hostiles = new ArrayList<Entity>();
	}
}
