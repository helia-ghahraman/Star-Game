import javax.swing.*;
import java.awt.*;

public class GameManager {

    private Board board;
    private final Player bluePlayer = new Player();
    private final Player redPlayer = new Player();
    private int starCount;

    public GameManager(Board board) {
        setBoard(board);
    }

    // Investigation of oblique motion
    public boolean IsValidMove(Player player, int btnId) {
        boolean flag;
        if (board.getCell()[player.getId()].getClientProperty("x") == board.getCell()[btnId].getClientProperty("x")
                || board.getCell()[player.getId()].getClientProperty("y") == board.getCell()[btnId].getClientProperty("y")) {
            flag = true;
        } else {
            JOptionPane.showMessageDialog(board, "Oblique movement is not allowed!", "Warning" , JOptionPane.WARNING_MESSAGE);
            flag = false;
        }

        return flag;
    }


    public boolean IsBlock(Player player, int btnId) {

        boolean flag = false;

        if (board.getCell()[btnId].getName() == "red player" || board.getCell()[btnId].getName() == "blue player") {
            flag = true;
            JOptionPane.showMessageDialog(board, "Two pieces can't be placed in one house!", "Warning" , JOptionPane.WARNING_MESSAGE);
        }

        if (board.getCell()[btnId].getName() == "wall") {
            flag = true;
            JOptionPane.showMessageDialog(board, "The piece can't be placed on the wall", "Warning" , JOptionPane.WARNING_MESSAGE);
        }

        // horizontal movement
        if (board.getCell()[player.getId()].getClientProperty("x") == board.getCell()[btnId].getClientProperty("x")) {

            if (player.getId() > btnId) {
                for (int i = player.getId(); i >= btnId; i--) {
                    if ("wall".equals(board.getCell()[i].getName())) {
                        flag = true;
                        JOptionPane.showMessageDialog(board, "You can't cross the wall!!", "Warning" , JOptionPane.WARNING_MESSAGE);
                    }
                }

                for (int i = player.getId(); i >= btnId ; i--) {
                    if (!flag)
                        checkComponents(player, i);
                }
            }

            else if (player.getId() < btnId) {
                for (int i = player.getId(); i <= btnId; i++) {
                    if ("wall".equals(board.getCell()[i].getName())) {
                        flag = true;
                        JOptionPane.showMessageDialog(board, "You can't cross the wall!!", "Warning" , JOptionPane.WARNING_MESSAGE);
                    }
                }

                for (int i = player.getId(); i <= btnId; i++) {
                    if (!flag)
                        checkComponents(player, i);
                }
            }
        }

        // vertical movement
        else if (board.getCell()[player.getId()].getClientProperty("y") == board.getCell()[btnId].getClientProperty("y")) {

            if (player.getId() > btnId) {

                for (int j = player.getId(); j >= btnId; j -= board.getCol()) {
                    if ("wall".equals(board.getCell()[j].getName())) {
                        flag = true;
                        JOptionPane.showMessageDialog(board, "You can't cross the wall!!", "Warning" , JOptionPane.WARNING_MESSAGE);
                    }
                }


                for (int j = player.getId(); j >= btnId; j -= board.getCol()) {

                    if (!flag)
                        checkComponents(player, j);
                }
            }

            else if (player.getId() < btnId) {

                for (int j = player.getId(); j <= btnId; j += board.getCol()) {
                    if ("wall".equals(board.getCell()[j].getName())) {
                        flag = true;
                        JOptionPane.showMessageDialog(board, "You can't cross the wall!!", "Warning" , JOptionPane.WARNING_MESSAGE);
                    }
                }


                for (int j = player.getId(); j <= btnId; j += board.getCol()) {

                    if (!flag)
                        checkComponents(player, j);
                }
            }
        }
        return flag;
    }


