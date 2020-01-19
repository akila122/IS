package model;

import exceptions.InvalidMoveParsingException;

public class Move {

	public enum MoveType {DOWN, UP, LEFT, RIGHT};
	
	private int xPoint,yPoint;
	public int getxPoint() {
		return xPoint;
	}
	public int getyPoint() {
		return yPoint;
	}
	public MoveType getMoveType() {
		return moveType;
	}

	private MoveType moveType;
	
	private String command;
	
	public Move(String command) throws InvalidMoveParsingException {
		this.command = command.toLowerCase();
		if(command.length() != 2)
			throw new InvalidMoveParsingException("Failed to parse "+command);
		if(Character.isAlphabetic(command.charAt(0))) {
			yPoint = Character.getNumericValue(command.charAt(0)) - Character.getNumericValue('a');
			if(!Character.isDigit(command.charAt(1)))
				throw new InvalidMoveParsingException("Failed to parse "+command);
			xPoint = Integer.parseInt(command.charAt(1)+"");
			moveType = MoveType.DOWN;
		}
		else if(Character.isDigit(command.charAt(0))) {
			yPoint = Integer.parseInt(command.charAt(0)+"");
			if(!Character.isAlphabetic(command.charAt(1)))
				throw new InvalidMoveParsingException("Failed to parse "+command);
			xPoint = Character.getNumericValue(command.charAt(1)) - Character.getNumericValue('a');
			moveType = MoveType.RIGHT;
		}
	}
	public Move(int xPoint,int yPoint,MoveType moveType) {
		this.xPoint = xPoint;
		this.yPoint = yPoint;
		this.moveType = moveType;
	
		this.command = moveType == MoveType.DOWN ? "" + (char)(yPoint + 'a') + (xPoint + "") :
														(yPoint + "") + (char)(xPoint + 'a'); 
	}
	
	
	
	public String toString() {
		return this.command;
	}        
	
	public String testString() {
		return this.xPoint + "," + this.yPoint + "|" + this.moveType.toString();
	}
                          	       
	public static void main(String[] argv) {
		
		System.out.println(new Move(2,1,MoveType.DOWN));
	}
}
 