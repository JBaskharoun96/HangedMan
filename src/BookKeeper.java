import java.util.Random;

/* Author: Jonathan Baskharoun
 * Date: 02/02/2022
 * Version: Java 1.8
 * The BookKeeper class holds the word list, tracks round and scores
 */

public class BookKeeper {

	private String[] myWordList;
	private int roundsPlayed;
	private int totalScore;

	// Constructor 
	public BookKeeper(String[] wordOptions) {
		myWordList = wordOptions;
		roundsPlayed = 0;
		totalScore = 0;
	}

	// Gets the next word from String array, returns it in char[] form
	public char[] getWordChars() {

		String fromWord = null;
		char[] newWord 	= null;
		int wordLength 	= 0;

		// Using roundsPlayed as an iterator, each round gets a new word
		fromWord = myWordList[roundsPlayed];

		// Getting the length of the word to guess 
		wordLength = fromWord.length();

		// char[] same length as word to guess
		newWord = new char[wordLength];

		// placing each letter from word into the next slot of array
		for (int i = 0; i < wordLength; ++i) {
			newWord[i] = fromWord.charAt(i);
		}

		return newWord;
	}

	// gets the current word as a string
	public String getWordString() {
		return myWordList[roundsPlayed];
	}

	// increases round counter
	public void nextRound() {
		++roundsPlayed;
	}

	// increment score on round win
	public void plusScore() {
		++totalScore;
	}

	// Using Fisher-Yates shuffle to re-arrange list of words
	public void shuffleBook() {

		Random randGen = new Random();
		String temp;
		int ind2 = 0;

		// loop from (end of loop - 1) down to 0, picking a random 
		// index between 0 and current index and swapping
		for (int ind1 = myWordList.length - 1; ind1 > 0; --ind1) {
			ind2 = randGen.nextInt(ind1);

			// swap
			temp = myWordList[ind1];
			myWordList[ind1] = myWordList[ind2];
			myWordList[ind2] = temp;			
		}
	}

	// Getters and Setters
	public String[] getWordList() {
		return myWordList;
	}

	public void setWordList(String[] myWordList) {
		this.myWordList = myWordList;
	}

	public int getRounds() {
		return roundsPlayed;
	}

	public void setRounds(int roundsPlayed) {
		this.roundsPlayed = roundsPlayed;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

}
