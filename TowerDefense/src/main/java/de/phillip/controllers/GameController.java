package de.phillip.controllers;

import de.phillip.animation.GameLoopTimer;
import de.phillip.components.GameMenu;
import de.phillip.components.MenuState;
import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class GameController implements EventHandler<GameEvent> {
	
	private GameLoopTimer gameLoopTimer;
	private LayerManager layerManager;
	private boolean isStarted;
	private StackPane stackPane;
	private GameMenu gameMenu;

	public GameController(StackPane stackPane, GameMenu gameMenu) {
		//layerManager = new LayerManager(stackPane);
		FXEventBus.getInstance().addEventHandler(GameEvent.TD_START, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.TD_LOST, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.TD_REPEAT, this);
		this.stackPane = stackPane;
		this.gameMenu = gameMenu;
		gameLoopTimer = new GameLoopTimer() {

			@Override
			public void tic(float secondsSinceLastFrame) {
				layerManager.update(secondsSinceLastFrame);
			}
		};
	}
	
	private void startGame() {
		if (!isStarted) {
			layerManager = new LayerManager(stackPane);
			isStarted = true;
		}
		gameLoopTimer.start();
	}

	@Override
	public void handle(GameEvent event) {
		switch (event.getEventType().getName()) {
			case "TD_START": 
				startGame();
				break;
			case "TD_LOST":
				stopGame();
				break;
			case "TD_REPEAT":
				repeatGame();
				break;
			default:
				break;
		}
	}
	
	private void stopGame() {
		//repeatGame();
		gameLoopTimer.stop();
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
		ft.setFromValue(0);
		ft.setToValue(1);
		gameMenu.setMenuOptions(MenuState.REPEAT);
		gameMenu.setVisible(true);
		ft.play();
		
	}
	
	private void repeatGame() {
		layerManager.reset();
		startGame();
		System.out.println("Repeated Game");
	}
}
