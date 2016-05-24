package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * TriangleHostileWithTarget is a Hostile which constantly follows the player.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-24
 */
public class TriangleHostileWithTarget extends HostileWithTarget {

	public TriangleHostileWithTarget(float x, float y, float width, float height, Texture texture, World world) {
		super(x, y, width, height, texture, world);
		
	}

}