    public boolean applySpeedBump(Player player, int btnId, String turn) {

        boolean flag = true;


        if (turn.equals("blue")) {

            if (!player.getSpeedBumps().isEmpty()) {

                int tafazol;

                // horizontal movement

                if (Math.abs(btnId - this.bluePlayer.getId()) < board.getCol())
                    tafazol = Math.abs(btnId - this.bluePlayer.getId());

                // vertical movement
                else
                    tafazol = (Math.abs(btnId - this.bluePlayer.getId())) / board.getCol();


                if (tafazol <= player.getSpeedBumps().get(0)) {
                    flag = true;
                    player.getSpeedBumps().remove(0);
                    if (player.getSpeedBumps().isEmpty())
                        ScoreBoard.setBlimit(0);
                    else
                        ScoreBoard.setBlimit(player.getSpeedBumps().get(0));
                }
                else {
                    JOptionPane.showMessageDialog(board, "Your opponent restricts your movement with the speedbump", "Warning" , JOptionPane.WARNING_MESSAGE);
                    flag = false;
                }
            }
        }

        else if (turn.equals("red")) {

            if (!player.getSpeedBumps().isEmpty()) {

                int tafazol;

                // horizontal movement
                if (Math.abs(btnId - this.redPlayer.getId()) < board.getCol())
                    tafazol = Math.abs(btnId - this.redPlayer.getId());

                // vertical movement
                else
                    tafazol = (Math.abs(btnId - this.redPlayer.getId())) / board.getCol();


                if (tafazol <= player.getSpeedBumps().get(0)) {
                    flag = true;
                    player.getSpeedBumps().remove(0);
                    if (player.getSpeedBumps().isEmpty())
                        ScoreBoard.setRlimit(0);
                    else
                        ScoreBoard.setRlimit(player.getSpeedBumps().get(0));
                } else {
                    JOptionPane.showMessageDialog(board, "Your opponent restricts your movement with the speedbump", "Warning" , JOptionPane.WARNING_MESSAGE);
                    flag = false;
                }
            }
        }
        return flag;
    }


    public void changeTurn(Player redPlayer, Player bluePlayer) {
        if (redPlayer.isTurn()) {
            redPlayer.setTurn(false);
            bluePlayer.setTurn(true);
        } else if (bluePlayer.isTurn()) {
            bluePlayer.setTurn(false);
            redPlayer.setTurn(true);
        }
    }
    // get the pieces
    public void checkComponents(Player player, int btnId) {
        if (null != board.getCell()[btnId].getName()) {
            switch (board.getCell()[btnId].getName()) {
                case "star":
                    player.increaseScore(1);
                    starCount--;
                    board.getCell()[btnId].setName("empty");
                    board.getCell()[btnId].setIcon(null);
                    break;


                case "speedbump":

                    player.getSpeedBumps().add(board.getCell()[btnId].getLimit());
                    if (player == bluePlayer) {
                        if (player.getSpeedBumps().isEmpty())
                            ScoreBoard.setRlimit(0);
                        else
                            ScoreBoard.setRlimit(player.getSpeedBumps().get(0));
                    }

                    if (player == redPlayer) {
                        if (player.getSpeedBumps().isEmpty())
                            ScoreBoard.setBlimit(0);
                        else
                            ScoreBoard.setBlimit(player.getSpeedBumps().get(0));
                    }

                    board.getCell()[btnId].setName("empty");
                    board.getCell()[btnId].setIcon(null);
                    board.getCell()[btnId].setToolTipText(null);

                    break;

                default:
                    break;
            }
        }
    }

    public boolean isFinished() {
        boolean flag = false;
        if (starCount == 0) {
            JOptionPane.showMessageDialog(board, "Finished!");
            flag = true;
            if (bluePlayer.getScore() > redPlayer.getScore()) {
                JOptionPane.showMessageDialog(board, "Blue Is Winner!");
            } else if (redPlayer.getScore() > bluePlayer.getScore()) {
                JOptionPane.showMessageDialog(board, "Red Is Winner!");
            } else {
                JOptionPane.showMessageDialog(board, "It's Tie");
            }

            Frame[] f = MainFrame.getFrames();
            f[0].dispose();
        }
        return flag;
    }


    public void setBoard(Board board) {
        this.board = board;
    }

    public void increaseStarCount() {
        this.starCount++;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }
}