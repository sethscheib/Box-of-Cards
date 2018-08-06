package edu.gvsu.boxofcards;


import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Andrew
 *
 */
public class Tests {

	
	@Test
	public void testNewCard() {
		Card c;
		c = new Card(14,3);
		assertEquals("A",c.face);
		assertEquals("SPADES",c.suit);
	}
	
	@Test
	public void testAllCards() {
		Card b;
		Card c;
		Card d;
		Card e;
		Card f;
		Card g;
		Card h;
		Card i;
		Card j;
		Card k;
		Card l;
		Card m;
		Card n;
		b = new Card(2,0);
		c = new Card(3,0);
		d = new Card(4,0);
		e = new Card(5,1);
		f = new Card(6,1);
		g = new Card(7,1);
		h = new Card(8,1);
		i = new Card(9,2);
		j = new Card(10,2);
		k = new Card(11,2);
		l = new Card(12,3);
		m = new Card(13,3);
		n = new Card(14,3);
		assertTrue((b.face == "2") && (b.suit == "HEARTS"));
		assertTrue((c.face == "3") && (c.suit == "HEARTS"));
		assertTrue((d.face == "4") && (d.suit == "HEARTS"));
		assertTrue((e.face == "5") && (e.suit == "DIAMONDS"));
		assertTrue((f.face == "6") && (f.suit == "DIAMONDS"));
		assertTrue((g.face == "7") && (g.suit == "DIAMONDS"));
		assertTrue((h.face == "8") && (h.suit == "DIAMONDS"));
		assertTrue((i.face == "9") && (i.suit == "CLUBS"));
		assertTrue((j.face == "10") && (j.suit == "CLUBS"));
		assertTrue((k.face == "J") && (k.suit == "CLUBS"));
		assertTrue((l.face == "Q") && (l.suit == "SPADES"));
		assertTrue((m.face == "K") && (m.suit == "SPADES"));
		assertTrue((n.face == "A") && (n.suit == "SPADES"));
	}
	
	@Test
	public void testPlayer() {
		Game g;
		g = new Game("GOFISH");
		Player p = new Player("Andrew");
		assertEquals("Andrew",p.name);
		for (int i = 0; i < 5; i++) {
			g.deal(p);
		}
		assertEquals(5,p.hand.size());
		assertEquals(47,g.deck.size());
		for (int i = 0; i<5; i++) {
			for (int j = 0; j < g.deck.size(); j++) {
				Card a = p.hand.get(i);
				Card b = g.deck.get(j);
				assertFalse((a.face == b.face) && (a.suit == b.suit));
			}
		}
		p.sortHand();
		Card previous = p.hand.get(0);
		Card next;
		for (int i = 1; i < 5; i++) {
			next = p.hand.get(i);
			assertTrue((previous.compareTo(next) == -1) || (previous.compareTo(next) == 0));
			previous = next;
		}
		
		
	}
	/*
	@Test
	public void testScore() {
		Game g = new Game("GOFISH");
		Player p = new Player("Andrew");
		p.hand.add(new Card(14,0));
		p.hand.add(new Card(14,1));
		p.hand.add(new Card(14,2));
		p.hand.add(new Card(14,3));
		p.hand.add(new Card(2,0));
		g.type.score(p);
		
		assertTrue(p.score == 1);
		
	}
	*/

	@Test
	public void testGame() {
		Game g = new Game("GOFISH");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("User"));
		players.add(new Player("Computer"));
		assertTrue(g.type.deckMax == 14);
		assertTrue(g.type.deckMin == 2);
		assertTrue(g.deck.size() == 52);
		for(Player p : players) {
			for(int i = 0; i < 5; i++)
				g.deal(p);
			p.sortHand();
		}
		g.tickGoFish(players);
		g.tickGoFish(players);
	}
	
	@Test
	public void testWin() {
		Game g = new Game("GOFISH");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("User"));
		players.add(new Player("Computer"));
		players.get(0).score = 20;
		Player winner = g.type.declareWinner(players);
		assertEquals("User", winner.name);
	}
	
}

