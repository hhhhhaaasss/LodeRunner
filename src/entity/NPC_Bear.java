package entity;

import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class NPC_Bear  extends Entity{
	
	public NPC_Bear(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
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
}
