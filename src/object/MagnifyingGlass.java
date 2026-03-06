package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class MagnifyingGlass extends SuperObject{
	public MagnifyingGlass() {
		name = "Magnifying Glass";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/magnifying_glass.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		solidArea.x = 5;
		solidArea.y = 5;
		
	}
	
	
}
