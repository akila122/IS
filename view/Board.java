package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.JFrame;

import exceptions.InvalidConnectionException;
import exceptions.InvalidGameInitException;
import model.Game;
import model.Move;
import model.Point;

public class Board extends JFrame {

	
	private Color ClrYlw = new Color(251,253,88);
	private Color ClrRed = new Color(238,48,48);
	private Color ClrBlue = new Color	(0,94,255);
	
	
	private Game myGame;
	private int n,m;
	private int BLOCK_SIZE;
	private int BOARD_WIDTH,BOARD_HEIGHT;
	private int POINT_SIZE;
	private boolean shouldDraw;
	private LinkedList<Move> moves;
	
	public Board(Game myGame){
		
		this.myGame = myGame;
		n = myGame.getN();
		m = myGame.getM();
		getContentPane().setBackground(Color.white);
		setResizable(false);
		setTitle("Dots & Boxes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		int max = n > m ? n : m;
		BLOCK_SIZE = screenSize.height/(max+1);
		
		BOARD_WIDTH = (m+1)*BLOCK_SIZE;
		BOARD_HEIGHT = (n+1)*BLOCK_SIZE;
		POINT_SIZE = BLOCK_SIZE/5;
		
		setSize(BOARD_WIDTH, BOARD_HEIGHT);
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		drawHelper(g);
		//drawGrid(g);
		drawBlocks(g);
		drawConnections(g);
		drawDots(g);
		drawScore(g);
		
		if(this.moves != null)
			drawHeuristics(g);
		
		
	}
	
	public void setShouldDraw(boolean b) {
		this.shouldDraw = b;
	}
	
	private void drawHeuristics(Graphics g) {
		
		
		Font old = g.getFont();
		Color oldC = g.getColor();
		g.setFont(new Font("Century Gothic", Font.BOLD, BLOCK_SIZE/16)); 
		g.setColor(myGame.getGameTurn() == Game.TurnType.BLUE_TURN ? ClrBlue : ClrRed);
		
		for(Move move : moves) {
			
			
			int x = (int) (BLOCK_SIZE * 1.5) - POINT_SIZE/2;
			int y = (int) (BLOCK_SIZE * 1.5) - POINT_SIZE/2;
			
			x += move.getxPoint()*BLOCK_SIZE;
			y += move.getyPoint()*BLOCK_SIZE;
			
			
			if(move.getMoveType() == Move.MoveType.RIGHT)
				x += BLOCK_SIZE / 1.5;
			else y += BLOCK_SIZE / 1.5;
			
			g.drawString(move.getHeur()+"", x, y);
		}
		
		g.setFont(old);
		g.setColor(oldC);
		
		
	}

	private void drawScore(Graphics g) {
		
		g.setColor(ClrBlue);
		g.drawString(myGame.getBluePoints()+"", BLOCK_SIZE/4, BLOCK_SIZE/2 + 20);
		g.setColor(Color.black);
		g.drawString(":", BLOCK_SIZE/2, BLOCK_SIZE/2 + 20);
		g.setColor(ClrRed);
		g.drawString(myGame.getRedPoints()+"", 3*BLOCK_SIZE/4, BLOCK_SIZE/2 + 20);
		
		g.setColor(Color.black);
		
		Color clrTurn = myGame.getGameTurn() == Game.TurnType.BLUE_TURN ? ClrBlue : ClrRed;
		String strTurn = "TURN";
		
		g.setColor(clrTurn);
		g.drawString(strTurn, BLOCK_SIZE/4,(int)( BLOCK_SIZE*3/4) + 20);

		g.setColor(Color.black);
		g.drawRoundRect(BLOCK_SIZE/8, BLOCK_SIZE/5+20, (int)(BLOCK_SIZE*0.9), (int)(BLOCK_SIZE*0.7), 50, 50);
	}

	private void drawHelper(Graphics g) {
		

		g.setFont(new Font("Century Gothic", Font.BOLD, BLOCK_SIZE/4)); 

		int xStartM = (int) (BLOCK_SIZE*1.5);
		int dXM = BLOCK_SIZE/2;
		int yM = (int) (BLOCK_SIZE*0.75);
		for(int i=0;i<m;i++) {
			g.setColor(Color.black);
			g.drawString(i+"", xStartM, yM);
			xStartM += dXM;
			String str = ((char)('A'+i))+"";
			if(i!=m-1) {
				
				g.setColor(ClrYlw);
				g.drawString(str, xStartM, yM);
				xStartM += dXM;
			}
		}
		
		int yStartN = (int) (BLOCK_SIZE*1.5);
		int dYN = BLOCK_SIZE/2;
		int xN = (int) (BLOCK_SIZE*0.5);
		for(int i=0;i<n;i++) {
			g.setColor(Color.black);
			g.drawString(i+"", xN, yStartN);
			yStartN += dYN;
			String str = ((char)('A'+i))+"";
			if(i!=n-1) {
				g.setColor(ClrYlw);
				g.drawString(str, xN, yStartN);
				yStartN += dYN;
			}
		}
		
		
	}

	private void drawDots(Graphics g) {
		
		g.setColor(ClrYlw);
		
		int x = (int) (BLOCK_SIZE * 1.5) - POINT_SIZE/2;
		int y = (int) (BLOCK_SIZE * 1.5) - POINT_SIZE/2;
		
		Graphics2D g2 = (Graphics2D) g;
		Stroke old = g2.getStroke();
		g2.setStroke(new BasicStroke(3));
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				g.setColor(ClrYlw);
				g.fillOval(x, y, POINT_SIZE-1, POINT_SIZE-1);
				g.setColor(Color.black);
				g.drawOval(x, y, POINT_SIZE-1, POINT_SIZE-1);
				x += BLOCK_SIZE;
			}
			x = (int) (BLOCK_SIZE * 1.5) - POINT_SIZE/2;
			y+=BLOCK_SIZE;
		}
	
