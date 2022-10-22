import java.util.Scanner;

/* Author: Jonathan Baskharoun
 * Date: 02/02/2022
 * Version: Java 1.8
 * The HangedManDriver class manages program flow and 
 * object creation for a game of THE HANGED MAN (18+)
 */

public class HangedManDriver {

	// Initial setup of words and variables
	static String[] wordOptions = {"raven", "smoke", "umbra", "casket", "shadow", "journey", "machine", "euphoria", 
			"pentagon", "quagmire", "algorithm", "manifesto", "blacksmith", "downstream", "quadriceps"};

	// used later to check if all words are used
	static final int NUM_WORDS = wordOptions.length;

	public static void main(String[] args) {

		// Setting up core objects
		BookKeeper ledger = new BookKeeper(wordOptions);
		GameRound thisRound = null;
		Scanner scnr = new Scanner(System.in);

		// Setting up menu control variables
		int guessResult = 0;
		int menuChoice = 0;
		char userGuess = '-';

		// Shuffles words to a different order before looping starts
		ledger.shuffleBook(); 

		// Game header for start of game
		System.out.println("Welcome to THE HANGED MAN (18+)");
		System.out.println("(X_X) (X_X) (X_X) (X_X)");

		// Main menu loop entry point
		do {
			System.out.println();
			System.out.println("Hanged Man Main Menu");
			System.out.println("--------------------");
			System.out.println("0. Quit");

			// if program is in loop and menuChoice is 0, this is the first round
			if (menuChoice == 0) 
				System.out.println("1. New Game");
			else 
				System.out.println("1. Next Word");

			// menu selection happens here
			System.out.println();
			System.out.print("Enter a number to select an option: ");
			menuChoice = scnr.nextInt();
			System.out.println();

			// if menuChoice is 0 after scan, then quit
			if (menuChoice == 0)
				break;

			// Creating a fresh instance for each round
			thisRound = new GameRound(ledger);

			// Stop looping if out of guesses or if checkStatus changes (won/lost game)
			while( (thisRound.getGuessesRemain() > 0) && (thisRound.checkStatus() == 0) ) {

				System.out.println("Wrong guesses remaining: " + thisRound.getGuessesRemain() );
				System.out.println("Please guess a letter in the mystery word:");

				System.out.println();
				thisRound.printBoard(); // prints the current state of word with *'s
				System.out.println();

				System.out.print("Your next letter: ");
				userGuess = scnr.next().charAt(0);
				userGuess = Character.toLowerCase(userGuess); // case control

				guessResult = thisRound.checkGuess(userGuess); // get result of user's guess

				System.out.println();

				// This if/else block checks result based on return from checkGuess()

				if (guessResult == 0) { // char not found in word

					System.out.println("Your letter doesn't exist in the mystery word");
					thisRound.loseGuess();
				}
				else if (guessResult == 1) { // char found in word

					System.out.println("You've discovered a letter in the mystery word!");
				}
				else if (guessResult == 2) { // char found in past guesses

					System.out.println("You already tried that letter");
					thisRound.loseGuess();
				}

				System.out.println();

				// This if/else checks status of round for a win or loss and if found,
				// displays the final state of the word, the guesses, and updated score
				if (thisRound.checkStatus() == 1) {
					ledger.plusScore();

					System.out.print("Congratulations! You've discovered the hidden word: "
							+ thisRound.getWholeWord());

					System.out.println();
					System.out.print("Letters guessed: ");
					thisRound.printGuesses();

					System.out.print("Current score: ");
					System.out.println(ledger.getTotalScore() );
				}
				else if (thisRound.checkStatus() == 2) {
					System.out.println("Sorry, you're out of guesses and your man is hung");
					System.out.println();

					System.out.print("Final state: ");
					thisRound.printBoard();

					System.out.print("Letters guessed: ");
					thisRound.printGuesses();

					System.out.print("Current score: ");
					System.out.println(ledger.getTotalScore() );
				}

			} // end: while (getGuesses > 0 && checkStatus == 0)

			ledger.nextRound(); // increments rounds played tracker

		// End menu if user picks 0 or if roundsPlayed >= words in ledger
		} while ( (menuChoice != 0) && (ledger.getRounds() < NUM_WORDS) );

		System.out.println();

		// Only print this if game ended due to reaching end of word list
		if (ledger.getRounds() >= NUM_WORDS) {
			System.out.println("There are no more words available");
		}
		System.out.println("Thanks for playing!");
		System.out.println();
		System.out.println("Final Results");
		System.out.println("-------------");
		System.out.println("People Freed: " + ledger.getTotalScore() );
		System.out.println("People Hanged: " + (ledger.getRounds() - ledger.getTotalScore() ) );

		scnr.close();

	} // end main

}
