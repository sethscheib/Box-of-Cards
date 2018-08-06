package projectCode;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author seth
 *
 */
public class GameType {
	/**
	 * Scanner.
	 */
	Scanner s;
	/**
	 * Game type name.
	 */
	String type;
	/**
	 * Number of players.
	 */
	int playerNum;
	/**
	 * Lowest value of deck.
	 */
	int deckMin;
	/**
	 * Highest value of deck.
	 */
	int deckMax;

	/**
	 * GameType Constructor.
	 * 
	 * @param gameName
	 *            Name of game
	 * @param playerNum
	 *            Number of Players
	 */
	public GameType(final String gameName, final int playerNum) {
		this.type = gameName;
		this.playerNum = playerNum;
		s = new Scanner(System.in);
		gameInit();
	}

	/**
	 * Initialize game type variables.
	 * 
	 * @throws Exception
	 *             Bad game type Exception
	 */
	private void gameInit() {
		switch (type) {
		case "GOFISH":
			this.deckMin = 2;
			this.deckMax = 14;
			break;
		case "WAR":
			this.deckMin = 2;
			this.deckMax = 14;
			break;
		default:
			//meh
		}
	}

	// TODO different game-mode logic

	/**
	 * Checks for a winner at the end of the game by comparing scores.
	 * 
	 * @param players
	 *            The ArrayList of players to check
	 * @return Returns the winning player
	 */
	public int declareWinnerGoFish(final ArrayList<Player> players) {
		if (players.get(0).score > players.get(1).score) {
			System.out.println(players.get(0).name 
					+ " WINS!!" + players.get(0).score 
					+ " - " + players.get(1).score);
			return 0;
		} else if (players.get(0).score < players.get(1).score) {
			System.out.println(players.get(1).name 
					+ " WINS!!" + players.get(1).score 
					+ " - " + players.get(0).score);
			return 1;
		} else {
			System.out.println("TIE, no body wins!!");
			return 2;
		}
	}
	
	/**
	 * 
	 * @param w
	 * 		The current win number;
	 * @return
	 * 		Returns who won.
	 */
	public int declareWinnerWar(final int w) {
		switch (w) {
		case 42:
			System.out.println("Player Wins!");
			return 0;
		case 13:
			System.out.println("Computer Wins!");
			return 1;
		default:
			return 2; //how?
		}
	}
	

	/**
	 * Increments the score in goFish game if the player has
	 * the correct amount of cards in their hand.
	 * @param p
	 * 		The player who's hand is being checked
	 */
	public void goFishScore(final Player p) {
		int count = 0;
		for (int i = 0; i < p.hand.size() - 1; i++) {
			for (int j = i + 1; j < p.hand.size(); j++) {
				if (p.hand.get(i).compareTo(p.hand.get(j)) == 0) {
					count++;
				}
			}
			if (count == 3) {
				Card ref;
				ref = p.hand.get(i);
				for (int x = 0; x < p.hand.size(); x++) {
					if (ref.compareTo(p.hand.get(x)) == 0) {
						p.discard.add(p.hand.get(x));
						p.hand.remove(x);
						x--;
					}
				}
				p.score++;
				System.out.println(p.name 
						+ " has Scored!!" 
						+ " Their Score is now " 
						+ p.score);
			}
			count = 0;
		}
	}
}
