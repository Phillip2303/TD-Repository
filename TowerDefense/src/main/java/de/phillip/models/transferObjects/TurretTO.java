package de.phillip.models.transferObjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.image.Image;

public class TurretTO {
	
	private int damage;
	private int range;
	private double bulletCooldown;
	private int bulletSpeed;
	private int cost;
	private int id;
	@JsonIgnore
	 private Image turretSprite;
	@JsonIgnore
	 private Image cannonSprite;
	@JsonIgnore
	private Image bulletImage;
	
	public TurretTO() {
		
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getBulletSpeed() {
		return bulletSpeed;
	}
	
	public void setBulletSpeed(int bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public double getBulletCooldown() {
		return bulletCooldown;
	}

	public void setBulletCooldown(double bulletCooldown) {
		this.bulletCooldown = bulletCooldown;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Image getTurretSprite() {
		return turretSprite;
	}

	public void setTurretSprite(Image turretSprite) {
		this.turretSprite = turretSprite;
	}

	public Image getCannonSprite() {
		return cannonSprite;
	}

	public void setCannonSprite(Image cannonSprite) {
		this.cannonSprite = cannonSprite;
	}

	public Image getBulletImage() {
		return bulletImage;
	}

	public void setBulletImage(Image bulletImage) {
		this.bulletImage = bulletImage;
	}
}
