package de.phillip.models;

import java.util.List;

import de.phillip.events.FXEventBus;
import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.gameUtils.Transformer;
import de.phillip.models.transferObjects.TurretTO;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Turret extends Actor implements EventHandler<MouseEvent>{
	
	private Image turretBaseSprite;
	private Image turretCannonSprite;
	private int ID;
	private boolean isSelected;
	private boolean isNew = true;
	private boolean isDeleted;
	private int range;
	private Color rangeColour = new Color(0.49803922f, 1.0f, 0.83137256f, 0.25);
	private TurretTO turret;
	private Enemy target;
	private float cooldownSeconds;
	private boolean isCoolingDown;

	public Turret(double width, double height, int ID, TurretTO turret) {
		super(width, height);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		this.turret = turret;
		this.turretBaseSprite = turret.getTurretSprite();
		this.turretCannonSprite = turret.getCannonSprite();
		this.ID = ID;
		range = turret.getRange();
	}
	
	public void unregisterHandler() {
		FXEventBus.getInstance().removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		if (isSelected) {
			//gc.rect(0, ID * Constants.TILESIZE, Constants.TILESIZE, Constants.TILESIZE);
			gc.setStroke(Color.WHITE);
	        gc.setLineWidth(1);
	        gc.strokeRect(getDrawPosition().getX(), getDrawPosition().getY(), Constants.TILESIZE, Constants.TILESIZE);
	        int turretRange = range * Constants.TILESIZE * 2;
	        gc.setFill(rangeColour);
	        gc.fillOval(getCenter().getX() - turretRange / 2, getCenter().getY() - turretRange / 2, turretRange, turretRange);
		}
		gc.drawImage(turretBaseSprite, 0, ID * Constants.TILESIZE, Constants.TILESIZE, 
				Constants.TILESIZE, getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), 
				getHeight());
		transformContext(gc);
		gc.drawImage(turretCannonSprite, 0, ID * Constants.TILESIZE, Constants.TILESIZE, 
				Constants.TILESIZE, getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), 
				getHeight());
		gc.restore();
	}

	@Override
	public void debugOut() {

	}
	
	public void select(boolean value) {
		if (isNew) {
			isNew = false;
		} else {
			isSelected = value;
		}
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public void handle(MouseEvent mouseEvent) {
		switch (mouseEvent.getButton()) {
			case PRIMARY:
				mouseLeftClicked(mouseEvent.getX(), mouseEvent.getY());
				break;
			case SECONDARY:
				mouseRightClicked(mouseEvent.getX(), mouseEvent.getY());
				break;
			default: 
				break;
		}
	}

	private void mouseRightClicked(double eventX, double eventY) {
		if (isSelected) {
			isDeleted = true;
		}
	}

	private void mouseLeftClicked(double eventX, double eventY) {
		Point2D selectedTileCoor = Transformer.transformPixelsCoordinatesToTile(eventX, eventY);
		double x = selectedTileCoor.getX() * Constants.TILESIZE;
		double y = selectedTileCoor.getY() * Constants.TILESIZE;
		if (getDrawPosition().getX() == x)  {
			if (getDrawPosition().getY() == y) {
				select(!isSelected);
				return;
			}
		}
		select(false);
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public int getRange() {
		return range;
	}
	
	public boolean canReach(List<Enemy> enemies) {
		if (target == null) {
			for (Enemy enemy: enemies) {
				if (Transformer.getDistance(enemy.getCenter().getX(), enemy.getCenter().getY(), getCenter().getX(),
						getCenter().getY()) <= range * Constants.TILESIZE) {
					target = enemy;
					return true;
				}
			}
			return false;
		} else {
			if (Transformer.getDistance(target.getCenter().getX(), target.getCenter().getY(), getCenter().getX(),
					getCenter().getY()) <= range * Constants.TILESIZE) {
				return true;
			} else {
				target = null;
				return false;
			}
		}
	}
	
	public Bullet shoot() {
		Bullet bullet = null;
		if (target != null && !isCoolingDown) {
			bullet = new Bullet(turret.getBulletImage(), turret.getRange());
			bullet.setStartPosition(getCenter().getX(), getCenter().getY());
			bullet.setRotation(Transformer.getDirection(target.getCenter().getX(), target.getCenter().getY(), getCenter().getX(), getCenter().getY())-90);
			bullet.setSpeedLevel(turret.getBulletSpeed());
			setRotation(Transformer.getDirection(target.getCenter().getX(), target.getCenter().getY(), getCenter().getX(), getCenter().getY()));
			isCoolingDown = true;
		}
		return bullet;
	}
	
	public void cooldown(float secondsSinceLastFrame) {
		if(isCoolingDown) {
			cooldownSeconds += secondsSinceLastFrame;
			if (cooldownSeconds >= turret.getBulletCooldown()) {
				isCoolingDown = false;
				cooldownSeconds = 0;
			}
		}
	}

}
