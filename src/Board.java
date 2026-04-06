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
	
	/**
     * Attempts to place a piece in the specified column.
     * Implements Gravity by finding the lowest available row.
     * * Time Complexity: O(R) where R is the number of rows.
     * Space Complexity: O(1) as no additional structures are created.
     * * @param col The column index (0 to columns-1) chosen by the player.
     * @param symbol The player's symbol ('R' or 'Y').
     * @return boolean True if piece was placed, false if column is full/invalid.
     */
    public boolean placePiece(int col, char symbol) {
        // Check if the column within bounds
        if (col < 0 || col >= columns) {
            System.out.println("Invalid column! Choose between 0 and " + (columns - 1));
            return false;
        }

        // Implement gravity logic, start from the bottom row and move up
        for (int i = rows - 1; i >= 0; i--) {
            if (grid[i][col] == EMPTY) {
                grid[i][col] = symbol;
                return true; // Success!
            }
        }

        //If the loop finishes, the column is full
        System.out.println("Column " + col + " is full!");
        return false;
    }
	
	/**
	 * Checks if the last player to move has won the game.
	 * Time Complexity: O(1) relative to board size if we only check 
	 * around the last move, or O(R*C) for a full board scan.
	 * * @param symbol The symbol of the player to check ('R' or 'Y').
	 * @return boolean True if 4-in-a-row is found.
	*/
	public boolean checkWin(char symbol) {
    // Horizontal Check
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j <= columns - 4; j++) {
            if (grid[i][j] == symbol && grid[i][j+1] == symbol && 
                grid[i][j+2] == symbol && grid[i][j+3] == symbol) return true;
        }
    }

    // Vertical Check
    for (int i = 0; i <= rows - 4; i++) {
        for (int j = 0; j < columns; j++) {
            if (grid[i][j] == symbol && grid[i+1][j] == symbol && 
                grid[i+2][j] == symbol && grid[i+3][j] == symbol) return true;
        }
    }

    // Diagonal Check (Descending: \ )
    for (int i = 0; i <= rows - 4; i++) {
        for (int j = 0; j <= columns - 4; j++) {
            if (grid[i][j] == symbol && grid[i+1][j+1] == symbol && 
                grid[i+2][j+2] == symbol && grid[i+3][j+3] == symbol) return true;
        }
    }

    // Diagonal Check (Ascending: / )
    for (int i = 3; i < rows; i++) {
        for (int j = 0; j <= columns - 4; j++) {
            if (grid[i][j] == symbol && grid[i-1][j+1] == symbol && 
                grid[i-2][j+2] == symbol && grid[i-3][j+3] == symbol) return true;
        }
    }

    return false;
}
	
	
}