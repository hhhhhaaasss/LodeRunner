package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean checkDebugText;
	public boolean enterPressed = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		//Title State
		if(gp.gameState == gp.titleState) {
			
			if(gp.ui.titleScreenState == 0) {
				if(code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP){
					gp.ui.commandNum--;
					if(gp.ui.commandNum < 0) {
						gp.ui.commandNum = 4;
					}
				}
				if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
					gp.ui.commandNum++;
					if(gp.ui.commandNum > 4) {
						gp.ui.commandNum = 0;
					}
				}
				if(code == KeyEvent.VK_ENTER) {
					if(gp.ui.commandNum == 0) {
						gp.ui.titleScreenState = 1;
						
					}
					if(gp.ui.commandNum == 1) {
						//add later
					}
					if(gp.ui.commandNum == 2) {
						//add later
					}
					
					if(gp.ui.commandNum == 4) {
						System.exit(0);
					}
				}
			}
			else if(gp.ui.titleScreenState == 1) {
				if(code == KeyEvent.VK_ENTER) {
					gp.gameState = gp.playState;
					gp.ui.titleScreenState = 0;
					gp.playMusic(0);

			}
		}
	}
		
		//Play State
		if(gp.gameState == gp.playState) {
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
		if(code == KeyEvent.VK_ESCAPE){
			if(gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			}
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
			switch(gp.currentMap) {
			case 0: gp.tileM.loadMap("/maps/test.txt",0); break;
			//case 1: gp.tileM.loadMap("/maps/test.txt",0); break;
			}
			
		}
		

		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.optionsState;
		}
		
	}
		//Pause State
		else if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_ESCAPE){
				gp.gameState = gp.playState;
			}
		}
		
		//Dialogue State
		else if(gp.gameState == gp.dialogueState) {
			if(code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.playState;
			}
		}
		
		//Option State
		else if(gp.gameState == gp.optionsState) {
			optionsState(code);
		}
		
		//Game Over State
		else if(gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		
}

	public void optionsState(int code) {
		
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
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
		}
		if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
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
				gp.retry();
				gp.playMusic(0);
			}
			else if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.retry();
			}
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
		
	}

}
