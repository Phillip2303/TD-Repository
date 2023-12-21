package de.phillip.controllers;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.ui.ActionLayer;
import de.phillip.ui.InfoLayer;
import de.phillip.ui.TerrainLayer;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class LayerManager {
	
	private InfoLayer infoLayer;
	private TerrainLayer terrainLayer;
	private ActionLayer actionLayer;
	private int level = 1;
	
	public LayerManager(StackPane stackPane) {
		infoLayer = new InfoLayer(Constants.TERRAINLAYER_HEIGHT, Constants.TERRAINLAYER_HEIGHT, level);
		infoLayer.getGraphicsContext2D().drawImage(ResourcePool.getInstance().getBackground(), 0, 0);
		terrainLayer = new TerrainLayer(Constants.TERRAINLAYER_WIDTH, Constants.TERRAINLAYER_HEIGHT, level);
		actionLayer = new ActionLayer(Constants.TERRAINLAYER_WIDTH, Constants.TERRAINLAYER_HEIGHT, level);
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
		actionLayer.update(secondsSinceLastFrame);
	}
}
