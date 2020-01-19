package strategies;

import java.util.LinkedList;

import control.Computer;
import control.Player;
import exceptions.InvalidConnectionException;
import model.Game;
import model.Move;

public class BeginnerStrategy extends Strategy {

	public BeginnerStrategy(Game myGame,Computer myPlayer) {
		super(myGame, myPlayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move makeMove() {
		
		
		LinkedList<Move> moves = myGame.getPossibleMoves();
	
		
		try {
			for(Move move : moves) {
				Game copyGame = (Game) myGame.clone();
				if(copyGame.makeMove(move)>0)
					return move;
			}
			
		} catch (CloneNotSupportedException | InvalidConnectionException e) {
			e.printStackTrace();
		}
		
		return moves.get((int) (moves.size()*Math.random()));
	}

}
