package de.phillip.components;

import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameMenu extends Parent {
	
	VBox startMenu;
	VBox repeatMenu;
	private MenuState menuState = MenuState.START;
	private MenuButton startButton;
	MenuButton repeatButton;
	MenuButton exitButton;

	public GameMenu() {
		createButtons();
		createStartMenu();
		createRepeatMenu();
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
		repeatButton = new MenuButton("REPEAT GAME");
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
	
	private void addMenu() {
		switch (menuState) {
			case REPEAT:
				//repeatMenu.getChildren().addAll(repeatButton, exitButton);
				this.getChildren().add(repeatMenu);
				break;
			default:
				break;
		}
	}
	
	private void removeMenu() {
			/*switch (menuState) {
			case REPEAT:
				repeatMenu.getChildren().removeAll(repeatButton, exitButton);
				break;
			default:
				break;
		}*/
			this.getChildren().removeAll(startMenu, repeatMenu);
	}
	
	private void createStartMenu() {
		startMenu = new VBox(10);
		startMenu.setTranslateX(350);
		startMenu.setTranslateY(400);
		startMenu.getChildren().add(startButton);
	}
	
	private void createRepeatMenu() {
		repeatMenu = new VBox(10);
		repeatMenu.setTranslateX(350);
		repeatMenu.setTranslateY(400);
		repeatMenu.getChildren().addAll(repeatButton, exitButton);
	}
	
	public void setMenuOptions(MenuState menuState) {
		this.menuState = menuState;
	}

}
