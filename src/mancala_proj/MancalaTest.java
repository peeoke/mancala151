package mancala_proj;

/**
 * This program tests the Mancala game
 * @author pebbles
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class creates the visual components of the frame that work with the model to make a functioning Mancala game.
 */
public class MancalaTest {
	public static final int DEFAULT_FRAME_WIDTH = 1100;
	public static final int DEFAULT_FRAME_HEIGHT = 385;
	public static final int MIN_INITIAL_STONE = 3;
	public static final int MAX_INITIAL_STONE = 4;
	public static final int ASK_STONE_AMT_FIELD_SIZE = 5;
	
	/**
	 * Creates the frame with an initial screen, Mancala board, undo button, and, initial stone set up prompt that work with the model.
	 * @param args unused
	 */
	public static void main(String[] args) {
		DataModel model = new DataModel(new TreeMap<String, Integer>());
		MancalaBoard board = new MancalaBoard(model);
		
		model.attach(board);
		
		/* 
		 * DIFFERENT PARTS:
		 * frame = the frame
		 * initialScreen = the initial screen asking for desired style
		 * secondScreen = the game screen after initial screen
		 * gamePanel = center of second screen; the board with player and Mancala labels
		 * numStonesPanel = bottom of second screen; asks to enter initial stone per pit amount
		 * undoButton = top of second screen; allows player to undo 3x maximum in their current turn
		 * winPanel = south of frame; displays current player/winner
		 */
		
		
		JFrame frame = new JFrame(); //the frame
		frame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
		frame.setTitle("Mancala by pebbles");
		
		JPanel secondScreen = new JPanel(); //the game screen after initial screen
		secondScreen.setLayout(new BorderLayout());
		secondScreen.setVisible(false);
		
		JPanel gamePanel = new JPanel(); //the board with player and Mancala labels
		gamePanel.setLayout(new BorderLayout());
        gamePanel.add(board, BorderLayout.CENTER);
        JLabel playerALabel = new JLabel("<-- Player A");
        playerALabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel playerBLabel = new JLabel("Player B -->");
        playerBLabel.setHorizontalAlignment(JLabel.CENTER);
        gamePanel.add(playerALabel, BorderLayout.NORTH);
        gamePanel.add(playerBLabel, BorderLayout.SOUTH);
        gamePanel.add(new JLabel("Mancala A"), BorderLayout.WEST);
        gamePanel.add(new JLabel("Mancala B"), BorderLayout.EAST);
        secondScreen.add(gamePanel, BorderLayout.CENTER);

		/*
		 * INITIAL SCREEN (STYLES)
		 * Display 2 buttons for users to choose a style and go to gamePanel
		 */
		JPanel initialScreen = new InitialPanel(board, secondScreen); //the initial screen asking for desired style
		frame.setLayout(new BorderLayout());

		frame.add(initialScreen, BorderLayout.NORTH);
		frame.add(secondScreen,BorderLayout.CENTER);
		
		/**
		 * SECOND SCREEN (BOARD SET-UP)
		 * Display empty board (done in initial panel) and ask if player wants 3 or 4 stones per pit
		 */
		JPanel numStonesPanel = new JPanel(); //ask and enter stone amount
		JLabel label = new JLabel("Enter initial number of stones per pit (" + MIN_INITIAL_STONE + " or " + MAX_INITIAL_STONE+ "):");
		JTextField field = new JTextField(ASK_STONE_AMT_FIELD_SIZE);
		numStonesPanel.add(label);
		numStonesPanel.add(field);
		secondScreen.add(numStonesPanel,BorderLayout.SOUTH);
		
		/*
		 * GAME SCREEN
		 * Display board with stones and undo button
		 */
		JLabel errorText = new JLabel();
		errorText.setFont(new Font("Arial", Font.PLAIN, 12));
		errorText.setHorizontalAlignment(JTextField.CENTER);
		errorText.setForeground(Color.RED);
		errorText.setVisible(false);

		JLabel remainingUndo = new JLabel();
		remainingUndo.setFont(new Font("Arial", Font.PLAIN, 12));
		remainingUndo.setHorizontalAlignment(JTextField.CENTER);
		remainingUndo.setForeground(Color.BLACK);
		remainingUndo.setVisible(false);
		
		JPanel gameText = new JPanel(new BorderLayout());
		gameText.add(errorText, BorderLayout.NORTH);
		gameText.add(remainingUndo, BorderLayout.SOUTH);
 
		JButton undoButton = new JButton("Undo");
		undoButton.setVisible(false);
		undoButton.addActionListener(event -> {			
			if (model.getNumUndo() == 3) {
				if (model.isFinalMoveP1()) {
					errorText.setText("Player A cannot use more undos. Player B make a move.");
				}
				else if (model.isFinalMoveP2()) {
					errorText.setText("Player B cannot use more undos. Player A make a move.");
				}
				errorText.setVisible(true);
			}
			else if (!model.isUndoEnabled()) {
				errorText.setText("Please make a move first.");
				errorText.setVisible(true);
			}
			else if (model.isUndoEnabled() && model.getNumUndo() < 3) {
				model.setNumUndo(model.getNumUndo() + 1);
				remainingUndo.setText("Remaining Undos: " +  (3 - model.getNumUndo()));
				model.popUndo();
				model.update();
				errorText.setVisible(false);
			}
		});
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(event -> {
			try {
				int numStones = Integer.parseInt(field.getText());
				if(numStones == MIN_INITIAL_STONE || numStones == MAX_INITIAL_STONE) {
					model.setGame(true);
					model.setData(numStones);
					model.setTurn(true);
					model.update();
					numStonesPanel.setVisible(false);
					
					undoButton.setVisible(true); //make undo visible after game starts
					model.enableUndo(false); //need to make move before enabling button
					remainingUndo.setText("Remaining Undos: 3");
					remainingUndo.setVisible(true);
					secondScreen.add(gameText, BorderLayout.SOUTH);
				}
			}
			catch(NumberFormatException ex){
				
			}
		});
		
		numStonesPanel.add(startButton, BorderLayout.SOUTH);
		secondScreen.add(undoButton, BorderLayout.NORTH);
		
		/*
		 * CURRENT TURN AND WINNER
		 * Display who is playing / who won the game
		 */
		StatePanel statePanel = new StatePanel(model);
		model.attach(statePanel);
		frame.add(statePanel, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
