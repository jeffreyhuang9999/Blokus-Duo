package display;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

import controller.*;
import modeller.Board;
import modeller.Piece;

//import java.awt.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.*;
import java.io.File;
import java.io.IOException;
//import java.awt.CardLayout;
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Container;
//import java.awt.Cursor;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.util.Collections;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.*;

public class Game {

	JFrame frame = new JFrame("Blokus Duo");//name window

	//dummy values
	public static int x = 275;
	public static int y = 100;
	public static int width = 220;
	public static int height = 50;
	public static int instruct_y = 50;

	//below: can detect width of user's screen
	final GraphicsConfiguration config = frame.getGraphicsConfiguration();

	final int left = Toolkit.getDefaultToolkit().getScreenInsets(config).left;
	final int right = Toolkit.getDefaultToolkit().getScreenInsets(config).right;
	final int top = Toolkit.getDefaultToolkit().getScreenInsets(config).top;
	final int bottom = Toolkit.getDefaultToolkit().getScreenInsets(config).bottom;

	final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	final int s_width = screenSize.width - left - right;
	final int s_height = screenSize.height - top - bottom;

	JPanel container = new JPanel();
	JPanel menu = new JPanel();
	JPanel instruction = new JPanel();

	JButton play_human = new JButton("Play a Human");
	JButton play_computer = new JButton("Play Computer");

	JButton game_help = new JButton("How to Play");
	JButton back = new JButton("Back");//after done reading instruction manual
	JButton back_2 = new JButton("Back");//when playing a human
	JButton back_3 = new JButton("Back");//when playing computer
	public CardLayout Layout1 = new CardLayout();

	//Below is the text for the instruction manual
	JLabel manual_p1 = new JLabel("<html><h2>OBJECT OF THE GAME</h2></html>");
	JLabel manual_p2 = new JLabel("Each player has to fit as many of their 21 pieces on the board as possible.");
	JLabel manual_p3 = new JLabel("<htmL><h2>CONTROLS</h2></html>");
	JLabel manual_p4 = new JLabel(
			"<html>Click a piece on the menu to choose a piece.<br>Use mouse wheel to rotate piece.</br><br>Right-click to flip the piece.</br><br>To unhide the pieces on the selector menu, scroll when hovering over it.</br></html>");
	JLabel manual_p5 = new JLabel("<html><h2>HOW TO PLAY</h2></html>");
	JLabel manual_p6 = new JLabel("<html>1. Player 1 places one of their pieces on one of the two starting points (marked in black)."
			+ "<br>Player 2 places one of their pieces on the second starting point. </br>"
			+ "<br>2. Play continues as each player lays down one piece each turn.</br>"
			+ "<br>• Each new piece must touch at least one other piece of the same color, but only at the corners.</br>"
			+ "<br>• No flat edges of same colored pieces can touch.</br>"
			+ "<br>• There are no restrictions on how differently colored pieces can touch one another.</br>"
			+ "<br>3. When a player is unable to place one of their remaining pieces on the </br>"
			+ "<br>board, that player clicks the \"surrender\" button.</br>"
			+ "<br>4. The game ends when both players are blocked from laying down any more pieces. This also includes a player who may have placed all of their pieces on the board. Scores are tallied, and the player with the highest score is the winner.</br></html>");
	JLabel manual_p7 = new JLabel("<html><h2>SCORING</h2></html>");
	JLabel manual_p8 = new JLabel(
			"<html>Players count the number of unit squares in their remaining pieces (1 unit square = -1 point)."
					+ "<br>If a player places all 21 pieces on the board, they earn +15 points. </br>"
					+ "<br>They receive an additional 5 bonus points if the last piece placed on the board was the smallest piece (one square).</br></html>");

	/*
	public void Refresher(int bot_detector) {
		if (bot_detector==0) {
		Layout1.show(container, "1");
		Layout1.show(container, "3");
		}
		else {
			Layout1.show(container, "1");
			Layout1.show(container, "4");
		}
	}
	*/
	
