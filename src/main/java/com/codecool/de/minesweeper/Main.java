package com.codecool.de.minesweeper;

public class Main {

    public static void main(String[] args) {
        int row = 15;
        int column = 15;
        int mines = row * column / 5;

        MinesweeperTable minesweeper = new MinesweeperTable(row, column, mines);
        minesweeper.printTable();
        GUI gui = new GUI(minesweeper);
        gui.show();
    }
}
