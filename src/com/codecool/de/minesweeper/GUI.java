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
    JFrame frame;
    JLabel info;
    JButton restart;

    public GUI (Minesweeper minesweeper){
        this.minesweeper = minesweeper;
        this.gamePanel = new JPanel(new SpringLayout());
        this.infoPanel = new JPanel(null);
        infoPanel.setPreferredSize(new Dimension(minesweeper.column*40, 40));
        this.frame = new JFrame("DE! Minesweeper");
        table = new JLabel[minesweeper.row][minesweeper.column];
    }

    private void drawInfoPanel() {

        info = new JLabel("ᕙ(⇀‸↼‶)ᕗ");
        info.setForeground(new Color(179, 195, 196));
        restart = new JButton("Restart");

        Dimension infoSize = info.getPreferredSize();
        info.setBounds(10, (40-infoSize.height)/2, infoSize.width, infoSize.height);
        Dimension restartSize = restart.getPreferredSize();
        restart.setBounds((minesweeper.column*40)-(restartSize.width+10),
                (40-restartSize.height)/2,
                restartSize.width, restartSize.height);
        infoPanel.add(info);
        infoPanel.add(restart);
    }

    private void drawGamePanel() {
        JLabel emptySpace = new JLabel();
        emptySpace.setPreferredSize(new Dimension(40*minesweeper.column/3, 40));

        for (int x = 0; x < minesweeper.row; x++) {
            JLabel[] labelList = new JLabel[minesweeper.column];
            for (int y = 0; y < minesweeper.column; y++) {
                String value = Character.toString(minesweeper.table[x][y]);
                JLabel label = new JLabel("ツ");
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
                                info.setText("You lose (╯°□°）╯︵ ┻━┻");
                                Dimension infoSize = info.getPreferredSize();
                                info.setBounds(10, (40-infoSize.height)/2, infoSize.width, infoSize.height);
                                revealAll(false);
                            }
                            else {
                                label.setBackground(new Color(179, 195, 196));
                                if (isWinner()){
                                    info.setText("You win ♪~ ᕕ(ᐛ)ᕗ");
                                    Dimension infoSize = info.getPreferredSize();
                                    info.setBounds(10, (40-infoSize.height)/2, infoSize.width, infoSize.height);
                                    revealAll(true);
                                }
                            }
                            label.setOpaque(true);
                        }
                    }
                });
                gamePanel.add(label);
                labelList[y] = label;
            }
            table[x] = labelList;
        }
        gamePanel.add(emptySpace);
    }

    private void revealAll(Boolean isWinner) {
        for (int x = 0; x < minesweeper.row; x++) {
            for (int y = 0; y < minesweeper.column; y++) {
                JLabel label = table[x][y];
                String value = Character.toString(minesweeper.table[x][y]);
                label.setText(value);
                if (value.equals("*") && !isWinner) {
                    label.setBackground(new Color(231, 76, 60));
                }
                else {
                    label.setBackground(new Color(179, 195, 196));
                }
            }
        }
    }

    private Boolean isWinner(){
        int coveredCounter = 0;
        for (JLabel[] row : table){
            for (JLabel label : row){
                if (label.getText().equals("ツ")) {
                    coveredCounter++;
                }
            }
        }
        if (coveredCounter == (minesweeper.row * minesweeper.column) -
                ((minesweeper.row * minesweeper.column) - minesweeper.mines)){
            return true;
        }
        else {
            return false;
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
        JPanel masterPanel = new JPanel(new BorderLayout());
        masterPanel.add(infoPanel, BorderLayout.PAGE_START);
        masterPanel.add(gamePanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setContentPane(masterPanel);
        frame.pack();

        setFramePos();
    }
}

