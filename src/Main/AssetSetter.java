package Main;

import object.MagnifyingGlass;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new MagnifyingGlass();
		gp.obj[0].worldX = 13 * gp.tileSize;
		gp.obj[0].worldY = 12 * gp.tileSize;
		
		gp.obj[1] = new MagnifyingGlass();
		gp.obj[1].worldX = 20 * gp.tileSize;
		gp.obj[1].worldY = 1 * gp.tileSize;

		gp.obj[2] = new MagnifyingGlass();
		gp.obj[2].worldX = 4 * gp.tileSize;
		gp.obj[2].worldY = 1 * gp.tileSize;

	}
}
