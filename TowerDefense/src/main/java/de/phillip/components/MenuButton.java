package de.phillip.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {
	
	private Text text;
	
	public MenuButton(String name) {
		text = new Text(name);
		text.setFont(Font.font(20));
		text.setFill(Color.WHITE);
		Rectangle background = new Rectangle(250, 30);
		background.setEffect(new GaussianBlur(5));
		background.setOpacity(0.6);
		background.setFill(Color.BLACK);
		this.setAlignment(Pos.CENTER_LEFT);
		getChildren().addAll(text, background);
		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());
		this.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				MenuButton.this.setEffect(drop);
			}
			
		});
		this.setOnMouseReleased(e -> setEffect(null));
	}
}
