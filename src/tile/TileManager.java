package tile;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][]; //Using text file as maps
	
	

	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		tile = new Tile[10];
		mapTileNum = new int[gp.maxMap][gp.maxScreenCol][gp.maxScreenRow];
		
		getTileImage();
		loadMap("/maps/test2.txt",0);
		//loadMap("/maps/test.txt",1);
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bg_1.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_1.png"));
			tile[1].collision = 1;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stairs_1.png"));
			tile[2].collision = 2;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rope_1.png"));
			tile[3].collision = 3;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_1.png"));
			tile[4].collision = 1;
			tile[4].isDestructible = true;
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//Scan our map file and create the map from a txt file
	
	//TODO THIS METHOD LAGS THE GAME
	public void loadMap(String filePath, int map) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			//Read the content of the text file
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			String line = br.readLine(); // read a single line 
			
				while(col < gp.maxScreenCol) {
					//Split a line and get tile numbers one by one using space bar
					String numbers[] = line.split(" "); // Splits this string around matches of the given regular expression
				
					int num = Integer.parseInt(numbers[col]); // Use col as an index for numbers array
				
					mapTileNum[map][col][row] = num;
					col++;
				}
				if(col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			System.err.println("Map not loaded");
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int worldX = 0;
		int worldY = 0;
		

		while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int tileNum = mapTileNum[gp.currentMap][col][row];
			
			g2.drawImage(tile[tileNum].image, worldX, worldY, gp.tileSize, gp.tileSize, null);
			col++;
			worldX += gp.tileSize;
			
			if(col == gp.maxScreenCol) {
				col = 0;
				worldX = 0;
				row++;
				worldY += gp.tileSize;
			}
		}
	}
}
