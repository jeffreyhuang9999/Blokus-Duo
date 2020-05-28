package display;

//import java.awt.CardLayout;

//import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import controller.Human;
import controller.*;
import display.Game;
//import display.Game.Frame.PieceLabelClickListener;
import modeller.Board;
import modeller.Board.IllegalMoveException;
import modeller.Piece;
//import ActionListener;


public class Frame extends JPanel {
	public static final int NUM_PLAYERS = 2;

	public static final int turn_number = 1;
	public static final int last_piece = 0;

	public int turn_number_p1 = 0;// counts the moves made by player 1
	public int turn_number_p2 = 0;// counts moves made by player 2

	boolean box_showed = false;

	// Menu menu = new Menu();
	public Board board;
	public Human[] players;
	public Bot BotPlayer;

	public int turn = -1;

	public int playing_bot = 0;// detect if playing with a bot or not
	public static boolean entered_game = true;

	public int pieceIndex;
	public Point selected;

	public JPanel master = new JPanel();

	public JPanel mainPanel = new JPanel();//like a master panel
	public JPanel sidePanel = new JPanel();//panel that holds the pieces
	public JPanel piecesPanel = new JPanel();//also holds the pieces
	public JPanel boardPanel = new JPanel();//the board
	
	public JPanel coordinate_holder_1 = new JPanel();
	public JPanel coordinate_holder_2 = new JPanel();
	
	JScrollPane jsp;

	JLabel label;
	ImageIcon boardImage;
	JButton surrender;

	JLabel coord_L1 = new JLabel("                                    1            2            3             4             5             6            7            8            9             A             B             C            D             E");
	// JLabel coord_L2=new JLabel("A");
	JLabel coord_L2 = new JLabel(
			"<html><br>1</br><br> </br><br>2</br><br> </br><br> </br><br>3</br><br> </br><br> </br><br>4</br><br> </br><br> </br><br>5</br><br> </br><br> </br><br>6</br><br> </br><br> </br><br>7</br><br> </br><br>8</br><br> </br><br> </br><br>9</br><br> </br><br> </br><br>A</br><br> </br><br> </br><br>B</br><br> </br><br>C</br><br> </br><br> </br><br>D</br><br> </br><br> </br><br>E</br><br> </br><br> </br></html>");
	
	JLabel move_displayer = new JLabel(
			"Red player's moves: " + turn_number_p1 + "; Green player's moves: " + turn_number_p2);

	// public CardLayout Layout1=new CardLayout();

	public Frame() {

		board = new Board();
		players = new Human[NUM_PLAYERS];

		if (playing_bot == 0) {
			players[0] = new Human(Board.BLACK);
			players[1] = new Human(Board.WHITE);
		}

		else {
			BotPlayer = new Bot(Board.BLACK);
			players[1] = new Human(Board.WHITE);
		}
		/*
		 * coord_L1.setBounds(Game.x+350, 0, 500, 50); add(coord_L1); repaint();
		 * coord_L2.setBounds(Game.x+145, Game.y+30, 10, 635); add(coord_L2);
		 * 
		 * //move_displayer.setBounds(1200, 600, 45, 10); //add(move_displayer);
		 */
		if (playing_bot == 0) {
			initializeGUI();
			startNewTurn();
		}
		else {
			// TODO: Allow the bot to move
			BotMoveOne();
			initializeGUI();
			//startNewTurn();
		}

	}

