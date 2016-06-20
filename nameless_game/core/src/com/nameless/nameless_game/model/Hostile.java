package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * The entity model for the hostile entities in the game world.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2015-05-06
 *
 */
public abstract class Hostile extends Entity {
	protected Entity target;
	protected HostileType type;

	public HostileType getType() {
		return type;
	}

	public void setType(HostileType type) {
		this.type = type;
	}

	public Hostile(float x, float y, float width, float height, Texture texture,
			World world) {
		super(texture);

		body = PhysicsHelper.createDynamicBody(this,
				ScreenGameRenderer.pixelToMeter(x),
				ScreenGameRenderer.pixelToMeter(y),
				ScreenGameRenderer.pixelToMeter(width),
				ScreenGameRenderer.pixelToMeter(height), world);
	}

	public Hostile(Texture texture) {
		sprite = new Sprite(texture);
	}

	public Hostile() {
		// Intentionally left empty.
	}

	@Override
	public abstract void update(float deltaTime);
}
