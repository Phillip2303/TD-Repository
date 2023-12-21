package de.phillip.ui;

import de.phillip.gameUtils.Constants;
import javafx.scene.canvas.Canvas;

public class InfoLayer extends Canvas {

	public InfoLayer(int tileWidth, int tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
		setOnMouseClicked(event -> {
			System.out.println("X: " + event.getX());
			System.out.println("Y: " + event.getY());
		});
	}

}
