package com.nameless.nameless_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.sun.glass.ui.Pixels;
import com.sun.glass.ui.Pixels.Format;

public class NamelessGame extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	Rectangle rectangle;
	
	OrthographicCamera camera;
	
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		rectangle = new Rectangle(100,100,100,100);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
	}

	@Override
	public void render () {		
		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(1, 0, 0, 1);
		
		shapeRenderer.translate(1, 0, 0);
		
		shapeRenderer.rect(0, 0, rectangle.width, rectangle.height);
		
		shapeRenderer.end();
		
	}
}
