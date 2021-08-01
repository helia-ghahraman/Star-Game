import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MainFrame extends JFrame {

    private Toolbar toolbar;
    private Board board;
    private ScoreBoard scoreBoard;
    private boolean board_flag = false;
    private Menu menu;

    public MainFrame() throws IOException {

        setMainFrame();
    }

    public void setMainFrame() throws IOException {

        setTitle("Game");
        setBackground(Color.ORANGE);
        setLocation(400, 100);
        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu = new Menu();
        add(menu);
        setSize(800, 700);

        menu.getNewgame().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                remove(menu);
                toolbar = new Toolbar();
                add(toolbar, BorderLayout.WEST);
                setSize(801, 700);
                setOKbtn();
                setPlaybtn();
            }
        });

        menu.getRules().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menu, "HELLO WELCOME TO GAME\n 1) THE PREVALENCE OF THE GAME WITH THE PLAYER IS BLUE\n 2) PLAYERS ONLY MOVE HORIZONTALLY AND VERTICALLY\n 3) IF YOU HIT A WALL,YOU CAN NOT MOVE OVER IT\n 4) THE ONE WHO CAN GET MORE STARTS IS THE WINNER\n 5) SPEED BUMP: RESTRICTS THE MOVEMENT OF THE OPPOSING PLAYER\n GOOD LUCK :)", "Rules", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        menu.getExit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void setOKbtn() {

        toolbar.getOkBtn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (toolbar.getCol() <= 1 || toolbar.getRow() <= 1)
                    JOptionPane.showMessageDialog(MainFrame.this, "Please enter valid values for the number of rows and columns.", "Warning", JOptionPane.WARNING_MESSAGE);

                else {
                    board = new Board(toolbar.getRow(), toolbar.getCol());
                    setBoard_flag(true);
                    add(board, BorderLayout.CENTER);
                    setSize(802, 700);
                }
            }
        });
    }

    public void setPlaybtn() {

        toolbar.getPlayBtn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!isBoard_flag())
                    JOptionPane.showMessageDialog(MainFrame.this, "First set the board", "Warning", JOptionPane.WARNING_MESSAGE);

                else {
                    if (board.isbPlayerCount() && board.isrPlayerCount() && board.isStarCount()) {
                        remove(toolbar);
                        board.setComoBox(false);
                        board.setGame(true);
                        scoreBoard = new ScoreBoard();
                        board.setScoreBoard(scoreBoard);
                        add(scoreBoard, BorderLayout.WEST);
                        setSize(850, 720);
                    } else if (!board.isbPlayerCount() || !board.isrPlayerCount() || !board.isStarCount())
                        JOptionPane.showMessageDialog(MainFrame.this, "Damn! One player is missed", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public boolean isBoard_flag() {
        return board_flag;
    }

    public void setBoard_flag(boolean board_flag) {
        this.board_flag = board_flag;
    }
}