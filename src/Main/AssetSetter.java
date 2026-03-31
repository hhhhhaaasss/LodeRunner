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
 		
		gp.obj[0][0] = new MagnifyingGlass();
		gp.obj[0][0].worldX = 14 * gp.tileSize;
		gp.obj[0][0].worldY = 11 * gp.tileSize;
		
		
		gp.obj[0][1] = new MagnifyingGlass();
		gp.obj[0][1].worldX = 13 * gp.tileSize;
		gp.obj[0][1].worldY = 11 * gp.tileSize;

		gp.obj[0][2] = new MagnifyingGlass();
		gp.obj[0][2].worldX = 15 * gp.tileSize;
		gp.obj[0][2].worldY = 11 * gp.tileSize;
		nbObj[0] = 3;
		

	}
	
	public void setNPC() {
		
		int mapNum = 0;
		
		gp.npc[0][0] = new NPC_Bear(gp);
		gp.npc[0][0].x = 22 * gp.tileSize;
		gp.npc[0][0].y = gp.tileSize;
		
		
	}
}
