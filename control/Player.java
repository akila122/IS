package control;

import model.Game.TurnType;
import model.Move;

public abstract class Player {
	
	
	
	protected TurnType myType;
	
	public abstract Move makeMove();
	
	public Player(TurnType myType) {
		this.myType = myType;
	}
	
	public TurnType getMyType() {
		return myType;
	}
	
}