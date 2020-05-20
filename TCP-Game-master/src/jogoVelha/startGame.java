package jogoVelha;

import java.util.*;

public class startGame {
	Random rand = new Random();
	public String playerInitialize;
	
	public startGame(String playerOne, String playerTwo) {
		int value = rand.nextInt(2);
		if(value == 0) {
			this.playerInitialize = playerOne;
		}
		else {
			this.playerInitialize = playerTwo;
		}
	}
}
