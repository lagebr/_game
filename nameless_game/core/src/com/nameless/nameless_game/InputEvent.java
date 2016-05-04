package com.nameless.nameless_game;

/**
 * InputEvent describes an event added to an event queue. Contains an input
 * action and a boolean stating if the key was pressed or released when causing
 * the event.
 * 
 * @author Isaac Arvestad, Henrik Lagebrand
 * @version 2016-05-04
 */
public final class InputEvent {
	public InputAction action;
	public boolean keyPressed;

	/**
	 * Constructor takes an input action and a boolean stating if the key was
	 * pressed or released when causing the event.
	 * 
	 * @param action
	 *            The input action.
	 * @param keyPressed
	 *            true if key was pressed, false if it was released.
	 */
	public InputEvent(InputAction action, boolean keyPressed) {
		this.action = action;
		this.keyPressed = keyPressed;
	}
}
