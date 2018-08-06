//CHECKSTYLE:OFF
package projectCode;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testPName() {
		Player p = new Player("Andrew");
		assertEquals("Andrew",p.name);
	}
	
	@Test
	public void testHand(){
		Card a = new Card(14,3);
		Player p = new Player("Andrew");
		p.hand.add(a);
		assertTrue(p.hand.size() != 0);
		assertTrue(p.hand.get(0) == a);
		p.hand.clear();
		assertTrue(p.hand.size() == 0);
	}
	
	@Test
	public void testDiscard(){
		Card a = new Card(14,3);
		Player p = new Player("Andrew");
		p.discard.add(a);
		assertTrue(p.discard.size() != 0);
		assertTrue(p.discard.get(0) == a);
		p.discard.clear();
		assertTrue(p.discard.size() == 0);
	}
	
	@Test
	public void testPrintOrder(){
		Player p = new Player("Andrew");
		Card a = new Card(14,3);
		Card b = new Card(2,3);
		p.hand.add(b);
		p.hand.add(a);
		p.sortHand();
		assertEquals(b,p.hand.get(0));
		p.printHand();
	}

}
//CHECKSTYLE:ON
