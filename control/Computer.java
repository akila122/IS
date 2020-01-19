package control;

import model.Game;
import model.Game.TurnType;
import model.Move;
import strategies.AdvancedStrategy;
import strategies.BeginnerStrategy;
import strategies.ExpertStrategy;
import strategies.Strategy;
import view.InputFrame;

public class Computer extends Player {
	
	
	protected Strategy strategy;
	protected Game game;
	protected InputFrame inFrame;
	protected boolean stepMode;
	protected int treeDepth;
	
	public int getTreeDepth() {
		return treeDepth;
	}
	
	public Computer(TurnType myType,Game game,String strategy, InputFrame inFrame, boolean stepMode,int treeDepth) {
		super(myType);
		this.treeDepth = treeDepth;
		this.game = game; 
		this.inFrame = inFrame;
		this.stepMode = stepMode;
		this.strategy = strategy.equals("Beginner") ? new BeginnerStrategy(game,this) :
						strategy.equals("Advanced") ? new AdvancedStrategy(game,this) :
													  new ExpertStrategy(game,this);
	}

	@Override
	public Move makeMove() {
		
		if(stepMode) {
			inFrame.enableButton();
			synchronized(inFrame) {
				try {
					inFrame.wait();
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
			}
			inFrame.disableButton();
		}
		
		return strategy.makeMove();
		
		
			
	}

}
