package de.phillip;

import de.phillip.controllers.GameController;
import de.phillip.controls.ResourcePool;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TowerDefense extends Application {
	
	private GameController gameController;

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
		gameController.startGame();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private Scene createScene() {
		StackPane stackPane = new StackPane();
		gameController = new GameController(stackPane);
		return new Scene(stackPane);
	}

}
