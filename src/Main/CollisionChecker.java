package Main;

import entity.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.x + entity.solidArea.x;
		int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.y + entity.solidArea.y;
		int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		int tempNum1, tempNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			
			int entityTopRow2 = entityTopRow + 1;
			tempNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow2];
			tempNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow2];	
			
			if(gp.tileM.tile[tileNum1].collision == 2 || gp.tileM.tile[tileNum2].collision == 2) {
				entity.collisionOn = 2;
				
				
				
			}else if ((gp.tileM.tile[tileNum1].collision == 0 || gp.tileM.tile[tileNum2].collision == 0) && (gp.tileM.tile[tempNum1].collision == 2 || gp.tileM.tile[tempNum2].collision == 2 )) {
				entity.collisionOn = 2;
			}//else if pour la corde
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			entityTopRow2 = entityTopRow - 1;
			tempNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow2];
			tempNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow2];
			
			if(gp.tileM.tile[tileNum1].collision == 2 || gp.tileM.tile[tileNum2].collision == 2) {
				entity.collisionOn = 2;
			}else if ((gp.tileM.tile[tileNum1].collision == 0 || gp.tileM.tile[tileNum2].collision == 0) && (gp.tileM.tile[tempNum1].collision == 2 || gp.tileM.tile[tempNum2].collision == 2 )) {
				entity.collisionOn = 2;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == 1 || gp.tileM.tile[tileNum2].collision == 1) {
				entity.collisionOn = 1;
			}
			
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == 1 || gp.tileM.tile[tileNum2].collision == 1) {
				entity.collisionOn = 1;
			}
			
			break;
		}
		
	}
	public int checkObject(Entity entity, boolean player) {
	
		int index = 999;
		
		for(int i=0; i < gp.obj.length;i++) {
			if(gp.obj[i] != null) {
				//Get entity solid area pos
				entity.solidArea.x = entity.x + entity.solidArea.x; //increasing every time
				entity.solidArea.y = entity.y + entity.solidArea.y;
				
				//Get obj solid area pos
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch(entity.direction) {
				case "left":
					entity.solidArea.x -= entity.speed;
					//Intersect method from the awt rectangle class, check if two rectangles are coliding with each other
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = 1;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = 1;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}

		}
		
		return index;
	}
}
