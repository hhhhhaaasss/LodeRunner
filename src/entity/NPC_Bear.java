package entity;

import Main.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;

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
	
	//TEMPORARY
	public void setDialogue() {
		
		dialogues[0] = "Among us ";
		dialogues[1] = "Lorem Ipsum ffijdsofjdio\nsfjfids";
		dialogues[2] = "Tutorial ";
		dialogues[3] = "SUS ";
		
		
	}
	
	@Override
    public void setAction() {
        if(!onPath) return;

        int startCol = x / gp.tileSize;
        int startRow = y / gp.tileSize;
        int goalCol = (gp.player.x + gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.y + gp.player.solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if(gp.pFinder.search()) {
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            if(y > nextY) direction = "up";
            else if(y < nextY) direction = "down";
            else if(x > nextX) direction = "left";
            else if(x < nextX) direction = "right";
        }
    }
	
	//TEMPORARY
	
	public void speak() {
		onPath = true;
	}
	
	
	
}
