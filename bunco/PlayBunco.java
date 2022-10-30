package bunco;

import java.util.Scanner;

public class PlayBunco {
	

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean playing = true;
		boolean samePlayers = true;
		
		int numPlayers = BuncoGame.getNumPlayers(input);
		String[] playerNames = BuncoGame.getPlayerNames(input, numPlayers);
		Player[] players = BuncoGame.createPlayers(playerNames);
		
		while (playing) {
			if (!samePlayers) {
				numPlayers = BuncoGame.getNumPlayers(input);
				playerNames = BuncoGame.getPlayerNames(input, numPlayers);
				players = BuncoGame.createPlayers(playerNames);
			}

			BuncoGame game = new BuncoGame(BuncoGame.arrangePlayers(input, players), new DiceCup());
			game.play();
			
			if (!BuncoGame.promptReplay(input)) {
				BuncoGame.goodbye();
				playing = false;
			} else {
				samePlayers = BuncoGame.promptPlaySame(input);
			}
		}
	}
}
