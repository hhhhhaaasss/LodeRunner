package entity;

import Main.GamePanel;
import Main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity{

	KeyHandler keyH;
	public int score = 0;
	public int life = 3;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		this.keyH = keyH;
		
		
		
		setDefaultValues();
		getPlayerImage();
		
		//We will be setting the area in witch the collision is detected
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
	}
	
	public void setDefaultValues() {
		
		x = 500;
		y = 470;
		speed = 2;
		direction = "down";
	}
	public void setDefaultPositions() {
		x = 500;
		y = 470;
		direction = "down";
	}
	public void restoreLife() {
		life = 3;
	}
	
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"))	;				
					
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		

		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true ||keyH.rightPressed == true) {
			if(keyH.upPressed == true) direction = "up";
			else if(keyH.downPressed == true) direction = "down";
			else if(keyH.leftPressed == true) direction = "left";
			else if(keyH.rightPressed == true) direction = "right";

			//CHECK TILE COLLISION
			collisionOn = 0;
			gp.cChecker.checkTile(this);
			
			//Check Object Collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//Check NPC Collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			

			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == 0 || collisionOn == 4) {
				switch(direction) {
				case "left":x -= speed;	break;
				case "right":x += speed;break;
				}
			}
			else if(collisionOn == 2) {
				switch(direction) {
				case "up": y -= speed; break;
				case "down": y += speed;break;
				}
			}
			else if(collisionOn == 3) {
				if(gp.keyH.ropePressed == true) {
					switch(direction){
					case "left":x -= speed;	break;
					case "right":x += speed;break;
					case "down": y += speed; break;
					}
					if(gp.keyH.ropePressed == false) {
						collisionOn = 0;
					}
				}
			}
			
			//Update Walking animation
			spriteCounter++;
			if(spriteCounter > 12) { //Speed for the change
				if(spriteNum == 1) {
					spriteNum = 2;
				}else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		gp.gravity.CheckGravity(this);
		
		if(life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.stopMusic();
			//gp.playMusic(index);
		}
		
		if(gp.keyH.spacePressed == true) {	
			gp.tileI.breaking(this);
		}
	}
	
	public int pickUpObject(int i) {
		if(i != 999) {
			gp.obj[gp.currentMap][i] = null;
			gp.playSE(1);
			score++;
			gp.ui.showMessage("among us");
		}
		return score;
	}
	
	//TEMPORARY (FOR THE DIALOGUE)
	//MAYBE CHANGE THIS BECAUSE OF THE ARRAY CHANGE
	public void interactNPC(int i) {
		if(i != 999) {
			if(contactPlayer == true) life--;
		}
	}
	
	public void draw(Graphics2D g2) {

	
		BufferedImage image = null;
		
		switch(direction) {
		
		case "up":
			
			if(spriteNum == 1) image = up1;
			if(spriteNum == 2) image = up2;
			
			break;
		case "down":
			
			if(spriteNum == 1) image = down1;
			if(spriteNum == 2) image = down2;
			
			break;
		case "left":
			
			if(spriteNum == 1) image = left1;
			if(spriteNum == 2) image = left2;
			
			break;
		case "right":
			
			if(spriteNum == 1) image = right1;
			if(spriteNum == 2) image = right2;
			
			break;

		case "ropeUp":
			if(spriteNum == 1) image = up1;
			if(spriteNum == 2) image = up2;
			
			break;
		}
		//DrawImage() Draw an image on the screen
		// image, pos x, pos y, width, height, Image Observer
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
	}
}