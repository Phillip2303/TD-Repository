package de.phillip.controls;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

import de.phillip.ui.TerrainTile;
import de.phillip.ui.Tile;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class ResourcePool {
	private static ResourcePool resourcePool;
	private Image background;
	private Tile[][] terrainTiles;
	private Image terrain;
	/*private AudioClip explosion;
	private Image player;
	private Image enemy1;
	private Image enemy2;
	private Image bullet;
	private Image explosionImage;
	private Properties gameProperties;*/

	private ResourcePool() {
		
	}
	
	public static ResourcePool getInstance () {
		if(resourcePool == null) {
			resourcePool = new ResourcePool();
		}
		return resourcePool;
	}
	
	public void loadResources() {
		background = new Image(getClass().getResource("/assets/images/background.png").toString());
		terrain = new Image(getClass().getResource("/assets/images/tiles/terrain.png").toString());
		terrainTiles = new Tile[Constants.TERRAINLAYER_HEIGHT][Constants.TERRAINLAYER_WIDTH];
		try (InputStream input = getClass().getResourceAsStream("/assets/maps/terrain1.pxt")) {
			try(Scanner scanner = new Scanner(input)) {
				while (scanner.hasNext()) {
					for (int y = 0; y < terrainTiles.length; y++) {
						for (int x = 0; x < terrainTiles[y].length; x++) {
							int ID = scanner.nextInt();
							terrainTiles[y][x] = new TerrainTile(x*Constants.TILESIZE, y*Constants.TILESIZE, ID, Constants.TILESIZE);
						}
					}
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		/*explosion = new AudioClip(getClass().getResource("/sounds/Explosion.wav").toString());
		player = new Image(getClass().getResource("/images/rocketWithThrust.png").toString());
		enemy1 = new Image(getClass().getResource("/images/enemyRocket.png").toString());
		enemy2 = new Image(getClass().getResource("/images/enemyRocket2.png").toString());
		bullet = new Image(getClass().getResource("/images/bullet.png").toString());
		explosionImage = new Image (getClass().getResource("/images/explosion.png").toString());*/ 
		/*gameProperties = new Properties();
		try (InputStream input = getClass().getResourceAsStream("/text/game.properties")) {
			gameProperties.load(input);
			System.out.println(gameProperties.getProperty("info.color.text"));
			System.out.println(gameProperties.getProperty("info.color.healthbar"));
		} catch (IOException e) {
			e.printStackTrace();
		};*/
	}
	
	public Image getBackground() {
		return background;
	}
	
	public Image getTerrain() {
		return terrain;
	}
	
	public Tile[][] getTerrainTiles() {
		return terrainTiles;
	}
	
	/*public Image getExplosionImage() {
		return explosionImage;
	}

	public AudioClip getExplosion() {
		return explosion;
	}

	public Image getPlayer() {
		return player;
	}

	public Image getEnemy1() {
		return enemy1;
	}

	public Image getEnemy2() {
		return enemy2;
	}

	public Image getBullet() {
		return bullet;
	}
	
	public Properties getGameProperties() {
		return gameProperties;
	}
	
	public void saveGameProperties() {
		try (OutputStream out = new FileOutputStream(getClass().getResource("/text/game.properties").getFile())) {
			gameProperties.store(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		};
	}*/
}




