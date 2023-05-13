import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.SystemColor;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JSlider;
import java.awt.Font;
import javax.swing.JTextField;

public class Dots extends JFrame {

	private JPanel contentPane;
	private JTextField playerA;
	private JTextField playerB;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dots frame = new Dots();
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
	public Dots() {
		
		
		
		

		setResizable(false);
		setBackground(SystemColor.activeCaptionBorder);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 597);
		contentPane = new JPanel(){
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		DotsGameBoard gameBoard = new DotsGameBoard(); //create gameboard
		
		gameBoard.setBorder(new LineBorder(new Color(91, 111, 112), 1, true));
		gameBoard.setBackground(new Color(182, 210, 212));
		gameBoard.setBounds(132, 75, 406, 406);
		
		gameBoard.addMouseMotionListener(new MouseMotionAdapter() { //mouse motion listener for hover effect
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				
				
				
				
				gameBoard.savePoint(new Point(x,y));
				
				
				gameBoard.repaint();
				
			}
		
			
		});
		
		
		
		
		
		
		
		//Grid Size Label
		JLabel gridSizeL = new JLabel("Grid Size:"); 
		gridSizeL.setFont(new Font("Arial", Font.PLAIN, 16));
		gridSizeL.setBounds(104, 262, 91, 33);
		contentPane.add(gridSizeL);
		
		
		
		//Slider to select Grid Size.
		JSlider slider = new JSlider(1, 8, 8); 
		slider.setBorder(new LineBorder(new Color(91, 111, 112), 3, true));
		slider.setBounds(169, 306, 358, 40);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		contentPane.add(slider);
		
		JLabel boxesSizeText = new JLabel("8 x 8 boxes");
		boxesSizeText.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
		boxesSizeText.setBounds(314, 357, 114, 14);
		contentPane.add(boxesSizeText);
		
		slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                boxesSizeText.setText(value + " x " + value + " boxes");
                // You can update the UI or perform any other action based on the selected value here
            }
        });
		
		
		
		playerA = new JTextField();
		playerA.setText("A Player");
		playerA.setBounds(104, 203, 86, 20);
		contentPane.add(playerA);
		playerA.setColumns(10);
		
		playerB = new JTextField();
		playerB.setText("B Player");
		playerB.setBounds(480, 203, 86, 20);
		contentPane.add(playerB);
		playerB.setColumns(10);
		
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
		
		
		
		
		JButton restartBTN = new JButton("Restart");
		restartBTN.setFont(new Font("Arial", Font.PLAIN, 18));
		restartBTN.setBounds(258, 508, 170, 33);
		
		//contentPane.add(gameBoard);
		
		strtBTN.addMouseListener(new MouseAdapter() { // when the start button is pressed the start screen components are removed and game compoennts are added.
			@Override
			public void mouseClicked(MouseEvent e) {
				
				nameA.setText(playerA.getText());
				nameB.setText(playerB.getText());
				gameBoard.setPlayers(playerA.getText().substring(0,1), playerB.getText().substring(0,1));
				gameBoard.saveBoardSize(slider.getValue());
				scoreA.setText("0");
				scoreB.setText("0");
				
				if (gameBoard.whoTurn ==1) {
					labelAboveBoard.setText(playerA.getText()+"'s Turn");
				}else {
					labelAboveBoard.setText(playerB.getText()+"'s Turn");
				}
				
				contentPane.remove(gridSizeL);
				contentPane.remove(slider);
				contentPane.remove(boxesSizeText);
				contentPane.remove(playerA);
				contentPane.remove(playerB);
				contentPane.remove(pAL);
				contentPane.remove(pBL);
				contentPane.remove(dnBL);
				contentPane.remove(strtBTN);
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
		
		restartBTN.addMouseListener(new MouseAdapter() { //scores are reset resets to start screen.
			@Override
			public void mouseClicked(MouseEvent e) {
				
				gameBoard.clearScores();
				
				
				contentPane.add(gridSizeL);
				contentPane.add(slider);
				contentPane.add(boxesSizeText);
				contentPane.add(playerA);
				contentPane.add(playerB);
				contentPane.add(pAL);
				contentPane.add(pBL);
				contentPane.add(dnBL);
				contentPane.add(strtBTN);
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
		gameBoard.addMouseListener(new MouseAdapter() { //handle board clicks
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				int result =gameBoard.boardClicked(x,y);
				
				if (result == 0) { // line drawn switch players
					gameBoard.switchPlayers();
					gameBoard.repaint();
					
					
					if (gameBoard.whoTurn ==1) {
						labelAboveBoard.setText(playerA.getText()+"'s Turn");
					}else {
						labelAboveBoard.setText(playerB.getText()+"'s Turn");
					}
					
					
				} else if (result == 1) { //box complete update the scores
					
					scoreA.setText(""+gameBoard.playerAScore);
					scoreB.setText(""+gameBoard.playerBScore);
					gameBoard.repaint();
				}
				
				if (gameBoard.isBoardFull()) { // display the winner message.
					
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
	}
}
