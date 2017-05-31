import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by eszti on 2017.05.31..
 */
public class GUI {

    JLabel[][] table;
    Minesweeper minesweeper;
    JPanel panel;
    JFrame frame;

    public GUI (Minesweeper minesweeper){
        this.minesweeper = minesweeper;
        this.panel = new JPanel(new SpringLayout());
        this.frame = new JFrame("DE! Minesweeper");
        table = new JLabel[minesweeper.row][minesweeper.column];
    }

    private void drawTable() {
        for (int x = 0; x < minesweeper.row; x++) {
            for (int y = 0; y < minesweeper.column; y++) {
                String value = Character.toString(minesweeper.table[x][y]);
                JLabel label = new JLabel(" ");
                label.setPreferredSize(new Dimension(40, 40));
                label.setOpaque(true);
                label.setBackground(new Color(127, 140, 141));
                label.setBorder(new LineBorder(new Color(72, 84, 85)));
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) {
                            label.setText(value);
                            if (value.equals("*")) {
                                label.setBackground(new Color(231, 76, 60));
                                JLabel lost = new JLabel("You lost (╯°□°）╯︵ ┻━┻");
                                lost.setForeground(new Color(197, 213, 214));
                                lost.setPreferredSize(new Dimension(200, 40));
                                lost.setHorizontalAlignment(SwingConstants.CENTER);
                                lost.setVerticalAlignment(SwingConstants.CENTER);
                                panel.add(lost);
                            }
                            else {
                                label.setBackground(new Color(179, 195, 196));
                            }
                            label.setOpaque(true);
                        }
                    }
                });
                panel.add(label);
            }
        }
    }

    private void setFramePos() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        int xPos = (dim.width/2) - (frame.getWidth()/2);
        int yPos = (dim.height/2) - (frame.getHeight()/2);
        frame.setLocation(xPos, yPos);
    }

    public void show(){
        drawTable();
        SpringUtilities.makeCompactGrid(panel,
                minesweeper.row, minesweeper.column,
                0, 40,
                0, 0);

        panel.setBackground(new Color(72, 84, 85));
        panel.setOpaque(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setContentPane(panel);
        frame.pack();

        setFramePos();
    }
}

