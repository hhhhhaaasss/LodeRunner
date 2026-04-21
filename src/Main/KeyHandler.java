package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Player;

public class KeyHandler implements KeyListener{

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, ropePressed, spacePressed;
	public boolean checkDebugText;
	public boolean enterPressed = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (gp.gameState == gp.transitionLvlState) {
		    return;
		}
		
		//Title State
		if(gp.gameState == gp.titleState) {
			if(gp.ui.titleScreenState == 0) {
				if(code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP){
					gp.ui.commandNum--;
					if(gp.ui.commandNum < 0) {
						gp.ui.commandNum = 4;
					}
					gp.playSE(3);
				}
				if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
					gp.ui.commandNum++;
					if(gp.ui.commandNum > 4) {
						gp.ui.commandNum = 0;
					}
					gp.playSE(3);
				}
				if(code == KeyEvent.VK_ENTER) {
					if(gp.ui.commandNum == 0) {
						gp.ui.titleScreenState = 1;
						gp.playSE(4);
						
					}
					if(gp.ui.commandNum == 1) {
						
						gp.gameState = gp.lanState;
						gp.keyH.enterPressed = true;
						gp.ui.lanMenuNum = 0;
					    gp.stopMusic();
					    gp.playMusic(0);

					}
					if(gp.ui.commandNum == 2) {
						//add later
					}
					if(gp.ui.commandNum == 3) {
						gp.gameState = gp.settingsState;
						gp.playSE(4);
					}
					
					if(gp.ui.commandNum == 4) {
						System.exit(0);
						gp.client.closeEverything();
						
					}
				}
			}
			else if(gp.ui.titleScreenState == 1) {
				if(code == KeyEvent.VK_ENTER) {
					gp.gameState = gp.transitionLvlState;
					enterPressed = false;
					gp.ui.titleScreenState = 0;
					gp.stopMusic();
					gp.playSE(4);
					gp.playMusic(0);

				}
			}
		}
		
		
		//Play State
		if(gp.gameState == gp.playState) {
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP){
			upPressed = true;
		}
		if((code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) && (gp.player.collisionOn == 3 || gp.player.collisionOn == 4)){
			ropePressed = true;
		}
		else if(((code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) ||code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN ) && ropePressed == true) {
			ropePressed = false;
		}
		
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
			downPressed = true;
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT){
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_E || code == KeyEvent.VK_RIGHT){
			rightPressed = true;
		}
		
		if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_P){
			if(gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
				gp.pauseMusic();
			}
			gp.playSE(5);
		}
		
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
		
		if(code == KeyEvent.VK_T) {
			if(checkDebugText == false) {
				checkDebugText = true;
			}
			else if(checkDebugText == true) {
				checkDebugText = false;
			}
		}
		if(code == KeyEvent.VK_R) {
		
			gp.tileM.loadMap(gp.mapLocation.getMap(gp.currentMap), gp.currentMap);
			gp.player.setDefaultPositions();
			
		}
	}
		
		//Option State
		else if(gp.gameState == gp.pauseState) {
			optionsState(code);
		}
		
		//Game Over State
		else if(gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		
		//Settings State
		else if(gp.gameState == gp.settingsState) {
			settingsState(code);
		}
		
		else if(gp.gameState == gp.transitionLvlState) {
			transitionState();
		}
		else if(gp.gameState == gp.revealState) {
			revealState(code);
		}
		else if(gp.gameState == gp.lanState) {
			lanState(code);
		}
		else if(gp.gameState == gp.endGameState) {
			finalGameState(code);
		}
		
}
	
	public void settingsState(int code) {
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.titleState;
			gp.playSE(4);
		}
		if(code == KeyEvent.VK_ENTER && gp.ui.commandNum == 0) {
			enterPressed = true;
			gp.playSE(4);
		}
		
		int maxCommandNum = 0;
		switch(gp.ui.subState) {
		case 0: maxCommandNum = 2; break;// Change this later
		case 1: maxCommandNum = 1; break;
		}
		
		if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W || code == KeyEvent.VK_Z) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
			gp.playSE(3);
		}
		if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(3);
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
				}
				if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
				}
			}
			gp.playSE(3);
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
				}
				if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
				}
			}
			gp.playSE(3);
		}
	}

	public void optionsState(int code) {
		
		
		if(code == KeyEvent.VK_ESCAPE ||code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
			gp.playSE(5);
			gp.resumeMusic();
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
			gp.playSE(5);
		}
		int maxCommandNum = 0;
		switch(gp.ui.subState) {
		case 0: maxCommandNum = 4; break;// Change this later
		case 2: maxCommandNum = 1; break;
		}
		
		if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W || code == KeyEvent.VK_Z) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
			gp.playSE(3);
		}
		if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(3);
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
				}
				if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
				}
			}
			gp.playSE(3);
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
				}
				if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
				}
			}
			gp.playSE(3);
		}
		
	}
	public void gameOverState(int code) {
		
		if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W || code == KeyEvent.VK_Z) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
		}
		if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
				}
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				if(gp.player.life <= 0) {
					gp.reload();
				}
				else {
					gp.retry();
				}	
				gp.stopMusic();
				switch(gp.currentMap) {
				case 0:
					gp.playMusic(0);
					break;
				case 1:
					gp.playMusic(10);
					break;
				case 2:
					gp.playMusic(9);
					break;
				}
			}
			else if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.stopMusic();
				gp.playMusic(2);
				gp.reload();
			}
		}		
	}
	
	public void transitionState() {
		gp.gameState = gp.transitionLvlState;

		gp.ui.fadeAlpha = 0f;
		gp.ui.fadingIn = true;
		gp.ui.transitionCounter = 0;
		gp.ui.revealSE = true;
	
	}
	
	public void revealState(int code) {
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP){
			upPressed = true;
		}

		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
			downPressed = true;
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT){
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_E || code == KeyEvent.VK_RIGHT){
			rightPressed = true;
		}
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP){
			upPressed = false;
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
			downPressed = false;
		}
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT){
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_E || code == KeyEvent.VK_RIGHT){
			rightPressed = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		
	}
	
	public void lanState(int code) {

	    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) gp.ui.lanMenuNum--;
	    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) gp.ui.lanMenuNum++;

	    if (gp.ui.lanMenuNum < 0) gp.ui.lanMenuNum = 2;
	    if (gp.ui.lanMenuNum > 2) gp.ui.lanMenuNum = 0;

	    if (code == KeyEvent.VK_ENTER && enterPressed == false) {

	    	enterPressed = true;
	    	
	        if (gp.ui.lanMenuNum == 0) {
	            gp.isHost = true;
	            gp.isMultiplayer = true;

	            gp.startServer();

	            gp.remotePlayer = new Player(gp);
	            //gp.gameState = gp.connectingState;     
	        }

	        if (gp.ui.lanMenuNum == 1) {
	            gp.isClient = true;
	            gp.isMultiplayer = true;

	            gp.startClient();

	            gp.remotePlayer = new Player(gp); 
	            //gp.gameState = gp.connectingState;      
	        }

	        if (gp.ui.lanMenuNum == 2) {
	            gp.gameState = gp.titleState;
	        }
	    }
	}
	
	public void finalGameState(int code) {
		if(gp.gameState == gp.endGameState) {

		    if(code == KeyEvent.VK_ENTER) {
		        gp.scoreManager.saveScore(gp.ui.playerName);
		    	gp.gameState = gp.titleState;
		    }

		    // letters
		    if(Character.isLetterOrDigit(code)) {
		        gp.ui.playerName += KeyEvent.getKeyText(code);
		    }

		    // delete
		    if(code == KeyEvent.VK_BACK_SPACE && gp.ui.playerName.length() > 0) {
		        gp.ui.playerName = gp.ui.playerName.substring(0, gp.ui.playerName.length()-1);
		    }
		}
	}
	
	public PlayerInput buildInput() {
		PlayerInput i = new PlayerInput();
		
        i.up = upPressed;
        i.down = downPressed;
        i.left = leftPressed;
        i.right = rightPressed;
        i.rope = ropePressed;
        i.space = spacePressed;
		
		return i;
	}

}
