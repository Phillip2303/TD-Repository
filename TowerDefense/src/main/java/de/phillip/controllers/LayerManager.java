package de.phillip.controllers;

import java.util.function.Consumer;

import de.phillip.gameUtils.Constants;
import de.phillip.rendering.Renderer;
import de.phillip.ui.ActionLayer;
import de.phillip.ui.InfoLayer;
import de.phillip.ui.TerrainLayer;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class LayerManager implements Consumer<ActionEvent>{
	
	private InfoLayer infoLayer;
	private TerrainLayer terrainLayer;
	private ActionLayer actionLayer;
	private int level = 1;
	private Renderer renderer;
	private boolean waveStarted;
	
	public LayerManager(StackPane stackPane) {
		renderer = new Renderer();
		infoLayer = new InfoLayer(Constants.TERRAINLAYER_HEIGHT, Constants.TERRAINLAYER_HEIGHT, level);
		infoLayer.setOnAction(this);
		terrainLayer = new TerrainLayer(Constants.TERRAINLAYER_WIDTH, Constants.TERRAINLAYER_HEIGHT, level);
		actionLayer = new ActionLayer(Constants.TERRAINLAYER_WIDTH, Constants.TERRAINLAYER_HEIGHT, level);
		renderer.registerCanvasLayer(actionLayer);
		renderer.registerCanvasLayer(infoLayer);
		StackPane.setAlignment(terrainLayer, Pos.TOP_LEFT);
		StackPane.setAlignment(actionLayer, Pos.TOP_LEFT);
		stackPane.getChildren().addAll(infoLayer, terrainLayer, actionLayer);
	}
	
	public void nextLevel() {
		level++;
		actionLayer.setLevel(level);
		terrainLayer.setLevel(level);
	}
	
	public void update(float secondsSinceLastFrame) {
		renderer.prepare();
		if (waveStarted) {
			actionLayer.update(secondsSinceLastFrame);
		}
		renderer.render();
	}
	
	public void handleMouseEvent(MouseEvent mouseEvent) {
		infoLayer.handleMouseEvent(mouseEvent);
	}

	@Override
	public void accept(ActionEvent t) {
		waveStarted = true;
	}
}
