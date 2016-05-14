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
	// TODO Change from Map and List to IntArray from LibGDX
	
	// The mapping of keys (ie keycodes) to specific InputActions
	public static Map<Integer, InputAction> actionLookUp = new HashMap<Integer, InputAction>();
	// The queue for the processed InputActions (ie InputEvents)
	private List<InputEvent> actionQueue = new LinkedList<InputEvent>();

	public GameInputProcessor() {
		// Moving LEFT
		actionLookUp.put(Input.Keys.A, InputAction.LEFT);
		actionLookUp.put(Input.Keys.LEFT, InputAction.LEFT);
		
		// Moving RIGHT
		actionLookUp.put(Input.Keys.D, InputAction.RIGHT);
		actionLookUp.put(Input.Keys.RIGHT, InputAction.RIGHT);
		
		// Moving UP
		actionLookUp.put(Input.Keys.W, InputAction.UP);
		actionLookUp.put(Input.Keys.UP, InputAction.UP);
		
		// Boosting
		actionLookUp.put(Input.Keys.SPACE, InputAction.BOOST);
	}

	public void setActionLookUp(Map<Integer, InputAction> actionLookUp) {
		this.actionLookUp = actionLookUp;
	}

	/**
	 * Processes the key input.
	 */
	@Override
	public boolean keyDown(int keycode) {
		InputAction action = actionLookUp.get(keycode);
		// If null the LookUp found no corresponding InputAction
		if (action != null) {
			InputEvent event = new InputEvent(action, true);
			// Otherwise we init and add the InputEvent to the queue
			actionQueue.add(event);
		}
	
		return true;
	}
	
	/**
	 * Processes the key input.
	 */
	@Override
	public boolean keyUp(int keycode) {
		InputAction action = actionLookUp.get(keycode);

		if (action != null) {
			InputEvent event = new InputEvent(action, false);
			
			actionQueue.add(event);
		}
		
		return true;
	}
	
	/**
	 * Getter for the ActionQueue.
	 * @return The ActionQueue
	 */
	public List<InputEvent> getActionQueue() {
		return actionQueue;
	}
}