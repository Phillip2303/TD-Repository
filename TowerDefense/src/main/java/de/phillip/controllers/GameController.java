package de.phillip.controllers;

import de.phillip.animation.GameLoopTimer;
import de.phillip.components.GameMenu;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class GameController implements EventHandler<MouseEvent> {
	
	private GameLoopTimer gameLoopTimer;
	private LayerManager layerManager;
	private boolean isStarted;
	private GameMenu gameMenu;
	private StackPane stackPane;

	public GameController(StackPane stackPane) {
		//layerManager = new LayerManager(stackPane);
		this.stackPane = stackPane;
		gameLoopTimer = new GameLoopTimer() {

			@Override
			public void tic(float secondsSinceLastFrame) {
				layerManager.update(secondsSinceLastFrame);
			}
		};
	}
	
	public void startGame() {
		if (!isStarted) {
			layerManager = new LayerManager(stackPane);
			gameLoopTimer.start();
			isStarted = true;
		}
	}
	
	public void setMenu(GameMenu gameMenu) {
		this.gameMenu = gameMenu;
	}

	@Override
	public void handle(MouseEvent event) {
		System.out.println("X: " + event.getX());
		System.out.println("Y: " + event.getY());
	}
}
