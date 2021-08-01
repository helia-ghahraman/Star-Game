import Pieces.SpeedBump;
import Pieces.Star;
import Pieces.Wall;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private Buttons[] cell;
    private int row, col;
    private boolean rPlayerCount = false;
    private boolean bPlayerCount = false;
    private boolean starCount = false;
    private boolean ComoBox = true;
    private boolean game = false;
    private int Id; // location of Destination
    private ScoreBoard scoreBoard;
    private GameManager gameManager;
    private JComboBox comboBox;
    // items for combobox
    private final String[] items = {"Star", "SpeedBump", "Wall", "Blue Player", "Red Player"};

    public Board(int row, int col) {
        setRow(row);
        setCol(col);
        setBoard();
    }

    public void setBoard() {

        setBackground(Color.orange);
        cell = new Buttons[col * row];

        // Save button numbers
        for (int i = 0; i < row * col; i++) {
            cell[i] = new Buttons("empty");
            cell[i].putClientProperty("id", i);
            add(cell[i]);
        }

        // Save button coordinates
        int k = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (k < row * col) {
                    cell[k].putClientProperty("x", i);
                    cell[k].putClientProperty("y", j);
                    k++;
                }
            }
        }

        // design board
        int x = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (x < row * col) {
                    if (i % 2 == 0) {
                        if (j % 2 == 0)
                            cell[x].setBackground(Color.cyan);
                        else
                            cell[x].setBackground(Color.pink);
                    }

                    else if (i % 2 != 0) {
                        if (j % 2 == 0)
                            cell[x].setBackground(Color.pink);
                        else
                            cell[x].setBackground(Color.cyan);
                    }
                    x++;
                }
            }
        }

        gameManager = new GameManager(this);
        setPreferredSize(new Dimension(620, 590));
        setLayout(new GridLayout(row, col));
        setComboBox();
        Movement();
    }


    public void setComboBox() {
        comboBox = new JComboBox(items);
        comboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Object selected = comboBox.getSelectedItem();

                // put the pieces on the board
                switch (selected.toString()) {

                    case "Star":
                        if (cell[Id].getName() != "blue player" && cell[Id].getName() != "red player"
                                && cell[Id].getName() != "wall" && cell[Id].getName() != "speedbump") {
                            cell[Id].setIcon(Star.star_icon);
                            cell[Id].setName("star");
                            setStarCount(true);
                            gameManager.increaseStarCount();
                        }
                        break;

                    case "Wall":

                        if (cell[Id].getName() != "blue player" && cell[Id].getName() != "red player"
                                && cell[Id].getName() != "star" && cell[Id].getName() != "speedbump") {
                            cell[Id].setIcon(Wall.wall_icon);
                            cell[Id].setName("wall");
                        }
                        break;


                    case "SpeedBump":

                        if (cell[Id].getName() != "blue player" && cell[Id].getName() != "red player"
                                && cell[Id].getName() != "wall" && cell[Id].getName() != "star") {

                            String limit = JOptionPane.showInputDialog(Board.this, "enter the limit : ");

                            try {

                                int value = Integer.parseInt(limit);
                                if (value > row || value > col)
                                    JOptionPane.showMessageDialog(Board.this, "The number entered is larger than the board size!", "Error", JOptionPane.WARNING_MESSAGE);
                                if (value <= 0)
                                    JOptionPane.showMessageDialog(Board.this, "Enter a valid value!", "Error", JOptionPane.WARNING_MESSAGE);
                                else {
                                    cell[Id].setLimit(value);
                                    cell[Id].setIcon(SpeedBump.speedbump_icon);
                                    cell[Id].setToolTipText("The value of limit is : " + value);
                                    cell[Id].setName("speedbump");
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(Board.this, "Value of limit is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;


                    case "Blue Player":

                        if (!isbPlayerCount() && cell[Id].getName() != "speedbump" && cell[Id].getName() != "red player"
                                && cell[Id].getName() != "wall" && cell[Id].getName() != "star") {
                            cell[Id].setIcon(Player.Bplayer_icon);
                            cell[Id].setName("blue player");
                            gameManager.getBluePlayer().setId(Id);
                            setbPlayerCount(true);
                        }
                        break;

                    case "Red Player":

                        if (!isrPlayerCount() && cell[Id].getName() != "speedbump" && cell[Id].getName() != "blue player"
                                && cell[Id].getName() != "wall" && cell[Id].getName() != "star") {
                            cell[Id].setIcon(Player.Rplayer_icon);
                            cell[Id].setName("red player");
                            gameManager.getRedPlayer().setId(Id);
                            setrPlayerCount(true);
                        }
                        break;

                    default:
                        break;
                }

                remove(comboBox);
                gameManager.getBluePlayer().setTurn(true);
            }
        });
    }

    public void Movement() {

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton clicked = (JButton) e.getSource();
                Id = (int) clicked.getClientProperty("id");

                // show the combobox
                if (isComoBox()) {
                    add(comboBox);
                    comboBox.setBounds(cell[Id].getX() + 10, cell[Id].getY() + 10, 90, 20);
                    comboBox.setBackground(Color.lightGray);
                    comboBox.showPopup();

                }

                // start the game and move the players
                else if (isGame()) {

                    // The movement of the blue player
                    if (gameManager.getBluePlayer().isTurn()) {

                        if (!gameManager.isFinished()
                                && gameManager.IsValidMove(gameManager.getBluePlayer(), Id)
                                && gameManager.applySpeedBump(gameManager.getRedPlayer(), Id, "blue")
                                && !gameManager.IsBlock(gameManager.getBluePlayer(), Id)) {

                            cell[Id].setIcon(Player.Bplayer_icon);
                            cell[gameManager.getBluePlayer().getId()].setIcon(null);
                            cell[Id].setName("blue player");
                            cell[gameManager.getBluePlayer().getId()].setName("empty");
                            gameManager.getBluePlayer().setId(Id);
                            gameManager.changeTurn(gameManager.getRedPlayer(), gameManager.getBluePlayer());
                            scoreBoard.setTurn2("red");
                            scoreBoard.setBscore(gameManager.getBluePlayer().getScore());
                        }
                    }

                    // The movement of the red player
                    else if (gameManager.getRedPlayer().isTurn()) {

                        if (!gameManager.isFinished()
                                && gameManager.IsValidMove(gameManager.getRedPlayer(), Id)
                                && gameManager.applySpeedBump(gameManager.getBluePlayer(), Id, "red")
                                && !gameManager.IsBlock(gameManager.getRedPlayer(), Id)) {

                            cell[Id].setIcon(Player.Rplayer_icon);
                            cell[gameManager.getRedPlayer().getId()].setIcon(null);
                            cell[Id].setName("red player");
                            cell[gameManager.getRedPlayer().getId()].setName("empty");
                            gameManager.getRedPlayer().setId(Id);
                            gameManager.changeTurn(gameManager.getRedPlayer(), gameManager.getBluePlayer());
                            scoreBoard.setTurn2("blue");
                            scoreBoard.setRscore(gameManager.getRedPlayer().getScore());
                        }
                    }
                    if (gameManager.isFinished()) {
                        setGame(false);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };

        for (int i = 0; i < row * col; i++) {
            cell[i].addMouseListener(mouseListener);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isrPlayerCount() {
        return rPlayerCount;
    }

    public boolean isbPlayerCount() {
        return bPlayerCount;
    }

    public void setrPlayerCount(boolean rPlayerCount) {
        this.rPlayerCount = rPlayerCount;
    }

    public void setbPlayerCount(boolean bPlayerCount) {
        this.bPlayerCount = bPlayerCount;
    }

    public void setComoBox(boolean comoBox) {
        ComoBox = comoBox;
    }

    public void setGame(boolean game) {
        this.game = game;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public Buttons[] getCell() {
        return cell;
    }

    public boolean isComoBox() {
        return ComoBox;
    }

    public boolean isGame() {
        return game;
    }

    public boolean isStarCount() {
        return starCount;
    }

    public void setStarCount(boolean starCount) {
        this.starCount = starCount;
    }
}