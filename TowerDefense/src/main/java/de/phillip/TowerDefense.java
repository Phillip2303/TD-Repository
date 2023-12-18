package de.phillip;

import de.phillip.components.GameMenu;
import de.phillip.controllers.GameController;
import de.phillip.controls.ResourcePool;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TowerDefense extends Application {
	
	private GameController gameController;
	private GameMenu gameMenu;

	@Override
	public void start(Stage primaryStage) throws Exception {
		ResourcePool.getInstance().loadResources();
		primaryStage.setTitle("Tower Defense Game");
		primaryStage.setMinHeight(1040);
		primaryStage.setMinWidth(1012);
		primaryStage.setMaxHeight(1040);
		primaryStage.setMaxWidth(1012);
		primaryStage.setResizable(false);
		primaryStage.setScene(createScene());
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private Scene createScene() {
		StackPane stackPane = new StackPane();
		BackgroundImage bgi = new BackgroundImage(ResourcePool.getInstance().getGalaxy(), BackgroundRepeat.NO_REPEAT,  
                BackgroundRepeat.NO_REPEAT,  
                BackgroundPosition.DEFAULT,  
                BackgroundSize.DEFAULT);
		stackPane.setBackground(new Background(bgi));
		gameController = new GameController(stackPane);
		gameMenu = new GameMenu(gameController);
		gameController.setMenu(gameMenu);
		gameMenu.setVisible(true);
		stackPane.getChildren().add(gameMenu);
		return new Scene(stackPane);
	}

}
