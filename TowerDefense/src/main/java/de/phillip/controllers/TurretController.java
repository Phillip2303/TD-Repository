package de.phillip.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.phillip.gameUtils.Constants;
import de.phillip.gameUtils.ResourcePool;
import de.phillip.models.Turret;
import de.phillip.models.transferObjects.TurretTO;
import de.phillip.models.transferObjects.TurretsTO;
import javafx.scene.image.Image;

public class TurretController {
	
	private int level;
	private TurretsTO turrets;

	public TurretController(int level) {
		this.level = level;
		setLevel(this.level);
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
		loadLevelResource();
		createTurretSprites();
	}
	
	private void loadLevelResource() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			turrets = objectMapper.readValue(ResourcePool.getInstance().loadLevelResource(level, "TURRET"), TurretsTO.class);
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createTurretSprites() {
		turrets.getTurrets().forEach(b -> {
			Image turretSprite = ResourcePool.getInstance().getTurretSprite(turrets.getTurretSpritePath());
			b.setTurretSprite(turretSprite);
			Image cannonSprite = ResourcePool.getInstance().getTurretSprite(turrets.getCannonSpritePath());
			b.setCannonSprite(cannonSprite);
			Image bulletImage = ResourcePool.getInstance().getBullet(level);
			b.setBulletImage(bulletImage);
		});
	}
	
	public Turret createTurret(int ID) {
		TurretTO turret = turrets.getTurrets().stream().filter(a -> a.getId() == ID).collect(Collectors.toList()).get(0);
		return new Turret(Constants.TILESIZE, Constants.TILESIZE, ID, turret);
	}
	
	public List<TurretTO> getTurrets() {
		return turrets.getTurrets();
	}
	
	public int costOfUpgrade(int ID) {
		TurretTO turret = turrets.getTurrets().stream().filter(a -> a.getId() == ID + 1).collect(Collectors.toList()).get(0);
		if (turret != null) { 
			return turret.getCost();
		} else {
			return 1000000000;
		}
	}
	
	public boolean canUpgrade(int ID) {
		TurretTO turret = turrets.getTurrets().stream().filter(a -> a.getId() == ID + 1).collect(Collectors.toList()).get(0);
		if (turret != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void upgrade(Turret selectedTurret) {
		TurretTO turret = turrets.getTurrets().stream().filter(a -> a.getId() == selectedTurret.getID() + 1).collect(Collectors.toList()).get(0);
		if (turret != null) {
			selectedTurret.setTurret(turret);
			selectedTurret.setID(turret.getId());
			selectedTurret.setTurretBaseSprite(turret.getTurretSprite());
			selectedTurret.setTurretCannonSprite(turret.getCannonSprite());
		}
	}

}
