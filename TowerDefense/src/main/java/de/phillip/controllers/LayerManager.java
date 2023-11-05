package de.phillip.controllers;

import de.phillip.controls.Constants;
import de.phillip.controls.ResourcePool;
import de.phillip.ui.TerrainLayer;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class LayerManager {
	
	private Canvas backgroundLayer;
	private Canvas terrainLayer;
	private int level = 0;
	private StackPane stackPane;
	
	public LayerManager(StackPane stackPane) {
		this.stackPane = stackPane;
		backgroundLayer = new Canvas(1012, 1012);
		backgroundLayer.getGraphicsContext2D().drawImage(ResourcePool.getInstance().getBackground(), 0, 0);
	}
	
	public void nextLevel() {
		level++;
		createLevel();
	}
	
	private void createLevel() {
		stackPane.getChildren().removeAll(backgroundLayer);
		terrainLayer = new TerrainLayer(Constants.TERRAINLAYER_WIDTH*Constants.TILESIZE, Constants.TERRAINLAYER_HEIGHT*Constants.TILESIZE);
		StackPane.setAlignment(terrainLayer, Pos.TOP_LEFT);
		stackPane.getChildren().addAll(backgroundLayer, terrainLayer);
	}
}