package de.phillip.controllers;

import de.phillip.events.FXEventBus;
import de.phillip.events.GameEvent;
import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.Tile;
import de.phillip.models.TurretTile;
import de.phillip.rendering.Renderer;
import de.phillip.ui.ActionLayer;
import de.phillip.ui.InfoLayer;
import de.phillip.ui.TerrainLayer;
import de.phillip.ui.InfoLayer.State;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class LayerManager implements EventHandler<Event>{
	
	private InfoLayer infoLayer;
	private TerrainLayer terrainLayer;
	private ActionLayer actionLayer;
	private WaveController waveController;
	private TurretController turretController;
	private int level = 1;
	private Renderer renderer;
	
	public LayerManager(StackPane stackPane) {
		FXEventBus.getInstance().addEventHandler(GameEvent.TD_NEXTLEVEL, this);
		renderer = new Renderer();
		turretController = new TurretController(level);
		waveController = new WaveController(level);
		infoLayer = new InfoLayer(Constants.TERRAINLAYER_HEIGHT, Constants.TERRAINLAYER_HEIGHT, level, turretController, waveController);
		terrainLayer = new TerrainLayer(Constants.TERRAINLAYER_WIDTH, Constants.TERRAINLAYER_HEIGHT, level);
		actionLayer = new ActionLayer(Constants.TERRAINLAYER_WIDTH, Constants.TERRAINLAYER_HEIGHT, level, turretController, waveController);
		renderer.registerCanvasLayer(actionLayer);
		renderer.registerCanvasLayer(infoLayer);
		StackPane.setAlignment(terrainLayer, Pos.TOP_LEFT);
		StackPane.setAlignment(actionLayer, Pos.TOP_LEFT);
		stackPane.getChildren().addAll(terrainLayer, actionLayer, infoLayer);
		stackPane.getChildren().get(0).toFront();
	}
	
	public void nextLevel() {
		level++;
		actionLayer.setLevel(level);
		terrainLayer.setLevel(level);
		infoLayer.setLevel(level);
	}
	
	public void update(float secondsSinceLastFrame) {
		renderer.prepare();
		actionLayer.updateLayer(secondsSinceLastFrame);
		infoLayer.updateLayer(secondsSinceLastFrame);
		renderer.render();
	}

	@Override
	public void handle(Event event) {
		switch (event.getEventType().getName()) {
		case "TD_NEXTLEVEL":
			nextLevel();
			break;
		default:
			break;
		}
	}
}
