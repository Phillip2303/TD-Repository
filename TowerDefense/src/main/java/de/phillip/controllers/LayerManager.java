package de.phillip.controllers;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.Tile;
import de.phillip.models.TurretTile;
import de.phillip.rendering.Renderer;
import de.phillip.ui.ActionLayer;
import de.phillip.ui.InfoLayer;
import de.phillip.ui.TerrainLayer;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class LayerManager {
	
	private InfoLayer infoLayer;
	private TerrainLayer terrainLayer;
	private ActionLayer actionLayer;
	private int level = 1;
	private Renderer renderer;
	
	public LayerManager(StackPane stackPane) {
		renderer = new Renderer();
		infoLayer = new InfoLayer(Constants.TERRAINLAYER_HEIGHT, Constants.TERRAINLAYER_HEIGHT, level);
		terrainLayer = new TerrainLayer(Constants.TERRAINLAYER_WIDTH, Constants.TERRAINLAYER_HEIGHT, level);
		actionLayer = new ActionLayer(Constants.TERRAINLAYER_WIDTH, Constants.TERRAINLAYER_HEIGHT, level);
		renderer.registerCanvasLayer(actionLayer);
		renderer.registerCanvasLayer(infoLayer);
		StackPane.setAlignment(terrainLayer, Pos.TOP_LEFT);
		StackPane.setAlignment(actionLayer, Pos.TOP_LEFT);
		stackPane.getChildren().addAll(terrainLayer, actionLayer, infoLayer);
	}
	
	public void nextLevel() {
		level++;
		actionLayer.setLevel(level);
		terrainLayer.setLevel(level);
	}
	
	public void update(float secondsSinceLastFrame) {
		renderer.prepare();
		actionLayer.update(secondsSinceLastFrame);
		renderer.render();
	}
}
