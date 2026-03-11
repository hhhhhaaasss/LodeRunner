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
		int tempNum12, tempNum22;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			
			int entityTopRow2 = entityTopRow + 1;
			tempNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow2];
			tempNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow2];

			tempNum12 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow-1];
			tempNum22 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow-1];	
			
			if(gp.tileM.tile[tileNum1].collision == 2 || gp.tileM.tile[tileNum2].collision == 2) {
				entity.collisionOn = 2;
				
				
				
			}else if ((gp.tileM.tile[tileNum1].collision == 0 || gp.tileM.tile[tileNum2].collision == 0) && (gp.tileM.tile[tempNum1].collision == 2 || gp.tileM.tile[tempNum2].collision == 2 )) {
				entity.collisionOn = 2;
				System.out.println("bonjour");

			}else if ((gp.tileM.tile[tempNum12].collision == 3 || gp.tileM.tile[tempNum22].collision == 3 )) {
				entity.collisionOn = 3;
				System.out.println("bonjour bg de la street");
			}
			
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			
			entityTopRow2 = entityTopRow - 1;
			tempNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow2];
			tempNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow2];
			
			if(gp.tileM.tile[tileNum1].collision == 2 || gp.tileM.tile[tileNum2].collision == 2) {
				entity.collisionOn = 2;
			}else if ((gp.tileM.tile[tileNum1].collision == 0 || gp.tileM.tile[tileNum2].collision == 0) && (gp.tileM.tile[tempNum1].collision == 2 || gp.tileM.tile[tempNum2].collision == 2 )) {
				entity.collisionOn = 2;
			}

			else if ((gp.tileM.tile[tileNum1].collision == 0 || gp.tileM.tile[tileNum2].collision == 0) && (gp.tileM.tile[tempNum1].collision == 3 || gp.tileM.tile[tempNum2].collision == 3 )) {
				entity.collisionOn = 2;
			}

			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == 1 || gp.tileM.tile[tileNum2].collision == 1) {
				entity.collisionOn = 1;
			}
			
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == 1 || gp.tileM.tile[tileNum2].collision == 1) {
				entity.collisionOn = 1;
			}
			
			break;
		}
		
	}


	
	public int checkObject(Entity entity, boolean player) {
	
		int index = 999;
		
		for(int i=0; i < gp.obj[1].length;i++) {
			if(gp.obj[gp.currentMap][i] != null) {
				//Get entity solid area pos
				entity.solidArea.x = entity.x + entity.solidArea.x; //increasing every time
				entity.solidArea.y = entity.y + entity.solidArea.y;
				
				//Get obj solid area pos
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;
				
				switch(entity.direction) {
				case "left":
					entity.solidArea.x -= entity.speed;
					//Intersect method from the awt rectangle class, check if two rectangles are coliding with each other
					if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision == true) {
							entity.collisionOn = 1;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision == true) {
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
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
			}

		}
		
		return index;
	}
	//NPC Collision
	public int checkEntity(Entity entity, Entity[][] target) {
		
		int index = 999;
		
		for(int i=0; i < target[1].length;i++) {
			if(target[gp.currentMap][i] != null) {
				//Get entity solid area pos
				entity.solidArea.x = entity.x + entity.solidArea.x; //increasing every time
				entity.solidArea.y = entity.y + entity.solidArea.y;
				
				//Get target solid area pos
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].x + target[gp.currentMap][i].solidArea.x;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].y + target[gp.currentMap][i].solidArea.y;
				
				switch(entity.direction) {
				case "left":
					entity.solidArea.x -= entity.speed;
					//Intersect method from the awt rectangle class, check if two rectangles are coliding with each other
					if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
							entity.collisionOn = 1;
							index = i;
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
							entity.collisionOn = 1;
							index = i;
						}
					break;
					}
					entity.solidArea.x = entity.solidAreaDefaultX;
					entity.solidArea.y = entity.solidAreaDefaultY;
					target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
					target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
				}

		
			}
		
		return index;
		
	}
	
	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;
		
		//Get entity solid area pos
		entity.solidArea.x = entity.x + entity.solidArea.x; //increasing every time
		entity.solidArea.y = entity.y + entity.solidArea.y;
		
		//Get target solid area pos
		gp.player.solidArea.x = gp.player.x + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.y + gp.player.solidArea.y;
		
		switch(entity.direction) {
		case "left":
			entity.solidArea.x -= entity.speed;
			//Intersect method from the awt rectangle class, check if two rectangles are coliding with each other
			if(entity.solidArea.intersects(gp.player.solidArea)) {
					entity.collisionOn = 1;
					contactPlayer = true;
					
			}
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			if(entity.solidArea.intersects(gp.player.solidArea)) {
					entity.collisionOn = 1;
					contactPlayer = true;

				}
			break;
			}
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			
			return contactPlayer;
		}
	
		
}
