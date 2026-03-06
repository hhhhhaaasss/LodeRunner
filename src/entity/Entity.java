package entity;
//Super Class for all entities of the game
//Abstract Class (Blueprint)

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.GamePanel;

public class Entity {

	GamePanel gp;
	public int x,y;
	public int speed;
	
	//BufferedImage describes an Image with an accessible buffer or image data
	//We use this to store our image files
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	public String direction;
	
	//Walking animation
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	//Collision
	public Rectangle solidArea = new Rectangle(0,0,48,48); // takes X, Y , Width, Height
	public int solidAreaDefaultX, solidAreaDefaultY;
	public int collisionOn = 0;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
}
