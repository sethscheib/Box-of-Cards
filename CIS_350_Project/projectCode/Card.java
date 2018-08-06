package projectCode;

/**
 * 
 * @author seth
 *
 */
public class Card implements Comparable<Card> {
	/**
	 * Numerical value of card.
	 */
	int value;
	/**
	 * Face letter or number of card.
	 */
	String face;
	/**
	 * Suit string.
	 */
	String suit;
	/**
	 * Map Array for card face values.
	 */
	final String[] faceMap = { "0", "2", "3", 
			"4", "5", "6", 
			"7", "8", "9", 
			"10", "J", "Q", 
			"K", "A" };
	/**
	 * Map Array for card suit strings.
	 */
	final String[] suitMap = { "HEARTS", "DIAMONDS", 
			"CLUBS", "SPADES" };

	/**
	 * Card Constructor.
	 * 
	 * @param value
	 *            Numerical value of card
	 * @param suit
	 *            Suit string
	 */
	public Card(final int value, final int suit) {
		this.value = value;
		this.face = faceMap[value - 1];
		this.suit = suitMap[suit];
	}

	@Override
	public int compareTo(final Card c) {
		if (this.value > c.value) {
			return 1;
		}
		if (this.value < c.value) {
			return -1;
		}
		return 0;
	}

	/**
	 * Card to string for printing.
	 * 
	 * @return String "value of suit"
	 */
	public String toString() {
		return this.face + " of " + this.suit;
	}

}
