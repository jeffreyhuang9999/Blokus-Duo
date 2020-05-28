package controller;

import java.util.LinkedList;

import modeller.Piece;

abstract class Player {
	public LinkedList<Piece> pieces;
	public boolean firstMove = true;
	public boolean canPlay = true;
	public int total; // total score of player
	public int added_bonus = 5;
	// public int pieces_remaining=21;
	// public int score;
	public static boolean p1_bonus_eligible = false;// if they have the one-piece in possession on move 20
	public static boolean p2_bonus_eligible = false;

	public static boolean p1_bonus_earned = false;
	public static boolean p2_bonus_earned = false;

	public Player(int color) {

		int[][][] shapes = Piece.getAllShapes();

		pieces = new LinkedList<Piece>();

		for (int i = 0; i < shapes.length; i++) {
			pieces.add(new Piece(shapes[i], color));
		}
	}

	public int getScore() {
		total = 0;
		for (Piece bp : pieces) {
			total -= bp.getPoints();
		}
		// for each square a player has in their possession they get minus one point.

		// if someone has placed every piece, they get 15 points
		if (total == 0) {
			total += 15;
		}

		return total;
	}
}
