package de.phillip.rendering;

import java.util.ArrayList;
import java.util.List;

import de.phillip.controls.Constants;
import de.phillip.models.Actor;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Renderer {

	private GraphicsContext graphicsContext;
	private Image background;
	private List<Actor> actors = new ArrayList<>();
	
	public Renderer(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
	}
	
	public List<Actor> getActors() {
		return actors;
	}

	public void render() {
		graphicsContext.save();
		for (Actor actor : actors) {
			transformContext(actor);
			actor.drawToCanvas(graphicsContext);
		}
		graphicsContext.restore();
	}

	private void transformContext(Actor actor) {
		Point2D center = actor.getCenter();
		Rotate rotate = new Rotate(actor.getRotation(), center.getX(), center.getY());
		graphicsContext.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(),
				rotate.getTy());
	}
	
	public void prepare() {
		graphicsContext.clearRect(0, 0, Constants.TERRAINLAYER_WIDTH*Constants.TILESIZE, Constants.TERRAINLAYER_HEIGHT*Constants.TILESIZE);
	}

}