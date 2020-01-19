package strategies;

import control.Computer;
import control.Player;
import exceptions.InvalidConnectionException;
import model.Game;
import model.Move;
import strategies.minmax.MinMaxAB;

public class AdvancedStrategy extends Strategy {

	
	
	
	public AdvancedStrategy(Game myGame,Computer  myPlayer) {
		super(myGame, myPlayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move makeMove() {
		
		
		for(Move move : myGame.getPossibleMoves()) {
			
			try {
				Game copyGame = (Game) myGame.clone();
				if(copyGame.makeMove(move)>0)
					return move;
			} catch (InvalidConnectionException | CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return MinMaxAB.resolve(myGame, myPlayer, myPlayer.getTreeDepth(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE).getMove();
	}

}
