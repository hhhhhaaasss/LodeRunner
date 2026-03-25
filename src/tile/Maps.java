package tile;

import Main.GamePanel;

public class Maps {
	GamePanel gp;
	public String[] mapLocation;
	public String[] grid;
	
	
	public Maps(GamePanel gp){
		this.gp = gp;
		mapLocation = new String[gp.maxMap];
		//grid = new String[gp.maxMap];
		mapLocation[0] = "/maps/test2.txt";
		//grid[0] = "";
		mapLocation[1] = "/maps/test.txt";
	}
	
	public String getMap(int mapNum) {
		return mapLocation[mapNum];
	}
}
