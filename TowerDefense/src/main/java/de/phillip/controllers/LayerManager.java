package de.phillip.controllers;

import java.util.function.Consumer;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.CanvasButton;
import de.phillip.models.Tile;
import de.phillip.models.TurretTile;
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
		stackPane.getChildren().addAll(terrainLayer, actionLayer, infoLayer);
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
		if (t.getSource() instanceof CanvasButton) {
			waveStarted = true;
		} else if (t.getSource() instanceof TurretTile){
			TurretTile overlay = (TurretTile) t.getSource();
			checkValidTilePosition(overlay);
		}
	}
	
	private void checkValidTilePosition(TurretTile tile) {
		Point2D pointCenter = tile.getCenter();
		Point2D selectedTileCoor = Transformer.transformPixelsCoordinatesToTile(pointCenter.getX(), pointCenter.getY());
		Tile[][] terrainTiles = ResourcePool.getInstance().getTerrainTiles(level);
		Tile selectedTile = terrainTiles[(int) selectedTileCoor.getY()][(int) selectedTileCoor.getX()];
		if (selectedTile.getID() == 8) {
			System.out.println("Valid");
		} else {
			System.out.println("Not Valid");
		}
	}
}
