package bunco;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
	 * Constructor for a Player object using the default name when none is specified
	 */
	public Player() {
		this.points = 0;
		this.name = defaultName;
	}
	
	/**
	 * Constructor for a Player object using a user-input name
	 * @param name
	 */
	public Player(String name) {
		this.points = 0;
		this.name = name;
	}
	
	public void playRound() {
		
	}
	
	public int getPoints() {
		return this.points;
	}

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
	
	public int rollDice(Dice dice) {
		System.out.printf("%s's turn to roll the dice (Press Enter to roll).", this.name);
		detectEnter();
		int roll = dice.roll();
		System.out.printf("%s rolled a %d!%n", this.name, roll);
		return roll;
	}
	
	public Integer[] rollDiceCup(DiceCup diceCup) {
		System.out.printf("%s's turn to roll the dices (Press Enter to roll).", this.name);
		detectEnter();
		return diceCup.roll();
	}
}
