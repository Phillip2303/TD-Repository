package de.phillip.components;

import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class SimplePane extends Pane {
	
	private MenuButton exitButton;
	private VBox menu;
	private MenuButton startButton;
	
	public SimplePane() { 
		menu = new VBox(10);
		setStyle("-fx-background-color:gray");
		setOpacity(0.4);
		setPrefSize(1024, 1024);
		createButtons();
		menu.setTranslateX(350);
		menu.setTranslateY(400);
		menu.getChildren().addAll(exitButton, startButton);
		getChildren().add(menu);
		
		visibleProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					System.out.println("Add Menu");
				}else{
					System.out.println("Remove Menu");
				}
			}
		});
	}
	
	private void createButtons() {
		createExitButton();
		createStartButton();
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
	
	private void createStartButton() {
		startButton = new MenuButton("START");
		startButton.setOnMouseClicked(event1 -> {
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
			ft.setFromValue(1);
			ft.setToValue(0);
			ft.setOnFinished(event2 -> {
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.TD_START, null));
			});
			ft.play();
			startButton.setVisible(false);
		});
	}
}
