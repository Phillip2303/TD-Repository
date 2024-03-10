package de.phillip.gameUtils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import de.phillip.models.TerrainTile;
import de.phillip.models.Tile;
import javafx.scene.image.Image;

public class ResourcePool {
	private static ResourcePool resourcePool;
	private Image background;
	private Image galaxy;
	private Tile[][] terrainTiles1, path1;
	private Image terrain;
	private Image enemy1;
	private Image bullet1;
	private Image turretBase;
	private Image turretTileSprite;
	private Image startWave;

	private ResourcePool() {
		
	}
	
	public static ResourcePool getInstance() {
		if(resourcePool == null) {
			resourcePool = new ResourcePool();
		}
		return resourcePool;
	}
	
	public void loadResources() {
		background = new Image(getClass().getResource("/assets/images/background.png").toString());
		galaxy = new Image(getClass().getResource("/assets/images/galaxy.jpg").toString());
		turretBase = new Image(getClass().getResource("/assets/images/turrets/turret-base.png").toString());
		turretTileSprite = new Image(getClass().getResource("/assets/images/turrets/turrets_complete.png").toString());
		
		terrain = new Image(getClass().getResource("/assets/images/tiles/terrain.png").toString());
	//	enemy1 = new Image(getClass().getResource("/assets/images/enemies/enemy-red.png").toString());
		startWave = new Image(getClass().getResource("/assets/images/startWave.png").toString());
		terrainTiles1 = new Tile[Constants.TERRAINLAYER_HEIGHT][Constants.TERRAINLAYER_WIDTH];
		try (InputStream input = getClass().getResourceAsStream("/assets/maps/terrain1.pxt")) {
			try(Scanner scanner = new Scanner(input)) {
				while (scanner.hasNext()) {
					for (int y = 0; y < terrainTiles1.length; y++) {
						for (int x = 0; x < terrainTiles1[y].length; x++) {
							int ID = scanner.nextInt();
							terrainTiles1[y][x] = new TerrainTile(x, y, ID, Constants.TILESIZE);
						}
					}
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		path1 = new Tile[Constants.TERRAINLAYER_HEIGHT][Constants.TERRAINLAYER_WIDTH];
		try (InputStream input = getClass().getResourceAsStream("/assets/maps/path1.pxt")) {
			try(Scanner scanner = new Scanner(input)) {
				while (scanner.hasNext()) {
					for (int y = 0; y < path1.length; y++) {
						for (int x = 0; x < path1[y].length; x++) {
							int ID = scanner.nextInt();
							path1[y][x] = new TerrainTile(x*Constants.TILESIZE, y*Constants.TILESIZE, ID, Constants.TILESIZE);
						}
					}
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getStartWave() {
		return startWave;
	}
	
	public Image getBackground() {
		return background;
	}
	
	public Image getEnemy(int level) {
		switch (level) {
		case 1: 
			return enemy1;
		case 2:
			return null;
		default:
			return null;
		}
	}
	
	public Image getBullet(int ID) {
		return new Image(getClass().getResource("/assets/images/turrets/bullet" + ID + ".png").toString());
	}
	
	public Image getTerrain() {
		return terrain;
	}
	
	public Tile[][] getTerrainTiles(int level) {
		switch (level) {
		case 1: 
			return terrainTiles1;
		case 2:
			return terrainTiles1;
		case 3: 
			return terrainTiles1;
		case 4:
			return terrainTiles1;
		default:
			return null;
		}
	}
	
	public Tile[][] getPaths(int level) {
		switch (level) {
		case 1: 
			return path1;
		case 2:
			return path1;
		case 3: 
			return path1;
		case 4: 
			return path1;
		default:
			return null;
		}
	}
	
	public Image getEnemySprite(String resourcePath) {
		return new Image(getClass().getResource(resourcePath).toString());
	}
	
	public Image getTurretSprite(String resourcePath) {
		return new Image(getClass().getResource(resourcePath).toString());
	}
	
	public Image getGalaxy() {
		return galaxy;
	}

	public Image getTurretBase() {
		return turretBase;
	}

	public Image getTurretTileSprite() {
		return turretTileSprite;
	}

	public File loadLevelResource(int level, String actorType) {
		String resourcePath = "assets/level/";
		String actor = "";
		switch (actorType) {
			case "ENEMY":
				actor = "enemy";
				break;
			case "TURRET":
				actor = "turret";
				break;
			default:
				break;
		}
		resourcePath = resourcePath + actor + level + ".json";
		URL url = getClass().getClassLoader().getResource(resourcePath);
		if (url == null) {
			throw new IllegalArgumentException("file not found: " + resourcePath);
		} else {
			try {
				return new File(url.toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}




