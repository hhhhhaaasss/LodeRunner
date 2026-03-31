package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.KeyHandler;

public class revealPlayer extends Entity{

	KeyHandler keyH;
	String words[] = new String[3];
	
	public revealPlayer(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		this.keyH = keyH;
		setDefaultValues();
		getRevealPlayerImage();
		
		words[0] = "knive";
		words[1] = "";
		words[2] = "";
		
	}
	
	public void getRevealPlayerImage() {
		try {
			reveal = ImageIO.read(getClass().getResourceAsStream("/player/pen.png"));
			System.out.println("Reveal image loaded: " + (reveal != null));
					
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDefaultValues() {
		
		x = 500;
		y = 500;
		speed = 6;
		direction = "down";

	}
	
	public void update() {
		if(gp.gameState != gp.revealState) return;

		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true ||keyH.rightPressed == true) {
			if(keyH.upPressed == true) direction = "up";
			else if(keyH.downPressed == true) direction = "down";
			else if(keyH.leftPressed == true) direction = "left";
			else if(keyH.rightPressed == true) direction = "right";
			
			switch(direction){
			case "up":y -= speed; break;
			case "left":x -= speed;	break;
			case "right":x += speed;break;
			case "down": y += speed; break;
			}
		}
	}
	
	public String getWords(int i) {
		return words[i];
	}
	
	public void revealWord() {
		
	}
	
	public void generateWords() {
		
	}
	
	@Override
	public void draw(Graphics2D g2) {

		    g2.drawImage(reveal, x, y, gp.tileSize, gp.tileSize,null);
		
	}
	
	
}
