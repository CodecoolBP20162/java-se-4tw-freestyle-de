package com.codecool.de.minesweeper;

/**
 * Created by eszti on 2017.05.29..
 */
public class Main {

    public static void main(String[] args) {
        int row = 15;
        int column = 15;
        int mines = row * column / 5;

        Minesweeper minesweeper = new Minesweeper(row, column, mines);
        minesweeper.doit();
        GUI gui = new GUI(minesweeper);
        gui.show();
    }
}
