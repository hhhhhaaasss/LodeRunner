package Main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

	//Explain later
	public static JFrame window;
	
	public static void main(String[] args) {
		
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Load Runner");
		new Main().setIcon();
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		gamePanel.config.loadConfig();
		
		if(gamePanel.fullScreenOn == true) {
			window.setUndecorated(true);
		}
		
		window.pack(); //Causes this Window to be sized to fit the preferred size and loyouts of its subcomponents = GamePanel
		

		
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
	public void setIcon() {
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/player/boy_down_1.png"));
		window.setIconImage(icon.getImage());
	}

}
