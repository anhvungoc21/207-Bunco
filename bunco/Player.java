package bunco;

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
}
