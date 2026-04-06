import java.util.*;

/**
 * Main controller class for the Connect Four game.
 * Handles the game loop, user input, and menu navigation.
 */
public class ConnectFour {
	private static Scanner scanner = new Scanner(System.in);
	
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
		char currentPlayer = 'R'; // 'R' for Red, 'Y' for Yellow
    
		while (!isGameOver) {
			board.displayBoard();
			System.out.println("Player " + currentPlayer + "'s turn.");
			System.out.print("Enter column (0-6): ");
        
			int col = scanner.nextInt();
			
			if (board.placePiece(col, currentPlayer)) {
				// Check if this move won the game
				if (board.checkWin(currentPlayer)) {
					board.displayBoard();
					System.out.println("Congratulations! Player " + currentPlayer + " wins!");
					isGameOver = true;
				} else {
					// Switch players if no win
					currentPlayer = (currentPlayer == 'R') ? 'Y' : 'R';
				}
			}
		}
	}
	
	
}