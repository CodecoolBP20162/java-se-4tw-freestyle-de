package com.codecool.de.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class DifficultyGUI {
    JFrame frame = new JFrame("DE! Minesweeper");
    JPanel panel = new JPanel(null);
    JLabel choose = new JLabel("Choose difficulty!");
    JRadioButton easy;
    JRadioButton medium;
    JRadioButton hard;
    JButton play = new JButton("Play!");
    int panelWidth = 200;
    int panelHeight = 150;
    GameGUI newGameGUI;
    GameGUI openedGameGUI;


    private void createRadioButtons(){
        easy = new JRadioButton("easy");
        easy.setFocusPainted(false);

        medium = new JRadioButton("medium");
        medium.setSelected(true);

        hard = new JRadioButton("hard");
        hard.setMnemonic(KeyEvent.VK_H);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(easy);
        buttonGroup.add(medium);
        buttonGroup.add(hard);
    }

    private void openGameGUI(int row, int column, int mines){
        MinesweeperTable minesweeper = new MinesweeperTable(row, column, mines);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        try {openedGameGUI.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));}
        catch (NullPointerException e){}
        newGameGUI = new GameGUI(minesweeper);
        newGameGUI.show();
    }

    private void addPlayButtonAction(){
        play.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(easy.isSelected()){
                    int row = 8;
                    int column = 16;
                    int mines = row * column / 6;
                    openGameGUI(row, column, mines);
                }
                else if(medium.isSelected()){
                    int row = 16;
                    int column = 16;
                    int mines = row * column / 6;
                    openGameGUI(row, column, mines);
                }
                else if(hard.isSelected()){
                    int row = 16;
                    int column = 24;
                    int mines = row * column / 6;
                    openGameGUI(row, column, mines);
                }
            }
        });
    }

    private void setComponentsPosition(){
        Dimension radioButtons = medium.getPreferredSize();
        int radioX = (panelWidth/2) - (radioButtons.width/2);
        choose.setBounds(0,
                (panelHeight/5)*0,
                panelWidth, panelHeight/5);
        easy.setBounds(radioX,
                panelHeight/5*1,
                radioButtons.width, panelHeight/5);
        medium.setBounds(radioX,
                panelHeight/5*2,
                radioButtons.width, panelHeight/5);
        hard.setBounds(radioX,
                panelHeight/5*3,
                radioButtons.width, panelHeight/5);
        play.setBounds(0,
                panelHeight/5*4,
                panelWidth, panelHeight/5);

    }

    private void addComponents(){
        choose.setHorizontalAlignment(SwingConstants.CENTER);
        choose.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(choose);
        panel.add(easy);
        panel.add(medium);
        panel.add(hard);
        panel.add(play);
    }

    public void show(){
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        createRadioButtons();
        addPlayButtonAction();
        setComponentsPosition();
        addComponents();
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new GUICommon().setFramePos(frame);
        frame.setVisible(true);
    }
}
