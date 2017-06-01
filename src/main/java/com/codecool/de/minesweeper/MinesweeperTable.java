package com.codecool.de.minesweeper;

import java.util.Random;

public class MinesweeperTable {

    int row;
    int column;
    int mines;
    char[][] table;

    public MinesweeperTable(int row, int column, int mines) {
        if (row < 2 || column < 2) {
            throw new IllegalArgumentException(
                    "The numbers of rows and columns have to be minimum 2!");
        }
        if ((row * column) < mines) {
            throw new IllegalArgumentException(
                    "Num of mines cannot be larger than row * column!");
        }
        this.row = row;
        this.column = column;
        this.mines = mines;
        table = new char[row][column];
        createTable();
    }

    private void createEmptyFields() {
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                table[x][y] = '0';
            }
        }
    }

    private void createMines() {
        Random random = new Random();
        int numOfGeneratedMines = 0;
        while (numOfGeneratedMines < mines) {
            int randRow = random.nextInt(row);
            int randCol = random.nextInt(column);
            if (table[randRow][randCol] != '*') {
                table[randRow][randCol] = '*';
                numOfGeneratedMines++;
            }
        }
    }

    private void replaceZerosWithSpace() {
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                if (table[x][y] == '0') {
                    table[x][y] = ' ';
                }
            }
        }
    }

    private void increaseValueOfNeighborCells(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int i2 = -1; i2 < 2; i2++) {
                try {
                    if (table[x + i][y + i2] != '*') {
                        int newNum = Character.getNumericValue(table[x+i][y+i2]) + 1;
                        String str = String.valueOf(newNum);
                        table[x + i][y + i2] = str.charAt(0);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    private void createValidNumbers() {
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                if (table[x][y] == '*') {
                    increaseValueOfNeighborCells(x, y);
                }
            }
        }
    }

    private void createTable() {
        createEmptyFields();
        createMines();
        createValidNumbers();
        replaceZerosWithSpace();
    }

    public void printTable() {
        for (char[] row : table) {
            for (char cell : row) {
                System.out.print(cell);
                System.out.print("\t");
            }
            System.out.println("\n");
        }
    }

}
