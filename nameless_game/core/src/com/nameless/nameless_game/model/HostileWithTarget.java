package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * HostileWithTarget describes a hostile which bases it's behavior on a
 * different Entity which is its' target. Class is abstract and should be
 * sub-classed when wanting targeting behavior.
 * 
 * @author Isaac Arvestad
 * @version 2016-05-11
 */
public abstract class HostileWithTarget extends Hostile {
	protected Entity target;
	
	public HostileWithTarget(float x, float y, float width, float height, Texture texture, World world) {
		super(x, y, width, height, texture, world);
	}
	
	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}
}
