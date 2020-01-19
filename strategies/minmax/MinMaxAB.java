package strategies.minmax;

import java.util.LinkedList;

import control.Player;
import exceptions.InvalidConnectionException;
import model.Game;
import model.Move;

public class MinMaxAB {
	
	

	public static class MinMaxResult{
		public MinMaxResult(int bestScore, Move bestMove) {
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
	
	public static MinMaxResult resolve(Game game,Player player,int maxDepth,int currDepth,int alpha,int beta) {
		
		
		if(game.end() || currDepth == maxDepth)
			return new MinMaxResult(evaluate(game,player),null);
		
		MinMaxResult bestResult = new MinMaxResult(
						game.getGameTurn() == player.getMyType() ? Integer.MIN_VALUE : Integer.MAX_VALUE,
						null
						);
		
		
		
		LinkedList<Move> moves = game.getPossibleMovesAlpha();
		if(moves.size()==0)
			moves = game.getPossibleMoves();
		
		
		for(Move move : moves) {
			try {
				
				
				
				Game newGame = (Game) game.clone();
				newGame.makeMove(move);
				MinMaxResult currResult = resolve(newGame,player,maxDepth,currDepth+1,alpha,beta);
				currResult.setMove(move);
				
				if(game.getGameTurn() == player.getMyType()) {
					if(currResult.getScore() > bestResult.getScore()) {
						bestResult = currResult;
						if(bestResult.getScore() >= beta)
							return bestResult;
					}
					alpha = alpha > bestResult.getScore() ? alpha : bestResult.getScore();
				}
				else {
					
					if(currResult.getScore() < bestResult.getScore()) {
						bestResult = currResult;
						if(bestResult.getScore() <= alpha)
							return bestResult;
					}
					beta = beta < bestResult.getScore() ? beta : bestResult.getScore();
					
				}
				
			} catch (CloneNotSupportedException | InvalidConnectionException e) {
				e.printStackTrace();
			}
			
		}
		
		return bestResult;
		
	}
	
	
	private static int evaluate(Game game,Player player) {
		return player.getMyType() == Game.TurnType.BLUE_TURN ? game.getBluePoints() : game.getRedPoints();
			
	}
	
	
	
	
}
