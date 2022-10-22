
/* Author: Jonathan Baskharoun
 * Date: 02/02/2022
 * Version: Java 1.8
 * The GameInstance class manages details specific to the current round
 */

public class GameRound {

	private int guessesRemain;
	private char[] currentWord;
	private char[] guessedWord;
	private char[] lettersUsed;
	private String wholeWord;

	// Constructor
	public GameRound(BookKeeper ledger) {

		// Game always starts with 8 guesses
		guessesRemain = 8;

		// Getting a fresh word out of ledger in char[] form
		currentWord = ledger.getWordChars();

		// Create new char[] with length matching the word to be guessed
		guessedWord = new char[currentWord.length];

		// Fill second char[] with *
		for (int i = 0; i < currentWord.length; ++i) {
			guessedWord[i] = '*';
		}

		// Setting letters used to a new empty char[] size 20 because
		// max word length 10 + max wrong guesses 8 means there could only be 18 total
		lettersUsed = new char[20];

		// getting the same word but as a string for printing at the end of the game
		wholeWord = ledger.getWordString();
	}

	// prints the char[] one char at a time
	public void printBoard() {

		for (char c : guessedWord) {
			System.out.print(c + " ");
		}
		System.out.println();
	}

	// prints the guesses used so far
	public void printGuesses() {

		for (char c : lettersUsed) {
			System.out.print(c + " ");
		}
		System.out.println();
	}

	// Checks user guess and returns an int based on result
	public int checkGuess(char userGuess) {

		int guessResult = 0; // status 0 means not found

		// first check against letters used
		for (int i = 0; i < lettersUsed.length; ++i) {

			// if found in lettersUsed, update status 2
			if (lettersUsed[i] == userGuess) {
				guessResult = 2;
				return guessResult;
				// return is needed here so that duplicat isn't
				// added to lettersUsed by the next for loop
			}
		}

		// if letter wasn't found in lettersUsed it's added
		for (int i = 0; i < lettersUsed.length; ++i) {

			// looks for first null char and replaces it with guess
			if (lettersUsed[i] == '\u0000') {
				lettersUsed[i] = userGuess;
				break;
			}
		}

		// checks if char exists in the word to be guessed
		for (int i = 0; i < currentWord.length; ++i) {

			// if found in word to guess, update result to 1
			if (currentWord[i] == userGuess) {
				guessedWord[i] = userGuess;
				guessResult = 1;
			}
		}
		return guessResult;
	}

	// checks status of game
	public int checkStatus() {

		int gameStatus = 1; // 1 means win

		// if no more guesses set 2 (lose)
		if (guessesRemain == 0) {
			gameStatus = 2;
			return gameStatus;
		}

		// if user hasn't lost but there are still *, 
		// status 0 means continue round
		for (char c : guessedWord) {
			if (c == '*') {
				gameStatus = 0;
			}
		}

		// if we arrive here with guesses > 0  and no * in word,
		// gameStatus is still 1 which returns a win
		return gameStatus;
	}

	// subtracts a guess
	public void loseGuess() {
		--guessesRemain;
	}

	// Getters and Setters
	public char[] getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(char[] currentWord) {
		this.currentWord = currentWord;
	}

	public char[] getGuessedWord() {
		return guessedWord;
	}

	public void setGuessedWord(char[] guessedWord) {
		this.guessedWord = guessedWord;
	}

	public int getGuessesRemain() {
		return guessesRemain;
	}

	public void setGuessesRemain(int guessesRemain) {
		this.guessesRemain = guessesRemain;
	}

	public String getWholeWord() {
		return wholeWord;
	}

	public void setWholeWord(String wholeWord) {
		this.wholeWord = wholeWord;
	}

}