	public Game() {
		//icon aka favicon of the game that replaces java icon
		try {
			frame.setIconImage(ImageIO.read(getClass().getResourceAsStream("icon.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		// System.out.println(s_width);
		// System.out.println(s_height);
		x = s_width / 2 - 60; // position buttons to center
		y = s_height / 2;

		menu.setLayout(null);// so that buttons can be placed anywhere
		instruction.setLayout(null);// so that buttons can be placed anywhere

		container.setLayout(Layout1);// declares intent for master frame to go into card layout

		menu.add(play_human);
		play_human.setBounds(x-30, y, width, height);

		menu.add(play_computer);
		play_computer.setBounds(x-30, y + 75, width, height);

		menu.add(game_help);
		game_help.setBounds(x-30, y + 150, width, height);

		back.setBounds(10, 10, width - 70, height - 10);

		Icon img = new ImageIcon(getClass().getResource("blokus_logo_quartered.png"));
		// ImageIcon img = new ImageIcon("blokus_logo_quartered.png");
		// JLabel logo = new JLabel(img);
		JLabel logo = new JLabel();
		logo.setIcon(img);
		logo.setBounds(x - 250, y - 570, 1280, 800);
		menu.add(logo);
		// menu.validate();

		/*
		 * The following is just adding text as JLabels to the instruction manual.
		 */

		instruction.add(back);
		// menu.setBackground(Color.BLACK);
		// instruction.setBackground(Color.GREEN);
		instruction.add(manual_p1);
		manual_p1.setBounds(x - 30, instruct_y, width + 400, height);// place text in center of screen

		instruction.add(manual_p2);
		manual_p2.setBounds(x - 150, instruct_y + 20, width + 300, height + 10);// place text below title

		instruction.add(manual_p3);
		manual_p3.setBounds(x + 15, instruct_y + 60, width, height);// place title text in center of screen

		instruction.add(manual_p4);
		manual_p4.setBounds(x - 175, instruct_y + 50, width + 400, height + 100);// place text below title

		instruction.add(manual_p5);
		manual_p5.setBounds(x, instruct_y + 45, width + 400, height + 200);// place text below

		instruction.add(manual_p6);
		manual_p6.setBounds(x - 200, instruct_y + 150, width + 400, height + 200);// place text below title

		instruction.add(manual_p7);
		manual_p7.setBounds(x + 15, instruct_y + 270, width + 400, height + 200);// place text below

		instruction.add(manual_p8);
		manual_p8.setBounds(x - 200, instruct_y + 315, width + 400, height + 200);// place text below title

		//might add a picture here
		
		/*
		 * End of adding text
		 */

		container.add(menu, "1");// add panels onto master panel
		container.add(instruction, "2");// add panel to master panel with reference

		Frame game = new Frame();
		game.master.add(back_2);

		Frame bot_game = new Frame();
		bot_game.master.add(back_3);

		container.add(game.master, "3");
		container.add(bot_game.master, "4");

		Layout1.show(container, "1");// show menu

		game_help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Layout1.show(container, "2");// go to instruction manual
			}
		});

		// The method below is experimental
		play_human.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Layout1.show(container, "3");
				//Frame.playing_bot = 0;
				// Frame bf=new Frame();
			}
		});

		// The method below is experimental
		play_computer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Layout1.show(container, "4");
				// Layout1.show(container, "3");
				bot_game.playing_bot = 1;
				// Frame bf=new Frame();
			}
		});

		// back_1 is used when the user was at the instruction manual
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Layout1.show(container, "1");// basically, go back to menu
			}
		});

		// back 2 is used when the user is playing a game with a human
		back_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				play_human.setText("Continue Game with Human");// card layout does not change discard progress

				Layout1.show(container, "1");// basically, go back to menu
			}
		});

		// back 3 is used when the user is playing a game with a computer
		back_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				play_computer.setText("Continue Game with Computer");// card layout does not change discard progress
				Layout1.show(container, "1");// basically, go back to menu
			}
		});

		frame.add(container);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.pack();
		// frame.setSize(700, 700); //goes after frame.pack(); otherwise will be ignored

		// frame.setSize(s_width,s_height);
		frame.setSize(1024, 728);// derived from previous line
		frame.setResizable(false);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setResizable(false);//prevent player from changing game dimensions
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		// frame.validate();
	}

}
