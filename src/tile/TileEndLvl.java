package tile;

import Main.GamePanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TileEndLvl implements Runnable{

	GamePanel gp;
	
	BufferedImage closedImage;
	BufferedImage stairsImage;
	
	Thread stairs;
	
	boolean activated = false;
	
	public TileEndLvl(GamePanel gp) {
		this.gp = gp;
		getTileImage();
		setClosedState();
	}
	
	public void getTileImage() {
		 try {
	          	closedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/bg_2.png"));
	          	stairsImage = ImageIO.read(getClass().getResourceAsStream("/tiles/stairs_2.png"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	public void startThread(){
		stairs = new Thread(this);
		stairs.start();
	}
	
	public void checkEndLvl() {
		if(gp.aSetter.nbObj[gp.currentMap] == 0) {
			activated = true;
			startThread();
		}
	}
	
	public void setClosedState() {
		gp.tileM.tile[5].image = closedImage;
		gp.tileM.tile[5].collision = 0;
	}
	
	public void setStairsState() {
		gp.tileM.tile[5].image = stairsImage;
		gp.tileM.tile[5].collision = 2;
	}
	
	
	
	@Override
	public void run() {
		try {
			Thread.sleep(800);
			setStairsState();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
