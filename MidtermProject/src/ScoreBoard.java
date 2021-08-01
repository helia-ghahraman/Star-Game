import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ScoreBoard extends JPanel {

    private final JLabel blueScore = new JLabel("Blue score : ");
    private final JLabel redScore = new JLabel("Red score : ");
    private final JLabel Bscore = new JLabel("0");
    private final JLabel Rscore = new JLabel("0");
    private final JLabel turn1 = new JLabel("Turn : ");
    private final JLabel turn2 = new JLabel("blue");
    private final JLabel blueLimit = new JLabel("Blue limit : ");
    private final JLabel redLimit = new JLabel("Red limit : ");
    private static final JLabel Blimit = new JLabel("0");
    private static final JLabel Rlimit = new JLabel("0");

    public ScoreBoard() {
        setScoreBoard();
    }

    public void setScoreBoard() {

        setPreferredSize(new Dimension(150, 100));
        setBackground(Color.LIGHT_GRAY);

        blueScore.setForeground(Color.blue);
        redScore.setForeground(Color.red);
        Bscore.setForeground(Color.BLUE);
        Rscore.setForeground(Color.red);
        blueLimit.setForeground(Color.blue);
        redLimit.setForeground(Color.red);
        Blimit.setForeground(Color.blue);
        Rlimit.setForeground(Color.red);
        turn1.setForeground(Color.BLACK);
        turn2.setForeground(Color.blue);


        Border innerBorder = BorderFactory.createTitledBorder("ScoreBoard");
        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        add(turn1, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        add(turn2, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(blueScore, gc);


        gc.gridx = 1;
        gc.gridy = 1;
        add(Bscore, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        add(redScore, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(Rscore, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        add(blueLimit, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        add(Blimit, gc);


        gc.gridx = 0;
        gc.gridy = 4;
        add(redLimit, gc);

        gc.gridx = 1;
        gc.gridy = 4;
        add(Rlimit, gc);
    }

    public void setBscore(int bscore) {
        Bscore.setText(String.valueOf(bscore));
    }

    public void setRscore(int rscore) {
        Rscore.setText(String.valueOf(rscore));
    }

    public void setTurn2(String turn2) {

        this.turn2.setText(turn2);
        if (turn2.equals("blue"))
            this.turn2.setForeground(Color.blue);
        else if (turn2.equals("red"))
            this.turn2.setForeground(Color.red);
    }

    public static void setBlimit(int blimit) {
        Blimit.setText(String.valueOf(blimit));
    }

    public static void setRlimit(int rlimit) {
        Rlimit.setText(String.valueOf(rlimit));
    }
}