package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import exceptions.InvalidConnectionException;
import model.Game;
import model.Move;
import strategies.AdvancedStrategy;
import strategies.BeginnerStrategy;
import strategies.ExpertStrategy;
import strategies.minmax.MinMaxAB;
import strategies.minmax.NegascoutAB;
import view.Board;
import view.MoveHistory;

public class GameController extends Thread {

	private Player redPlayer, bluePlayer;
	private Game game;
	private Board board;
	private MoveHistory moveHistory;
	private boolean draw;


	
	public void export() {
		
		File f = new File("D&B_export.txt");
		try {
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			for(String s : game.exportGame()) {
				fw.write(s+"\n");
			}
			JOptionPane.showMessageDialog(new JFrame(),"Exported successfully at "+f.getAbsolutePath(), "Export done",
			        JOptionPane.INFORMATION_MESSAGE);
			fw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(),e.getMessage(), "Export failed",
			        JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public GameController(Player redPlayer, Player bluePlayer, Game game, Board board,MoveHistory moveHistory,boolean drawHeuristics) {
		super();
		this.redPlayer = redPlayer;
		this.bluePlayer = bluePlayer;
		this.game = game;
		this.board = board;
		this.moveHistory = moveHistory;
		this.setDaemon(true);
		this.draw = drawHeuristics;
	}
	
	public void showHistory() {
		this.moveHistory.setVisible(true);
	}
	
	public void run() {



		while (!interrupted()) {
		
			try {
				
				
				
				if(draw &&
				  (
				  game.getGameTurn() == Game.TurnType.BLUE_TURN && bluePlayer instanceof Computer &&
				  !(((Computer)bluePlayer).getStrategy() instanceof BeginnerStrategy)
				  ||
				  game.getGameTurn() == Game.TurnType.RED_TURN && redPlayer instanceof Computer  &&
				  !(((Computer)redPlayer).getStrategy() instanceof BeginnerStrategy))
				  ) {
					
					
					
					LinkedList<Move> moves = game.getPossibleMoves();
					for(Move move : moves) {
						Game copy = (Game)game.clone();
						copy.makeMove(move);
						if(game.getGameTurn() == Game.TurnType.BLUE_TURN && bluePlayer instanceof Computer) {
							 if((((Computer)bluePlayer).getStrategy() instanceof AdvancedStrategy)) {
								 move.setHeur(MinMaxAB.evaluate(copy, bluePlayer));
							 }
							 else if((((Computer)bluePlayer).getStrategy() instanceof ExpertStrategy)) {
								 move.setHeur(NegascoutAB.evaluate(copy, bluePlayer));
							 }
						}
						else if(game.getGameTurn() == Game.TurnType.BLUE_TURN && redPlayer instanceof Computer) {
							if((((Computer)redPlayer).getStrategy() instanceof AdvancedStrategy)) {
								move.setHeur(MinMaxAB.evaluate(copy, redPlayer));
							}
							else if((((Computer)redPlayer).getStrategy() instanceof ExpertStrategy)) {
								 move.setHeur(NegascoutAB.evaluate(copy, bluePlayer));
							}
						}
					}
					
					board.setMoves(moves);
					board.repaint();
				
				}
				
				Move toMake = game.getGameTurn() == Game.TurnType.BLUE_TURN ? bluePlayer.makeMove() : redPlayer.makeMove();
		
				game.makeMove(toMake);
				moveHistory.putString(toMake.toString());
				
				board.repaint();
				board.setMoves(null);
				if(game.end()) {
					
					String msg = (game.getBluePoints() > game.getRedPoints() ? "Blue" : "Red" )+" player won!";
					if(game.getBluePoints()==game.getRedPoints())
						msg = "It's a draw!";
					JOptionPane.showMessageDialog(new JFrame(),msg, "Game Done!",
					        JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} catch (InvalidConnectionException | CloneNotSupportedException e) {
				JOptionPane.showMessageDialog(new JFrame(),e.getMessage(), "Dialog",
				        JOptionPane.ERROR_MESSAGE);
			}

		}

	}

}
