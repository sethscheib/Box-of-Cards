package edu.gvsu.boxofcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.xml.transform.Templates;

/**
 * 
 * @author seth
 * @author andrew
 *
 */
public class Game {

	/**
	 * GameType.
	 */
    GameType type;
    
    /**
     * 
     */
    ArrayList<Card> UserBF = new ArrayList<Card>();
    
    /**
     * 
     */
    ArrayList<Card> CompBF = new ArrayList<Card>();
    
    /**
     * 
     */
    Card warComp;

    /**
     * 
     */
    Card warUser;

    /**
     * 
     */
    int warState;

    /**
     * Turn Tracking
     */
	int turn;

    /**
     * Tracks the win of the game
     */
	int win;

    /**
     * List of moved cards
     */
	ArrayList<Card> movedCards = new ArrayList<>();

    /**
     * Deck of cards for game.
     */
	ArrayList<Card> deck;
	/**
	 * Game constructor.
	 * 
	 * @param gameName
	 *            Name of game
	 */
	public Game(final String gameName){
		this.deck = new ArrayList<Card>();
		this.turn = 0;
		this.win = 0;
		if (gameName.equals("GOFISH")) {
			startGoFish();
		} else if (gameName.equals("MEMORY")) {
            startMemory();
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
	public Card deal(final Player p) {
		Card c = this.deck.get(0);
		p.hand.add(c);
		this.deck.remove(0);
		return c;
	}

	/***********************************************************************************************
	 * GO FISH
	 **********************************************************************************************/

	/**
	 * Initializes GO FISH game.
	 */
	public void startGoFish() {
		this.type = new GameType("GOFISH", 2);
		this.fillDeck(this.type.deckMin, this.type.deckMax);
	}

    /**
     * Playing function for GO FISH.
     *
     * @param players
     *            ArrayList of players
     */
    public int tickGoFish(final ArrayList<Player> players, Card request, boolean player) {
        Player user = players.get(0); // human player
        Player comp = players.get(1); // computer player
        Random rand = new Random();
        int gameState = 0;
        //0 -- draw from deck
        //1 -- take opponent cards
        //2 -- someone won


        if (player) { // user turn
            if(user.hand.size() == 0){
                this.win = 1;
                return 2;
            }
            if(user.hand.size() == 0 && this.deck.size() != 0){
                gameState = 0;
            }
            user.sortHand();

            for (Card r: comp.hand){
                if(r.compareTo(request) == 0){
                    movedCards.add(r);
                }
            }
            if (movedCards.size() != 0){ //opponent has requested card
                for( Card r: movedCards){
                    user.hand.add(r);
                    comp.hand.remove(r);
                }
                gameState = 1;
            }else{ //opponent does not have card
                if(this.deck.size() != 0){
                    gameState = 0;
                }
            }
            user.sortHand();
        } else { // computer turn
            if(comp.hand.size() == 0){
                this.win = 1;
                return 2;
            }
            if(comp.hand.size() == 0 && this.deck.size() != 0){
                gameState = 0;
            }
            int r = rand.nextInt(comp.hand.size());
            Card rCard = comp.hand.get(r); // rand card from computer's hand

            for (Card a: user.hand){
                if(a.face.equals(rCard.face)){
                    movedCards.add(a);
                }
            }
            if (movedCards.size() != 0){
                for( Card a: movedCards){
                    comp.hand.add(a);
                    user.hand.remove(a);
                }
                gameState = 1;
            }else{
                if(this.deck.size() != 0){
                    gameState = 0;
                }else{
                    System.out.println("Deck Empty!");
                }
            }
            comp.sortHand();
        }
        return gameState;
    }

    public ArrayList<Card> getMovedCards() {
        ArrayList<Card> temp = new ArrayList<>();

        for(Card c: movedCards) {
            temp.add(c);
        }

        movedCards.clear();
        return temp;
    }

    public ArrayList<Card> checkScore(Player p) {
        ArrayList<Card> card = type.goFishScore(p);
        ArrayList<Card> temp = new ArrayList<>();

        if(card.size() > 0) p.score++;

        for(Card c: card) {
            temp.add(c);
        }

        return temp;
    }

	/***********************************************************************************************
	 * MEMORY
	 **********************************************************************************************/

	/**
	 *  Memory Board
	 */
	Card[][] board;
	/**
	 *  Random cards for Computer
	 */
	ArrayList<Card> compCards = new ArrayList<>();
	/**
	 *  Cards not playable
	 */
	ArrayList<Card> consumedCards = new ArrayList<>();

	/**
	 * Initializes MEMORY game.
	 */
	public void startMemory() {
		this.type = new GameType("MEMORY", 1);
		this.fillDeck(this.type.deckMin, this.type.deckMax);

		board = new Card[4][8];

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 8; j++) {
				board[i][j] = this.deck.get(0);
				this.deck.remove(0);
			}
		}
	}

