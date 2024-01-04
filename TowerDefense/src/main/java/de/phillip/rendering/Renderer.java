package de.phillip.rendering;

import java.util.ArrayList;
import java.util.List;

import de.phillip.gameUtils.Constants;
import de.phillip.models.Actor;
import de.phillip.models.CanvasLayer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

public class Renderer {

	private List<CanvasLayer> canvasLayers = new ArrayList<>();
	
	public Renderer() {
		
	}
	
	public void registerCanvasLayer(CanvasLayer canvasLayer) {
		canvasLayers.add(canvasLayer);
	}

	public void render() {
		canvasLayers.forEach(e -> {
			e.getGraphicsContext2D().save();
			e.getDrawables().forEach(d -> d.drawToCanvas(e.getGraphicsContext2D()));
			e.getGraphicsContext2D().restore();
		});
	}
	
	public void prepare() {
		canvasLayers.forEach(e -> e.prepareLayer());
	}

}