package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.Computer;
import control.GameController;
import control.Human;
import control.Player;
import exceptions.InvalidConnectionException;
import exceptions.InvalidGameInitException;
import exceptions.InvalidMoveParsingException;
import model.Game;
import model.Move;
import view.Board;
import view.InitMenue;
import view.InputFrame;
import view.MoveHistory;

public class GameStarter {

	public static void main(String[] argv) throws InterruptedException, InvalidGameInitException {

		Player playerBlue, playerRed;
		File initFile;
		int treeDepth = 0;
		int n = 0, m = 0;
		String RType, BType, RMode, BMode;
		boolean stepMode = false;

		InitMenue initMenue = new InitMenue();
		initMenue.setVisible(true);

		synchronized (initMenue) {
			initMenue.wait();
		}

		
		Game game = null;

		initFile = initMenue.getInitFile();
		n = initMenue.getN();
		m = initMenue.getM();
		treeDepth = initMenue.getTreeDepth();
		stepMode = initMenue.getStepMode();
		
		if (initFile != null) {
			
			try {
				String line;
				BufferedReader reader = new BufferedReader(new FileReader(initFile));
				line = reader.readLine();
				m = Integer.parseInt(line.split(",")[0]);
				n = Integer.parseInt(line.split(",")[1]);
				game = new Game(m, n);

				line = reader.readLine();
				
				while (line != null) {
				
					game.makeMove(new Move(line));
					line = reader.readLine();

				}
				reader.close();
			} catch (IOException | InvalidConnectionException | InvalidMoveParsingException | NumberFormatException e) {
				JOptionPane.showMessageDialog(new JFrame(), e, "Invalid init file", JOptionPane.ERROR_MESSAGE);
				game = null;
			}
		} else
			game = new Game(m, n);

		if (game != null) {

			RType = initMenue.getRType();
			RMode = initMenue.getRMode();
			BType = initMenue.getBType();
			BMode = initMenue.getBMode();

			
			
			initMenue.setVisible(false);

			Board board = new Board(game);
			InputFrame inFrame = new InputFrame();
			
			playerRed = RType.equals("Human") ? new Human(Game.TurnType.RED_TURN, inFrame)
					: new Computer(Game.TurnType.RED_TURN, game, RMode,inFrame,stepMode,treeDepth);
			playerBlue = BType.equals("Human") ? new Human(Game.TurnType.BLUE_TURN, inFrame)
					: new Computer(Game.TurnType.BLUE_TURN, game, BMode,inFrame,stepMode,treeDepth);
			

			MoveHistory mh = new MoveHistory();
			
			boolean drawHeuristics = (RType.equals("Computer") || BType.equals("Computer")) && stepMode;
			
			GameController controller = new GameController(playerRed, playerBlue, game, board,mh,drawHeuristics);
			
			inFrame.setGc(controller);
			
			controller.start();

			board.setVisible(true);
			inFrame.setVisible(true);

		}
	}

}
