package de.phillip.controllers;

import de.phillip.animation.GameLoopTimer;
import de.phillip.components.GameMenu;
import de.phillip.gameUtils.Transformer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
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
		System.out.println("Old X: " + event.getX());
		System.out.println("Old Y: " + event.getY());
		Point2D point = Transformer.transformPixelsCoordinatesToTile(event.getX(), event.getY());
		System.out.println("New X: " + point.getX());
		System.out.println("New Y: " + point.getY());
	}
}
