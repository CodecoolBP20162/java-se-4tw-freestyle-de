package com.codecool.de.minesweeper;

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
    JPanel gamePanel;
    JPanel infoPanel;
    JPanel masterPanel;
    JFrame frame;
    JLabel info;
    JButton restart;

    public GUI(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
        this.gamePanel = new JPanel(new SpringLayout());
        this.infoPanel = new JPanel(null);
        infoPanel.setPreferredSize(new Dimension(minesweeper.column * 40, 40));
        this.frame = new JFrame("DE! Minesweeper");
        table = new JLabel[minesweeper.row][minesweeper.column];
    }

    private void drawInfoPanel() {

        info = new JLabel("ᕙ(⇀‸↼‶)ᕗ");
        info.setForeground(new Color(179, 195, 196));
        restart = new JButton("Restart");
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Minesweeper newMinesweeper = new Minesweeper(minesweeper.row, minesweeper.column, minesweeper.mines);
                minesweeper = newMinesweeper;
                minesweeper.doit();
                gamePanel.removeAll();
                drawGamePanel();
                SpringUtilities.makeCompactGrid(gamePanel,
                        minesweeper.row, minesweeper.column,
                        0, 0,
                        0, 0);
                gamePanel.updateUI();
                infoPanel.removeAll();
                drawInfoPanel();
                infoPanel.updateUI();
                masterPanel.updateUI();
            }
        });

        Dimension infoSize = info.getPreferredSize();
        info.setBounds(10, (40 - infoSize.height) / 2, infoSize.width, infoSize.height);
        Dimension restartSize = restart.getPreferredSize();
        restart.setBounds((minesweeper.column * 40) - (restartSize.width + 10),
                (40 - restartSize.height) / 2,
                restartSize.width, restartSize.height);
        infoPanel.add(info);
        infoPanel.add(restart);
    }

    private void drawGamePanel() {
        for (int x = 0; x < minesweeper.row; x++) {
            JLabel[] labelList = new JLabel[minesweeper.column];
            for (int y = 0; y < minesweeper.column; y++) {
                String value = Character.toString(minesweeper.table[x][y]);
                JLabel label = new JLabel("ツ");
                int row = x;
                int column = y;
                label.setPreferredSize(new Dimension(40, 40));
                label.setOpaque(true);
                label.setBackground(new Color(127, 140, 141));
                label.setBorder(new LineBorder(new Color(72, 84, 85)));
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (label.getText().equals("\uD83C\uDFF4")){
                                label.setText("ツ");
                                label.setBackground(new Color(127, 140, 141));
                            }
                            else {
                                label.setText(value);
                                if (value.equals("*")) {
                                    label.setBackground(new Color(231, 76, 60));
                                    info.setText("You lose. Fuuuu- (╯°□°）╯︵ ┻━┻");
                                    Dimension infoSize = info.getPreferredSize();
                                    info.setBounds(10, (40 - infoSize.height) / 2, infoSize.width, infoSize.height);
                                    revealAll(false);
                                } else {
                                    label.setBackground(new Color(179, 195, 196));
                                    if (label.getText().equals(" ")) {
                                        revealGroup(row, column);
                                    }
                                    if (isWinner()) {
                                        info.setText("You win. Yeaaaaa ヾ(⌐■_■)ノ♪");
                                        Dimension infoSize = info.getPreferredSize();
                                        info.setBounds(10, (40 - infoSize.height) / 2, infoSize.width, infoSize.height);
                                        revealAll(true);
                                    }

                                }
                                label.setOpaque(true);
                            }
                        }
                        if (SwingUtilities.isRightMouseButton(e)) {
                            if (label.getText().equals("ツ")){
                                label.setText("\uD83C\uDFF4");
                                label.setBackground(new Color(230, 126, 34));
                            }
                        }
                    }
                });
                gamePanel.add(label);
                labelList[y] = label;
            }
            table[x] = labelList;
        }
    }

    private void revealAll(Boolean isWinner) {
        for (int x = 0; x < minesweeper.row; x++) {
            for (int y = 0; y < minesweeper.column; y++) {
                JLabel label = table[x][y];
                String value = Character.toString(minesweeper.table[x][y]);
                label.setText(value);
                if (value.equals("*") && !isWinner) {
                    label.setBackground(new Color(231, 76, 60));
                } else {
                    label.setBackground(new Color(179, 195, 196));
                }
            }
        }
    }

    private void revealGroup(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int i2 = -1; i2 < 2; i2++) {
                try {
                    JLabel label = table[x + i][y + i2];
                    if (label.getText().equals("ツ")) {
                        String value = Character.toString(minesweeper.table[x + i][y + i2]);
                        label.setText(value);
                        label.setBackground(new Color(179, 195, 196));
                        if (value.equals(" ")) {
                            revealGroup(x + i, y + i2);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    private Boolean isWinner() {
        int coveredCounter = 0;
        for (JLabel[] row : table) {
            for (JLabel label : row) {
                if (label.getText().equals("ツ") || label.getText().equals("\uD83C\uDFF4")) {
                    coveredCounter++;
                }
            }
        }
        if (coveredCounter == (minesweeper.row * minesweeper.column) -
                ((minesweeper.row * minesweeper.column) - minesweeper.mines)) {
            return true;
        } else {
            return false;
        }
    }

    private void setFramePos() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        int xPos = (dim.width / 2) - (frame.getWidth() / 2);
        int yPos = (dim.height / 2) - (frame.getHeight() / 2);
        frame.setLocation(xPos, yPos);
    }

    public void show() {
        drawInfoPanel();
        drawGamePanel();
        SpringUtilities.makeCompactGrid(gamePanel,
                minesweeper.row, minesweeper.column,
                0, 0,
                0, 0);

        gamePanel.setBackground(new Color(72, 84, 85));
        gamePanel.setOpaque(true);
        infoPanel.setBackground(new Color(72, 84, 85));
        infoPanel.setOpaque(true);
        masterPanel = new JPanel(new BorderLayout());
        masterPanel.add(infoPanel, BorderLayout.PAGE_START);
        masterPanel.add(gamePanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setContentPane(masterPanel);
        frame.pack();
        frame.setResizable(false);

        setFramePos();
    }
}

