package tile;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileEndLvl implements Runnable{

	GamePanel gp;
	
	BufferedImage closedImage;
	BufferedImage stairsImage;
	
	Thread stairs;
	
	boolean close = true;
	
	boolean activated = false;
	
	public TileEndLvl(GamePanel gp) {
		this.gp = gp;
		getTileImage();
		setClosedState();
	}
	
	public void getTileImage() {
		 try {
	          	closedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/bg_1.png"));
	          	stairsImage = ImageIO.read(getClass().getResourceAsStream("/tiles/stairs_1.png"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	public BufferedImage load(String path) {
	    try {
	        return ImageIO.read(getClass().getResourceAsStream(path));
	    } catch(Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public void changeTileE(int map) {

	    switch(map) {

	        case 0:
	            closedImage = load("/tiles/bg_1.png");
	            stairsImage = load("/tiles/stairs_1.png");
	            break;

	        case 1:
	            closedImage = load("/tiles/bg_2.png");
	            stairsImage = load("/tiles/stairs_2.png");
	            break;

	        case 2:
	            closedImage = load("/tiles/bg_3.png");
	            stairsImage = load("/tiles/stairs_3.png");
	            break;
	    }

	    if(close) setClosedState();
	    else setStairsState();
	}
	
	public void startThread(){
		stairs = new Thread(this);
		stairs.start();
	}
	
	public void checkEndLvl() {
	    if(!activated && gp.aSetter.nbObj[gp.currentMap] == 0) {
	        activated = true;
	        close = false;
	        startThread();
	    }
	}
	
	public void reset() {
	    activated = false;
	    close = true;
	    setClosedState();
	}
	
	public void setClosedState() {
		gp.tileM.tile[5].image = closedImage;
		gp.tileM.tile[5].collision = 0;
		close = true;
	}
	
	public void setStairsState() {
		gp.tileM.tile[5].image = stairsImage;
		gp.tileM.tile[5].collision = 2;
		close = false;
	}
	
	
	
	@Override
	public void run() {
	    try {
	        Thread.sleep(800);
	        if(close == false) {
	            setStairsState();
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
