package com.nameless.nameless_game.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameInputProcessorTest {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Tests that the queue is empty at first.
	 */
	@Test
	public void testQueueEmpty() {
		GameInputProcessor inputProcessor = new GameInputProcessor();
		assertTrue("Queue should be empty at first.", inputProcessor.getActionQueue().isEmpty());
	}

}
