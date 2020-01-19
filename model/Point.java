package model;

import exceptions.InvalidConnectionException;

public class Point {

	public enum PointBorderState {
		NONE, BLUE, RED
	};

	private PointBorderState myBorderState = PointBorderState.NONE;
	private Point pointUp,pointDown,pointLeft,pointRight;
	private Point[][] points;
	private int x, y;
	private int m, n;

	
	public boolean canGo(String command) {
		try {
			
			Point next = this;
			for(int i = 0; i<command.length(); i++) {
				switch(command.charAt(i)) {
				case 'U':
					next = next.pointUp;
					break;
				case'D':
					next = next.pointDown;
					break;
				case 'L':
					next = next.pointLeft;
					break;
				case 'R':
					next = next.pointRight;
					break;
				}
				if(next==null)
					return false;
			}
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public Point(int x, int y, Point[][] points) {
		this.x = x;
		this.y = y;
		this.m = points[0].length;
		this.n = points.length;
		this.points = points;
	}

	public PointBorderState getMyBorderState() {
		return myBorderState;
	}

	public void setMyBorderState(PointBorderState myBorderState) {
		this.myBorderState = myBorderState;
	}
	
	public boolean connectedUp() {
		return pointUp!=null;
	}
	public boolean connectedDown() {
		return pointDown!=null;
	}
	public Point getPointUp() {
		return pointUp;
	}

	public Point getPointDown() {
		return pointDown;
	}

	public Point getPointLeft() {
		return pointLeft;
	}

	public Point getPointRight() {
		return pointRight;
	}

	public Point[][] getPoints() {
		return points;
	}

	public void setPointUp(Point pointUp) {
		this.pointUp = pointUp;
	}

	public void setPointDown(Point pointDown) {
		this.pointDown = pointDown;
	}

	public void setPointLeft(Point pointLeft) {
		this.pointLeft = pointLeft;
	}

	public void setPointRight(Point pointRight) {
		this.pointRight = pointRight;
	}

	public void setPoints(Point[][] points) {
		this.points = points;
	}

	public boolean connectedLeft() {
		return pointLeft!=null;
	}
	public boolean connectedRight() {
		return pointRight!=null;
	}
	
	
	public int connectUp(PointBorderState playerType) throws InvalidConnectionException {
		
		if (pointUp != null || y == 0)
			throw new InvalidConnectionException("connectUp failed at (" + x + "," + y + ")");
		Point toConnect = points[y-1][x];
		pointUp = toConnect;
		toConnect.pointDown = this;
		int toRet = 0;
		if (x != m - 1) {
			try {
				Point test = pointUp.pointRight.pointDown.pointLeft.pointUp;
				pointUp.myBorderState = playerType;
				toRet++;
				
			} catch (NullPointerException e) {}
		}
		if (x != 0) {
			try {
				Point test = pointUp.pointLeft.pointDown.pointRight.pointUp;
				pointUp.pointLeft.myBorderState = playerType;
				toRet++;
			} catch (NullPointerException e) {}
		}
		
		return toRet;
	}
	
	public int connectDown(PointBorderState playerType) throws InvalidConnectionException {

		if (pointDown != null || y == n-1)
			throw new InvalidConnectionException("connectDown failed at (" + x + "," + y + ")");
		Point toConnect = points[y+1][x];
		pointDown = toConnect;
		toConnect.pointUp = this;
		int toRet = 0;
		if (x != m - 1) {
			try {
				
				Point test = pointDown.pointRight.pointUp.pointLeft.pointDown;
				this.myBorderState = playerType;
				toRet++;
			} catch (NullPointerException e) {}
		}
		if (x != 0) {
			try {
				Point test = pointDown.pointLeft.pointUp.pointRight.pointDown;
				pointDown.pointLeft.pointUp.myBorderState = playerType;
				toRet++;
			} catch (NullPointerException e) {}
		}
		
		return toRet;
	}
	
	public int connectLeft(PointBorderState playerType) throws InvalidConnectionException {

		if (pointLeft != null || x == 0)
			throw new InvalidConnectionException("connectLeft failed at (" + x + "," + y + ")");
		Point toConnect = points[y][x-1];
		pointLeft = toConnect;
		toConnect.pointRight = this;
		int toRet = 0;
		if (y != n - 1) {
			try {
				Point test = pointLeft.pointDown.pointRight.pointUp.pointLeft;
				pointLeft.myBorderState = playerType;
				toRet++;
			} catch (NullPointerException e) {}
		}
		if (y != 0) {
			try {
				Point test = pointLeft.pointUp.pointRight.pointDown.pointLeft;
				pointLeft.pointUp.myBorderState = playerType;
				toRet++;
			} catch (NullPointerException e) {}
		}
		return toRet;
	}
	
	public int connectRight(PointBorderState playerType) throws InvalidConnectionException {

		if (pointRight != null || x == m-1)
			throw new InvalidConnectionException("connectRight failed at (" + x + "," + y + ")");
		Point toConnect = points[y][x+1];
		pointRight = toConnect;
		toConnect.pointLeft = this;
		int toRet = 0;
		if (y != n - 1) {
			try {
				Point test = pointRight.pointDown.pointLeft.pointUp.pointRight;
				this.myBorderState = playerType;
				toRet++;
			} catch (NullPointerException e) {}
		}
		if (y != 0) {
			try {
				Point test = pointRight.pointUp.pointLeft.pointDown.pointRight;
				pointUp.myBorderState = playerType;
				toRet++;
			} catch (NullPointerException e) {}
		}
		return toRet;
	}
	
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
