package Main;

//Importing the java awt for the UI
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
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
	
	int FPS = 60;
	
	
	KeyHandler keyH = new KeyHandler(this);
	
	Thread gameThread;
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	Sounds music = new Sounds();
	Sounds se = new Sounds();
	public UI ui = new UI(this);
	
	
	//COLLISION AND GRAVITY
	public CollisionChecker cChecker = new CollisionChecker(this); 
	public AssetSetter aSetter = new AssetSetter(this);
	public Gravity gravity = new Gravity(this);
	
	//ENTITY AND OBJ
	Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10]; //prepare 10 slots for objects

	//GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //if set to true, all the drawing from this component will be done in an offscreen painting buffer
		this.addKeyListener(keyH);
		this.setFocusable(true); // With this, this GamePanel can be "focused" to receive key input
	}

	
	public void setupGame() {
		aSetter.setObject();
		playMusic(0);
		gameState = playState;
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
			repaint();//Calls the paintComponent method
			
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
		
		if(gameState == playState) {player.update();}
		if(gameState == pauseState) {
			music.stop();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		
		//Tile
		tileM.draw(g2);
		
		//Object
		for(int i = 0; i < obj.length;i++) {
			if(obj[i] !=null) {
				obj[i].draw(g2, this);
			}
		}
		
		//Player
		player.draw(g2);

		//UI
		ui.draw(g2);
		
		g2.dispose();//Dispose of this graphics context and release any system resources that it is using
		
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.volume(-10.0f);
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.volume(-10.0f);
		se.play();
		
	}
}
