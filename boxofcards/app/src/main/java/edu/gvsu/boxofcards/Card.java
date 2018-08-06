package edu.gvsu.boxofcards;

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
	 * Image ID for card face
	 */
	int image;
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
		this.image = getCardImage();
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

	/**
	 * Returns a card int value from 0 - 51.
	 * @return cardInt value from 0 - 51
	 */
	public int getCardInt() {
		int i = this.value;
		int j;
		String s = this.suit;

		if (value == 14) i = 1;

		i--;

		if(s.equals(suitMap[0])) {
			j = 0;
		}
		else if (s.equals(suitMap[1])) {
			j = 1;
		}
		else if (s.equals(suitMap[2])) {
			j = 2;
		}
		else {
			j = 3;
		}

		return i + (j * 13);
	}

	/**
	 * Returns image to display for card.
	 *
	 * @return imageID Image ID for display
	 */
	public int getCardImage() {
		int s = 5;
		for(int i = 0; i<4; i++) {
			if(this.suit.equals(suitMap[i])) {
				s = i;
				break;
			}
		}

		switch(s) {
			case 0:
				switch(this.value) {
					case 2:
						return R.drawable.h2;

					case 3:
						return R.drawable.h3;

					case 4:
						return R.drawable.h4;

					case 5:
						return R.drawable.h5;

					case 6:
						return R.drawable.h6;

					case 7:
						return R.drawable.h7;

					case 8:
						return R.drawable.h8;

					case 9:
						return R.drawable.h9;

					case 10:
						return R.drawable.h10;

					case 11:
						return R.drawable.h11;

					case 12:
						return R.drawable.h12;

					case 13:
						return R.drawable.h13;

					case 14:
						return R.drawable.h1;

				}

			case 1:
				switch(this.value) {
					case 2:
						return R.drawable.d2;

					case 3:
						return R.drawable.d3;

					case 4:
						return R.drawable.d4;

					case 5:
						return R.drawable.d5;

					case 6:
						return R.drawable.d6;

					case 7:
						return R.drawable.d7;

					case 8:
						return R.drawable.d8;

					case 9:
						return R.drawable.d9;

					case 10:
						return R.drawable.d10;

					case 11:
						return R.drawable.d11;

					case 12:
						return R.drawable.d12;

					case 13:
						return R.drawable.d13;

					case 14:
						return R.drawable.d1;

				}

			case 2:
				switch(this.value) {
					case 2:
						return R.drawable.c2;

					case 3:
						return R.drawable.c3;

					case 4:
						return R.drawable.c4;

					case 5:
						return R.drawable.c5;

					case 6:
						return R.drawable.c6;

					case 7:
						return R.drawable.c7;

					case 8:
						return R.drawable.c8;

					case 9:
						return R.drawable.c9;

					case 10:
						return R.drawable.c10;

					case 11:
						return R.drawable.c11;

					case 12:
						return R.drawable.c12;

					case 13:
						return R.drawable.c13;

					case 14:
						return R.drawable.c1;

				}

			case 3:
				switch(this.value) {
					case 2:
						return R.drawable.s2;

					case 3:
						return R.drawable.s3;

					case 4:
						return R.drawable.s4;

					case 5:
						return R.drawable.s5;

					case 6:
						return R.drawable.s6;

					case 7:
						return R.drawable.s7;

					case 8:
						return R.drawable.s8;

					case 9:
						return R.drawable.s9;

					case 10:
						return R.drawable.s10;

					case 11:
						return R.drawable.s11;

					case 12:
						return R.drawable.s12;

					case 13:
						return R.drawable.s13;

					case 14:
						return R.drawable.s1;

				}
		}
		return 0;
	}
}
