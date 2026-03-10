package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.MagnifyingGlass;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font arial_40; //CHANGE FONT HERE
	BufferedImage keyImage; //TODO CHANGE NAME LATER
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public String currentDialogue = ""; 
	public int commandNum = 0;
	public int titleScreenState = 0; // 0: the first screen:
	int subState = 0;
	
	public UI (GamePanel gp) {
		this.gp = gp;

		//IMPORTING FONT
	//	InputStream is = getClass().getResourceAsStream("/font/FontName.ttf"); 
	/*	try{
		FontName = Font.createFont(Font.TRUETYPE FONT, is);
		} catch(FontFormatException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
		*/
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		
		//Setting Anti ALIASING for future font
	//	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALISING, RenderingHints.VALUE TEXT_ANTIALIAS ON);
		
		g2.setColor(Color.white);
		
		
		//Title State
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		//Play State
		if(gp.gameState == gp.playState) {
			drawUI();
		}
		
		//Pause State
		if(gp.gameState == gp.pauseState) {
			drawOptionsScreen();
		}
		
		//Game Over State
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		
		if(gp.gameState == gp.settingsState) {
			drawSettingsScreen();
		}
	}
	
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			//Background Color
			g2.setColor(new Color(0,0,0));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			
			//TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,95F));
			String text = "LOAD RUNNER";
			int x = getXforCenteredText(text);
			int y = gp.tileSize *3;
			
			//SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x+5, y+5);
			
			
			//Main color
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			//MENU
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
			
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize*4;
			g2.drawString(text,x,y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "COOP";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text,x,y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "VERSUS";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text,x,y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "SETTINGS";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text,x,y);
			if(commandNum == 3) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text,x,y);
			if(commandNum == 4) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		}
		else if(titleScreenState == 1) {
			//Background Color
			g2.setColor(new Color(0,0,0));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			//CHANGE LATER
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Here are the controls";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "UP,Down,Left,Right/WASD or ZQSD for basic movement";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "?? for breaking blocs";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			
		}
	}
	
		
	public void drawGameOverScreen() {
		
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
		
		text = "Game Over";
		//Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		//Main
		g2.setColor(Color.white);
		g2.drawString(text,x-4,y-4);
		
		//Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		//Back to the title screen
		text = "Quit";
		x = getXforCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
		
	}
	
	
	public void drawOptionsScreen() {
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		//Sub Window
		int frameX = gp.tileSize*9;
		int frameY = gp.tileSize * 2;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize*10;
		
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		
		switch(subState) {
		case 0: options_top (frameX, frameY); break;
		case 1: options_fullScreenNotification(frameX, frameY); break;
		case 2: options_endGameConfirmation(frameX, frameY); break;
		}
		
		gp.keyH.enterPressed = false;
	}
	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		//TITLE
		String text = "Pause";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		//Full Screen On/Off
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		
		//Music
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		
		//SE
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		
	
		//END GAME(Change this in the pause menu)
		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		//Back
		textY += gp.tileSize * 2;
		g2.drawString("Back", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}
		
		//Full Screen CheckBox
		textX = frameX + gp.tileSize*5;
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		
		//Music Volume
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		//Sound Effect Volume
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();
	}
	
	public void options_fullScreenNotification(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;
		
		currentDialogue = "The change will take \neffect after restarting \nthe game";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		//Back
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 0;
			}
		}
	}
	
	public void options_endGameConfirmation(int frameX, int frameY){
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 2;
		
		currentDialogue = "Quit the game \nand return to the \ntitle screen?";
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		//Yes
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.tileSize* 2;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.stopMusic();
				gp.playMusic(2);
			}
		}
		//No
		 text = "No";
		 textX = getXforCenteredText(text);
			textY += gp.tileSize;
			g2.drawString(text, textX, textY);
			if(commandNum == 1) {
				g2.drawString(">", textX-25, textY);
				if(gp.keyH.enterPressed == true) {
					subState = 0;
					commandNum = 4;
				}
			}
	}
	
	public void drawSettingsScreen() {
		//Background Color
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		
		
	}

	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,230);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5)); // change width
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
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
		//g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize,null);
		//set text on screen
		g2.drawString("SCORE: " + gp.player.score,50,50); //Y indicate bottom of screen
		
	}
}
