package projectCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Seth
 * @author Andrew
 * @author Pat
 *
 */
public class Game {

	/**
	 * GameType.
	 */
	GameType type;
	/**
	 * Deck of cards for game.
	 */
	ArrayList<Card> deck;
	/**
	 * Scanner.
	 */
	Scanner scan = new Scanner(System.in);
	/**
	 * Turn tracking.
	 */
	int turn;
	/**
	 * Tracks the win of the game.
	 */
	int win;
	/**
	 * Game constructor.
	 * 
	 * @param gameName
	 *            Name of game
	 * 
	 */
	public Game(final String gameName) {
		this.deck = new ArrayList<Card>();
		this.turn = 0;
		this.win = 0;
		switch (gameName) {
		
		case "GOFISH":
			initGoFish();
			break;
		case "WAR":
			initWar();
			break;
		default:
			//how?
			break;
		}
	}

	/**
	 * Fills deck based on type of game.
	 * 
	 * @param min
	 *            minimum value for deck
	 * @param max
	 *            maximum value for deck
	 */
	private void fillDeck(final int min, final int max) {
		int suit, value;
		for (suit = 0; suit < 4; suit++) {
			for (value = min; value <= max; value++) {
				this.deck.add(new Card(value, suit));
			}
		}
		Collections.shuffle(this.deck);
	}

	/**
	 * Deal card from deck to player.
	 * 
	 * @param p
	 *            Player to be dealt
	 */
	public void deal(final Player p) {
		p.hand.add(this.deck.get(0));
		this.deck.remove(0);
	}
	
	/**
	 * 
	 * @param players
	 * 		The list of players playing the game
	 */
	public void dealGoFish(final ArrayList<Player> players) {
		for (int i = 0; i < 7; i++) {
			for (Player p :players) {
				this.deal(p);
			}
		}
	}
	
	/**
	 * 
	 * @param players
	 * 		the list of players playing the game
	 */
	public void dealWar(final ArrayList<Player> players) {
		while (this.deck.size() != 0) {
			for (Player p :players) {
				this.deal(p);
			}
		}
	}

	/**
	 * Initializes GO FISH game.
	 * 
	 */
	public void initGoFish() {
		this.type = new GameType("GOFISH", 2);
		this.fillDeck(this.type.deckMin, this.type.deckMax);
		
	}
	
	/**
	 * Initializes War game.
	 */
	public void initWar() {
		this.type = new GameType("WAR", 2);
		this.fillDeck(this.type.deckMin, this.type.deckMax);
	}
	
	/**
	 * 
	 * @param players
	 * 		The list of players playing the game.
	 */
	public void goFish(final ArrayList<Player> players) {
		this.dealGoFish(players);
		this.win = 0;
		while (win == 0) {
			tickGoFish(players);
		}
		this.type.declareWinnerGoFish(players);
	}
	
	/**
	 * Playing function for GO FISH.
	 * 
	 * @param players
	 *            ArrayList of players
	 */
	public void tickGoFish(final ArrayList<Player> players) {
		String request;
		Player user = players.get(0); // human player
		Player comp = players.get(1); // computer player
		ArrayList<Card> c = new ArrayList<Card>();
		c.clear();
		Random rand = new Random();


		if (this.turn == 0) { // user turn
			if (user.hand.size() == 0 && this.deck.size() == 0) {
				this.win = 1;
				return;
			}
			if (user.hand.size() == 0 && this.deck.size() != 0) {
				this.deal(user);
			}
			user.sortHand();
			user.printHand();
			System.out.print("Enter card request (A for Ace): ");
			request = scan.nextLine();

			for (Card r: comp.hand) {
				if (r.face.equals(request)) {
					c.add(r);
				}
			}
			if (c.size() != 0) {
				for (Card r: c) {
					System.out.println("Opponent has the "
							+ r.toString());
					user.hand.add(r);
					comp.hand.remove(r);
				}
				System.out.println("Added to hand");
			} else {
				System.out.println("Opponent does not " 
						+ "have that card. " 
						+ "Draw a card...");
				if (this.deck.size() != 0) {
					this.deal(user);
				} else {
					System.out.println("Deck Empty!");
				}
			}
			user.sortHand();
			user.printHand();
			type.goFishScore(user);
			this.turn = 1;
			System.out.println("\n");
			c.clear();
		} else { // computer turn
			if (comp.hand.size() == 0 && deck.size() == 0) {
				this.win = 1;
				return;
			}
			if (comp.hand.size() == 0 && this.deck.size() != 0) {
				this.deal(comp);
			}
			int r = rand.nextInt(comp.hand.size());
			// rand card from computer's hand
			Card rCard = comp.hand.get(r);
			System.out.println("Opponent has requested: " 
					+ rCard.face);
			
			for (Card a: user.hand) {
				if (a.face.equals(rCard.face)) {
					c.add(a);
				}
			}
			if (c.size() != 0) {
				for (Card a: c) {
					System.out.println("User has the "
							+ a.toString());
					comp.hand.add(a);
					user.hand.remove(a);
				}
				System.out.println("Added to hand");
			} else {
				System.out.println("User does not " 
						+ "have that card. " 
						+ "Draw a card...");
				if (this.deck.size() != 0) {
					this.deal(comp);
				} else {
					System.out.println("Deck Empty!");
				}
			}
		
			this.turn = 0;
			c.clear();
			type.goFishScore(comp);
			System.out.println("\n");
		}
	}
	
