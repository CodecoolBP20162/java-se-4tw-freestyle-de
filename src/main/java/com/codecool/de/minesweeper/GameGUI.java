package com.codecool.de.minesweeper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Responsible for the user interface
 */
public class GameGUI {

    JLabel[][] table;
    MinesweeperTable minesweeper;
    JPanel gamePanel = new JPanel(new SpringLayout());
    JPanel infoPanel = new JPanel(null);
    JPanel masterPanel;
    JFrame frame;
    JLabel winLoseInfo = new JLabel();
    JLabel allMinesInfo = new JLabel();
    JLabel flagsPlacedInfo = new JLabel();
    JButton restart = new JButton("Restart");
    JButton changeDifficulty = new JButton("Change difficulty");
    String coveredSymbol = "ツ";
    String flagSymbol = "\uD83C\uDFF4";
    int flags;
    Color infoColor = new Color(179, 195, 196);

    /**
     * Passing the table to the minesweeper newGameGUI
     * @param minesweeper Gets the minsweeper table
     */
    public GameGUI(MinesweeperTable minesweeper) {
        this.minesweeper = minesweeper;
        infoPanel.setPreferredSize(new Dimension(minesweeper.column * 40, 65));
        table = new JLabel[minesweeper.row][minesweeper.column];
        frame = new JFrame("DE! Minesweeper");
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGameAction();
            }
        });
        changeDifficulty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { changeDifficultyAction(); }
        });
    }

    /**
     * On click restarts the game
     */
    private void restartGameAction() {
        MinesweeperTable newMinesweeper = new MinesweeperTable(
                minesweeper.row, minesweeper.column, minesweeper.mines);
        minesweeper = newMinesweeper;
        gamePanel.removeAll();
        drawGamePanel();
        drawSpringLayoutForGamePanel();
        gamePanel.updateUI();
        infoPanel.removeAll();
        drawInfoPanel();
        infoPanel.updateUI();
        masterPanel.updateUI();
    }

    private void changeDifficultyAction() {
        DifficultyGUI difficultyGUI = new DifficultyGUI();
        difficultyGUI.openedGameGUI = this;
        difficultyGUI.show();
    }

    /**
     * Checks the cells label
     * @param label Gets the label value
     * @return Returns true or false if it is revealed
     */
    private Boolean isCovered(JLabel label){
        if (label.getText().equals(coveredSymbol)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks the cell if it is flagged
     * @param label Gets the label value
     * @return Returns true or false if it is flagged
     */
    private Boolean isFlagged(JLabel label){
        if (label.getText().equals(flagSymbol)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks the cell if it is mine
     * @param value Gets the label value
     * @return Returns true or false if it is a mine
     */
    private Boolean isMine(String value){
        if (value.equals("*")){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks the cell if it is empty
     * @param value Gets the label value
     * @return Returns true or false if it is empty
     */
    private Boolean isEmpty(String value){
        if (value.equals(" ")){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks tha gamestate, if a counter reaches a given limit the player wins
     * @return Returns true or false about winning
     */
    private Boolean isWinner() {
        int coveredCounter = 0;
        for (JLabel[] row : table) {
            for (JLabel label : row) {
                if (isCovered(label) || isFlagged(label)) {
                    coveredCounter++;
                }
            }
        }
        if (coveredCounter == minesweeper.mines) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Draws the layout for the game
     */
    private void drawSpringLayoutForGamePanel() {
        SpringUtilities.makeCompactGrid(gamePanel,
                minesweeper.row, minesweeper.column,
                0, 0,
                0, 0);
    }

    /**
     * Restarts winLoseInfo parts on the top of the newGameGUI
     */
    private void setInfoComponentsPosition() {
        Dimension infoSize = winLoseInfo.getPreferredSize();
        winLoseInfo.setBounds((minesweeper.column * 40 / 2) - (infoSize.width/2),
                (40 - infoSize.height) / 2,
                infoSize.width, infoSize.height);
        Dimension restartSize = restart.getPreferredSize();
        restart.setBounds((minesweeper.column * 40) - (restartSize.width + 10),
                (40 - restartSize.height) / 2,
                restartSize.width, restartSize.height);
        Dimension minesInfoSize = allMinesInfo.getPreferredSize();
        allMinesInfo.setBounds(10,
                (40 - minesInfoSize.height) / 2,
                minesInfoSize.width, minesInfoSize.height);
        Dimension flagInfoSize = flagsPlacedInfo.getPreferredSize();
        flagsPlacedInfo.setBounds(10,
                (90 - flagInfoSize.height) / 2,
                flagInfoSize.width, flagInfoSize.height);
        Dimension changeDifficultySize = changeDifficulty.getPreferredSize();
        changeDifficulty.setBounds((minesweeper.column * 40) - (changeDifficultySize.width + 10),
                (90 - changeDifficultySize.height) / 2,
                changeDifficultySize.width, changeDifficultySize.height);
        infoPanel.add(allMinesInfo);
        infoPanel.add(winLoseInfo);
        infoPanel.add(restart);
        infoPanel.add(flagsPlacedInfo);
        infoPanel.add(changeDifficulty);
    }

    /**
     * Draws the panel winLoseInfo
     */
    private void drawInfoPanel() {
        flags = 0;
        winLoseInfo.setText("ᕙ(⇀‸↼‶)ᕗ");
        allMinesInfo.setText(String.format("Mines: %d", minesweeper.mines));
        flagsPlacedInfo.setText(String.format("Flags placed: %d", flags));
        winLoseInfo.setForeground(infoColor);
        allMinesInfo.setForeground(infoColor);
        flagsPlacedInfo.setForeground(infoColor);
        restart.setFocusPainted(false);
        setInfoComponentsPosition();
    }

    /**
     * Draws loose message and reveals table
     */
    private void lostGameAction() {
        winLoseInfo.setText("You lose. Fuuuu- (╯°□°）╯︵ ┻━┻");
        setInfoComponentsPosition();
        revealAll(false);
    }

    /**
     * Draws win message and reveals table
     */
    private void wonGameAction() {
        winLoseInfo.setText("You win. Yeaaaaa ヾ(⌐■_■)ノ♪");
        setInfoComponentsPosition();
        revealAll(true);
    }

    /**
     * Sets the properties for the cells
     * @param label Gets the label value
     */
    private void setBasicTableCellProperties(JLabel label) {
        label.setPreferredSize(new Dimension(40, 40));
        label.setOpaque(true);
        label.setBackground(new Color(127, 140, 141));
        label.setBorder(new LineBorder(new Color(72, 84, 85)));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Sets the action on the cell
     * @param label Gets the label value
     * @param value Value of the field
     * @param row x coordinate
     * @param column y coordinate
     */
    private void setTableCellAction(JLabel label, String value, int row, int column){
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                        label.setText(value);
                        if (isMine(value)) {
                            label.setBackground(new Color(231, 76, 60));
                            lostGameAction();
                        } else {
                            label.setBackground(new Color(179, 195, 196));
                            if (isEmpty(value)) {
                                revealEmptyCells(row, column);
                            }
                            if (isWinner()) {
                                wonGameAction();
                            }
                        }
                        label.setOpaque(true);
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (isFlagged(label)) {
                        label.setText(coveredSymbol);
                        label.setBackground(new Color(127, 140, 141));
                        flags -= 1;
                        flagsPlacedInfo.setText(String.format("Flags placed: %d", flags));
                        setInfoComponentsPosition();
                    }
                    else if (isCovered(label)) {
                        label.setText(flagSymbol);
                        label.setBackground(new Color(230, 126, 34));
                        flags += 1;
                        flagsPlacedInfo.setText(String.format("Flags placed: %d", flags));
                        setInfoComponentsPosition();
                    }
                }
            }
        });
    }

    /**
     * Draws gamepanel
     */
    private void drawGamePanel() {
        for (int x = 0; x < minesweeper.row; x++) {
            JLabel[] labelList = new JLabel[minesweeper.column];
            for (int y = 0; y < minesweeper.column; y++) {
                String value = Character.toString(minesweeper.table[x][y]);
                JLabel label = new JLabel(coveredSymbol);
                int row = x;
                int column = y;
                setBasicTableCellProperties(label);
                setTableCellAction(label, value, row, column);
                gamePanel.add(label);
                labelList[y] = label;
            }
            table[x] = labelList;
        }
    }

    /**
     * Reveals all cells
     * @param isWinner Gets win state
     */
    private void revealAll(Boolean isWinner) {
        for (int x = 0; x < minesweeper.row; x++) {
            for (int y = 0; y < minesweeper.column; y++) {
                JLabel label = table[x][y];
                String value = Character.toString(minesweeper.table[x][y]);
                label.setText(value);
                if (isMine(value) && !isWinner) {
                    label.setBackground(new Color(231, 76, 60));
                } else {
                    label.setBackground(new Color(179, 195, 196));
                }
            }
        }
    }

    /**
     * Reveals empty cells
     * @param x x coordinates
     * @param y y coordinates
     */
    private void revealEmptyCells(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int i2 = -1; i2 < 2; i2++) {
                try {
                    JLabel label = table[x + i][y + i2];
                    if (isCovered(label) || isFlagged(label)) {
                        if(isFlagged(label)){
                            flags -= 1;
                            flagsPlacedInfo.setText(String.format("Flags placed: %d", flags));
                            setInfoComponentsPosition();
                        }
                        String value = Character.toString(minesweeper.table[x + i][y + i2]);
                        label.setText(value);
                        label.setBackground(new Color(179, 195, 196));
                        if (isEmpty(value)) {
                            revealEmptyCells(x + i, y + i2);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    /**
     * Shows the panel
     */
    public void show() {
        drawInfoPanel();
        drawGamePanel();
        drawSpringLayoutForGamePanel();
        gamePanel.setBackground(new Color(72, 84, 85));
        gamePanel.setOpaque(true);
        infoPanel.setBackground(new Color(72, 84, 85));
        infoPanel.setOpaque(true);
        masterPanel = new JPanel(new BorderLayout());
        masterPanel.add(infoPanel, BorderLayout.PAGE_START);
        masterPanel.add(gamePanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(masterPanel);
        frame.pack();
        frame.setResizable(false);
        new GUICommon().setFramePos(frame);
        frame.setVisible(true);
    }
}

