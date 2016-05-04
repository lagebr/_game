package com.nameless.nameless_game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.InputAdapter;

/**
 * GameInputProcessor is responsible for interpreting input.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-04
 */
public class GameInputProcessor extends InputAdapter {

	private Map<Integer, InputAction> actionLookUp = new HashMap<Integer, InputAction>();

	private List<InputAction> actionQueue = new LinkedList<InputAction>();

	@Override
	public boolean keyDown(int keycode) {
		processInput(keycode);

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		processInput(keycode);

		return true;
	}

	/**
	 * Checks if a keycode corresponds to an action, if so adds it to the action
	 * queue.
	 * 
	 * @param keycode
	 *            The keycode.
	 */
	private void processInput(int keycode) {
		InputAction action = actionLookUp.get(keycode);

		if (action != null) {
			actionQueue.add(action);
		}
	}

	public List<InputAction> getActionQueue() {
		return actionQueue;
	}
}