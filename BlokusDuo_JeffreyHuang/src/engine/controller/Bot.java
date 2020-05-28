package controller;

import java.awt.Color;

//import static duo.Color.ORANGE;
//import static duo.Color.VIOLET;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import display.Game;
import modeller.Piece;
import display.*;

import modeller.*;
import display.*;
/*
import duo.Board;
import duo.Color;
import duo.Move;
import duo.MutableBoard;
import duo.Piece;
import duo.Player;
import duo.UI;
*/

public class Bot extends Player {
	
	/** Piece names array. */
	/*
    public String[] pieces = {"1", "2", "v", "3", "t", "s", "d", "i", "z", "N",
                               "Y", "P", "F", "V", "X", "T", "U",
                               "L", "I", "Z", "W"};
    */
    /** Col vals array. */
    private String[] cols = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                             "a", "b", "c", "d"};
    /** Row vals array. */
    private String[] rows = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                             "a", "b", "c", "d"};
    /** Orientations vals array. */
    private String[] orientations = {"0", "1", "2", "3", "4", "5", "6", "7"};

    JOptionPane surrender_box = new JOptionPane();
    
    public Bot(int color) {
    	//color=1;
		super(color);
		// TODO Auto-generated constructor stub
	}

    
	/*
	 * An AI named NAME, playing the COLOR pieces, and using UI
     *  for messages.  This kind of player computes its own moves.
     */
    
    /*
    AI(String name, UI ui) {
        super(name, ui);
    }
    */
    
    //** depth value. */
    private final int startDepth = 100;
    //** cutoff value. */
    private final int cutoffVal = 15;

    public void move() {
    	/*
    	if (Frame.turn_number_p1<1) {
    		placePiece(I-piece);
    		
    	}
    	else {
    		*/
    	outerloop:
    		//loop through each piece
    		for (int i=0;i<23;i++) {
    			for(int j=0;j<8;j++) {
    			//loop until a valid move is found
    			for (int x_coor=1;x_coor<14;x_coor++) {
    				for (int y_coor=1;y_coor<14;y_coor++) {
        				//if piece[i] with orientation [j] @ x_coor, y_coor works,
    					//then PlacePiece();
    					break outerloop;
        			}
    			}
    			
    			
    			if (i==22){
    				//surrender
    				//Display info box for AI surrender; player continues to play
    				JOptionPane.showMessageDialog(surrender_box, "AI has surrendered.");
    				//disable bot's ability to play
    			}
    			}
    		}
    		
    		
    	//}
    	//bot_game.turn_number_p1++;
    }
    /*
    @Override
    void move() {
        Color playercolor = getColor();
        int numMoves = _game.getNumMoves();
        String previousmove = _game.getPreviousmove();
        Board board = _game.getBoard();
        boolean gameNotEnded = _game.movesLeft();
        if (numMoves > startDepth && gameNotEnded) {
            Move bestmove = findBestMove(_game.getBoard().playerOnMove(),
                                         _game.getBoard(), 0, cutoffVal);
            _game.move(bestmove.getMove());
            _ui.reportMove(playercolor, numMoves, bestmove.getMove());
        } else if (gameNotEnded) {
            Move bestmove = findBestMove(_game.getBoard().playerOnMove(),
                                         _game.getBoard(), 0, cutoffVal);
            _game.move(bestmove.getMove());
            _ui.reportMove(playercolor, numMoves, bestmove.getMove());
        } else {
            int[] finalScore = _game.score();
            _ui.reportWinner(finalScore[0], finalScore[1]);
        }
    }
    */
    
    /*
    /* A legal move for PLAYERSMOVE that either has an.
     * estimated value >= CUTOFF.
     * or that has the best estimated value for player PLAYERSMOVE,
     starting from position BOARD, and looking up to DEPTH moves ahead.
    * @return Move best move.
    * 
    Move findBestMove(Color playersMove, Board board,
                      int depth, double cutoff) {
        if (!_game.movesLeft()) {
            int[] finalscore = _game.score();
            if (finalscore[0] > finalscore[1] && playersMove == ORANGE) {
                return new Move("", Integer.MAX_VALUE);
            } else if (finalscore[0] < finalscore[1] && playersMove == VIOLET) {
                return new Move("", Integer.MAX_VALUE);
            } else {
                return new Move("", -1 * Integer.MAX_VALUE);
            }
        }
        if (depth == 0) {
            return guessBestMove(playersMove, board, cutoff);
        }
        Move bestSoFar = new Move("", -1 * Integer.MAX_VALUE);
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        for (String piece : pieces) {
            if (((playersMove == ORANGE) && (!board.orangePieceUsed(piece)))
                || ((playersMove == VIOLET)
                    && (!board.violetPieceUsed(piece)))) {
                for (String col : cols) {
                    for (String row : rows) {
                        for (String orientation : orientations) {
                            String possibleMove = piece + col + row
                                + orientation;
                            if (board.isLegal(possibleMove)) {
                                int value = assignMoveValue(board, playersMove,
                                                            possibleMove);
                                Move legalMove = new Move(possibleMove, value);
                                legalMoves.add(legalMove);
                            }
                        }
                    }
                }
            }
        }
        for (Move move : legalMoves) {
            Color opponent;
            MutableBoard testBoard = new MutableBoard(board);
            testBoard.makeMove(move.getMove());
            Board next = new MutableBoard(testBoard);
            if (playersMove == ORANGE) {
                opponent = VIOLET;
            } else {
                opponent = ORANGE;
            }
            Move response = findBestMove(opponent, next, depth - 1,
                                         -1 * bestSoFar.getValue());
            if (move.getValue() - response.getValue() > bestSoFar.getValue()) {
                move.setValue(move.getValue() - response.getValue());
                bestSoFar = move;
                if (move.getValue() >= cutoff) {
                    break;
                }
            }
        }
        return bestSoFar;
    }

    /** Guesses the best move.
     * Based off PLAYERSMOVE color, the BOARD, and the CUTOFF.
     * @return Move best guess. 
    Move guessBestMove(Color playersMove, Board board, double cutoff) {
        Move bestSoFar = new Move("", -1 * Integer.MAX_VALUE);
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        for (String piece : pieces) {
            if (((playersMove == ORANGE) && (!board.orangePieceUsed(piece)))
                || ((playersMove == VIOLET)
                    && (!board.violetPieceUsed(piece)))) {
                for (String col : cols) {
                    for (String row : rows) {
                        for (String orientation : orientations) {
                            String possibleMove = piece + col
                                + row + orientation;
                            if (board.isLegal(possibleMove)) {
                                int value = assignMoveValue(board, playersMove,
                                                            possibleMove);
                                Move legalMove = new Move(possibleMove, value);
                                legalMoves.add(legalMove);
                            }
                        }
                    }
                }
            }
        }
        for (Move move : legalMoves) {
            MutableBoard testBoard = new MutableBoard(board);
            testBoard.makeMove(move.getMove());
            Board next = new MutableBoard(testBoard);
            move.setValue(assignMoveValue(next, playersMove, move.getMove()));
            if (move.getValue() > bestSoFar.getValue()) {
                bestSoFar = move;
                if (move.getValue() >= cutoff) {
                    break;
                }
            }
        }
        return bestSoFar;
    }
    /** Hexadecimal value. 
    private final int hexaVal = 16;
    /** MoveNumber for center. 
    private final int checkCenter = 14;

    /** Uses a heuristic to assign a move value.
     * Uses BOARD, a color PLAYERSMOVE and a string STRINGMOVE.
     * @return int heuristic value. 
    int assignMoveValue(Board board, Color playersMove, String stringMove) {
        Color[][] gameBoard =  board.getBoard();
        int moveNum = board.getMoveNum();
        int value = 0;
        HashMap piecesHash = board.getPIECES();
        Piece currPiece = (Piece) piecesHash.get(stringMove.substring(0, 1));
        int[][] configuration = currPiece.getConfiguration();
        int col = Integer.parseInt(stringMove.substring(1, 2), hexaVal);
        int row = Integer.parseInt(stringMove.substring(2, 3), hexaVal);
        for (int[] x : configuration) {
            for (int y : x) {
                if (y == 1) {
                    value += 2;
                }
            }
        }
        if (moveNum < checkCenter) {
            if (col > 3 && col < 8) {
                value += 1;
            }
            if (row > 3 && row < 8) {
                value += 1;
            }
        }
        return value;
    }
    
    */
    
}
