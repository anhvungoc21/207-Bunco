package bunco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BuncoGame {
	/**
	 * Constant for the starting round number in a Bunco game, 1.
	 */
	private static final int startingRound = 1;
	
	/**
	 * Constant for the number of rounds in a Bunco game, 6.
	 */
	private static final int numRounds = 6;
	
	/**
	 * Constant for the number of players if the game is singleplayer, 1.
	 */
	private static final int singlePlayerNum = 1;

	/**
	 * Constant for the number of points in a Bunco
	 */
	public static final int buncoPoints = 21;

	/**
	 * Constant for the number of points in a mini Bunco
	 */
	public static final int miniBuncoPoints = 5;
	
	/**
	 * Private variable for the players in this Bunco game
	 */
	private Player[] players;
	
	/**
	 * Private variable for the diceCup used in this game
	 */
	private DiceCup diceCup;
	
	/**
	 * Private variable for the numbering of this game
	 */
	private int gameNum;
	
	/**
	 * Constructor for a BuncoGame object
	 * @param players An array of Player objects
	 * @param diceCup The DiceCup used to roll dices
	 * @param gameNum The 
	 */
	public BuncoGame(Player[] players, DiceCup diceCup, int gameNum) {
		this.players = players;
		this.diceCup = diceCup;
		this.gameNum = gameNum;
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
	public static boolean isAMiniBunco(Integer[] rolls) {
		return isABunco(rolls, rolls[0]);
	}

	/**
	 * Checks whether an Integer array contains an integer `target`
	 * @param rolls An Integer array 
	 * @param target A target integer to be matched
	 * @return A boolean value of whether `target` is in `rolls`
	 */
	public static boolean containsTarget(Integer[] rolls, int target) {
		for (Integer roll: rolls) {
			if (roll == target) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the number of players from user
	 * @param sc Scanner object to get user input
	 * @return An integer for the number of players 
	 */
	public static int getNumPlayers(Scanner sc) {
		String regex = "[1-4]";		
		while (true) {
			System.out.print("How many players will be participating? Please choose between 1 and 4 players: ");
			String choice = sc.next();
			
			if (choice.matches(regex)) {
				return Integer.parseInt(choice);
			} else {
				System.out.printf("Invalid number of players. Please choose between 1 and 4.%n%n");
			}
		}
	}
	

	/**
	 * Helper for `getPlayerNames()`. Get a player's name from the user.
	 * @param sc Scanner object to get user input
	 * @param playerNum The index of the player to be named
	 * @param numPlayers Total number of players
	 * @return A String of the player's name
	 */
	public static String getPlayerName(Scanner sc, int playerNum, int numPlayers) {
		if (numPlayers == 1) {
			System.out.printf("Please enter your name: ");
		} else {
			System.out.printf("Please enter the name of player %d: ", playerNum);
		}

		return sc.next();
	}
	
	/**
	 * Get the players' names from user
	 * @param sc Scanner object to get user input
	 * @param numPlayers An integer for the number of players 
	 * @return A String array of player names
	 */
	public static String[] getPlayerNames(Scanner sc, int numPlayers) {
		Set<String> namesSet = new HashSet<>();

		// Single-player
		if (numPlayers == 1) {
			namesSet.add(Player.defaultName); // Prevent player from naming themselves Computer
			
			String name = getPlayerName(sc, numPlayers, numPlayers);
			while (namesSet.contains(name)) {
				System.out.printf("You cannot name yourself \"Computer\" in single-player. Please choose again.%n");
				name = getPlayerName(sc, numPlayers, numPlayers);
			}			
			
			System.out.printf("Hello, %s! You will be playing against the Computer!%n", name);
			return new String[] {name};
		
		// Multi-player
		} else {
			String[] names = new String[numPlayers];
			
			for (int i = 0; i < numPlayers; i++) {
				String thisName = getPlayerName(sc, i + 1, numPlayers);
				while (namesSet.contains(thisName)) {
					System.out.printf("There is already a player named \"%s\". Please choose again.%n", thisName);
					thisName = getPlayerName(sc, i + 1, numPlayers);
				}
				names[i] = thisName;
				namesSet.add(thisName);
			}
			return names;
		}
	}
	
	/**
	 * Arrange the players play order based on dice rolls
	 * @param sc Scanner object to get user input
	 * @param players An array of Player objects 
	 * @return `players` rearranged by dice rolls 
	 */
	public static Player[] arrangePlayers(Scanner sc, Player[] players) {
		System.out.printf("You will be rolling a dice to determine who goes first.%n%n");
		Player[] retPlayers = new Player[players.length];
		Player firstPlayer = players[0];
		DiceCup diceCup = new DiceCup();
		int highest = 0;
		
		// Each player rolls the dice
		for (int i = 0; i < players.length; i++) {
			Player thisPlayer = players[i];
			int thisRolls = DiceCup.getSum(thisPlayer.rollDiceCup(diceCup));
			System.out.printf("A total of %d!%n%n", thisRolls);
			if (thisRolls > highest) {
				highest = thisRolls;
				firstPlayer = thisPlayer;
			}
		}
		
		System.out.printf("%s rolled the highest dice! The order of play will be: ", firstPlayer.getName());
		
		// Announce order of play
		retPlayers[0] = firstPlayer;
		List<String> names = new ArrayList<>();
		names.add(firstPlayer.getName());
		
		int index = 1;
		for (Player player: players) {
			if (player == firstPlayer) continue;
			retPlayers[index] = player;
			names.add(player.getName());
			index++;
		}
		
		System.out.println(String.join(", ", names));
				
		return retPlayers;
	}
	
	/**
	 * Create players from an array of names
	 * @param names A String array of Player names
	 * @return An array of Player objects with names in `names`
	 */
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

	/**
	 * Simulate playing a round of a Bunco game
	 * @param diceCup A DiceCup object used to roll dices
	 * @param curRound The numbering of the current round
	 */
	private void playRound(DiceCup diceCup, int curRound) {
		// Each player plays their round
		boolean bunco = false;
		for (Player player: this.players) {
			if (bunco) break;
			System.out.printf("%s's turn! %n%n", player.getName());
			bunco = player.playRound(diceCup, curRound);
			
		}
		
		// Announce round winner
		System.out.printf("Round %d has ended! ", curRound);
		List<Player> roundWinners = getRoundWinners();
		if (roundWinners.size() == 1) {
			System.out.printf("%s is this round's winner!%n", roundWinners.get(0).getName());
		} else {
			String winnersStr = joinPlayerNames(roundWinners);
			System.out.printf("%s are this round's winners!%n", winnersStr);
		}
		
		// Announce current scores
		System.out.println("The current scores are:");		
		for (Player player: this.players) {
			player.reportScore();
		}
	}
	
	
	/**
	 * Simulate playing a Bunco game
	 * @param numGame The numbering of this game. The same as the number of games played.
	 * @return An array of Players who won the game
	 */
	public String play() {
		// Play game by playing each round separately in `playRound`
		int curRound = startingRound;
		while (curRound <= numRounds) {
			System.out.printf("%n----- ROUND %d -----%n", curRound);
			this.playRound(this.diceCup, curRound);
			curRound++;
		}
		
		// Announce winner(s)
		List<Player> winners = getWinners();
		String winnerStr = "";
		
		if (winners.size() == 1) {
			Player winner = winners.get(0);
			winner.winGame();
			winnerStr = winner.getName();
			System.out.printf("%n%s is this game's winner!%n", winnerStr);
		} else {
			for (Player winner: winners) {
				winner.winGame();
			}
			
			winnerStr = joinPlayerNames(winners);
			System.out.printf("%s are this game's winners!%n", winnerStr);
		}
		
		System.out.printf("%n---------- END OF GAME ----------%n%n");
		
		// Report past games and winners
		for (Player player: this.players) {
			player.resetPoints();
		}	
		
		System.out.printf("You have played %d games so far. ", gameNum);
		List<Player> pastWinners = getPastWinners();
		if (pastWinners.size() == 1) {
			System.out.printf("Currently, the player with the most games won is: %s.%n", pastWinners.get(0).getName());
		} else {
			String pastWinnersStr = joinPlayerNames(pastWinners);
			System.out.printf("Currently, the players with the most games won are: %s.%n", pastWinnersStr);
		}
		
		return winnerStr;
	}
	
	/**
	 * Get the list of players with the highest `metric`
	 * `metric` can either be "points" or "wins"
	 * Helper function for `getPastWinners` and `getWinners`
	 * @param metric A string of the desired metric
	 * @return A List of Player objects with the highest `metric`
	 */
	private List<Player> getHighestPlayerMetric(String metric) {
		int highest = (metric.equals("points")) ? this.players[0].getPoints() : this.players[0].getGamesWon();
		List<Player> winners = new ArrayList<>();
		
		for (Player player: this.players) {
			int points = (metric.equals("points")) ? player.getPoints() : player.getGamesWon();
			if (points > highest) {
				highest = points;
				winners.removeAll(winners);
				winners.add(player);
			} else if (points == highest) {
				winners.add(player);
			}
		}
		
		return winners;
	}
	
	/**
	 * Get the Players with the most number of wins so far
	 * @return A List of Players with the most number of wins so far
	 */
	private List<Player> getWinners() {
		return this.getHighestPlayerMetric("points");
	}
	
	/**
	 * Get the winners of a Bunco game or round
	 * @return A List of Players that is the winners of this Bunco game
	 */
	private List<Player> getPastWinners() {
		return this.getHighestPlayerMetric("wins");
	}
	
	/**
	 * Get the winners of a round of a bunco game
	 * @return A Player array that is the winners of this round
	 */
	private List<Player> getRoundWinners() {
		int highestDiff = this.players[0].getPoints() - this.players[0].getLastPoints();
		List<Player> roundWinners = new ArrayList<>();
		
		for (Player player: this.players) {
			int diff = player.getPoints() - player.getLastPoints();
			if (diff > highestDiff) {
				highestDiff = diff;
				roundWinners.removeAll(roundWinners);
				roundWinners.add(player);
			} else if (diff == highestDiff) {
				roundWinners.add(player);
			}
		}
		
		return roundWinners;
	}

	/**
	 * Prompt user to answer Yes or No
	 * @param sc Scanner object to get user input
	 * @param message A message to be printed prior to prompting for input
	 * @return A boolean value of Yes (true) or No (false)
	 */
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
	
	/**
	 * Ask for user's choice to play again
	 * @param sc Scanner object to get user input
	 * @return A boolean value of whether user wants to play again
	 */
	public static boolean promptReplay(Scanner sc) {
		return promptYesNo(sc, "Do you want to play again? ");
	}
	
	/**
	 * Ask for user's choice to play again with the same Players
	 * @param sc Scanner object to get user input
	 * @return A boolean value of whether user wants to play again with the same Players
	 */
	public static boolean promptPlaySame(Scanner sc) {
		return promptYesNo(sc, "Do you want to play again with the same players? ");
	}
	
	/**
	 * Prints welcome message when user enters the game
	 */
	public static void welcome() {
		System.out.println("Welcome to Bunco!");
	}
	
	/**
	 * Prints goodbye message when user leaves the game
	 */
	public static void goodbye() {
		System.out.println("Thank you for playing Bunco! See you next time!");
	}
	
	/**
	 * Join the name of a List of Players into a comma-separated string
	 * @param players List of Players
	 * @return A String of all Player names, separated by commas
	 */
	private static String joinPlayerNames(List<Player> players) {
		StringBuilder names = new StringBuilder();
		int index = 0;
		for (Player winner: players) {
			if (index == players.size() - 1) {
				names.append(winner.getName());
			} else {
				names.append(winner.getName() + ", ");
			}
			index++;
		}
		return names.toString();
	}
}
