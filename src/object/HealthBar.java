package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class HealthBar extends SuperObject{
		public HealthBar() {
			name = "HealthBar";
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/objects/heart.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
			solidArea.x = 5;
			solidArea.y = 5;
			
		}
}
