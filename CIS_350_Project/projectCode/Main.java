//CHECKSTYLE:OFF
package projectCode;

import java.util.ArrayList;

/**
 * 
 * @author Seth
 *
 */
public class Main {

	/**
	 * 
	 * @param args args
	 * @throws Exception
	 *             Bad game type Exception
	 */
	public static void main(final String[] args) throws Exception {
//		Game game = new Game("WAR");
//		ArrayList<Player> players = new ArrayList<Player>();
//		players.add(new Player("User"));
//		players.add(new Player("Computer"));
//		
//		game.war(players);
		
		
		Game game = new Game("GOFISH");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("User"));
		players.add(new Player("Computer"));
		game.goFish(players);
	}
}
//CHECKSTYLE:ON
