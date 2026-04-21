package Main;

import entity.Entity;
//Importing the java awt for the UI
import entity.Player;
import entity.revealPlayer;
import network.ClientSide;
import network.InputPacket;
import network.Server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import OldSetup.ClientOld;
import OldSetup.ServerOld;
import ai.PathFinder;
import object.SuperObject;
import tile.Maps;
import tile.TileEndLvl;
import tile.TileInteractive;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable{
	
	private HashMap<Integer, int[]> playersInformations = new HashMap<>();
	private BufferedImage idleAnim;
	
	//Screen Settings
	final int originalTitleSize = 16; // 16x16 tile for characters and objects
	//Scalling the character to fit modern screen
	final int scale = 3;
	
	public final int tileSize = originalTitleSize * scale; // 48x48 title, its public so that Player.java can use it
	public final int maxScreenCol =28;
	public final int maxScreenRow = 17;
	public final int screenWidth = tileSize * maxScreenCol; // 1248 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 720 pixels
	
	//World Settings
	public final int maxMap = 7;
	public int currentMap = 0;
	public Maps mapLocation = new Maps(this);
	
	
	
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
	public TileManager tileM = new TileManager(this, mapLocation);
	public TileInteractive tileI = new TileInteractive(this);
	public TileEndLvl tileE = new TileEndLvl(this);
	Sounds music = new Sounds();
	Sounds se = new Sounds();
	public revealPlayer rp = new revealPlayer(this, keyH);
	public UI ui = new UI(this);
	Config config = new Config(this);
	
	//COLLISION AND GRAVITY
	public CollisionChecker cChecker = new CollisionChecker(this); 
	public AssetSetter aSetter = new AssetSetter(this);
	public Gravity gravity = new Gravity(this);
	public PathFinder pFinder = new PathFinder(this);
	
	//ENTITY AND OBJ
	public Player player;
	public Player remotePlayer;
	public SuperObject obj[][] = new SuperObject[maxMap][10]; //prepare 10 slots for objects
	public Entity npc[][] = new Entity[maxMap][10];
	
	//COOP
	
	
	//GAME STATE
	public int gameState;
	public final int introState = -1;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int gameOverState = 3;
	public final int settingsState = 4;
	public final int transitionLvlState = 5;
	
	//REVEAL STATE
	public final int revealState = 6;
	
	//COOP STATE
	public final int coopState = 7;
	
	//VERSUS STATE
	public final int versusState = 8;
	
	//Lan State
	public final int lanState = 9;
	public final int connectingState = 10;
	
	//End State
	public final int endGameState = 11;
	
	
	//NETWORK
	public ClientSide client;
	public Server server;
	public boolean isHost = false;
	public boolean isClient = false;
	public boolean isMultiplayer = false;
	
	public final int SERVER = 7777;
	
	public boolean serverReady = false;
	public boolean clientReady = false;
	public boolean gameStarted = false;
	
	//Score
	public Score scoreManager = new Score(this);
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //if set to true, all the drawing from this component will be done in an offscreen painting buffer
		this.addKeyListener(keyH);
		this.setFocusable(true); // With this, this GamePanel can be "focused" to receive key input
		this.playersInformations.put(0, new int[3]);
		this.playersInformations.put(1, new int[3]);
		
		try {
			this.idleAnim = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	public void setupGame() {
		player = new Player(this);	
		player.setDefaultPositions();
		
		aSetter.setObject();
		aSetter.setNPC();
		pFinder.instantiateNodes();
		playMusic(2);
		gameState = titleState;
		
		 //debugNavGrid();
		
		//FullScreen settings
		tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();//Everything this g2 draw will be recorded here
		
		if(fullScreenOn == true) {
			setFullScreen();
		}
		
	}
	
	public void startGame() {
	    if (gameStarted) return;

	    gameStarted = true;

	    if (player != null) player.setDefaultPositions();

	    if (remotePlayer == null) {
	        remotePlayer = new Player(this);
	        remotePlayer.setDefaultPositions();
	    }

	    gameState = playState;

	    System.out.println("GAME STATE CHANGED TO PLAY");
	}
	
	
	
	public void setupMultiplayer() {
	    if (player == null) player = new Player(this);

	    if (remotePlayer == null) {
	        remotePlayer = new Player(this);
	    }

	    clientReady = false;
	    serverReady = false;
	    gameStarted = false;
	}
	
	
	
	public void retry() {
		player.setDefaultPositions();
		aSetter.setObject();
		aSetter.setNPC();
		tileE.reset();
	}
	
	public void reload() {
		gameState = endGameState;
		currentMap = 0;
		player.setDefaultPositions();
		player.restoreLife();
		player.score = 0;
		aSetter.setObject();
		aSetter.setNPC();
		tileM.resetTiles(currentMap);
		tileI.changeTileI(currentMap);
		tileE.reset();
		tileE.changeTileE(currentMap);
	}
	
	
	
	/*public Entity getMainTarget() {

	    if(gameState == coopState) {

	        int distP1 = Math.abs(coop.p1.x - player.x) + Math.abs(coop.p1.y - player.y);
	        int distP2 = Math.abs(coop.p2.x - player.x) + Math.abs(coop.p2.y - player.y);

	        return (distP1 < distP2) ? coop.p1 : coop.p2;
	    }

	    return player;
	}*/
	
	public boolean isRevealMap(int mapIndex) {
	    return mapIndex == 10 || mapIndex == 11; // TODO CHANGE THIS :example maps
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
			//repaint();
			
		//This try catch is here to make sure our character does not disappear on screen and has time to run
			try {
				double remainingTime = nextDrawTime - System.nanoTime(); //We need to sleep the thread using the remaining time
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
	
	public void update() {


	    if (gameState == playState) {
	        if (isMultiplayer && client != null) {
	        	if(this.isHost) {
	        		try {
	        			client.writeStream.write(InputPacket.encodeMovement(1, 0, this.player.x, this.player.y));
	        			player.input = keyH.buildInput();
	        			player.update();
	        		}catch(IOException e) {
	        			
	        		}
	        	}else {
	        		try {	        			
	        			client.writeStream.write(InputPacket.encodeMovement(0, 0, this.player.x, this.player.y));
	        			player.input = keyH.buildInput();
	        			player.update();
	        		}catch(IOException e) {
	        			
	        		}
	        	}
	        	/*
	            client.localInput = keyH.buildInput();
	            client.sendInput();

	            player.input = client.localInput;

	            // Spawn remote player ONCE
	            if (remotePlayer == null) {
	                remotePlayer = new Player(this);
	                remotePlayer.x = player.x;
	                remotePlayer.y = player.y;
	            }

	           
	            if (client.playerId == 1) {

	                player.x = client.remoteP1.x;
	                player.y = client.remoteP1.y;

	                remotePlayer.x = client.remoteP2.x;
	                remotePlayer.y = client.remoteP2.y;

	            } else {

	                player.x = client.remoteP2.x;
	                player.y = client.remoteP2.y;

	                remotePlayer.x = client.remoteP1.x;
	                remotePlayer.y = client.remoteP1.y;
	            }
				*/
	        } else {
	            player.input = keyH.buildInput();
	            player.update();
	        }
	        for (int i = 0; i < npc[currentMap].length; i++) {
	            if (npc[currentMap][i] != null) {
	                npc[currentMap][i].update();
	            }
	        }
	    }
	    else if (gameState == connectingState) {
	    	/*
	    	if(this.isHost) {
	    		try {
	    			this.server = new Server(new ServerSocket(7777));
	    			this.server.start();
	    			
	    			this.client = new ClientSide(new Socket("localhost", 7777));
	    			this.client.start();
	    			
	    		}catch(IOException e) {
	    			System.out.println("ERROR: GamePanel connecting state got IOException Host");
	    		}	    		
	    	}else {
	    		try {
	    			this.client = new ClientSide(new Socket("localhost", 7777));
	    		}catch(IOException e) {
	    			System.out.println("ERROR: GamePanel connecting state got IOException Client");
	    		}	
	    	}

	        boolean serverReady = isHost && server != null;
	        boolean networkReady = (client != null && client.receivedFirstState);

	        
	        if (networkReady) {
	            clientReady = true;
	        }

	        System.out.println("STATE = " + gameState);
	        System.out.println("received=" + client.receivedFirstState);
	        // START GAME ONLY WHEN READY
	        if (serverReady && clientReady) {
	            System.out.println("STARTING GAME");
	            startGame();
	        }
	        */
	    }
	}
	
	public void handlePlayerInformations(int[] informations) {
		// Information 0 = isHost / 1 = animId / 2 = x / 3 = y
		if(this.isHost && informations[0] == 0) {
			//System.out.println("Got client modified");
			this.playersInformations.get(0)[0] = (int) informations[1];
	        // Adding the x position from the packet
	        this.playersInformations.get(0)[1] = (int) informations[2];
	        // Adding the y position from the packet
	        this.playersInformations.get(0)[2] = (int) informations[3];
		}
		else if((!this.isHost) & informations[0] == 1) {
			//System.out.println("Got host modified");
			this.playersInformations.get(1)[0] = (int) informations[1];
	        // Adding the x position from the packet
	        this.playersInformations.get(1)[1] = (int) informations[2];
	        // Adding the y position from the packet
	        this.playersInformations.get(1)[2] = (int) informations[3];
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
				
		else if(gameState == revealState){
			
			//Reveal Evidence
			tileM.draw(g2);
			rp.draw(g2);
			
		}
		// OTHERS
		else {
				
		//Tile
		tileM.draw(g2);
				
		// Break
		tileI.draw(g2);
		
		//End LVL
		tileE.checkEndLvl();
		
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
			
	
			//PLAYER
			if(isHost) {
				// System.out.println("about to draw client at pos " + this.playersInformations.get(0)[1] + this.playersInformations.get(0)[2]);
				g2.drawImage(this.idleAnim, this.playersInformations.get(0)[1], this.playersInformations.get(0)[2], tileSize, tileSize,null);
			}else {
				// System.out.println("about to draw host at pos " + this.playersInformations.get(1)[1] + this.playersInformations.get(1)[2]);
				g2.drawImage(this.idleAnim, this.playersInformations.get(1)[1], this.playersInformations.get(1)[2], tileSize, tileSize,null);
			}
			player.draw(g2);
			
			if(isMultiplayer && remotePlayer !=null) {
				remotePlayer.draw(g2);
			}

								
		//DEBUG
		if(keyH.checkDebugText == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
						
			g2.setFont(getFont().deriveFont(40F));
			g2.setColor(Color.black);
			int x = 10;
			int y = 300;
			int lineHeight = 40;
					
			g2.drawString("WorldX" + player.x, x, y); y+= lineHeight;
			g2.drawString("WorldY" + player.y, x, y); y+= lineHeight;
			g2.drawString("Col" + (player.x + player.solidArea.x)/tileSize, x,y); y+= lineHeight;
			g2.drawString("Row" + (player.y + player.solidArea.y)/tileSize, x,y); y+= lineHeight;
			g2.drawString("Draw Time: " +  passed, x,y) ;y+= lineHeight;
			g2.drawString("Player Collision: " +  player.collisionOn, x,y); y+= lineHeight;
			g2.drawString("Rope Up: " +  keyH.ropePressed, x,y); y+= lineHeight;			
				
			}
		}
		//UI
		ui.draw(g2);
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
	
	public void pauseMusic() {
		music.pause();
	}
	
	public void resumeMusic() {
		music.resume();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
		
	}
	
	//NETWORK
	public void startServer() {
	    try {
	    	this.server = new Server(new ServerSocket(7777));
			this.server.start();
			
			this.client = new ClientSide(new Socket("localhost", 7777), this);
			this.client.start();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
    public void startClient() {
        try {
        	this.client = new ClientSide(new Socket("localhost", 7777), this);
            this.client.start();

            isClient = true;
            isMultiplayer = true;


        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	public void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
	}
	
}
