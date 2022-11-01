package bunco;

import java.util.Scanner;

public class Player {
	/**
	 * Constant for the default name of a Player
	 */
	public static final String defaultName = "Computer";

	/**
	 * Private variable for storing the points of this Player
	 */
	private int points;
	
	/**
	 * Private variable for storing the last round's points of this Player
	 */
	private int lastPoints;
	
	/**
	 * Private variable for storing the name of this Player
	 */
	private String name;
	
	/**
	 * Private variable for the number of games won so far
	 */
	private int gamesWon;
	
	/**
	 * Private variable for whether this Player is a Computer
	 */
	private boolean isComputer;
	
	/**
	 * Constructor for a Player object using the default name when none is specified
	 */
	public Player() {
		this.points = 0;
		this.lastPoints = 0;
		this.name = defaultName;
		this.gamesWon = 0;
		this.isComputer = true;
	}
	
	/**
	 * Constructor for a Player object using a user-input name
	 * @param name
	 */
	public Player(String name) {
		this.points = 0;
		this.name = name;
		this.gamesWon = 0;
		this.isComputer = false;
	}
	
	/**
	 * Getter for this Player's points
	 * @return An integer of this Player's points
	 */
	public int getPoints() {
		return this.points;
	}
	
	/**
	 * Getter for this Player's points last round
	 * @return An integer of this Player's points last round
	 */
	public int getLastPoints() {
		return this.lastPoints;
	}
	
	/**
	 * Get the difference between current points and last points. 
	 * Represents the points gained in one round
	 * @return An integer of the difference of current and last points
	 */
	public int getDiffPoints() {
		return this.lastPoints - this.points;
	}
	
	/**
	 * Add points to a player after a round.
	 * Also sets lastPoints to keep track of last round's points
	 */
	public void addRoundPoints(int points) {
		this.lastPoints = this.points;
		this.points += points;
	}
	
	/**
	 * Getter for this Player's name
	 * @return A string of this Player's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter for the number of games won
	 * @return Integer of the number of games won
	 */
	public int getGamesWon() {
		return this.gamesWon;
	}
	
	/**
	 * Helper function for detecting when user hits Enter
	 */
	private static void detectEnter() {
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '\n') {
				scan.close();
				break;
			}
		}
	}
	
	/**
	 * Emulate this Player rolling a cup of dice
	 * @param diceCup The dice cup to be rolled
	 * @return An integer array of values of the rolled dices
	 */
	public Integer[] rollDiceCup(DiceCup diceCup) {
		if (!this.isComputer) {
			System.out.printf("%s's turn to roll the dices (Press Enter to roll).", this.name);
			detectEnter();
		}
		Integer[] rolls = diceCup.roll();
		System.out.printf("%s rolled a %d, a %d, and a %d!%n", this.name, rolls[0], rolls[1], rolls[2]);
		return rolls;
	}

	/**
	 * Simulates playing a turn for this Player during a round
	 * @param diceCup The dice cup to be rolled
	 * @param round THe current round number
	 * @return A boolean value of whether a Bunco is acquired
	 */
	public boolean playRound(DiceCup diceCup, int round) {
		boolean playing = true;
		boolean bunco = false;
		int roundPoints = 0;
		
		while (playing) {
			System.out.printf("%s now has %d points!%n", this.name, this.points + roundPoints);
			Integer[] rolls = this.rollDiceCup(diceCup);
			
			// Case 1: Player rolls a Bunco
			if (BuncoGame.isABunco(rolls, round)) {
				roundPoints += BuncoGame.buncoPoints;
				System.out.printf("%s rolled a Bunco and got 21 points!%n", this.name);
				playing = false;
				bunco = true;
			
			// Case 2: Player rolls a mini Bunco
			} else if (BuncoGame.isAMiniBunco(rolls)) {
				roundPoints += BuncoGame.miniBuncoPoints;
				System.out.printf("%s rolled a mini Bunco and got 5 points!%n", this.name);

			// Case 3: Player rolls current target
			} else if (DiceCup.containsTarget(rolls, round)) {
				int points = DiceCup.countTarget(rolls, round);
				roundPoints += points;
				System.out.printf("%s rolled %d %d's and got %d points!%n", this.name, points, round, points);
			
			// Case 4: Player rolls nothing relevant
			} else {
				System.out.printf("%s didn't roll a %d!%n", this.name, round);
				playing = false;
			}
			
			System.out.println();
		}
		
		// Add points officially
		this.points += roundPoints;
		System.out.printf("%s gained %d points this round!%n", this.name, roundPoints);
		
		// Print points at the end of round
		if (bunco) {
			System.out.printf("%s got a Bunco and Round %d is over! %s now has %d points in total!%n-----%n%n", this.name, round, this.name, this.points);
			return true;
		} else {
			System.out.printf("%s's turn has ended! %s now has %d points in total!%n-----%n%n", this.name, this.name, this.points);
			return false;
		}
	}

	/**
	 * Resets the points of this Player to 0
	 */
	public void resetPoints() {
		this.points = 0;
	}
 	
	/**
	 * Simulate winning a game by incrementing gamesWon
	 */
	public void winGame() {
		this.gamesWon++;
	}
	
	/**
	 * Prints a formatted score report for this player
	 */
	void reportScore() {
		System.out.printf("- %s has %d points%n", this.name, this.points);
	}
}
