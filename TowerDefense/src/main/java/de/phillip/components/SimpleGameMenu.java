package de.phillip.components;

import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class SimpleGameMenu extends Pane {
	
	private MenuButton exitButton;
	private MenuButton repeatButton;
	private MenuButton startButton;
	private VBox menu;
	
	public SimpleGameMenu() {
		menu = new VBox(10);
		setStyle("-fx-background-color:gray");
		setOpacity(0.4);
		setPrefSize(1024, 1024);
		createButtons();
		menu.setTranslateX(350);
		menu.setTranslateY(400);
		menu.getChildren().addAll(exitButton, repeatButton, startButton);
		getChildren().add(menu);
	}
	
	private void createButtons() {
		createStartButton();
		createRepeatButton();
		createExitButton();
	}
	
	private void createStartButton() {
		startButton = new MenuButton("START");
		startButton.setOnMouseClicked(event1 -> {
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
			ft.setFromValue(1);
			ft.setToValue(0);
			ft.setOnFinished(event2 -> {
				setVisible(false);
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.TD_START, null));
			});
			ft.play();
		});
	}
	
	private void createExitButton() {
		exitButton = new MenuButton("EXIT");
		exitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.exit(0);
			}
			
		});
	}
	
	private void createRepeatButton() {
		repeatButton = new MenuButton("REPEAT LEVEL");
		repeatButton.setOnMouseClicked(event1 -> {
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
			ft.setFromValue(1);
			ft.setToValue(0);
			ft.setOnFinished(event2 -> {
				setVisible(false);
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.TD_REPEAT, null));
			});
			ft.play();
		});
	}
}
