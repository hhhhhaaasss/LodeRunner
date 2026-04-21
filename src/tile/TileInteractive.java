package tile;

import Main.GamePanel;
import entity.Entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileInteractive implements Runnable{
	
	GamePanel gp;
	public Tile tile[];
	public int mapTileNum[][][];
	public boolean destructible = false;
	public BufferedImage break1, break2, break3, break4;
	public int breakImage = 0;
	public int spriteNum = 0;
	

	int entityBottomRow;
	
	int brokenCol = -1;
	int brokenRow = -1;
	int originalTile =-1;
	
	boolean tileBroken = false;
	boolean tileLocked = false;
	
	int target = 0;
	
	boolean destructable = false;

	Thread restore;
	
	
	public void startThread(){
		restore = new Thread(this);
		restore.start();
	}

	public TileInteractive(GamePanel gp){

		this.gp = gp;
		getTileImage();
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
	
	public BufferedImage load(String path) {
	    try {
	        return ImageIO.read(getClass().getResourceAsStream(path));
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public void changeTileI(int map) {

	    switch(map) {

	        case 0:
	            break1 = load("/interactive/wall_1_break_1.png");
	            break2 = load("/interactive/wall_1_break_2.png");
	            break3 = load("/interactive/wall_1_break_3.png");
	            break4 = load("/interactive/bg_1.png");
	            break;

	        case 1:
	            break1 = load("/interactive/wall_2_break_1.png");
	            break2 = load("/interactive/wall_2_break_2.png");
	            break3 = load("/interactive/wall_2_break_3.png");
	            break4 = load("/interactive/bg_2.png");
	            break;

	        case 2:
	            break1 = load("/interactive/wall_3_break_1.png");
	            break2 = load("/interactive/wall_3_break_2.png");
	            break3 = load("/interactive/wall_3_break_3.png");
	            break4 = load("/interactive/bg_3.png");
	            break;
	    }
	}
	
	public void breaking(Entity entity) {
		
	if(tileLocked) return; //This is for locking the player in only breaking one block

    // Player tile position (center of sprite)
    int playerCol = (entity.x + gp.tileSize / 2) / gp.tileSize;
    int playerRow = (entity.y + gp.tileSize / 2) / gp.tileSize;

    int targetCol = playerCol;
    int targetRow = playerRow;

    if(entity.direction.equals("left")) {
        targetCol = playerCol - 1;
        targetRow = playerRow + 1;
    }
    else if(entity.direction.equals("right")) {
        targetCol = playerCol + 1;
        targetRow = playerRow + 1;
    }

    int tileNum = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow];

    if(gp.tileM.tile[tileNum].isDestructible) {
    	
        spriteNum++;
        destructable = true;
        
        if(spriteNum > 10 && spriteNum < 20) breakImage = 1;
        if(spriteNum > 20 && spriteNum < 30) breakImage = 2;
        if(spriteNum > 30 && spriteNum < 40) breakImage = 3;
        if(spriteNum > 40 && spriteNum < 50) {
			breakImage = 4;
			gp.player.score += 50;
			
			if(!tileBroken) {
				brokenCol = targetCol;
				brokenRow = targetRow;
				originalTile = tileNum;				
				
				tileBroken = true;
				tileLocked = true;
				
				
				gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow] = 0;
				
				startThread();
			}
			
		}

        target = targetCol;
        entityBottomRow = targetRow;
    	}
	}
	
	public synchronized void restore() {
		
		
		if(tileBroken == true) {

			gp.tileM.mapTileNum[gp.currentMap][brokenCol][brokenRow] = originalTile;
			tileBroken = false;
			tileLocked = false;
	        
		}

		spriteNum = 0;
		breakImage = 0;
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		if(tileBroken == false && breakImage == 0) return;
		
		int worldX = target * gp.tileSize;
		int worldY = entityBottomRow * gp.tileSize;
		
		if(breakImage == 1) image = break1;
		if(breakImage == 2) image = break2;
		if(breakImage == 3) image = break3;
		if(breakImage == 4) image = break4;
		
		if(image != null) {
			
		g2.drawImage(image, worldX, worldY , gp.tileSize, gp.tileSize, null);
		}
		
	}

	@Override
	public void run(){

		
		try {
			Thread.sleep(3000);
			
			breakImage = 3;
			Thread.sleep(150);
			
			breakImage = 2;
			Thread.sleep(150);
			
			breakImage = 1;
			Thread.sleep(150);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		restore();
	}	
}
