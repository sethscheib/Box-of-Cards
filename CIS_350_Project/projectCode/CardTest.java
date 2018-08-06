//CHECKSTYLE:OFF
package projectCode;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {

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
	public void testCompare(){
		Card a = new Card(14,3);
		Card b = new Card(14,1);
		Card c = new Card(2,1);
		assertEquals(0,a.compareTo(b));
		assertEquals(1,a.compareTo(c));
		assertEquals(-1,c.compareTo(a));
	}

}
//CHECKSTYLE:ON
