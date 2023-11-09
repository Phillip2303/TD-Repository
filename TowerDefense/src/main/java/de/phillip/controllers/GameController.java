package de.phillip.controllers;

import de.phillip.animation.GameLoopTimer;
import javafx.scene.layout.StackPane;

public class GameController {
	
	private GameLoopTimer gameLoopTimer;
	private LayerManager layerManager;
	private boolean isStarted;

	public GameController(StackPane stackPane) {
		layerManager = new LayerManager(stackPane);
		gameLoopTimer = new GameLoopTimer() {

			@Override
			public void tic(float secondsSinceLastFrame) {
				layerManager.update(secondsSinceLastFrame);
			}
		};
	}
	
	public void startGame() {
		if (!isStarted) {
			gameLoopTimer.start();
			isStarted = true;
		}
	}
}
