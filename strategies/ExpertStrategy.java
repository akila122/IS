package strategies;

import java.util.LinkedList;

import control.Computer;
import control.Player;
import exceptions.InvalidConnectionException;
import model.Game;
import model.Move;
import strategies.minmax.NegascoutAB;

public class ExpertStrategy extends Strategy {

	public ExpertStrategy(Game myGame, Computer myPlayer) {
		super(myGame, myPlayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move makeMove() {



		LinkedList<Move> moves = myGame.getPossibleMoves();
		try {
			for (Move move : moves) {
				Game copyGame = (Game) myGame.clone();
				if (copyGame.makeMove(move) > 0)
					return move;
			}

		} catch (CloneNotSupportedException | InvalidConnectionException e) {
			e.printStackTrace();
		}

		return NegascoutAB.resolve(myGame, myPlayer, myPlayer.getTreeDepth(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE)
				.getMove();

	}
}
