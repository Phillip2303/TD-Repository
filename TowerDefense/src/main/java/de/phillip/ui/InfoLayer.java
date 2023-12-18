package de.phillip.ui;

import de.phillip.controls.Constants;
import javafx.scene.canvas.Canvas;

public class InfoLayer extends Canvas {

	public InfoLayer(int tileWidth, int tileHeight, int level) {
		super(tileWidth*Constants.TILESIZE, tileHeight*Constants.TILESIZE);
	}

}