		g2.setStroke(old);
	}

	private void drawConnections(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		Stroke old = g2.getStroke();
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.black);
		
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++){
				if(myGame.pointAt(j, i).connectedRight()){
					
					int x = (int) (BLOCK_SIZE*(1.5+j));
					int y = (int) (BLOCK_SIZE*(1.5+i));
			
					g.drawLine(x, y, x+BLOCK_SIZE, y);
					
				}
				if(myGame.pointAt(j, i).connectedDown()) {
					
					int x = (int) (BLOCK_SIZE*(1.5+j));
					int y = (int) (BLOCK_SIZE*(1.5+i));
			
					g.drawLine(x, y, x, y+BLOCK_SIZE);
				}
				
			}
		}
		
		g2.setStroke(old);
		
	}

	private void drawBlocks(Graphics g) {
		
		for(int i = 0;i<n;i++) {
			for(int j = 0; j<m;j++) {
				if(myGame.pointAt(j, i).getMyBorderState()!=Point.PointBorderState.NONE) {
					Color clr = myGame.pointAt(j,i).getMyBorderState() == Point.PointBorderState.BLUE ? ClrBlue : ClrRed;
					g.setColor(clr);
					int x = (int) (BLOCK_SIZE*(1.5+j));
					int y = (int) (BLOCK_SIZE*(1.5+i));
					g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
		
		
		
		
	}

	private void drawGrid(Graphics g) {
		int hX = 0,hYStart = 0,hYEnd = (n+1)*BLOCK_SIZE;
		int vY = 0,vXStart = 0,vXEnd = (m+1)*BLOCK_SIZE;
		
		for(int i = 0; i<n;i++)
			for(int j = 0;j<m;j++) {
				g.drawLine(hX, hYStart, hX,hYEnd);
				g.drawLine(vXStart, vY, vXEnd, vY);
				hX += BLOCK_SIZE;
				vY += BLOCK_SIZE;
			}
		
	}

	public static void main(String[] argv) throws InvalidGameInitException, InvalidConnectionException {
		
		
		Game game = new Game(6,6);
		game.pointAt(0, 0).connectRight(Point.PointBorderState.BLUE);
		game.pointAt(0, 0).connectDown(Point.PointBorderState.BLUE);
		game.pointAt(1, 0).connectDown(Point.PointBorderState.BLUE);
		game.pointAt(0, 1).connectRight(Point.PointBorderState.BLUE);
		
		game.pointAt(1, 0).connectRight(Point.PointBorderState.BLUE);
		game.pointAt(2, 0).connectRight(Point.PointBorderState.BLUE);
		game.pointAt(3, 0).connectDown(Point.PointBorderState.BLUE);
		game.pointAt(2, 1).connectRight(Point.PointBorderState.BLUE);
		game.pointAt(2, 1).connectLeft(Point.PointBorderState.BLUE);
		game.pointAt(2, 1).connectUp(Point.PointBorderState.RED);
		
		
		Board board = new Board(game);
		board.setVisible(true);
	}

	public void setMoves(LinkedList<Move> moves) {
		this.moves = moves;
		
	}
	
}
