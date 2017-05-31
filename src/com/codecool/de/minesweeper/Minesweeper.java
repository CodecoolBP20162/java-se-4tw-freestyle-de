import java.util.ArrayList;
import java.util.Random;

/**
 * Created by eszti on 2017.05.24..
 */
public class Minesweeper {

    int row;
    int column;
    int mines;
    char[][] table;
    int[][] allCoordinates;
    ArrayList<Integer> available;
    int[][] minesCoordinates;

    public Minesweeper(int row, int column, int mines) {
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

    private void createEmptyFields() {
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                table[x][y] = '.';
            }
        }
    }

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

    private void generateMinesCoordinates() {
        Random randomizer = new Random();
        for (int i = 0; i < mines; i++) {
            int randomNum = randomizer.nextInt(available.size());
            int[] coordinates = allCoordinates[available.get(randomNum)];
            minesCoordinates[i] = coordinates;
            available.remove(available.get(randomNum));
        }
    }

    private void createMines() {
        generateAllCoordinates();
        generateMinesCoordinates();
        for (int[] coordinate : minesCoordinates) {
            table[coordinate[0]][coordinate[1]] = '*';
        }
    }

    private void replaceDotsWithZero(){
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                if (table[x][y] == '.') {
                    table[x][y] = '0';
                }
            }
        }
    }

    private void replaceZerosWithSpace(){
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                if (table[x][y] == '0') {
                    table[x][y] = ' ';
                }
            }
        }
    }

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

    private void replaceEmptyFieldsWithNums() {
        replaceDotsWithZero();
        for (int[] coordinates : minesCoordinates) {
            for (int i = -1; i < 2; i++) {
                for (int i2 = -1; i2 < 2; i2++) {
                    increaseValueOfCell(coordinates[0] + i, coordinates[1] + i2);
                }

            }
        }
    }

    private void createTable() {
        table = new char[row][column];
        createEmptyFields();
        createMines();
        replaceEmptyFieldsWithNums();
        replaceZerosWithSpace();
    }

    private void printTable(){
        for (char[] row : table) {
            for (char cell : row) {
                System.out.print(cell);
                System.out.print("\t");
            }
            System.out.println("\n");
        }
    }

    public void doit() {
        createTable();
        printTable();
    }

}
