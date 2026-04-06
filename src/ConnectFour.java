import java.util.Scanner;
import java.util.Stack;


/**
 * Main controller class for the Connect Four game.
 * Handles the game loop, user input, and menu navigation.
 */
public class ConnectFour {
	private static Scanner scanner = new Scanner(System.in);
	
	// This stack will store the history of columns played
    private static Stack<Integer> moveHistory = new Stack<>();
	
	// History of moves undone (to allow Redo)
	private static Stack<Integer> redoStack = new Stack<>();
	
	
    public static void main(String[] args) {
		printWelcomeMessage();
		int choice = showMenu();
		
		//Confirm the game mode selection
		String modeName = "";
		switch(choice) {
			case 1: modeName = "Player vs Player"; break;
			case 2: modeName = "Player vs CPU (Easy)"; break;
			case 3: modeName = "Player vs CPU (Medium)"; break;
			case 4: modeName = "Player vs CPU (Hard)"; break;
			default: modeName = "Custom Mode"; break;
		}
		
		System.out.println("\n>>> " + modeName + " selected. Starting game now...");
		
		// Implement a 3 second countdown
		try {
			for (int i = 3; i > 0; i--) {
				System.out.print("Starting in " + i + "...\n");
				Thread.sleep(1000); // Wait for 1 second (1000 milliseconds)
			}
			System.out.println("GO!\n");
		} catch (InterruptedException e) {
			System.err.println("Countdown interrupted.");
		}

		Board gameBoard = new Board(6, 7);
		startGame(gameBoard);
	}

	/**
     * Displays an ASCII welcome message
     * 
     */
    public static void printWelcomeMessage() {
        System.out.println("====================================================");
        System.out.println("  _____                               _     _  _   ");
        System.out.println(" / ____|                             | |   | || |  ");
        System.out.println("| |     ___  _ __  _ __   ___  ___  | |_  | || |_ ");
        System.out.println("| |    / _ \\| '_ \\| '_ \\ / _ \\/ __| | __| |__   _|");
        System.out.println("| |___| (_) | | | | | | |  __/ (__  | |_     | |  ");
        System.out.println(" \\_____\\___/|_| |_|_| |_|\\___|\\___|  \\__|    |_|  ");
        System.out.println("====================================================");
        System.out.println("                Welcome to Connect 4                 ");
        System.out.println("====================================================\n");
    }
	
	/**
     * Displays the main menu and captures the user's game mode selection.
     * @return int The selected menu option.
     */
    public static int showMenu() {
        System.out.println("MAIN MENU");
        System.out.println("1. Player vs Player");
        System.out.println("2. Player vs CPU (Easy)");
        System.out.println("3. Player vs CPU (Medium)");
        System.out.println("4. Player vs CPU (Hard)");
        System.out.print("Enter choice (1-4): ");
        
        return scanner.nextInt();
    }
	
	/**
	* Main game loop implementation.
	* Manages turn-taking and interaction between players.
	*/
	public static void startGame(Board board) {
		boolean isGameOver = false;
		char currentPlayer = 'R'; 

		while (!isGameOver) {
			board.displayBoard();
			
			// Display the action bar of the game
			System.out.println(GameSettings.CYAN + " [U] Undo ↺ " + GameSettings.RESET + " | " + 
							   GameSettings.YELLOW + " [R] Redo ↻ " + GameSettings.RESET + " | " + 
							   " [0-6] Drop Piece");
			
			// Implmenet Color-coded Player Prompt
			String color = (currentPlayer == 'R') ? GameSettings.RED : GameSettings.YELLOW;
			System.out.print("Player " + color + currentPlayer + GameSettings.RESET + " > ");
			
			String input = scanner.next().toUpperCase();

			// Handle the UNDO command
			if (input.equals("U")) {
				if (!moveHistory.isEmpty()) {
					int lastCol = moveHistory.pop();
					redoStack.push(lastCol);
					board.undoMove(lastCol);
					currentPlayer = (currentPlayer == 'R') ? 'Y' : 'R';
					System.out.println(GameSettings.GREEN + ">>> Undo successful." + GameSettings.RESET);
				} else {
					System.out.println(GameSettings.RED + ">>> Nothing to undo!" + GameSettings.RESET);
				}
				continue;
			}

			// 4. Handle the REDO command
			if (input.equals("R")) {
				if (!redoStack.isEmpty()) {
					int redoCol = redoStack.pop();
					board.placePiece(redoCol, currentPlayer);
					moveHistory.push(redoCol);
					currentPlayer = (currentPlayer == 'R') ? 'Y' : 'R';
					System.out.println(GameSettings.GREEN + ">>> Redo successful." + GameSettings.RESET);
				} else {
					System.out.println(GameSettings.RED + ">>> Nothing to redo!" + GameSettings.RESET);
				}
				continue;
			}

			// Handle Column Placement
			try {
				int col = Integer.parseInt(input);
				if (board.placePiece(col, currentPlayer)) {
					moveHistory.push(col);
					redoStack.clear(); // Clear redo history on a new move
					
					if (board.checkWin(currentPlayer)) {
						board.displayBoard();
						System.out.println(GameSettings.GREEN + "★ CONGRATULATIONS! Player " + currentPlayer + " WINS! ★" + GameSettings.RESET);
						isGameOver = true;
					} else if (board.isFull()) {
						board.displayBoard();
						System.out.println(GameSettings.YELLOW + "DRAW! The board is full." + GameSettings.RESET);
						isGameOver = true;
					} else {
						currentPlayer = (currentPlayer == 'R') ? 'Y' : 'R';
					}
				} else {
					System.out.println(GameSettings.RED + ">>> Column full or invalid!" + GameSettings.RESET);
				}
			} catch (NumberFormatException e) {
				System.out.println(GameSettings.RED + ">>> Error: Enter 0-6, U, or R." + GameSettings.RESET);
			}
		}
	}
	
	
}