package de.phillip.controllers;

import de.phillip.animation.GameLoopTimer;
import de.phillip.controls.Constants;
import de.phillip.controls.ResourcePool;
import de.phillip.rendering.Renderer;
import de.phillip.ui.ActionLayer;
import de.phillip.ui.TerrainLayer;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class LayerManager {
	
	private Canvas backgroundLayer;
	private TerrainLayer terrainLayer;
	private ActionLayer actionLayer;
	private int level = 1;
	private StackPane stackPane;
	
	public LayerManager(StackPane stackPane) {
		this.stackPane = stackPane;
		backgroundLayer = new Canvas(1012, 1012);
		backgroundLayer.getGraphicsContext2D().drawImage(ResourcePool.getInstance().getBackground(), 0, 0);
		terrainLayer = new TerrainLayer(Constants.TERRAINLAYER_WIDTH*Constants.TILESIZE, Constants.TERRAINLAYER_HEIGHT*Constants.TILESIZE, level);
		actionLayer = new ActionLayer(Constants.TERRAINLAYER_WIDTH*Constants.TILESIZE, Constants.TERRAINLAYER_HEIGHT*Constants.TILESIZE, level);
		StackPane.setAlignment(terrainLayer, Pos.TOP_LEFT);
		StackPane.setAlignment(actionLayer, Pos.TOP_LEFT);
		stackPane.getChildren().addAll(backgroundLayer, terrainLayer, actionLayer);
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
