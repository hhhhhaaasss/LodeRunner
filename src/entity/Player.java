package entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.PlayerInput;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/*	SCORING: 1500 passing LVL
 * 			 250 Picking up Obj
 * 			 50 breaking blocks
 * */



public class Player extends Entity{
	public KeyHandler keyH;
	public int score = 0;
	public int life = 5;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	
	//ONLINE
	public PlayerInput input = new PlayerInput();
	public boolean isRemote = false;
	
	public Player(GamePanel gp) {
		
		super(gp);
		
		
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
		
		x = 13 * gp.tileSize;
		y = 1 * gp.tileSize;
		speed = 2;
		direction = "down";
	}
	public void setDefaultPositions() {
		x = 13 * gp.tileSize;
		y = 1 * gp.tileSize;
		direction = "down";
	}
	public void restoreLife() {
		life = 5;
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
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
			ropeLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_rope_left_1.png"));
			ropeLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_rope_left_2.png"));
			
			ropeRight1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_rope_right_1.png"));
			ropeRight2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_rope_right_2.png"));
					
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
	
		//if (gp.gameState != gp.playState) return;
		
		boolean up = input.up;
		boolean down = input.down;
		boolean left = input.left;
		boolean right = input.right;
		//System.out.println("update got called");
		if(up == true || down == true || left == true ||right == true) {
			if(up == true) direction = "up";
			else if(down == true) direction = "down";
			else if(left == true) direction = "left";
			else if(right == true) direction = "right";
			
			//CHECK TILE COLLISION
			collisionOn = 0;
			gp.cChecker.checkTile(this);
			
			//Check Object Collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//Check NPC Collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			
			
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
				if(input.rope == true) {
					switch(direction){
					case "left":x -= speed;	break;
					case "right":x += speed;break;
					case "down": y += speed; break;
					}
					if(input.rope == false) {
						collisionOn = 0;
					}
				}
			}
			else if(collisionOn == 5) { 
			    gp.currentMap++;
			    score += 1500;

			    if(gp.currentMap != 3) {
				    gp.tileM.resetTiles(gp.currentMap);
				    gp.tileE.changeTileE(gp.currentMap);
				    gp.tileE.reset();
				    gp.tileI.changeTileI(gp.currentMap);
				    setDefaultPositions();
				    gp.stopMusic();
				    if(gp.currentMap == 1) {
				    	gp.playMusic(10);
				    }
				    else if(gp.currentMap == 2) {
				    	gp.playMusic(9);
				    }
				    
				    gp.gameState = gp.transitionLvlState;
				    gp.ui.resetTransition();
			    }
			    else {
			    	gp.gameState = gp.endGameState;
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
		
		
		if(input.space == true) {	
			gp.tileI.breaking(this);
		}
		
		if(invincible) {
		    invincibleCounter++;
		    if(invincibleCounter > 60) { // 1 second if 60 FPS
		        invincible = false;
		        invincibleCounter = 0;
		    }
		}
	}
	
	
	public int pickUpObject(int i) {
		if(i != 999) {
			gp.obj[gp.currentMap][i] = null;
			gp.playSE(1);

			score += 250;
			
			gp.aSetter.nbObj[gp.currentMap]--;
		}
		return score;
	}
	
	public void interactNPC(int i) {
	    if(i != 999 && !invincible) {

	        life--;
	        if(life < 0) life = 0;

	        invincible = true;

	        gp.gameState = gp.gameOverState;
	        gp.stopMusic();
	        gp.playMusic(6);
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
			
			if(input.rope == true) {
				if(spriteNum == 1) image = ropeLeft1;
				if(spriteNum == 2) image = ropeLeft2;
			}
			else {
				if(spriteNum == 1) image = left1;
				if(spriteNum == 2) image = left2;
			}
			
			break;
		case "right":
			
			if(input.rope == true) {
				if(spriteNum == 1) image = ropeRight1;
				if(spriteNum == 2) image = ropeRight2;
			}
			else {
				if(spriteNum == 1) image = right1;
				if(spriteNum == 2) image = right2;
			}

			
			break;

		}
		//DrawImage() Draw an image on the screen
		// image, pos x, pos y, width, height, Image Observer
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
	}
}