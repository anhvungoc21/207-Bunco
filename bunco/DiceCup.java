package bunco;

public class DiceCup {
	/**
	 * Constant variable for the number of dices in a dice cup
	 */
	private static final int numDices = 3;
	
	/**
	 * Private variable to store Dice objects in a dice cup
	 */
	private Dice[] dices;
	
	/**
	 * Constructor for a DiceCup object
	 */
	public DiceCup() {
		this.dices = new Dice[] {new Dice(), new Dice(), new Dice()};
	}
	
	/**
	 * Simulate rolling all dices in a dice cup
	 * @return An integer array of the values of all dices in this DiceCup
	 */
	public Integer[] roll() {
		Integer[] rolls = new Integer[numDices];
		for (int i = 0; i < numDices; i++) {
			rolls[i] = this.dices[i].roll();
		}
		return rolls;
	}
	
	/**
	 * 
	 * @param rolls
	 * @return
	 */
	public static int getSum(Integer[] rolls) {
		int mySum = 0;
		for (Integer roll: rolls) {
			mySum += roll;
		}
		return mySum;
	}
	
	/**
	 * Checks whether an integer `target` is in an integer array `rolls`
	 * @param rolls Integer array
	 * @param target Target integer to be matched
	 * @return Boolean value of whether `target` is in `rolls` 
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
	 * Count the number of occurences of an integer `target` in an integer array `rolls`
	 * @param rolls Integer array 
	 * @param target Target integer to be matched
	 * @return The number of occurences of `target` in `rolls`
	 */
	public static int countTarget(Integer[] rolls, int target) {
		int sum = 0;
		for (Integer roll: rolls) {
			if (roll == target) {
				sum += 1;
			}
		}
		return sum;
	}
}
