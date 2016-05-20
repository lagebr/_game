package com.nameless.nameless_game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * Entity describes a base entity which has a physics body and a texture for
 * rendering.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-04
 */
public class Entity {
	protected Body body;
	protected Sprite sprite;
	
	private boolean flaggedForDeletion = false;

	// Filtering masks
	public final static short PLAYER_ENTITY = 0x1;
	public final static short NPC_ENTITY = 0x1 << 1;

	/**
	 * Creates an entity with a static physics body and a texture.
	 * 
	 * @param x
	 *            Center on x-axis in number of pixels.
	 * @param y
	 *            Center on y-axis in number of pixels.
	 * @param width
	 *            Width in number of pixels.
	 * @param height
	 *            Height in number of pixels.
	 * @param texture
	 *            Texture to use.
	 * @param world
	 *            Physics world to add body to.
	 */
	public Entity(float x, float y, float width, float height, Texture texture, World world) {
		body = createStaticBody(ScreenRenderer.pixelToMeter(x), ScreenRenderer.pixelToMeter(y),
				ScreenRenderer.pixelToMeter(width), ScreenRenderer.pixelToMeter(height), world);

		sprite = new Sprite(texture, (int) width, (int) height);
	}

	/**
	 * Creates an entity with a texture.
	 * 
	 * @param texture
	 *            The texture.
	 */
	public Entity(Texture texture) {
		sprite = new Sprite(texture);
	}

	/**
	 * Empty constructor.
	 */
	public Entity() {
		// This constructor is intentionally left empty.
	}

	/**
	 * Updates the entities animation, and behavior.
	 * 
	 * @param deltaTime
	 *            Time past since last frame in seconds.
	 */
	public void update(float deltaTime) {
		float x = ScreenRenderer.meterToPixel(body.getPosition().x) - sprite.getWidth() / 2;
		float y = ScreenRenderer.meterToPixel(body.getPosition().y) - sprite.getHeight() / 2;

		sprite.setRotation(body.getAngle() * 180.0f / (float) Math.PI);
		sprite.setPosition(x, y);
	}

	/**
	 * createStaticBody creates a rectangular, static physics body, adds it to
	 * the physics world and returns it.
	 * 
	 * @param x
	 *            The x center of the body in physics meters.
	 * @param y
	 *            The y center of the body in physics meters.
	 * @param width
	 *            The width of the body in physics meters.
	 * @param height
	 *            The height of the body in physics meters.
	 * @param world
	 *            the world to add the body to
	 * @return the physics body
	 */
	private Body createStaticBody(float x, float y, float width, float height, World world) {
		BodyDef bodyDef = PhysicsHelper.createBodyDef(x, y, BodyType.StaticBody, true);

		Body physicsBody = world.createBody(bodyDef);

		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);

		FixtureDef fixtureDef = PhysicsHelper.createFixture(rectangle, 0.5f);

		// Collision masks
		fixtureDef.filter.categoryBits = Entity.NPC_ENTITY;
		fixtureDef.filter.maskBits = Entity.PLAYER_ENTITY | Entity.NPC_ENTITY;

		physicsBody.createFixture(fixtureDef);

		rectangle.dispose(); // LibGDX

		return physicsBody;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public boolean isFlaggedForDeletion() {
		return flaggedForDeletion;
	}

	public void setFlaggedForDeletion(boolean flaggedForDeletion) {
		this.flaggedForDeletion = flaggedForDeletion;
	}
}
