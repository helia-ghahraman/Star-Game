import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;

public class Toolbar extends JPanel {

    private final JLabel RowDimension = new JLabel("Row: ");
    private final JLabel ColDimension = new JLabel("Col: ");
    private final JTextField Row = new JTextField(10);
    private final JTextField Col = new JTextField(10);
    private final JButton okBtn = new JButton("Ok");
    private final JButton playBtn = new JButton("Play");

    public Toolbar() {
        setToolbar();
    }

    public void setToolbar() {

        setPreferredSize(new Dimension(180, 110));
        setBackground(Color.orange);

        playBtn.setBackground(Color.lightGray);
        playBtn.setForeground(Color.BLUE);
        okBtn.setBackground(Color.lightGray);
        okBtn.setForeground(Color.red);

        Border innerBorder = BorderFactory.createTitledBorder("Game Setting");
        Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.gridy = 0;
        add(RowDimension, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        add(Row, gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridy = 1;
        gc.gridx = 0;
        add(ColDimension, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(Col, gc);

        gc.weightx = 1;
        gc.weighty = 2;
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(okBtn, gc);


        gc.weightx = 1;
        gc.weighty = 2;
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(playBtn, gc);
    }

    public int getCol() {

        int col_number = 0;

        try {
            col_number = Integer.parseInt(Col.getText());

        }
        // If the user does not enter the number of columns :
        catch (Exception ex) {
            JOptionPane.showMessageDialog(Toolbar.this, "Please enter the dimensions of the page first", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return col_number;
    }

    public int getRow() {

        int row_number = 0;

        try {
            row_number = Integer.parseInt(Row.getText());

        }
        // If the user does not enter the number of rows :
        catch (Exception ex) {
            JOptionPane.showMessageDialog(Toolbar.this, "Please enter the dimensions of the page first", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return row_number;
    }

    public JButton getOkBtn() {
        return okBtn;
    }

    public JButton getPlayBtn() {
        return playBtn;
    }
}