package strategies;

import control.Computer;
import control.Player;
import model.Game;
import model.Move;

public abstract class Strategy {

	
	protected Game myGame;
	protected Computer myPlayer;
	
	public Strategy(Game myGame,Computer myPlayer) {
		this.myGame = myGame;
		this.myPlayer = myPlayer;
	}
	
	public abstract Move makeMove();
	
}
