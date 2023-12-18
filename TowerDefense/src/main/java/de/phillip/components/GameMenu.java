package de.phillip.components;

import de.phillip.controllers.GameController;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameMenu extends Parent {
	
	private GameController gameController;
	VBox startMenu;
	private MenuState menuState = MenuState.START;
	private MenuButton startButton;

	public GameMenu(GameController gameController) {
		this.gameController = gameController;
		createButtons();
		createStartMenu();
		Rectangle background = new Rectangle(1024, 1024);
		background.setFill(Color.GRAY);
		background.setOpacity(0.4);
		this.getChildren().addAll(background, startMenu);
		visibleProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					addMenu();
				}else{
					removeMenu();
				}
			}
		});
	}
	
	private void createButtons() {
		createStartButton();
	}
	
	private void createStartButton() {
		startButton = new MenuButton("START");
		startButton.setOnMouseClicked(event1 -> {
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
			ft.setFromValue(1);
			ft.setToValue(0);
			ft.setOnFinished(event2 -> {
				setVisible(false);
				gameController.startGame();
			});
			ft.play();
		});
	}
	
	private void addMenu() {
		//will be used in the future!
	}
	
	private void removeMenu() {
		this.getChildren().removeAll(startMenu);
	}
	
	private void createStartMenu() {
		startMenu = new VBox(10);
		startMenu.setTranslateX(350);
		startMenu.setTranslateY(400);
		startMenu.getChildren().add(startButton);
	}
}
