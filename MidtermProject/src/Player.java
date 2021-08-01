import javax.swing.*;
import java.util.ArrayList;

public class Player {

    private int score;
    private int id; // location of player
    private boolean turn = false;
    private final ArrayList<Integer> SpeedBumps = new ArrayList<>(); // An array for storage limit of the sppedbumps

    public static final ImageIcon Rplayer_icon = new ImageIcon(new ImageIcon("src/Pictures/red.png")
            .getImage().getScaledInstance(70, 70, 70));
    public static final ImageIcon Bplayer_icon = new ImageIcon(new ImageIcon("src/Pictures/blue.png")
            .getImage().getScaledInstance(80, 80, 80));


    public void increaseScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public ArrayList<Integer> getSpeedBumps() {
        return SpeedBumps;
    }
}