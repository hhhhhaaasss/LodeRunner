package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//Importing our own Main class Package
import Main.GamePanel;
import Main.KeyHandler;

public class Player extends Entity{

	KeyHandler keyH;
	public int score = 0;
	
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
			
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == 0) {
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
	}
	
	public int pickUpObject(int i) {
		if(i != 999) {
			gp.obj[i] = null;
			gp.playSE(1);
			score++;
			gp.ui.showMessage("among us");
		}
		return score;
	}
	
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
	
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
		}
		//DrawImage() Draw an image on the screen
		// image, pos x, pos y, width, height, Image Observer
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
	}
}