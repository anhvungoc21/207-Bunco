package bunco;

import java.util.Scanner;

public class Player {
	/**
	 * Constant for the default name of a Player
	 */
	private static final String defaultName = "Computer";

	/**
	 * Private variable for storing the points this Player
	 */
	private int points;
	
	/**
	 * Private variable for storing the name of this Player
	 */
	private String name;
	
	/**
	 * Private variable for the number of games won so far
	 */
	private int gamesWon;
	
	/**
	 * Constructor for a Player object using the default name when none is specified
	 */
	public Player() {
		this.points = 0;
		this.name = defaultName;
		this.gamesWon = 0;
	}
	
	/**
	 * Constructor for a Player object using a user-input name
	 * @param name
	 */
	public Player(String name) {
		this.points = 0;
		this.name = name;
		this.gamesWon = 0;
	}
	
	/**
	 * Getter for this Player's points
	 * @return An integer of this Player's points
	 */
	public int getPoints() {
		return this.points;
	}
	
	/**
	 * Getter for this Player's name
	 * @return A string of this Player's name
	 */
	public String getName() {
		return this.name;
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
		if (!this.name.equals("Computer")) {
			System.out.printf("%s's turn to roll the dices (Press Enter to roll).", this.name);
			detectEnter();
		}
		Integer[] rolls = diceCup.roll();
		System.out.printf("%s rolled a %d, a %d, and a %d!%n", this.name, rolls[0], rolls[1], rolls[2]);
		return rolls;
	}

	
	/**
	 * Simulates playing a turn for this Player during a round
	 */
	public void playRound(DiceCup diceCup, int round) {
		boolean playing = true;
		while (playing) {
			System.out.printf("%s now has %d points!%n", this.name, this.points);
			Integer[] rolls = this.rollDiceCup(diceCup);
			
			// Case 1: Player rolls a Bunco
			if (BuncoGame.isABunco(rolls, round)) {
				this.points += BuncoGame.buncoPoints;
				playing = false;
				System.out.printf("%s rolled a Bunco and got 21 points!%n", this.name);
			
			// Case 2: Player rolls a mini Bunco
			} else if (BuncoGame.isAMiniBunco(rolls)) {
				this.points += BuncoGame.miniBuncoPoints;
				System.out.printf("%s rolled a mini Bunco and got 5 points!%n", this.name);

			// Case 3: Player rolls current target
			} else if (DiceCup.containsTarget(rolls, round)) {
				int points = DiceCup.countTarget(rolls, round);
				this.points += points;
				System.out.printf("%s rolled %d %d's and got %d points!%n", this.name, points, round, points);
			
			// Case 4: Player rolls nothing relevant
			} else {
				System.out.printf("%s didn't roll a %d!%n", this.name, round);
				playing = false;
			}
		}
		
		System.out.printf("%s's turn has ended! %s now has %d points in total!%n%n", this.name, this.name, this.points);
	}

	public void resetPoints() {
		this.points = 0;
	}
	
	public void winGame() {
		this.gamesWon++;
		System.out.printf("%s is the winner!%n", this.name);
	}
	
	public void reportGamesWon() {
		System.out.printf("%s has won %d games", this.gamesWon);
	}
}
