package entity;
//Super Class for all entities of the game
//Abstract Class (Blueprint)

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

	GamePanel gp;
	public int x,y;
	public int speed;
	
	//BufferedImage describes an Image with an accessible buffer or image data
	//We use this to store our image files
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2, ropeLeft1, ropeLeft2, ropeRight1, ropeRight2, reveal;
	public String direction;
	
	//Walking animation
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	//Collision
	public Rectangle solidArea = new Rectangle(0,0,48,48); // takes X, Y , Width, Height
	public int solidAreaDefaultX, solidAreaDefaultY;
	public int collisionOn = 0;
	
	//Action for the NPC
	public int actionLockCounter = 0;
	
	
	public boolean onPath = false;
	boolean contactPlayer = false;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {}
	//TEMPORARY
	public void speak() {}
	
	
	public void checkCollision() {
		collisionOn = 0;
		gp.cChecker.checkTile(this);
		gp.gravity.CheckGravity(this);
		contactPlayer = gp.cChecker.checkPlayer(this);	
		
		if(contactPlayer == true) {
			gp.player.life--;
		}
	}
		public void update() {
		
		setAction();
		checkCollision();
		
			switch(direction) {
			case "left":x -= speed;	break;
			case "right":x += speed;break;
			case "up": y -= speed; break;
			case "down": y += speed;break;
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
		}
		
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
	}
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = (x + solidArea.x/gp.tileSize);
		int startRow = (y + solidArea.y/gp.tileSize); 
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		if(gp.pFinder.search() == true) {
			
			
			//Next WorldX & WorldY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
			
			//Entity solid area pos
			int enLeftX = x + solidArea.x;
			int enRightX = x + solidArea.x + solidArea.width;
			int enTopY = y + solidArea.y;
			int enBottomY = y + solidArea.y + solidArea.height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "up";
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			}
			else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				//left or right
				if(enLeftX > nextX) {
					direction = "left";
				}
				if(enLeftX < nextX) {
					direction = "right";
				}
			}
			else if(enTopY > nextY && enLeftX > nextX) {
				//up or left
				direction = "up";
				checkCollision();
				if(collisionOn == 1) {
					direction = "left";
				}
			}
			else if(enTopY > nextY && enLeftX < nextX) {
				//up or right
				direction = "up";
				checkCollision();
				if(collisionOn == 1) {
					direction = "left";
				}
			}
			else if(enTopY < nextY && enLeftX > nextX) {
				//down or left
				direction = "down";
				checkCollision();
				if(collisionOn == 1) {
					direction = "left";
				}
			}
			else if(enTopY < nextY && enLeftX < nextX) {
				//down or right
				direction = "down";
				checkCollision();
				if(collisionOn == 1) {
					direction = "right";
				}
			}
		}
	}
}
