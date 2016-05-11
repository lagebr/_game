/**
 * 
 */
package com.nameless.nameless_game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * Spins and if Player is in it's path after a delay it'll launch after Player.
 * TODO
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-09
 *
 */
public class HostileCircle extends Hostile {
	float screenWidth = Gdx.graphics.getWidth();
	float screenHeight = Gdx.graphics.getHeight();
	
	public HostileCircle(float x, float y, float radius, Texture texture, World world) {
		super(texture);
		body = createDynamicBody(ScreenRenderer.pixelToMeter(x), ScreenRenderer.pixelToMeter(y),
				ScreenRenderer.pixelToMeter(radius), world);
		body.setFixedRotation(false); // Needs to spin
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		body.setAngularVelocity(1);
		
		float x1 = body.getPosition().x;
		float y1 = body.getPosition().y;
		// TODO implement cleaner Radius information
		float x2 = screenWidth - x1;
		float y2 = 60 * MathUtils.sin(body.getAngle());
		
		float x1_corner = screenWidth - screenWidth/8;
		float y1_corner = screenHeight;
		float x2_corner = screenWidth;
		float y2_corner = screenHeight - screenHeight/8;
		
		if (body.getAngle() % 2*MathUtils.PI > 2*MathUtils.PI/4) {
			body.applyForceToCenter(10, 10, true);
			}
		
	}
}
