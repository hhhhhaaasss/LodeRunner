package Main;

import entity.Entity;

public class Gravity {
	GamePanel gp;
	
	public Gravity(GamePanel gp){
		this.gp = gp;
	}
	
	public void CheckGravity(Entity entity){
		int entityLeftWorldX = entity.x + entity.solidArea.x;
		int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.y + entity.solidArea.y;
		int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2, tileNum3, tileNum4;
		
		entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
		tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
		tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
		
		entityTopRow = (entityTopWorldY + entity.speed)/gp.tileSize;
		tileNum3 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow-1];
		tileNum4 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow-1];
		
		
		if(((gp.tileM.tile[tileNum1].collision == 0 && gp.tileM.tile[tileNum2].collision == 0) || (gp.tileM.tile[tileNum1].collision == 3 && gp.tileM.tile[tileNum2].collision == 3)) && gp.keyH.ropePressed == false){
			entity.y += entity.speed;
		}
	}
}
