package com.nameless.nameless_game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.nameless_game.model.Entity;
import com.nameless.nameless_game.render.Renderer;
import com.nameless.nameless_game.render.ScreenRenderer;

/**
 * NamelessGame is the main controller for the game. Handles input and updates.
 * 
 * @author Henrik Lagebrand, Isaac Arvestad
 * @version 2016-05-04
 */
public class NamelessGame extends ApplicationAdapter {

	Renderer renderer;
	World world;
	
	ArrayList<Entity> entities;

	@Override
	public void create() {
		renderer = new ScreenRenderer(800, 600);
		world = new World(new Vector2(0, 0), true);
		entities = new ArrayList<Entity>();
		
		Texture texture = new Texture(Gdx.files.internal("BlueSquare100x100.png"));
		Entity entity = new Entity(100, 100, 100, 100, texture, world);
		entities.add(entity);
	}

	@Override
	public void render() {
		renderer.render(entities);
	}
}
