import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class Menu extends JPanel {

    private final JButton newgame = new JButton("New Game");
    private final JButton rules= new JButton("Rules");
    private final JButton exit = new JButton("Exit");
    BufferedImage image = ImageIO.read(new File("src/Pictures/menu.png"));
    JLabel label = new JLabel(new ImageIcon(image));

    public Menu() throws IOException {
        setMenu();
    }

    public void setMenu() {
        setLayout(null);
        newgame.setBounds(300, 150, 200, 130);
        rules.setBounds(300, 300, 200, 130);
        exit.setBounds(300, 450, 200, 130);
        newgame.setBackground(Color.BLUE);
        newgame.setForeground(Color.red);
        rules.setBackground(Color.green);
        rules.setForeground(Color.MAGENTA);
        exit.setBackground(Color.red);
        exit.setForeground(Color.blue);
        newgame.setFont(new Font("Algerian", Font.PLAIN, 30));
        exit.setFont(new Font("Algerian", Font.PLAIN, 35));
        rules.setFont(new Font("Algerian", Font.PLAIN, 35));
        label.setBounds(0, 0, 800, 700);

        add(newgame);
        add(rules);
        add(exit);
        add(label);
    }

    public JButton getNewgame() {
        return newgame;
    }

    public JButton getExit() {
        return exit;
    }

    public JButton getRules() {
        return rules;
    }
}