package entity;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class NPC_Bear  extends Entity{
	
	public NPC_Bear(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getNPCImage();
	}
	
	public void getNPCImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_up_2.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_down_2.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_left_2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_right_2.png"))	;				
					
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void setAction() {
		
		if(onPath == true) {
			
			int goalCol = (gp.player.x + gp.player.solidArea.x)/gp.tileSize;
			int goalRow = (gp.player.y + gp.player.solidArea.y)/gp.tileSize;
			
			searchPath(goalCol,goalRow);
		}
		//Remove this else later
		else {
			actionLockCounter ++;
			
			if(actionLockCounter == 120) {
				
			
				Random random = new Random();
				int i = random.nextInt(100)+1;
			
				if(i <= 25) {
				direction = "up";
				}
			
				else if(i > 25 && i <= 50) {
					direction = "down";
				}
			
				else if(i > 50 && i<= 75) {
					direction = "left";
				}
				else {
					direction = "right";
				}
				
				actionLockCounter = 0;
			}
		}
		
	}	
	
}
