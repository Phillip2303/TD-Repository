package de.phillip.controllers;

import de.phillip.animation.GameLoopTimer;
import javafx.scene.layout.StackPane;

public class GameController {
	
	private GameLoopTimer gameLoopTimer;
	private LayerManager layerManager;

	public GameController(StackPane stackPane) {
		layerManager = new LayerManager(stackPane);
		gameLoopTimer = new GameLoopTimer() {

			@Override
			public void tic(float secondsSinceLastFrame) {
				
			}
			
		};
		layerManager.nextLevel();
	}

}
