package Main;

import entity.NPC_Bear;
import object.MagnifyingGlass;

public class AssetSetter {
	GamePanel gp;
	public int[] nbObj;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		this.nbObj = new int[gp.maxMap];
	}
	
	public void setObject() {
		
		
		//First [] is mapNum
		//Second [] is Object Num
 		
		//LEVEL 1
		gp.obj[0][0] = new MagnifyingGlass();
		gp.obj[0][0].worldX = 4 * gp.tileSize;
		gp.obj[0][0].worldY = 2 * gp.tileSize;
		
		
		gp.obj[0][1] = new MagnifyingGlass();
		gp.obj[0][1].worldX = 5 * gp.tileSize;
		gp.obj[0][1].worldY = 4 * gp.tileSize;

		gp.obj[0][2] = new MagnifyingGlass();
		gp.obj[0][2].worldX = 5 * gp.tileSize;
		gp.obj[0][2].worldY = 9 * gp.tileSize;
		
		gp.obj[0][3] = new MagnifyingGlass();
		gp.obj[0][3].worldX = 13 * gp.tileSize;
		gp.obj[0][3].worldY = 11 * gp.tileSize;
		
		gp.obj[0][4] = new MagnifyingGlass();
		gp.obj[0][4].worldX = 20 * gp.tileSize;
		gp.obj[0][4].worldY = 8 * gp.tileSize;
		
		gp.obj[0][5] = new MagnifyingGlass();
		gp.obj[0][5].worldX = 24 * gp.tileSize;
		gp.obj[0][5].worldY = 2 * gp.tileSize;
		nbObj[0] = 6;
		
		
		//LEVEL 2
		gp.obj[1][0] = new MagnifyingGlass();
		gp.obj[1][0].worldX = 12 * gp.tileSize;
		gp.obj[1][0].worldY = 7 * gp.tileSize;
		
		gp.obj[1][1] = new MagnifyingGlass();
		gp.obj[1][1].worldX = 13 * gp.tileSize;
		gp.obj[1][1].worldY = 7 * gp.tileSize;
		
		gp.obj[1][2] = new MagnifyingGlass();
		gp.obj[1][2].worldX = 14 * gp.tileSize;
		gp.obj[1][2].worldY = 7 * gp.tileSize;
		
		gp.obj[1][3] = new MagnifyingGlass();
		gp.obj[1][3].worldX = 15 * gp.tileSize;
		gp.obj[1][3].worldY = 7 * gp.tileSize;
		
		gp.obj[1][4] = new MagnifyingGlass();
		gp.obj[1][4].worldX = 16 * gp.tileSize;
		gp.obj[1][4].worldY = 7 * gp.tileSize;
		nbObj[1] = 5;
		
		
		//Level 3
		gp.obj[2][0] = new MagnifyingGlass();
		gp.obj[2][0].worldX = 4 * gp.tileSize;
		gp.obj[2][0].worldY = 2 * gp.tileSize;
		
		
		gp.obj[2][1] = new MagnifyingGlass();
		gp.obj[2][1].worldX = 8 * gp.tileSize;
		gp.obj[2][1].worldY = 8 * gp.tileSize;

		gp.obj[2][2] = new MagnifyingGlass();
		gp.obj[2][2].worldX = 9 * gp.tileSize;
		gp.obj[2][2].worldY = 13 * gp.tileSize;
		
		gp.obj[2][3] = new MagnifyingGlass();
		gp.obj[2][3].worldX = 22 * gp.tileSize;
		gp.obj[2][3].worldY = 2 * gp.tileSize;
		
		gp.obj[2][4] = new MagnifyingGlass();
		gp.obj[2][4].worldX = 20 * gp.tileSize;
		gp.obj[2][4].worldY = 12 * gp.tileSize;
		
		gp.obj[2][5] = new MagnifyingGlass();
		gp.obj[2][5].worldX = 22 * gp.tileSize;
		gp.obj[2][5].worldY = 13 * gp.tileSize;
		nbObj[2] = 6;

	}
	
	public void setNPC() {

		//LEVEL 1 
		gp.npc[0][0] = new NPC_Bear(gp);
		gp.npc[0][0].x = 4 * gp.tileSize;
		gp.npc[0][0].y = 1 * gp.tileSize;
		
		gp.npc[0][1] = new NPC_Bear(gp);
		gp.npc[0][1].x = 10 * gp.tileSize;
		gp.npc[0][1].y = 10 * gp.tileSize;
		
		gp.npc[0][2] = new NPC_Bear(gp);
		gp.npc[0][2].x = 24 * gp.tileSize;
		gp.npc[0][2].y = 1 * gp.tileSize;
		
		//LEVEL 2
		gp.npc[1][0] = new NPC_Bear(gp);
		gp.npc[1][0].x = 5 * gp.tileSize;
		gp.npc[1][0].y = 7 * gp.tileSize;
		
		gp.npc[1][1] = new NPC_Bear(gp);
		gp.npc[1][1].x = 23 * gp.tileSize;
		gp.npc[1][1].y = 7 * gp.tileSize;
		
		gp.npc[1][2] = new NPC_Bear(gp);
		gp.npc[1][2].x = 14 * gp.tileSize;
		gp.npc[1][2].y = 12 * gp.tileSize;
		
		//Level 3
		gp.npc[2][0] = new NPC_Bear(gp);
		gp.npc[2][0].x = 5 * gp.tileSize;
		gp.npc[2][0].y = 5 * gp.tileSize;
		
		gp.npc[2][1] = new NPC_Bear(gp);
		gp.npc[2][1].x = 13 * gp.tileSize;
		gp.npc[2][1].y = 12 * gp.tileSize;
		
		gp.npc[2][2] = new NPC_Bear(gp);
		gp.npc[2][2].x = 21 * gp.tileSize;
		gp.npc[2][2].y = 7 * gp.tileSize;
		
	}
}
