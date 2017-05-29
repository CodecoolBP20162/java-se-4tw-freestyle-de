package main.java;

import main.java.MinesweeperTable;

public class Main {
    public static void main(String[] args) {
        MinesweeperTable ms = new MinesweeperTable(9, 9, 15);
        ms.doit();
    }
}
