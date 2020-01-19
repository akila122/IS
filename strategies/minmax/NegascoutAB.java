package strategies.minmax;

import java.util.Iterator;
import java.util.LinkedList;

import control.Player;
import exceptions.InvalidConnectionException;
import model.Game;
import model.Move;
import strategies.minmax.MinMaxAB.MinMaxResult;

public class NegascoutAB {
	
	

	public static class NegascoutResult{
		public NegascoutResult(int bestScore, Move bestMove) {
			this.score = bestScore;
			this.move = bestMove;
		}
		public int getScore() {
			return score;
		}
		public Move getMove() {
			return move;
		}
		public void setScore(int bestScore) {
			this.score = bestScore;
		}
		public void setMove(Move bestMove) {
			this.move = bestMove;
		}
		private int score;
		private Move move;
		
		
	}
	
	public static NegascoutResult resolve(Game game,Player player,int maxDepth,int currDepth,int alpha,int beta) {
		
		
		if(game.end() || currDepth == maxDepth)
			return new NegascoutResult(evaluate(game,player),null);
		
		NegascoutResult bestResult = new NegascoutResult(
						Integer.MIN_VALUE,
						null
						);
		
		Integer adaptiveBeta = beta;
		
		LinkedList<Move> moves = game.getPossibleMovesAlpha();
		if(moves.size()==0)
			moves = game.getPossibleMoves();
		
		for(Move move : moves) {
			try {
				Game newGame = (Game) game.clone();
				newGame.makeMove(move);
				NegascoutResult currResult = resolve(newGame,player,maxDepth,currDepth+1,-adaptiveBeta,-(alpha > bestResult.getScore() ? alpha : bestResult.getScore()));
			
				currResult.setMove(move);
				currResult.setScore(-currResult.getScore());
				
				
				if(currResult.getScore() > bestResult.getScore()) {
					if(adaptiveBeta == beta || currDepth >= maxDepth-2) {
						bestResult = currResult;
					}
					else {
						NegascoutResult test = resolve(newGame,player,maxDepth,currDepth+1,-beta,-currResult.getScore());
						bestResult.setMove(test.getMove());
						bestResult.setScore(-test.getScore());
						
						if(bestResult.getScore() >= beta)
							return bestResult;
						
						adaptiveBeta = (alpha > bestResult.getScore() ? alpha  : bestResult.getScore()) + 1; 
					}
				}
				
			} catch (CloneNotSupportedException | InvalidConnectionException e) {
				e.printStackTrace();
			}
			
		}
		
		return bestResult;
		
	}
	
	
	private static int evaluate(Game game,Player player) {
		int ret = player.getMyType() == Game.TurnType.BLUE_TURN ? game.getBluePoints() : game.getRedPoints();
		
		int chain = game.getChain();
		
		
		if(chain != 0 && (chain % 2 == 0 && player.getMyType() == Game.TurnType.BLUE_TURN) || (chain % 2 == 1 && player.getMyType() == Game.TurnType.RED_TURN))
	    ret += chain;
		
		
		return ret;
			
	}
	
	
	
	
}
