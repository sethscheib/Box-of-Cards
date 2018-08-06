package edu.gvsu.boxofcards;

import java.lang.reflect.Array;
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
	 * Size of hand to be dealt.
	 */
	int handSize;
	/**
	 * Lowest value of deck.
	 */
	int deckMin;
	/**
	 * Highest value of deck.
	 */
	int deckMax;
	/**
	 * Number of cards in deck.
	 */
	int deckSize;

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
	 */
	private void gameInit() {
		switch (type) {
		case "GOFISH":
			this.deckMin = 2;
			this.deckMax = 14;
			break;
		case "MEMORY":
			this.deckMin = 7;
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

	/**
	 * Checks for a winner at the end of the game by comparing scores.
	 * 
	 * @param players
	 *            The ArrayList of players to check
	 * @return Returns the winning player
	 */
	public Player declareWinner(final ArrayList<Player> players) {
		int[] a;
		a = new int[this.playerNum];
		int count = 0;
		for (Player y : players) {
			a[count++] = y.score;
		}
		int maxAt = 0;

		for (int i = 0; i < a.length; i++) {
			if (a[i] > a[maxAt]) {
				maxAt = i;
			}
		}

		return players.get(maxAt);
	}

		
	/**
	 * 
	 * @param w
	 * @return
	 */
	public int declareWinnerWar(int w){
		switch(w){
		case 42:
			System.out.println("Player Wins!");
			return 0;
		case 13:
			System.out.println("Computer Wins!");
			return 1;
		default:
			return 2;//how?
		}
	}

    /**
     * Increments the score for player in goFish
     *
     * @param p
     */
    public ArrayList<Card> goFishScore(Player p){
		ArrayList<Card> c = new ArrayList<>();

        int count = 0;
        for(int i = 0; i < p.hand.size()-1; i++){
            for(int j = i+1; j < p.hand.size(); j++){
                if(p.hand.get(i).compareTo(p.hand.get(j)) == 0){
                    count++;
                }
            }
            if(count > 0){
                Card ref;
                ref = p.hand.get(i);
                count = 0;
                for(int x = 0; x < p.hand.size(); x++){
                    if(ref.compareTo(p.hand.get(x)) == 0){
						if(count > 2) break;
						count++;
                    	c.add(p.hand.get(x));
                        p.discard.add(p.hand.get(x));
                        p.hand.remove(x);
                        x--;
                    }
                }
				p.score++;
            }
            count = 0;
        }

        return c;
    }
}
