package com.nameless.nameless_game.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * GameInputProcessor is responsible for interpreting input.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-04
 */
public class GameInputProcessor extends InputAdapter {

	private Map<Integer, InputAction> actionLookUp = new HashMap<Integer, InputAction>();

	private List<InputEvent> actionQueue = new LinkedList<InputEvent>();

	public GameInputProcessor() {
		actionLookUp.put(Input.Keys.A, InputAction.LEFT);
		actionLookUp.put(Input.Keys.LEFT, InputAction.LEFT);
		actionLookUp.put(Input.Keys.D, InputAction.RIGHT);
		actionLookUp.put(Input.Keys.RIGHT, InputAction.RIGHT);
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		InputAction action = actionLookUp.get(keycode);

		if (action != null) {
			InputEvent event = new InputEvent(action, true);
			
			actionQueue.add(event);
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		InputAction action = actionLookUp.get(keycode);

		if (action != null) {
			InputEvent event = new InputEvent(action, false);
			
			actionQueue.add(event);
		}
		
		return true;
	}

	public List<InputEvent> getActionQueue() {
		return actionQueue;
	}
}