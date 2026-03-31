package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import object.MagnifyingGlass;
import object.HealthBar;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font noto_Sans, aldrich;
	BufferedImage MagGlassImage; //TODO CHANGE NAME LATER
	BufferedImage HealthBarImage;
	BufferedImage penImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public String currentDialogue = ""; 
	public int commandNum = 0;
	int titleScreenState = 0; // 0: the first screen:
	int settingsState = 0; 
	int subState = 0;
	boolean revealSE = false;
	
	Color black, red, white, gray;
	public float fadeAlpha = 0f;
	public boolean fadingIn = false;
	public int transitionCounter = 0;
	
	float circleAngle = 0;
	
	
	public UI (GamePanel gp) {
		this.gp = gp;

		//IMPORTING FONT

		try{
	  	InputStream is = getClass().getResourceAsStream("/font/Aldrich-Regular.ttf"); 
		aldrich = Font.createFont(Font.TRUETYPE_FONT, is);
		is = getClass().getResourceAsStream("/font/NotoSans-VariableFont_wdth,wght.ttf"); 
		noto_Sans = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch(FontFormatException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		//COLORS
		black = new Color(0x070707);
		red = new Color(0xEC4E20);
		white = new Color(0xF8F7FF); 
		gray = new Color(0x373e40);
		
		
		MagnifyingGlass mg = new MagnifyingGlass();
		MagGlassImage = mg.image;
		
		HealthBar hb = new HealthBar();
		HealthBarImage = hb.image;
		
		penImage = gp.rp.reveal;
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(aldrich);
		
		//Setting Anti ALIASING for future font
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(white);
		
		
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
		
		if(gp.gameState == gp.transitionLvlState) {
			drawLvl();
		}
		
		if(gp.gameState == gp.revealState) {
			drawReveal();
		}
	}
	
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			//Background Color
			g2.setColor(black);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			//Circle Animation
			circleAnim();
			
			//TITLE NAME
			g2.setFont(aldrich.deriveFont(95F));
			String text = "TRUTH";
			int x = getXforCenteredText(text) - 100;
			int y = gp.tileSize *3;
			

			
			//SHADOW
			g2.setColor(gray);
			g2.drawString(text, x+5, y+5);
			
			
			//Main color
			g2.setColor(white);
			g2.drawString(text, x, y);
			
			//TITLE DOWN
			text = "RUNNER";
			x = getXforCenteredText(text) +50;
			y = gp.tileSize *5;
			
			//SHADOW
			g2.setColor(gray);
			g2.drawString(text, x+5, y+5);
			
			
			//Main color
			g2.setColor(white);
			g2.drawString(text, x, y);
			
			//MENU
			
			g2.setFont(aldrich.deriveFont(40F));
			
			text = "SOLO";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
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
			g2.setColor(black);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			//CHANGE LATER
			g2.setColor(white);
			g2.setFont(g2.getFont().deriveFont(40F));
			
			String text = "Here are the controls";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "UP,Down,Left,Right/WASD or ZQSD for basic movement";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Space for breaking blocs";
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
		g2.setFont(g2.getFont().deriveFont(110f));
		
		text = "Game Over";
		//Shadow
		g2.setColor(black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		//Main
		g2.setColor(white);
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
		
		g2.setColor(white);
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
		drawUI();
	}
	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		//TITLE
		String text = "Pause";
		textX = getXforCenteredText(text)-50;
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
				gp.currentMap = 0;
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

		if(commandNum == 3) {
			commandNum = 0;
		}
		
		if(settingsState == 0) {
		
			//Background Color
			g2.setColor(black);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		
			g2.setFont(aldrich.deriveFont(95F));
			String text = "SETTINGS";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*5;
			g2.setColor(white);
			g2.drawString(text,x,y);
		
			g2.setFont(aldrich.deriveFont(40F));
			text = "Full Screen";
			x = getXforCenteredText(text)-30;
			y += gp.tileSize * 3;
			g2.drawString(text,x,y);
			if(commandNum == 0) {
				g2.drawString(">", x-35, y);
				if(gp.keyH.enterPressed == true) {
					if(gp.fullScreenOn == false) {
						gp.fullScreenOn = true;
					}
					else if(gp.fullScreenOn == true) {
						gp.fullScreenOn = false;
					}
					gp.keyH.enterPressed = false;
					settingsState = 1;
				}
			}
		
		
			text = "Music";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-35, y);
			}
			
			text = "SE";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-25, y);
			}
			
			//Full Screen CheckBox
			x += gp.tileSize * 5;
			y = gp.tileSize * 7 + 24;
			g2.setStroke(new BasicStroke(3));
			g2.drawRect(x, y, 24, 24);
			if(gp.fullScreenOn == true) {
				g2.fillRect(x, y, 24, 24);
			}
			
			//Music
			y += gp.tileSize;
			g2.drawRect(x,y,120,24);
			int volumeWidth = 24 * gp.music.volumeScale;
			g2.fillRect(x,y,volumeWidth,24);
			
			y += gp.tileSize;
			g2.drawRect(x,y,120,24);
			volumeWidth = 24 * gp.se.volumeScale;
			g2.fillRect(x,y,volumeWidth,24);
			
			gp.config.saveConfig();
		}
		else if(settingsState == 1) {
			//Background Color
			g2.setColor(black);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			g2.setColor(white);
			g2.setFont(g2.getFont().deriveFont(40F));
			int x = gp.tileSize * 10;
			int y = gp.tileSize * 8;
			
			String text = "The change will take \neffect after restarting \nthe game ";
			
			for(String line: text.split("\n")) {
				g2.drawString(line, x, y);
				y += 40;
			}
			
			//Back
			if(commandNum == 0) {
				if(gp.keyH.enterPressed == true) {
					settingsState = 0;
					commandNum = 0;
					gp.keyH.enterPressed = false;
				}	
			}
		}
		gp.keyH.enterPressed = false;
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
		int x = gp.tileSize-50;
		int y = gp.tileSize * 15;
		
		//Background color
		g2.setColor(black);
		g2.fillRect(x, y, gp.screenWidth+50, gp.screenHeight);
		
		x += gp.tileSize*4;
		y += gp.tileSize-25;
		
		//Score Rectangle
		g2.setColor(white);
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(x, y, 500, 50);
		
		g2.setFont(g2.getFont().deriveFont(40F));
		x += gp.tileSize - 40;
		y += gp.tileSize - 10;

		//set an image on screen
		
		//set text on screen
		g2.drawString("SCORE: " + gp.player.score,x,y);
		
		//Life Rectangle
		x += gp.tileSize * 11;
		y -=gp.tileSize -10;
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(x, y, 150, 50);
		
		x += gp.tileSize - 40;
		y += gp.tileSize - 43;
		g2.drawImage(HealthBarImage,x, y, gp.tileSize-10, gp.tileSize-10,null);
		
		x += gp.tileSize + 50;
		y += gp.tileSize - 13;
		g2.drawString(""+gp.player.life,x,y);
		
		//Item Quantity Rectangle
		x += gp.tileSize +35;
		y -=gp.tileSize -8;
		g2.drawRect(x, y, 150, 50);
		
		x += gp.tileSize - 40;
		y += gp.tileSize - 40;
		g2.drawImage(MagGlassImage,x, y, gp.tileSize-10, gp.tileSize-10,null);
		
		x += gp.tileSize + 50;
		y += gp.tileSize - 17;
		g2.drawString(""+gp.aSetter.nbObj[gp.currentMap],x,y);
		
	}
	
	public void circleAnim() {
		
		int centerX = gp.tileSize * -8;
	    int centerY = gp.tileSize *8;

	    int bigRadius = gp.screenWidth / 4;
	    int smallRadius = 60;

	    // Rotate animation
	    circleAngle += 0.7;
	    if(circleAngle >= 360) circleAngle = 0;

	    // Big circle
	    g2.setColor(red);
	    g2.fillOval(centerX, centerY, bigRadius * 2, bigRadius * 2);

	    // Small rotating circles
	    g2.setColor(black);

	    int numberOfCircles = 8;
	    float angleStep = 360f / numberOfCircles;

	    for(int i = 0; i < numberOfCircles; i++) {

	        float angle = circleAngle + (i * angleStep);

	        Point p = getPointOnCircle(angle, bigRadius - smallRadius);

	        g2.fillOval(p.x - smallRadius,p.y - smallRadius, smallRadius * 2,smallRadius * 2);
	    }
	}
	
	public Point getPointOnCircle(float degress, float radius) {
		int x = gp.tileSize-130;
		int y = gp.tileSize +700;
		
		double rads = Math.toRadians(degress-60);
		
		//Calculate the outter point of the line
		int xPos = Math.round((float) (x + Math.cos(rads) * radius));
		int yPos = Math.round((float) (y + Math.sin(rads) * radius));
		
		return new Point(xPos, yPos);
		
	}
	
	public void drawLvl() {

	    boolean isReveal = gp.currentMap == 1; // only map 1 is reveal

	    // Background
	    g2.setColor(black);
	    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

	    transitionCounter++;

	    // Fading in
	    if(fadingIn) {
	        fadeAlpha += 0.015f;
	        if(fadeAlpha >= 1f) {
	            fadeAlpha = 1f;
	            fadingIn = false;
	        }
	    }

	    // TEXT
	    g2.setFont(aldrich.deriveFont(95F));
	    String text = isReveal ? "REVEAL THE EVIDENCE" : "LEVEL " + (gp.currentMap + 1);
	    int x = getXforCenteredText(text);
	    int y = gp.tileSize * 9;

	    int alpha = (int)(fadeAlpha * 255);

	    // Decorative rects for reveal
	    if(isReveal) {
	        g2.setColor(new Color(red.getRed(), red.getGreen(), red.getBlue(), alpha));
	        int recX = x - 120;
	        int recY = y - 115;
	        g2.fillRect(recX, recY, gp.screenWidth + 50, 20);
	        recY += 150;
	        g2.fillRect(recX, recY, gp.screenWidth + 50, 20);
	    }

	    // Draw text
	    g2.setColor(new Color(255, 255, 255, alpha));
	    g2.drawString(text, x, y);

	    // Play reveal sound only for reveal levels
	    if(isReveal && revealSE) {
	    	gp.stopMusic();
	        gp.playSE(7);
	        gp.playMusic(8);
	        revealSE = false;
	    }

	    // After ~2 seconds → go to next state
	    int duration = isReveal ? 180 : 120; // reveal levels stay longer
	    if(transitionCounter > duration) {
	        if(isReveal) {
	        	
	            gp.gameState = gp.revealState;
	            gp.rp.setDefaultValues();
	           
	        } else {
	            gp.gameState = gp.playState;
	        }

	        // Reset for next time
	        transitionCounter = 0;
	        fadeAlpha = 0f;
	        fadingIn = false;
	        revealSE = true; // reset for next transition
	    }
	}
	
	public void drawReveal() {
		//SETTING UP IMAGE AND TEXT ON SCREEN
		int x = gp.tileSize-50;
		int y = gp.tileSize * 15;
				
		//Background color
		g2.setColor(black);
		g2.fillRect(x, y, gp.screenWidth+50, gp.screenHeight);
				
		x += gp.tileSize*4;
		y += gp.tileSize-25;
				
		//Score Rectangle
		g2.setColor(white);
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(x, y, 500, 50);
				
		g2.setFont(g2.getFont().deriveFont(40F));
		x += gp.tileSize - 40;
		y += gp.tileSize - 10;

		//set an image on screen
				
		//set text on screen
		g2.drawString("SCORE: " + gp.player.score,x,y);
				
		//Life Rectangle
		x += gp.tileSize * 11;
		y -=gp.tileSize -10;
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(x, y, 150, 50);
				
		x += gp.tileSize - 40;
		y += gp.tileSize - 43;
		g2.drawImage(penImage,x, y, gp.tileSize-10, gp.tileSize-10,null);
				
		x += gp.tileSize + 50;
		y += gp.tileSize - 13;
		g2.drawString(""+gp.player.life,x,y);
	}
}
