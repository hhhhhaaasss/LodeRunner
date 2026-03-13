package Main;

import entity.NPC_Bear;
import object.MagnifyingGlass;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		
		//First [] is mapNum
		//Second [] is Object Num
 		
		gp.obj[0][0] = new MagnifyingGlass();
		gp.obj[0][0].worldX = 13 * gp.tileSize;
		gp.obj[0][0].worldY = 12 * gp.tileSize;
		
		gp.obj[0][1] = new MagnifyingGlass();
		gp.obj[0][1].worldX = 20 * gp.tileSize;
		gp.obj[0][1].worldY = 1 * gp.tileSize;

		gp.obj[0][2] = new MagnifyingGlass();
		gp.obj[0][2].worldX = 4 * gp.tileSize;
		gp.obj[0][2].worldY = 1 * gp.tileSize;

	}
	
	public void setNPC() {
		
		int mapNum = 0;
		
		gp.npc[0][0] = new NPC_Bear(gp);
		gp.npc[0][0].x = 22 * gp.tileSize;
		gp.npc[0][0].y = gp.tileSize;;
	}
}
