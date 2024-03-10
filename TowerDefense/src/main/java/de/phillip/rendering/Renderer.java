package de.phillip.rendering;

import java.util.ArrayList;
import java.util.List;

import de.phillip.gameUtils.Constants;
import de.phillip.models.Actor;
import de.phillip.models.CanvasLayer;
import de.phillip.models.GameInfo;
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
		canvasLayers.forEach(canvas -> {
			canvas.getGraphicsContext2D().save();
			canvas.getDrawables().forEach(d -> d.drawToCanvas(canvas.getGraphicsContext2D()));
			canvas.getGraphicsContext2D().restore();
		});
	}
	
	public void prepare() {
		canvasLayers.forEach(e -> e.prepareLayer());
	}

}