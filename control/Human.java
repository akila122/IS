package control;

import javax.swing.JOptionPane;

import exceptions.InvalidMoveParsingException;
import model.Game.TurnType;
import view.InputFrame;
import model.Move;

public class Human extends Player {

	
	InputFrame myFrame;
	
	public Human(TurnType myType,InputFrame myFrame) {
		super(myType);
		this.myFrame = myFrame;
	}

	@Override
	public Move makeMove() {
		
		synchronized(myFrame) {
			try {
				myFrame.enableAll();
				myFrame.wait();
				myFrame.disableAll();
				return new Move(myFrame.getInput());
			} catch (InterruptedException | InvalidMoveParsingException e) {
				 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		
		return null;
	}

}
