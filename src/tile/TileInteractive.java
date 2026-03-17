package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import entity.Entity;

public class TileInteractive extends Thread{
	
	GamePanel gp;
	public Tile tile[];
	public int mapTileNum[][][];
	public boolean destructible = false;
	public BufferedImage break1, break2, break3, break4;
	public int breakImage = 0;
	public int spriteNum = 0;
	int tileNum1, tileNum2, tileNum3, tileNum4;
	
	int entityLeftWorldX;
	int entityRightWorldX;
	int entityBottomWorldY;
	
	int entityLeftCol;
	int entityRightCol;
	int entityBottomRow;
	
	int target = 0;
	
	boolean destructable = false;
	
	public TileInteractive(GamePanel gp){

		this.gp = gp;
		getTileImage();
		breakImage = 1;
	}
	
	public void getTileImage() {
		
		try {
			
			break1 = ImageIO.read(getClass().getResourceAsStream("/interactive/wall_1_break_1.png"));
			break2 = ImageIO.read(getClass().getResourceAsStream("/interactive/wall_1_break_2.png"));
			break3 = ImageIO.read(getClass().getResourceAsStream("/interactive/wall_1_break_3.png"));
			break4 = ImageIO.read(getClass().getResourceAsStream("/interactive/bg_1.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void breaking(Entity entity) { 
	    entityLeftWorldX = entity.x + entity.solidArea.x; 
		entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width; 
		entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height; 
		
		entityLeftCol = entityLeftWorldX/gp.tileSize; 
		entityRightCol = entityRightWorldX/gp.tileSize; 
		entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
		
		target = 0;
		
		tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow]; 
		tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

		
		
		switch(entity.direction) {
		case "left":
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol+1][entityBottomRow]; 
			target = entityLeftCol;
			break;
		
		case "right":
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol+1][entityBottomRow]; 
			target = entityRightCol;
			break;
		}
		if(gp.tileM.tile[tileNum1].isDestructible == true || gp.tileM.tile[tileNum2].isDestructible == true){ 
			spriteNum++;
			destructable = true;
			if(spriteNum > 10 && spriteNum < 20)  breakImage = 1; 
			if(spriteNum > 20 && spriteNum < 30)  breakImage = 2;  
			if(spriteNum > 30 && spriteNum < 40)  breakImage = 3;  
			if(spriteNum > 40 && spriteNum < 50)  breakImage = 4;
			
			} 
		else {
			destructable = false;
		}
		}
	
	public void restore() {
		
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		int worldX = target * gp.tileSize;
		int worldY = entityBottomRow * gp.tileSize;
		
		if(breakImage == 1) image = break1;
		if(breakImage == 2) image = break2;
		if(breakImage == 3) image = break3;
		if(breakImage == 4) image = break4;
		
		if(gp.keyH.spacePressed == true && destructable) {
			
		g2.drawImage(image, worldX, worldY , gp.tileSize, gp.tileSize, null);
		}
		
		
		
		//g2.drawImage(image, mapNumTile[][][], y, gp.tileSize, gp.tileSize,null);
	}
}
