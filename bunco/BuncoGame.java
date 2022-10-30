package bunco;

import java.util.List;
import java.util.Scanner;

public class BuncoGame {
	/**
	 * Constant for the value of dices in a "mini Bunco", 1.
	 */
	private static final int miniBuncoVal = 1;
	
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
	
	
	private Player[] players;
	private DiceCup diceCup;
	
	public BuncoGame(Player[] players, DiceCup diceCup) {
		this.players = players;
		this.diceCup = diceCup;
	}
}
