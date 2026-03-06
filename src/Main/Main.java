package Main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Loot Runner");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); //Causes this Window to be sized to fit the preferred size and loyouts of its subcomponents = GamePanel
		
		
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamePanel.setupGame();
		gamePanel.startGameThread();
	}

}
