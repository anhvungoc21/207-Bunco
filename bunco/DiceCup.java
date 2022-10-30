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
}
