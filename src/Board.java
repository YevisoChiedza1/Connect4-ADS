/**
 * Represents the game board for Connect 4.
 * This class handles the internal state of the board using a 2D array,
 * providing methods for initialization and display.
 * * Data Structure Justification: A 2D array was selected for O(1) coordinate 
 * access and memory efficiency.
 
 */
public class Board {
    private int rows;
    private int columns;
    private char[][] grid;
    
    /** The character representation of an empty cell. */
    private static final char EMPTY = '.';

    /**
     * Constructs a new Board with specified dimensions.
     * This supports dynamic board scaling for variable game sizes.
     * * @param rows The number of rows for the board.
     * @param columns The number of columns for the board.
     */
    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new char[rows][columns];
        initializeBoard();
    }

    /**
     * Initializes the board by filling all cells with the EMPTY character.
     * Time Complexity: O(R x C) where R is rows and C is columns.
     */
    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = EMPTY;
            }
        }
    }

    /**
     * Renders the current state of the board to the command line.
     * Displays the grid with vertical separators and column indices for 
     * player reference.
     */
    public void displayBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("|" + grid[i][j]);
            }
            System.out.println("|");
        }
        
        // Print column numbers at the bottom for easy reference
        for (int j = 0; j < columns; j++) {
            System.out.print(" " + j);
        }
        System.out.println("\n");
    }
}