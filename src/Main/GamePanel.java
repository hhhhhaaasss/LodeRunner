package Main;

import entity.Entity;
//Importing the java awt for the UI
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import ai.PathFinder;
import object.SuperObject;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable{
	
	//Screen Settings
	final int originalTitleSize = 16; // 16x16 tile for characters and objects
	//Scalling the character to fit modern screen
	final int scale = 3;
	
	public final int tileSize = originalTitleSize * scale; // 48x48 title, its public so that Player.java can use it
	public final int maxScreenCol =26;
	public final int maxScreenRow = 15;
	public final int screenWidth = tileSize * maxScreenCol; // 1248 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 720 pixels
	
	//World Settings
	public final int maxMap = 10;
	public int currentMap = 0;
	
	//FullScreen
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;
	
	int FPS = 60;
	
	
	public KeyHandler keyH = new KeyHandler(this);
	
	Thread gameThread;
	
	//SYSTEM
	public TileManager tileM = new TileManager(this);
	Sounds music = new Sounds();
	Sounds se = new Sounds();
	public UI ui = new UI(this);
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	
	//COLLISION AND GRAVITY
	public CollisionChecker cChecker = new CollisionChecker(this); 
	public AssetSetter aSetter = new AssetSetter(this);
	public Gravity gravity = new Gravity(this);
	
	//ENTITY AND OBJ
	public Player player = new Player(this, keyH);
	public SuperObject obj[][] = new SuperObject[maxMap][10]; //prepare 10 slots for objects
	public Entity npc[][] = new Entity[maxMap][10];
	

	//GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int gameOverState = 3;
	public final int settingsState = 4;
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //if set to true, all the drawing from this component will be done in an offscreen painting buffer
		this.addKeyListener(keyH);
		this.setFocusable(true); // With this, this GamePanel can be "focused" to receive key input
	}

	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		playMusic(2);
		gameState = titleState;
		
		//FullScreen settings
		tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();//Everything this g2 draw will be recorded here
		
		if(fullScreenOn == true) {
			setFullScreen();
		}
		
	}
	
	public void retry() {
		player.setDefaultPositions();
		player.restoreLife();
		player.score = 0;
		aSetter.setObject();
		aSetter.setNPC();
		
	}
	
	
	public void setFullScreen() {
		
		//Get Local Screen Device
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		//Get FullScreen Width and Height
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // 0.166 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		
		//Game Loop
		while(gameThread != null) {
			//long currentTime = System.nanoTime();
			
			//Update Info such as character pos
			update();
			//Draw the screen with updated info
			drawToTempScreen();// draw everything to the buffered image
			drawToScreen();// draw the buffered image to the screen
			
		//This try catch is here to make sure our character does not disappear on screen and has time to run
			try {
				double remainingTime = nextDrawTime - System.nanoTime(); //We need to sleed the thread using the remaining time
				remainingTime = remainingTime/1000000000; // converting to MilliSeconds
				
				if(remainingTime < 0) { //If it takes more than drawInterval to run the Thread, its to avoid bugs
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(){
		
		if(gameState == playState) {
			//Player
			player.update();
			//NPC
			for(int i = 0; i< npc[1].length; i++) {
				if(npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
				
			}
		}
	}
	
	public void drawToTempScreen() {
		//Debug
		long drawStart = 0;
		if(keyH.checkDebugText == true) {
			drawStart = System.nanoTime();		
			}
				
		//TITLE SCREEN
		if(gameState == titleState) {
			ui.draw(g2);
		}
				
		// OTHERS
		else {
				
		//Tile
		tileM.draw(g2);
				
		//Object
		for(int i = 0; i < obj[1].length;i++) {
				if(obj[currentMap][i] !=null) {
					obj[currentMap][i].draw(g2, this);
				}
			}
					
			//NPC
			for(int i = 0; i < npc[1].length;i++) {
				if(npc[currentMap][i] != null) {
					npc[currentMap][i].draw(g2);
				}
			}
					
			//Player
			player.draw(g2);

			//UI
			ui.draw(g2);
					
			//DEBUG
			if(keyH.checkDebugText == true) {
				long drawEnd = System.nanoTime();
				long passed = drawEnd - drawStart;
						
				g2.setColor(Color.black);
				int x = 10;
				int y = 400;
				int lineHeight = 30;
						
				g2.drawString("WorldX" + player.x, x, y); y+= lineHeight;
				g2.drawString("WorldY" + player.y, x, y); y+= lineHeight;
				g2.drawString("Col" + (player.x + player.solidArea.x)/tileSize, x,y); y+= lineHeight;
				g2.drawString("Row" + (player.y + player.solidArea.y)/tileSize, x,y); y+= lineHeight;
				g2.drawString("Draw Time: " +  passed, x,y);
			}
		}
	}
	

	public void drawToScreen() {
		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
		}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
		
	}
}
