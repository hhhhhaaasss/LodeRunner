package entity;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class NPC_Bear  extends Entity{
	
	
	public NPC_Bear(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 2;
		
		getNPCImage();
	}
	
	public void setAction() {

	    if(onPath) {

	        Entity target = gp.player;

	        int goalCol = (target.x + target.solidArea.x) / gp.tileSize;
	        int goalRow = (target.y + target.solidArea.y) / gp.tileSize;

	        searchPath(goalCol, goalRow);
	    }
	}

	
	public void getNPCImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/bear_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/bear_up_2.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/bear_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/bear_down_2.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/bear_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/bear_left_2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/bear_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/bear_right_2.png"))	;				
					
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
