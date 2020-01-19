package model;

import java.util.Iterator;
import java.util.LinkedList;

import exceptions.InvalidConnectionException;
import exceptions.InvalidGameInitException;
import exceptions.InvalidMoveParsingException;
import model.Move.MoveType;

public class Game implements Cloneable {

	public enum TurnType {
		BLUE_TURN, RED_TURN
	};

	private TurnType gameTurn = TurnType.BLUE_TURN;
	private Point[][] points;
	private int bluePoints = 0, redPoints = 0;
	private final int N, M;
	private final int MAX_POINTS;
	private LinkedList<String> history = new LinkedList<>();

	public boolean end() {
		return bluePoints + redPoints == MAX_POINTS;
	}

	// M - Colls, N - Rows (x,y)[y,x]

	public Point pointAt(int x, int y) {
		return points[y][x];
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				sb.append("O");
				if (points[i][j].connectedRight())
					sb.append("-");
				else
					sb.append(" ");
			}
			sb.append('\n');
			for (int j = 0; j < M; j++) {
				if (points[i][j].connectedDown())
					sb.append("|");
				else
					sb.append(" ");
				sb.append(' ');
			}

			sb.append('\n');
		}

		return sb.toString();
	}

	public TurnType getGameTurn() {
		return gameTurn;
	}

	public Point[][] getPoints() {
		return points;
	}

	public int getBluePoints() {
		return bluePoints;
	}

	public int getRedPoints() {
		return redPoints;
	}

	public int getN() {
		return N;
	}

	public int getM() {
		return M;
	}

	public int getMAX_POINTS() {
		return MAX_POINTS;
	}

	public void setGameTurn(TurnType gameTurn) {
		this.gameTurn = gameTurn;
	}

	public void setPoints(Point[][] points) {
		this.points = points;
	}

	public void setBluePoints(int bluePoints) {
		this.bluePoints = bluePoints;
	}

	public void setRedPoints(int redPoints) {
		this.redPoints = redPoints;
	}

	public LinkedList<Move> getPossibleMoves() {

		LinkedList<Move> ret = new LinkedList<Move>();

		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++) {
				if (!points[i][j].connectedDown() && i < N - 1)
					ret.add(new Move(j, i, MoveType.DOWN));
				if (!points[i][j].connectedRight() && j < M - 1)
					ret.add(new Move(j, i, MoveType.RIGHT));
			}

		return ret;

	}

	public Game(int m_points, int n_points) throws InvalidGameInitException {

		if (n_points < 2 || m_points < 2)
			throw new InvalidGameInitException("Could not create game with params " + m_points + "," + n_points);

		this.N = n_points;
		this.M = m_points;
		this.MAX_POINTS = (m_points - 1) * (n_points - 1);

		points = new Point[N][M];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++)
				points[i][j] = new Point(j, i, points);

	}

	public int makeMove(Move toMake) throws InvalidConnectionException {

		if (toMake == null || toMake.getxPoint() >= M || toMake.getyPoint() >= N)
			throw new InvalidConnectionException("Connection " + toMake + " invalid for the given board");

		int newPoints = 0;

		switch (toMake.getMoveType()) {

		case UP:
			newPoints = pointAt(toMake.getxPoint(), toMake.getyPoint()).connectUp(
					gameTurn == TurnType.BLUE_TURN ? Point.PointBorderState.BLUE : Point.PointBorderState.RED);
			break;
		case DOWN:
			newPoints = pointAt(toMake.getxPoint(), toMake.getyPoint()).connectDown(
					gameTurn == TurnType.BLUE_TURN ? Point.PointBorderState.BLUE : Point.PointBorderState.RED);
			break;
		case LEFT:
			newPoints = pointAt(toMake.getxPoint(), toMake.getyPoint()).connectLeft(
					gameTurn == TurnType.BLUE_TURN ? Point.PointBorderState.BLUE : Point.PointBorderState.RED);
			break;
		case RIGHT:
			newPoints = pointAt(toMake.getxPoint(), toMake.getyPoint()).connectRight(
					gameTurn == TurnType.BLUE_TURN ? Point.PointBorderState.BLUE : Point.PointBorderState.RED);
			break;
		}

		this.history.addLast(toMake.toString());

		if (newPoints != 0)
			if (this.gameTurn == TurnType.BLUE_TURN)
				bluePoints += newPoints;
			else
				redPoints += newPoints;
		else
			this.gameTurn = this.gameTurn == TurnType.BLUE_TURN ? TurnType.RED_TURN : TurnType.BLUE_TURN;

		return newPoints;
	}

	public LinkedList<String> exportGame() {
		LinkedList<String> ret = new LinkedList<>();
		ret.addLast(M + "," + N);
		Iterator<String> iter = history.iterator();
		while (iter.hasNext()) {
			ret.add(iter.next());
		}
		return ret;

	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		try {
			Game myClone = new Game(M, N);
			myClone.bluePoints = bluePoints;
			myClone.redPoints = redPoints;
			myClone.gameTurn = gameTurn;
			for (int i = 0; i < N; i++)
				for (int j = 0; j < M; j++) {
					Point clonedPoint = myClone.points[i][j];
					Point motherPoint = points[i][j];

					if (!clonedPoint.connectedDown() && motherPoint.connectedDown())
						clonedPoint.connectDown(Point.PointBorderState.NONE);
					if (!clonedPoint.connectedUp() && motherPoint.connectedUp())
						clonedPoint.connectUp(Point.PointBorderState.NONE);
					if (!clonedPoint.connectedLeft() && motherPoint.connectedLeft())
						clonedPoint.connectLeft(Point.PointBorderState.NONE);
					if (!clonedPoint.connectedRight() && motherPoint.connectedRight())
						clonedPoint.connectRight(Point.PointBorderState.NONE);

					clonedPoint.setMyBorderState(motherPoint.getMyBorderState());

				}

			return myClone;

		} catch (InvalidGameInitException | InvalidConnectionException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static void main(String[] argv) throws InvalidGameInitException, CloneNotSupportedException,
			InvalidConnectionException, InvalidMoveParsingException {

		Game game_test = new Game(4, 4);

		game_test.makeMove(new Move("A0"));
		game_test.makeMove(new Move("A1"));
		game_test.makeMove(new Move("0A"));
		game_test.makeMove(new Move("A3"));
		game_test.makeMove(new Move("C3"));

		Game cloned = (Game) game_test.clone();

		cloned.makeMove(new Move("B2"));

		System.out.println(game_test);
		System.out.println(cloned);

	}

	public LinkedList<Move> getPossibleMovesAlpha() {

		LinkedList<Move> ret = new LinkedList<Move>();

		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++) {
				if (!points[i][j].connectedDown() && i < N - 1) {

					boolean leftPass = true;
					boolean rightPass = true;

					// TestLeft

					if (j > 0) {

						int count = 0;

						if (points[i][j].connectedLeft())
							count++;
						if (points[i][j - 1].connectedDown())
							count++;
						if (points[i + 1][j].connectedLeft())
							count++;

						if (count >= 2)
							leftPass = false;
					}

					// TestRight

					if (j < M - 1) {

						int count = 0;

						if (points[i][j].connectedRight())
							count++;
						if (points[i][j + 1].connectedDown())
							count++;
						if (points[i + 1][j].connectedRight())
							count++;

						if (count >= 2)
							rightPass = false;

					}

					if (leftPass && rightPass)
						ret.add(new Move(j, i, MoveType.DOWN));
				}

				if (!points[i][j].connectedRight() && j < M - 1) {

					boolean upPass = true;
					boolean downPass = true;

					// TestUp

					if (i > 0) {
						int count = 0;
						if (points[i][j].connectedUp())
							count++;
						if (points[i - 1][j].connectedRight())
							count++;
						if (points[i][j + 1].connectedUp())
							count++;
						if (count >= 2)
							upPass = false;
					}

					// TestDown
					if (i < N - 1) {
						int count = 0;
						if (points[i][j].connectedDown())
							count++;
						if (points[i + 1][j].connectedRight())
							count++;
						if (points[i][j + 1].connectedDown())
							count++;
						if (count >= 2)
							downPass = false;
					}

					if (upPass && downPass)
						ret.add(new Move(j, i, MoveType.RIGHT));
				}

			}

		return ret;

	}

	public int getChain() {

		int numOfChains = 0;

		for (int y = 0; y < N; y++) {
			for (int x = 0; x < M; x++) {
				if(longChain(x,y)) numOfChains++;
			}
		}
		
		return numOfChains;
	}
	
	public boolean longChain(int x,int y) {
		
		Point p = pointAt(x,y);
		
		//Up
		
		if(p.canGo("UU") && p.canGo("DRUUU") && !p.connectedRight() && !p.getPointUp().connectedRight() && p.getPointDown().connectedRight())
				return true;
	
		//Down
		
		if(p.canGo("DDD") && p.canGo("RDDD") && p.connectedRight() && !p.getPointDown().connectedRight() && !p.getPointDown().getPointDown().connectedRight())
			return true;
		
		//Left
		
		if(p.canGo("LL") && p.canGo("RDLLL") && !p.connectedDown() && !p.getPointLeft().connectedDown() && p.getPointRight().connectedDown())
			return true;
		
		//Right
		
		if(p.canGo("RRR") && p.canGo("DRRR") && p.connectedDown() && !p.getPointRight().connectedDown() && !p.getPointRight().getPointRight().connectedDown())
			return true;
		
		//UpLeft
		
		if(p.canGo("L") && p.canGo("DRUULL") && !p.connectedUp() && !p.connectedRight() && p.getPointDown().connectedRight())
			return true;
		
		
		//UpRight
		
		if(p.canGo("DRUR") && p.canGo("URR") && !p.connectedRight() && !p.getPointUp().getPointRight().connectedDown() && p.getPointDown().connectedRight())
			return true;
		
		

		
		//DownLeft
		
		if(p.canGo("DL") && p.canGo("RDDLL") && p.connectedRight() && !p.getPointDown().connectedDown() && !p.getPointDown().connectedRight())
			return true;
		
		//DownRight
		
		if(p.canGo("DDRR") && p.canGo("RDR") && p.connectedRight() && !p.getPointDown().connectedRight() && !p.getPointRight().getPointDown().connectedDown())
			return true;
		//LeftUp
		
		if(p.canGo("U") && p.canGo("RDLLUU") && !p.connectedLeft() && !p.connectedDown() && p.getPointRight().connectedDown())
			return true;
		
		//LeftDown
		
		

		if(p.canGo("LDD") && p.canGo("RDLD") && !p.connectedDown() && !p.getPointLeft().getPointDown().connectedRight() && p.getPointRight().connectedDown())
			return true;
		
		
		
		//RightUp
		
		if(p.canGo("RU") && p.canGo("DRRUU") && p.connectedDown() && !p.getPointRight().connectedDown() && !p.getPointRight().connectedRight())
			return true;
		
		//RightDown
		
		if(p.canGo("RRD") && p.canGo("DRD") && p.connectedDown() && !p.getPointRight().connectedDown() && !p.getPointDown().getPointRight().connectedRight())
			return true;
		
	
		return false;
	}
	
}
