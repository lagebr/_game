package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenGameRenderer;

/**
 * TriangleHostileWithTarget is a Hostile which constantly follows the player.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-24
 */
public class TriangleHostileWithTarget extends Hostile {

	public TriangleHostileWithTarget(float x, float y, Texture texture,
			World world, Entity target) {
		super(texture);

		this.target = target;

		type = HostileType.TRIANGLE;

		body = PhysicsHelper.createDynamicTriangleBody(ScreenGameRenderer.pixelToMeter(x),
				ScreenGameRenderer.pixelToMeter(y), world, this);

		updateSprite();
	}

	@Override
	public void update(float deltaTime) {
		updateSprite();

		double dx = body.getPosition().x - target.getBody().getPosition().x;
		double dy = body.getPosition().y - target.getBody().getPosition().y;

		float x = (float) (-dx / Math.abs(dx) * 0.05);
		float y = (float) (-dy / Math.abs(dy) * 0.05);

		body.applyLinearImpulse(new Vector2(x, y), body.getWorldCenter(), true);
	}

	/**
	 * Updates the sprite position to correspond to the physics body position.
	 */
	@Override
	public void updateSprite() {
		float x = ScreenGameRenderer.meterToPixel(body.getPosition().x);
		float y = ScreenGameRenderer.meterToPixel(body.getPosition().y);

		sprite.setRotation(body.getAngle() * 180.0f / (float) Math.PI);
		sprite.setPosition(x, y);

	}
}
