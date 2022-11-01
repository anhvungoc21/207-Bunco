package bunco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Name of author: Anh Vu
 * Assignment name: Assignment 4
 * Assignment due date: October 31
 * Written/online sources used: https://docs.oracle.com/javase/7/docs/api/
 * Help obtained: None
 * I confirm that the above list of sources is complete AND that I have not talked to anyone else (e.g., CSC 207 students) about the solution to this problem.
 */

public class PlayBunco {
	

	public static void main(String[] args) {
		// State variables and records
		Scanner input = new Scanner(System.in);
		boolean playing = true;
		boolean samePlayers = true;
		int gamesPlayed = 0;
		List<String> pastGameWinners = new ArrayList<>();

		BuncoGame.welcome();
	
		// Create players
		int numPlayers = BuncoGame.getNumPlayers(input);
		String[] playerNames = BuncoGame.getPlayerNames(input, numPlayers);
		Player[] players = BuncoGame.createPlayers(playerNames);
		
		while (playing) {
			
			// If user wants new Players, reset
			if (!samePlayers) {
				gamesPlayed = 0;
				numPlayers = BuncoGame.getNumPlayers(input);
				playerNames = BuncoGame.getPlayerNames(input, numPlayers);
				players = BuncoGame.createPlayers(playerNames);
				pastGameWinners.removeAll(pastGameWinners);
			}
			
			// Play game
			gamesPlayed++;
			BuncoGame game = new BuncoGame(BuncoGame.arrangePlayers(input, players), new DiceCup(), gamesPlayed);
			String winnerStr = game.play();
			
			// Print each past game's winner(s)
			int index = 1;
			pastGameWinners.add(winnerStr);
			System.out.printf("The list of past winners are: %n", gamesPlayed);
			for (String winner: pastGameWinners) {
				System.out.printf("- Game %d: %s%n", index, winner);
				index++;
			}
			System.out.println();
			
			// Ask user to replay
			if (!BuncoGame.promptReplay(input)) {
				BuncoGame.goodbye();
				playing = false;
			} else {
				samePlayers = BuncoGame.promptPlaySame(input);
			}
		}
	}
}
