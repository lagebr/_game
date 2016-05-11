package com.nameless.nameless_game.model;

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
	
	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}
}
