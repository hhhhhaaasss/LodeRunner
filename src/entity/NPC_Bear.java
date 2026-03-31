package entity;

import Main.GamePanel;
import ai.Node;
import java.io.IOException;
import javax.imageio.ImageIO;

public class NPC_Bear  extends Entity{
	
	public NPC_Bear(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getNPCImage();
	}
	
	public void getNPCImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_up_2.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_down_2.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_left_2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/ennemy/oldman_right_2.png"))	;				
					
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
public void setAction() {
    if (!onPath) return;

    // 1️⃣ Calculer la position en tile
    int startCol = x / gp.tileSize;
    int startRow = y / gp.tileSize;
    int goalCol = (gp.player.x + gp.player.solidArea.x) / gp.tileSize;
    int goalRow = (gp.player.y + gp.player.solidArea.y) / gp.tileSize;

    // 2️⃣ Calculer le path si nécessaire
    gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
    if (gp.pFinder.search() && !gp.pFinder.pathList.isEmpty()) {

        // 3️⃣ Prendre le Node suivant
        Node nextNode = gp.pFinder.pathList.get(0);
        int nextX = nextNode.col * gp.tileSize;
        int nextY = nextNode.row * gp.tileSize;

        // 4️⃣ Déplacer l'ennemi vers le Node
        if (x < nextX) x += speed; 
        else if (x > nextX) x -= speed;

        if (y < nextY) y += speed;
        else if (y > nextY) y -= speed;

        // 5️⃣ Snap et passer au Node suivant quand atteint
        if (Math.abs(x - nextX) <= speed && Math.abs(y - nextY) <= speed) {
            x = nextX;
            y = nextY;
            gp.pFinder.pathList.remove(0);
        }

        // 6️⃣ Mettre à jour la direction pour l'animation (optionnel)
        if (y > nextY) direction = "up";
        else if (y < nextY) direction = "down";
        else if (x > nextX) direction = "left";
        else if (x < nextX) direction = "right";
    	}
	}
}
