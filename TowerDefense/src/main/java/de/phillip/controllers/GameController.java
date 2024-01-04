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
		if (isStarted) {
			switch (event.getEventType().getName()) {
				case "MOUSE_MOVED":
					layerManager.mouseMoved(event.getX(), event.getY());
					break;
				case "MOUSE_CLICKED":
					layerManager.mouseClicked(event.getX(), event.getY());
					break;
				default: 
					break;
			}
		}
	}
}
