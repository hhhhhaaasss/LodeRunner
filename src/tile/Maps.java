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
		mapLocation[2] = "/maps/test.txt";
		//TODO Change this later
		mapLocation[1] = "/maps/reveal.txt";
	}
	
	public String getMap(int mapNum) {
		return mapLocation[mapNum];
	}
}