	/**
	 * Playing function for MEMORY.
	 */
	public int tickMemory(Player player,  ArrayList<Card> c) {
	    if(player.name.compareTo("Comp") == 0) {
            c = generateComputerCards();
        } else {
            if (c.size() < 2) return 0;
        }

        if (c.get(0).value == c.get(1).value) {
	    	consumedCards.add(c.get(0));
			consumedCards.add(c.get(1));
            player.score++;

            if(consumedCards.size() == 32) return 3;

            return 1;
        }

        return 2;
	}

    /**
     * Gets the card at an index (MEMORY)
     *
     * @param i
     * @param j
     * @return
     */

	public Card getCardIndex(int i, int j) {
		return board[i][j];
	}

    /**
     * Determine if the card is playable (MEMORY)
     *
     * @param c
     * @return
     */

    private boolean cardPlayable(Card c) {
		for (Card card: consumedCards) {
			if((card.suit.compareTo(c.suit) == 0) && (card.value == c.value)) return false;
		}
    	return true;
    }

    /**
     * Generates a card list for the computer to play
     *
     * @return
     */

    private ArrayList<Card> generateComputerCards() {
        //start a random number generator with an always different seed
        Random r = new Random(System.currentTimeMillis());

        //clear the card list
        compCards.clear();

        //pick one card
        int i1 = r.nextInt(4);
        int j1= r.nextInt(8);

        //make sure card can be played
        while(!cardPlayable(board[i1][j1])) {
            i1 = r.nextInt(4);
            j1 = r.nextInt(8);
        }

        //valid card so add it to the list
        compCards.add(board[i1][j1]);

        //pick another card
        int i2 = r.nextInt(4);
        int j2 = r.nextInt(8);

        //determine if card can be played and is different from the first card
        while(!cardPlayable(board[i2][j2]) || (i1 == i2) && (j1 == j2)) {
            i2 = r.nextInt(4);
            j2 = r.nextInt(8);
        }

        //valid card so add it to the list
        compCards.add(board[i2][j2]);

        return compCards;
    }

    /**
     * Find the index of the given card
     *
     * @param c
     * @return
     */

    public int findCard(Card c) {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 8; j++) {
                if((c.suit == board[i][j].suit) &&
                   (c.value == board[i][j].value)) {

                    return (i * 10) + j;
                }
            }
        }

        return 0;
    }

    /**
     * Gets a card that the computer just played
     *
     * @param i
     * @return
     */

	public Card getCompCardList(int i) {
		return compCards.get(i);
	}

    /**************************************************************************************
     * WAR
     *************************************************************************************/

	/**
	 * Initializes War game.
	 */
	public void initWar(ArrayList<Player> players) {
        this.warState = 0;
		this.type = new GameType("WAR", 2);
        this.fillDeck(this.type.deckMin, this.type.deckMax);
        this.dealWar(players);
	}

    public void dealWar(ArrayList<Player> players){
        while(this.deck.size() != 0){
            for(Player p :players){
                this.deal(p);
            }
        }
    }

    public Card getCompCardWar(){
        return this.warComp;
    }

    public Card getUserCardWar(){
        return this.warUser;
    }
    /**
     * 
     */
    public int tickWar(ArrayList<Player> players){
		Player User = players.get(0);
		Player Comp = players.get(1);
		

        this.UserBF.add(User.hand.get(0));
        this.warUser = UserBF.get(this.UserBF.size()-1);
        User.hand.remove(0);
        this.CompBF.add(Comp.hand.get(0));
        this.warComp = CompBF.get(this.CompBF.size()-1);
        Comp.hand.remove(0);


        int b = this.UserBF.get(this.UserBF.size()-1).compareTo(this.CompBF.get(this.CompBF.size()-1));

        switch(b){
            case 1:
                for(Card c: this.CompBF){
                    User.hand.add(c);
                }
                for(Card c: this.UserBF){
                    User.hand.add(c);
                }
                this.CompBF.clear();
                this.UserBF.clear();
                this.turn++;
                break;

            case -1:
                for(Card c: this.CompBF){
                    Comp.hand.add(c);
                }
                for(Card c: this.UserBF){
                    Comp.hand.add(c);
                }
                this.CompBF.clear();
                this.UserBF.clear();
                this.turn++;
                break;

            case 0:
                for(int i = 0; i < 3; i++){
                    if(User.hand.size() != 0){
                        this.UserBF.add(User.hand.get(0));
                        User.hand.remove(0);
                    }
                    if(Comp.hand.size() != 0){
                        this.CompBF.add(Comp.hand.get(0));
                        Comp.hand.remove(0);
                    }
                }
                break;
            default:
                //how
        }

		if(User.hand.size() == 0){
			win = 13;
		}

		if(Comp.hand.size() == 0){
			win = 42;
		}

		if(this.turn == 10){
			if(User.hand.size() != 0){
				Collections.shuffle(User.hand);
			}
			if(Comp.hand.size() != 0){
				Collections.shuffle(Comp.hand);
			}
			this.turn = 0;
        }

        return b;
	}
}
