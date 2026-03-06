package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.MagnifyingGlass;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font arial_40;
	BufferedImage keyImage; //TODO CHANGE NAME LATER
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public UI (GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		MagnifyingGlass key = new MagnifyingGlass(); //TODO CHANGE NAME
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.playState) {
			drawUI();
		}
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
	}
	
	public void drawPauseScreen() {
		
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
	public void drawUI() {
		//SETTING UP IMAGE AND TEXT ON SCREEN
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		//set an image on screen
		g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize,null);
		//set text on screen
		g2.drawString("SCORE: " + gp.player.score,50,50); //Y indicate bottom of screen
		
		if(messageOn) {
			
			g2.setFont(g2.getFont().deriveFont(30F)); //Change font
			g2.drawString(message,gp.tileSize/2,gp.tileSize*5);
			
			messageCounter++;
			
			if(messageCounter > 120) { //removing the text from screen
				messageCounter = 0;
				messageOn = false;
			}
		}
	}
}
