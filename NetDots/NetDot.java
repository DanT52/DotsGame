//Daniel Basarab
// 5/21/2023
//CSE 223
//PA5

/*Dots Game
 * This is a dots game that is played on a grid. completed boxes are marked with player initials
 * the game is played with two players. each player has a turn and each turn a player can choose a box to mark.
 * 
 */



import java.awt.EventQueue;
import javax.swing.ButtonGroup;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.SystemColor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.LineBorder;
import javax.swing.JSlider;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NetDot extends JFrame {

	JPanel contentPane;
	JTextField playerA;
	JTextField playerB;
	ServerSocket serverSocket;
	Socket clientSocket;
	Socket socket;
	NetThread gameThread;
	
	int status = 0; //0 means not started, 1, waiting for connect. 
	
	
	private JTextField connectToField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NetDot frame = new NetDot();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NetDot() {
		

		setResizable(false);
		setBackground(SystemColor.activeCaptionBorder);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 597);
		
		contentPane = new JPanel(){ // main Jpanel add gradient.
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;

                // Define the gradient
                GradientPaint gradient = new GradientPaint(0, 0, new Color(180, 180, 180), getWidth(), getHeight(), new Color(204, 229, 255));

                // Paint the gradient onto the panel
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        //add all of the swing components to the window (panel)
        
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		DotsGameBoard gameBoard = new DotsGameBoard(); //create gameboard
		
		gameBoard.setBorder(new LineBorder(new Color(91, 111, 112), 1, true));
		gameBoard.setBackground(new Color(182, 210, 212));
		gameBoard.setBounds(132, 75, 406, 406);
		
		//Grid Size Label
		JLabel gridSizeL = new JLabel("Grid Size:"); 
		gridSizeL.setFont(new Font("Arial", Font.PLAIN, 16));
		gridSizeL.setBounds(313, 280, 91, 33);
		contentPane.add(gridSizeL);
		
		//Slider to select Grid Size.
		JSlider slider = new JSlider(1, 8, 8);
		slider.setBorder(new LineBorder(new Color(91, 111, 112), 3, true));
		slider.setBounds(180, 329, 358, 40);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		contentPane.add(slider);
		
		JLabel boxesSizeText = new JLabel("8 x 8 boxes");
		boxesSizeText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
		boxesSizeText.setBounds(311, 380, 114, 14);
		contentPane.add(boxesSizeText);
		
		slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                boxesSizeText.setText(value + " x " + value + " boxes");
                
            }
        });
		
	
		playerA = new JTextField();
		playerA.setText("Server");
		playerA.setBounds(104, 203, 86, 20);
		contentPane.add(playerA);
		playerA.setColumns(10);
		
		playerB = new JTextField();
		playerB.setText("Client");
		playerB.setBounds(480, 203, 86, 20);
		contentPane.add(playerB);
		playerB.setColumns(10);
		playerB.setEditable(false);
		
		JLabel pAL = new JLabel("Player A");
		pAL.setForeground(new Color(64, 0, 128));
		pAL.setFont(new Font("Arial", Font.PLAIN, 20));
		pAL.setBounds(104, 149, 103, 26);
		contentPane.add(pAL);
		
		JLabel pBL = new JLabel("Player B");
		pBL.setForeground(new Color(0, 64, 0));
		pBL.setFont(new Font("Arial", Font.PLAIN, 20));
		pBL.setBounds(480, 149, 103, 26);
		contentPane.add(pBL);
		
		JLabel dnBL = new JLabel("Dots and Boxes!");
		dnBL.setFont(new Font("Arial", Font.BOLD, 35));
		dnBL.setBounds(200, 39, 338, 65);
		contentPane.add(dnBL);
		
		
		
		JButton strtBTN = new JButton("Start");
		strtBTN.setFont(new Font("Arial", Font.BOLD, 18));
		strtBTN.setBounds(237, 424, 212, 73);
		contentPane.add(strtBTN);
		
		JRadioButton radioServer = new JRadioButton("Server");
		radioServer.setSelected(true);
		radioServer.setBounds(98, 241, 109, 23);
		
		
		
		JRadioButton radioClient = new JRadioButton("Client");
		radioClient.setBounds(474, 241, 109, 23);
		
		contentPane.add(radioClient);
		contentPane.add(radioServer);
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioClient);
		group.add(radioServer);
		
		
		
		JLabel noteLabel = new JLabel("message");
		noteLabel.setFont(new Font("Arial", Font.PLAIN, 11));
		noteLabel.setBounds(30, 483, 200, 64);
		contentPane.add(noteLabel);
		
		JLabel connectToLabel = new JLabel("Connect to:");
		connectToLabel.setFont(new Font("Arial", Font.PLAIN, 11));
		connectToLabel.setBounds(229, 321, 64, 27);
		
		
		connectToField = new JTextField();
		connectToField.setText("localhost");
		connectToField.setBounds(303, 324, 86, 20);
		
		connectToField.setColumns(10);
		
		
		JLabel labelAboveBoard = new JLabel("Player 1's Turn.");
		labelAboveBoard.setFont(new Font("Arial", Font.BOLD, 35));
		labelAboveBoard.setBounds(225, 11, 338, 65);
		
		
		JLabel scoreL = new JLabel("score:");
		scoreL.setFont(new Font("Arial", Font.PLAIN, 16));
		scoreL.setBounds(28, 197, 91, 33);
		
		
		JLabel scoreL_1 = new JLabel("score:");
		scoreL_1.setFont(new Font("Arial", Font.PLAIN, 16));
		scoreL_1.setBounds(587, 197, 91, 33);
		
		
		JLabel scoreB = new JLabel("0");
		scoreB.setFont(new Font("Arial", Font.BOLD, 40));
		scoreB.setBounds(597, 240, 69, 35);
		
		
		JLabel scoreA = new JLabel("0");
		scoreA.setFont(new Font("Arial", Font.BOLD, 40));
		scoreA.setBounds(38, 241, 69, 35);
		
		
		JLabel nameA = new JLabel("Player A");
		nameA.setForeground(new Color(64, 0, 128));
		nameA.setFont(new Font("Arial", Font.BOLD, 20));
		nameA.setBounds(20, 149, 103, 26);
		
		
		JLabel nameB = new JLabel("Player B");
		nameB.setForeground(new Color(0, 64, 0));
		nameB.setFont(new Font("Arial", Font.BOLD, 20));
		nameB.setBounds(570, 149, 103, 26);
		
		
		
		
		JButton restartBTN = new JButton("Quit");
		restartBTN.setFont(new Font("Arial", Font.PLAIN, 18));
		restartBTN.setBounds(258, 508, 170, 33);
		
		//contentPane.add(gameBoard);
		
		strtBTN.addMouseListener(new MouseAdapter() { // when the start button is pressed the start screen 
													  //components are removed and game compoennts are added.
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//displayer text for the player names.
				nameA.setText(playerA.getText());
				nameB.setText(playerB.getText());
				
				//save the player initials to DotsGameBoard.
				gameBoard.setPlayers(playerA.getText().substring(0,1), playerB.getText().substring(0,1)); 
				gameBoard.saveBoardSize(slider.getValue()); //save grid size to the DotsGameBoard class.
				scoreA.setText("0");
				scoreB.setText("0");
				
				if (gameBoard.whoTurn ==1) { // change the text to display current player turn.
					labelAboveBoard.setText(playerA.getText()+"'s Turn");
				}else {
					labelAboveBoard.setText(playerB.getText()+"'s Turn");
				}
				
				//remove start page components
				contentPane.remove(gridSizeL);
				contentPane.remove(slider);
				contentPane.remove(boxesSizeText);
				contentPane.remove(playerA);
				contentPane.remove(playerB);
				contentPane.remove(pAL);
				contentPane.remove(pBL);
				contentPane.remove(dnBL);
				contentPane.remove(strtBTN);
				contentPane.remove(connectToLabel);
	        	contentPane.remove(connectToField);
	        	contentPane.remove(radioClient);
	    		contentPane.remove(radioServer);
	    		radioServer.setSelected(true);
	    		radioClient.setSelected(false);
				
				
				//and game page components.
				contentPane.add(gameBoard);
				contentPane.add(restartBTN);
				contentPane.add(nameA);
				contentPane.add(nameB);
				contentPane.add(scoreL_1);
				contentPane.add(scoreL);
				contentPane.add(labelAboveBoard);
				contentPane.add(scoreB);
				contentPane.add(scoreA);
				
				contentPane.repaint();
				
				
			}
		});
		
		restartBTN.addMouseListener(new MouseAdapter() { //reset to starts screen when restart btn pressed.
			@Override
			public void mouseClicked(MouseEvent e) {
				
				gameBoard.clearScores(); // clear the players scores.
				
				//add start screen components.
				contentPane.add(gridSizeL);
				contentPane.add(slider);
				contentPane.add(boxesSizeText);
				contentPane.add(playerA);
				contentPane.add(playerB);
				contentPane.add(pAL);
				contentPane.add(pBL);
				contentPane.add(dnBL);
				contentPane.add(strtBTN);
				contentPane.add(radioClient);
				contentPane.add(radioServer);
				
				//remove game components.
				contentPane.remove(gameBoard);
				contentPane.remove(restartBTN);
				contentPane.remove(nameA);
				contentPane.remove(nameB);
				contentPane.remove(scoreL_1);
				contentPane.remove(scoreL);
				contentPane.remove(labelAboveBoard);
				contentPane.remove(scoreB);
				contentPane.remove(scoreA);
				
				contentPane.repaint();
				
				
			}
		});
		
		//mouse motion listener for hover effect
		gameBoard.addMouseMotionListener(new MouseMotionAdapter() { 
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				gameBoard.savePoint(new Point(x,y));
				
				gameBoard.repaint();
				
			}
		
			
		});
		
		//handle board clicks / line drawn
		gameBoard.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				int result =gameBoard.boardClicked(x,y); //run bordClicked method. 
														
				
				if (result == 0) { // line drawn no box complete switch players
					gameBoard.switchPlayers();
					gameBoard.repaint();
					
					
					if (gameBoard.whoTurn ==1) { // switch the text letting know whos turn.
						labelAboveBoard.setText(playerA.getText()+"'s Turn");
					}else {
						labelAboveBoard.setText(playerB.getText()+"'s Turn");
					}
					
					
				} else if (result == 1) { //box complete update the scores. dont switch turn
					
					scoreA.setText(""+gameBoard.playerAScore);
					scoreB.setText(""+gameBoard.playerBScore);
					gameBoard.repaint();
				}
				
				if (gameBoard.isBoardFull()) { // check if board is full.
											  // if so return who won and display the cooresponding message.
					
					String whoWonMsg = "";
					if(gameBoard.playerAScore>gameBoard.playerBScore) {
						whoWonMsg = (playerA.getText()+ " Wins!");
					}else if (gameBoard.playerBScore>gameBoard.playerAScore) {
						whoWonMsg = (playerB.getText()+ " Wins!");
					}else {
						whoWonMsg = ("TIE Game!");
					}
					
					labelAboveBoard.setText(whoWonMsg);
					gameBoard.repaint();

					
				}

				
			}
		});
		radioServer.addItemListener(new ItemListener() { //listen for server radio button selected
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		            // Code to execute when radioServer is selected

		        	playerB.setEditable(false);
		        	playerA.setEditable(true);
		        	contentPane.add(boxesSizeText);
		        	contentPane.add(slider);
		        	contentPane.add(gridSizeL);
		        	
		        	contentPane.remove(connectToLabel);
		        	contentPane.remove(connectToField);
		        	strtBTN.setText("Start");
		        	
		        	contentPane.repaint();
		            
		  
		        }
		    }
		});
		radioClient.addItemListener(new ItemListener() { //listen for client radio button selectecd.
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		        	contentPane.remove(slider);
		        	contentPane.remove(gridSizeL);
		        	contentPane.remove(boxesSizeText);
		        	contentPane.add(connectToLabel);
		        	contentPane.add(connectToField);
		        	playerB.setEditable(true);
		        	playerA.setEditable(false);
		        	strtBTN.setText("Connect");
		        	contentPane.repaint();
		            // Code to execute when radioClient is selected
		            
		        }
		    }
		});
	}
}
