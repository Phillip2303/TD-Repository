package de.phillip.models.transferObjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.image.Image;

public class WaveBlockTO {
	
	 private int speed;
	 private int health;
	 private String imagePath;
	 private int amount;
	 private int money;
	 private String type;
	 @JsonIgnore
	 private Image sprite;
	 
	 public WaveBlockTO() {

	}
	 
	 public void setSprite(Image sprite) {
		 this.sprite = sprite;
	 }
	 
	 public Image getSprite() {
		 return sprite;
	 }

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
