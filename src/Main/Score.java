package Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Score {

    GamePanel gp;

    public Score(GamePanel gp) {
        this.gp = gp;
    }

    public void saveScore(String name) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("scores.txt", true));

            // Name
            bw.write(name + ":" + gp.player.score);
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}