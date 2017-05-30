import java.util.ArrayList;
import java.util.Random;


/**
 * This file is responsible for creating the gamefield.
 */
public class MinesweeperTable {

    int row;
    int column;
    int mines;
    char[][] table;
    int[][] allCoordinates;
    ArrayList<Integer> available;
    int[][] minesCoordinates;

    /**
     * The constructor of the game
     * @param row number of rows
     * @param column number of columns
     * @param mines number of mines
     */
    public MinesweeperTable(int row, int column, int mines) {
        if ((row * column) < mines) {
            throw new IllegalArgumentException(
                    "Num of mines cannot be larger than row * column!");
        }
        this.row = row;
        this.column = column;
        this.mines = mines;

        allCoordinates = new int[row * column][2];
        available = new ArrayList<>();
        minesCoordinates = new int[mines][2];
    }

    /**
     * Creates the table with empty fields only
     */
    private void createEmptyFields() {
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                table[x][y] = '0';
            }
        }
    }

    /**
     * Creates all coordinates on the table
     */
    private void generateAllCoordinates() {
        int counter = 0;
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                allCoordinates[counter][0] = x;
                allCoordinates[counter][1] = y;
                available.add(counter);
                counter++;
            }
        }
    }

    /**
     * Creates an array with mine coordinates
     */
    private void generateMinesCoordinates() {
        Random randomizer = new Random();
        for (int i = 0; i < mines; i++) {
            int randomNum = randomizer.nextInt(available.size());
            int[] coordinates = allCoordinates[available.get(randomNum)];
            minesCoordinates[i] = coordinates;
            available.remove(available.get(randomNum));
        }
    }

    /**
     * Mixing fields with mines in an array
     */
    private void createMines() {
        generateAllCoordinates();
        generateMinesCoordinates();
        for (int[] coordinate : minesCoordinates) {
            table[coordinate[0]][coordinate[1]] = '*';
        }
    }

    /**
     *Increase cell value if a mine is nearby.
     * @param x X coordinate of the field
     * @param y Y coordinate of the field
     */
    private void increaseValueOfCell(int x, int y){
        try {
            if (table[x][y] != '*') {
                int newNum = Character.getNumericValue(table[x][y]) + 1;
                String str = String.valueOf(newNum);
                table[x][y] = str.charAt(0);
            }
        }
        catch (ArrayIndexOutOfBoundsException e){}
    }

    /**
     * Sets the value of every field
     */
    private void replaceEmptyFieldsWithNums() {
        for (int[] coordinates : minesCoordinates) {
            for (int i = -1; i < 2; i++) {
                for (int i2 = -1; i2 < 2; i2++) {
                    increaseValueOfCell(coordinates[0] + i, coordinates[1] + i2);
                }

            }
        }
    }

    /**
     * Creates the table with the written methods as an array
     */
    private void createTable() {
        table = new char[row][column];
        createEmptyFields();
        createMines();
        replaceEmptyFieldsWithNums();
    }

    /**
     * Prints out the table to the console
     */
    private void printTable(){
        for (char[] row : table) {
            for (char cell : row) {
                System.out.print(cell);
                System.out.print("\t");
            }
            System.out.println("\n");
        }
    }

    /**
     * Runs the most important methods
     */
    public void doit() {
        createTable();
        printTable();
    }

}
