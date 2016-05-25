/**
 * 
 */
package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A hostile that zigs and zags around the map.
 * TODO
 * 
 * 
 * @author lagebr
 *
 */
public class ZigZagHostile extends Hostile {

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param texture
	 * @param world
	 */
	public ZigZagHostile(float x, float y, float width, float height,
			Texture texture, World world) {
		super(x, y, width, height, texture, world);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public ZigZagHostile() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param texture
	 */
	public ZigZagHostile(Texture texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

}
