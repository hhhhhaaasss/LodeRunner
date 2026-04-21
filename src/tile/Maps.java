package tile;

import Main.GamePanel;

public class Maps {
	GamePanel gp;
	public String[] mapLocation;
	
	
	public Maps(GamePanel gp){
		this.gp = gp;
		mapLocation = new String[gp.maxMap];
		mapLocation[0] = "/maps/map01.txt";
		mapLocation[1] = "/maps/map02.txt";
		mapLocation[2] = "/maps/map03.txt";
		mapLocation[3] = "/maps/reveal.txt";
	}
	
	public String getMap(int mapNum) {
		return mapLocation[mapNum];
	}
}