	/**
	 * 
	 * @param players
	 * 		The list of players playing the game.
	 */
	public void war(final ArrayList<Player> players) {
		this.dealWar(players);
		this.win = 0;
		while (win == 0) {
			tickWar(players);
		}
		this.type.declareWinnerWar(this.win);
	}
	
	/**
	 * Plays the War Game.
	 * @param players
	 * ArrayList of the players. Only 2 player game.
	 */
	public void tickWar(final ArrayList<Player> players) {
		ArrayList<Card> userBF = new ArrayList<Card>();
		ArrayList<Card> compBF = new ArrayList<Card>();
		Player user = players.get(0);
		Player comp = players.get(1);
		int tie = 1;
		
		//Add to battle field and remove from hand
		userBF.add(user.hand.get(0));
		user.hand.remove(0);
		compBF.add(comp.hand.get(0));
		comp.hand.remove(0);
		
		//Checks the cards to see who wins
		while (tie == 1) {
			System.out.println("User has " 
		+ userBF.get(userBF.size() - 1).toString());
			System.out.println("Computer has " 
		+ compBF.get(compBF.size() - 1).toString());
			Card a = userBF.get(userBF.size() - 1);
			Card b = compBF.get(compBF.size() - 1);
			switch (a.compareTo(b)) {
			case 1:
				System.out.println("User Wins Battle!");
				for (Card c: compBF) {
					user.hand.add(c);
				}
				for (Card c: userBF) {
					user.hand.add(c);
				}
				compBF.clear();
				userBF.clear();
				System.out.println("User hand size is " 
						+ user.hand.size() 
						+ "\nComputer hand size is " 
						+ comp.hand.size());
				tie = 0;
				break;
			case -1:
				System.out.println("Computer Wins Battle!");
				for (Card c: compBF) {
					comp.hand.add(c);
				}
				for (Card c: userBF) {
					comp.hand.add(c);
				}
				compBF.clear();
				userBF.clear();
				System.out.println("User hand size is " 
						+ user.hand.size() 
						+ "\nComputer hand size is " 
						+ comp.hand.size());
				tie = 0;
				break;	
			case 0:
				System.out.println("WAR!");
				for (int i = 0; i < 4; i++) {
					if (user.hand.size() != 0) {
						userBF.add(user.hand.get(0));
						user.hand.remove(0);
					}
					if (comp.hand.size() != 0) {
						compBF.add(comp.hand.get(0));
						comp.hand.remove(0);
					}
				}
				break;
			default:
				//how?
				break;
			}
		}
		this.turn++;
		if (user.hand.size() == 0) {
			win = 13;
		}
		if (comp.hand.size() == 0) {
			win = 42;
		}
		if (this.turn == 10) {
			if (user.hand.size() != 0) {
				Collections.shuffle(user.hand);
			}
			if (comp.hand.size() != 0) {
				Collections.shuffle(comp.hand);
			}
			this.turn = 0;
		}
		//This was for the text based testing
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			
		}

	}
}
