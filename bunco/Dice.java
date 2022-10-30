package bunco;

public class Dice {	
	/**
	 * Constant for the minimum roll value of the dice, 1
	 */
	private static final int minRoll = 1;
	
	/**
	 * Constant for the maximum roll value of the dice, 6
	 */
	private static final int maxRoll = 6;
	
	public Dice() {}

	/**
	 * Simulates rolling a 6-faced dice. Returns the value of the roll.
	 * @return A random integer from 1 to 6 (inclusive)
	 */
	public int roll() {
		return (int) (Math.random() * maxRoll) + minRoll;
	}
}