	public void BotMoveOne() {		
			try {
				board.placePiece(BotPlayer.pieces.get(1), 8,8, true);
				//System.out.println("success");//the above code is not running
			} catch (IllegalMoveException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
			drawBoard();
			BotPlayer.pieces.remove(1);
			BotPlayer.firstMove = false;// first move of the player has expired
			players[turn].canPlay = players[turn].pieces.size() != 0;//does nothing?
			//startNewTurn();	
			
	}
	
	/*
	public void Refresh() {
		if (playing_bot==0) {
			Layout1.show(container, "1");
			Layout1.show(container, "3");
			}
			else {
				Layout1.show(container, "1");
				Layout1.show(container, "4");
			}
	}
	*/
		
	
	private void initializeGUI() {

		class BoardClickListener implements MouseListener, MouseMotionListener, MouseWheelListener {

			public void mouseClicked(MouseEvent e) {
				// Below: if user right-clicks, rotate piece
				if (e.getButton() == MouseEvent.BUTTON3) {
					flipPiece();
				}

				else {// clicked mouse in way that is not right-click
					try {
						//if(playing_bot==0){
						board.placePiece(players[turn].pieces.get(pieceIndex), selected.x - Piece.SHAPE_SIZE / 2,
								selected.y - Piece.SHAPE_SIZE / 2, players[turn].firstMove);

						// selected.x - Piece.SHAPE_SIZE / 2 becomes xOffset.
						// selected.y - Piece.SHAPE_SIZE / 2 becomes yOffset.

						drawBoard();
						players[turn].pieces.remove(pieceIndex);// so that pieces can only be used once
						players[turn].firstMove = false;// first move of the player has expired
						players[turn].canPlay = players[turn].pieces.size() != 0;
						startNewTurn();
						//}
						//else {
							
							
						//}
					} 
					catch (Board.IllegalMoveException ex) {
						displayMessage(ex.getMessage(), "Illegal Move!");
					}
				}
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			// method below prevents a situation where you can play pieces but buttons and
			// scrolling does not work
			public void mouseExited(MouseEvent e) {
				selected = null;
				board.resetOverlay();
				drawBoard();
			}

			public void mouseDragged(MouseEvent e) {

			}

			public void mouseMoved(MouseEvent e) {
				Point p = board.getCoordinates(e.getPoint(), Board.DEFAULT_RESOLUTION);
				if (!p.equals(selected)) {
					selected = p;
					board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
					drawBoard();
				}
			}

			// Piece rotation is controlled by mouse wheel
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() > 0) {
					rotateClockwise();
				} else {
					rotateCounterClockwise();
				}
			}
		}

		// Detect if player has surrendered
		class SurrenderListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				players[turn].canPlay = false;
				startNewTurn();
			}
		}

		// New note: something is missing here
		// mainPanel = new JPanel();
		// piecesPanel = new JPanel();
		piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.PAGE_AXIS));

		// controls scrollable pane for pieces
		jsp = new JScrollPane(piecesPanel);
		jsp.getVerticalScrollBar().setUnitIncrement(Piece.DEFAULT_RESOLUTION / 3);
		// ugly

		jsp.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION - 80, Board.DEFAULT_RESOLUTION - 30));

		// Button that allows players to surrender
		surrender = new JButton("Surrender");
		surrender.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION, 30));
		surrender.addActionListener(new SurrenderListener());

		// sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));

		// boardPanel = new JPanel();

		// master.setLayout(null);
		boardImage = new ImageIcon(board.render());

		label = new JLabel(boardImage);
		BoardClickListener bcl = new BoardClickListener();
		label.addMouseListener(bcl);
		label.addMouseMotionListener(bcl);
		label.addMouseWheelListener(bcl);

		boardPanel.add(label);
		// sidePanel.setBounds(0, 0, 1280, 700);
		sidePanel.add(jsp);
		sidePanel.add(surrender);

		//TODO: Fix "disappearing act"
		
		//JScrollBar verticalScrollBar = jsp.getVerticalScrollBar();
		//verticalScrollBar.setValue(verticalScrollBar.getMaximum());
		
		// mainPanel.setBounds(0, 0, 1280, 700);

		// mainPanel.setLayout(null);
		//TODO: Make coordinate labels work again (janky fix)
		//coord_L1.setBounds(0, 0, 500, 50);
		coordinate_holder_1.add(coord_L1);
		
		
		// mainPanel.add(coord_L1);
		//repaint();
		// mainPanel.setLayout(null);
		//coord_L2.setBounds(Game.x + 145, Game.y + 30, 10, 635);
		coordinate_holder_2.add(coord_L2);
		//coordinate_holder_2.setBounds(0, 50, 500, 50);
		// mainPanel.add(coord_L2);
		
		
		
		mainPanel.add(sidePanel);
		mainPanel.add(coordinate_holder_2);		
		mainPanel.add(boardPanel);
				
		mainPanel.setName("mainPanel");
		
		master.add(coordinate_holder_1);
		//coordinate_holder_1.setBounds(0, 0, 500, 50);
		
		add(mainPanel);
		// pack();
		setVisible(true);

		master.add(mainPanel);
		// master.setLayout(null);

	}

	public void rotateClockwise() {
		players[turn].pieces.get(pieceIndex).rotateClockwise();
		board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
		drawBoard();
	}

	public void rotateCounterClockwise() {
		players[turn].pieces.get(pieceIndex).rotateCounterClockwise();
		board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
		drawBoard();
	}

	public void flipPiece() {
		players[turn].pieces.get(pieceIndex).flipOver();
		board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
		drawBoard();
	}

	// the method below draws an updated version of the board with a piece hovering
	// over it
	public void drawBoard() {
		boardImage.setImage(board.render());
		label.repaint();// colors piece hovering over board
	}

	private void drawBorder() {
		JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
		piece.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
	}

	private void clearBorder() {
		JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
		piece.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	}

	private void displayMessage(String message, String title) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private class PieceLabelClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {

			BlokusPieceLabel bp = (BlokusPieceLabel) e.getComponent();
			clearBorder();
			pieceIndex = bp.pieceIndex;
			drawBorder();
			piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.PAGE_AXIS));
			// verticalScrollBar.setValue(verticalScrollBar.getMinimum());
			// piecesPanel.getViewport().setViewPosition(new Point(0,1));
			// piecesPanel.revalidate();
			// new note: something is missing here!
		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}
	}

	private void startNewTurn() {			
		
		turn++;
		turn %= NUM_PLAYERS;// If 4-player, goes 1,2,3,4. If 2-player, 1,2,1,2

		if (turn == 1) {
			turn_number_p1++;
		} else {
			turn_number_p2++;
		}

		if (isGameOver()) {
			gameOver();
		}

		if (!players[turn].canPlay) {
			startNewTurn();
			return;
		}

		piecesPanel.removeAll();// removing this prevents color change of side panel and does not redraw menu to
								// account for placed piece
		piecesPanel.repaint();// disabling this prevents color change and piece selection

		
		for (int i = 0; i < players[turn].pieces.size(); i++) {
			BlokusPieceLabel pieceLabel = new BlokusPieceLabel(i, players[turn].pieces.get(i),Piece.DEFAULT_RESOLUTION);
			pieceLabel.addMouseListener(new PieceLabelClickListener());
			pieceLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			piecesPanel.add(pieceLabel);
		}
		

		pieceIndex = 0;
		drawBorder();		
		
		//TODO: implement the 5-extra points system
		/*
		if (turn_number_p1==20) { 
			if(players[0].pieces.get(1)==shapes[20]) {//equals the one
				piece Player.p1_bonus_eligible=true; 
			} 
		}
		 
		if (turn_number_p2==20) { 
			if(players[1].pieces.get(1)==shapes[20]) {
				//equals the one
				piece Player.p2_bonus_eligible=true;
			}
		}
		  
		if (turn_number_p1==21){ 
			if(Player.p1_bonus_eligible=true&&PiecesLeft<1) {
				Player.p1_bonus_earned=true; 
			} 
		}
		  
		if (turn_number_p2==21) { 
			if(Player.p2_bonus_eligible=true&&PiecesLeft<1) {
				Player.p2_bonus_earned=true; 
			}
			gameOver();
		 }
		  
		 if (turn_number_p1==22) {
			gameOver(); 
		 }
		 
		 */
		
		// pack();
	}

	private boolean isGameOver() {
		for (int i = 0; i < NUM_PLAYERS; i++) {
			if (players[i].canPlay)
				return false;
		}
		return true;
	}

	// action taken when game is over
	// will implement option to play another game
	private void gameOver() {
		StringBuffer sb = new StringBuffer();

		/*
		 * for (int i = 0; i < NUM_PLAYERS; i++) {
		 * sb.append(Board.getColorName(getPlayerColor(i))); sb.append(": ");
		 * sb.append(players[i].getScore()); sb.append("\n"); }
		 */

		/*
		 * Basically, loop through the players and display their scores. Might change
		 * this to conventional non-loop.
		 * 
		 */

		sb.append("Red player's score: ");
		if (Human.p1_bonus_earned == true) {// 5-point bonus for player 1
			sb.append(players[0].getScore() + 5);
		}
		
		else {
			sb.append(players[0].getScore());
		}

		sb.append(" points");
		sb.append("\n");

		sb.append("Green player's score: ");

		if (Human.p1_bonus_earned == true) { // 5-point bonus for player 2
			sb.append(players[1].getScore() + 5);
		}
		
		else {
			sb.append(players[1].getScore());
		}

		sb.append(" points");
		sb.append("\n");

		if (players[0].getScore() > players[1].getScore()) {
			sb.append("Red player wins.");
		}

		else if (players[0].getScore() < players[1].getScore()) {
			sb.append("Green player wins.");
		}

		else {
			sb.append("Tied game.");
		}

		/*
		 * if(box_showed==false) { JOptionPane.showMessageDialog(this, sb.toString(),
		 * "Game Over", JOptionPane.INFORMATION_MESSAGE); box_showed=true; }
		 */

		JOptionPane.showMessageDialog(this, sb.toString(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
		// This makes the program close upon exiting mini window
	}

	private int getPlayerColor(int index) {
		switch (index) {
		case 0:
			return Board.BLACK;
		case 1:
			return Board.WHITE;
		// case 2: return Board.YELLOW;
		// case 3: return Board.GREEN;
		default:
			return 0;
		}
	}

	public static class BlokusPieceLabel extends JLabel {
		public int pieceIndex;

		public BlokusPieceLabel(int pieceIndex, Piece bp, int size) {
			super(new ImageIcon(bp.render(size)));// picture of piece
			this.pieceIndex = pieceIndex;
		}
	}

}
