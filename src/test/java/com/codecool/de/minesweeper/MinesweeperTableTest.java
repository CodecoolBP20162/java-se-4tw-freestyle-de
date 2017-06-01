package com.codecool.de.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperTableTest {


    @Test
    public void testConstuctorWithZeroMines() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            MinesweeperTable table = new MinesweeperTable(10, 10, 0);
            ;
        });
        assertEquals("Num of mines must be at least 1", exception.getMessage());
    }

    @Test
    public void testConstuctorWithSmallerThanTwoColumn() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            MinesweeperTable table = new MinesweeperTable(10, 0, 10);
            ;
        });
        assertEquals("The numbers of rows and columns have to be minimum 2!", exception.getMessage());
    }

    @Test
    public void testConstuctorWithSmallerThanTwoRow() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            MinesweeperTable table = new MinesweeperTable(0, 10, 10);
        });
        assertEquals("The numbers of rows and columns have to be minimum 2!", exception.getMessage());
    }

    @Test
    public void testConstuctorWithTooMuchMines() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            MinesweeperTable table = new MinesweeperTable(10, 10, 11110);
        });
        assertEquals("Num of mines cannot be larger than row * column!", exception.getMessage());
    }

    @Test
    public void testTableSizeIfItIsGood() {
        MinesweeperTable table1 = new MinesweeperTable(10, 10, 20);
        assertEquals(table1.row, table1.table.length);
    }

    @Test
    public void testMineNumberIsGood() {
        MinesweeperTable table1 = new MinesweeperTable(10, 10, 20);
        int counter = 0;
        for (char[] row : table1.table) {
            for (char cell : row) {
                if (cell == '*') {
                    counter++;
                }
            }
        }
        assertEquals(table1.mines, counter);
    }
}