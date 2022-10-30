package bunco;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BuncoGame {
	/**
	 * Constant for the value of dices in a "mini Bunco", 1.
	 */
	private static final int miniBuncoVal = 1;
	
	/**
	 * Constant for the number of rounds in a Bunco game, 6.
	 */
	private static final int numRounds = 6;
	
	/**
	 * Constant for the starting round number in a Bunco game, 1.
	 */
	private static final int startingRound = 1;
	
	/**
	 * Constant for the number of players if the game is singleplayer, 1.
	 */
	private static final int singlePlayerNum = 1;

	private Player[] players;
	private DiceCup diceCup;
	
	public BuncoGame(Player[] players, DiceCup diceCup) {
		this.players = players;
		this.diceCup = diceCup;
	}
	
	
	/**
	 * Checks whether an array of integer represents a "Bunco"
	 * @param rolls Integer values of the rolls of dices
	 * @param target Target value to match the roll of each dice
	 * @return A boolean value of whether `rolls` represents a "Bunco"
	 */
	public static boolean isABunco(Integer[] rolls, int target) {
		for (Integer roll: rolls) {
			if (roll != target) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks whether an array of integer represents a "mini Bunco"
	 * @param rolls Integer values of the rolls of dices
	 * @param target Target value to match the roll of each dice
	 * @return A boolean value of whether `rolls` represents a "mini Bunco"
	 */
	public static boolean isAMiniBunco(Integer[] rolls, int target) {
		return isABunco(rolls, miniBuncoVal);
	}

	public static boolean containsTarget(Integer[] rolls, int target) {
		for (Integer roll: rolls) {
			if (roll == target) {
				return true;
			}
		}
		return false;
	}
	
	public static int getNumPlayers(Scanner sc) {
		String regex = "[1-4]";		
		while (true) {
			System.out.print("How many players will be participating? Please choose between 1 and 4 players: ");
			String choice = sc.next();
			
			if (choice.matches(regex)) {
				return Integer.parseInt(choice);
			} else {
				System.out.println("Invalid number of players. Please choose between 1 and 4.");
			}
		}
	}
	
	public static String getPlayerName(Scanner sc, int playerNum) {
		System.out.printf("Please enter the name of player %d: ", playerNum);
		return sc.next();
	}
	
	public static String[] getPlayerNames(Scanner sc, int numPlayers) {
		Set<String> namesSet = new HashSet<>();
		String[] names = new String[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			String thisName = getPlayerName(sc, i + 1);
			while (namesSet.contains(thisName)) {
				System.out.printf("There is already a player named \"%s\". Please choose again.%n", thisName);
				thisName = getPlayerName(sc, i + 1);
			}
			names[i] = thisName;
			namesSet.add(thisName);
		}
		return names;
	}
		
	public static Player[] arrangePlayers(Scanner sc, Player[] players) {
		Player[] retPlayers = new Player[players.length];
		Player firstPlayer = players[0];
		int highest = 0;
		Dice dice = new Dice();
		
		for (int i = 0; i < players.length; i++) {
			Player thisPlayer = players[i];
			int thisRoll = thisPlayer.rollDice(dice);
			if (thisRoll > highest) {
				highest = thisRoll;
				firstPlayer = thisPlayer;
			}
		}
		
		retPlayers[0] = firstPlayer;
		int index = 1;
		for (Player player: players) {
			if (player == firstPlayer) continue;
			retPlayers[index] = player;
			index++;
		}
		
		return retPlayers;
	}
	
	
	public static Player[] createPlayers(String[] names) {
		if (names.length == singlePlayerNum) {
			return new Player[] {new Player(), new Player(names[0])};
		} else {
			Player[] players = new Player[names.length];
			for (int i = 0; i < names.length; i++) {
				players[i] = new Player(names[i]);
			}
			return players;
		}
	}

	
	private void playRound(int curRound) {
		for (Player player: this.players) {
			player.playRound();
		}
	}
	
	public void play() {
		int curRound = startingRound;
		while (curRound <= numRounds) {
			this.playRound(curRound);
			curRound++;
		}
	}
	
	private int getWinner() {
		int highest = this.players[0].getPoints();
		int winner = 0;
		
		for (int i = 0; i < players.length; i++) {
			Player thisPlayer = players[i];
			int points = thisPlayer.getPoints();
			if (thisPlayer.getPoints() > highest) {
				highest = points;
				winner = i;
			}
		}
		
		return winner;
	}
	
	private static boolean promptYesNo(Scanner sc, String message) {
		while (true) {
			System.out.print(message);
			System.out.print("(Yes or No): ");
			String choice = sc.next().toLowerCase();
			if (choice.equals("yes")) {
				return true;
			} else if (choice.equals("no")) {
				return false;
			} else {
				System.out.println("Please choose \"Yes\" or \"No\")");
			}
		}
	}
	
	public static boolean promptReplay(Scanner sc) {
		return promptYesNo(sc, "Do you want to play again? ");
	}
	
	public static boolean promptPlaySame(Scanner sc) {
		return promptYesNo(sc, "Do you want to play again with the same players? ");
	}
	

	public static void welcome() {
		System.out.println("Welcome to Bunco!");
	}
	
	public static void goodbye() {
		System.out.println("Thank you for playing Bunco! See you next time!");
	}
}
