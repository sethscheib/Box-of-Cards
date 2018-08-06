package projectCode;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author seth
 *
 */
public class Player {
	/**
	 * Name of Player.
	 */
	String name;
	/**
	 * List of Player's cards.
	 */
	ArrayList<Card> hand;
	/**
	 * List of Player's discard pile.
	 */
	ArrayList<Card> discard;
	/**
	 * Player's score.
	 */
	int score;

	/**
	 * Player constructor.
	 * @param name Player name
	 */
	public Player(final String name) {
		this.name = name;
		this.hand = new ArrayList<Card>();
		this.discard = new ArrayList<Card>();
	}

	/**
	 * Sorts player's hand by value.
	 */
	public void sortHand() {
		Collections.sort(hand);
	}
	

	/**
	 * Prints player's hand.
	 */
	public void printHand() {
		for (Card c : hand) {
			System.out.printf("%-15s", c.toString());
		}
		System.out.println("");
	}
}
