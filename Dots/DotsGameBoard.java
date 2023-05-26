import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class DotsGameBoard extends JPanel {
	public DotsGameBoard() {
	}
	
	//store needed values for the game.
	int dotSize = 6; //the size of dots does not change.
	int d2 = dotSize/2; //this is half the dot size
	int boxSize = 50;
	int gridSize; 
	int canvasSize = 400; //the canvas size does not change
	Point currentMousePos;
	String playerA ="A";
	String playerB ="B";
	int playerAScore =0;
	int playerBScore = 0;
	int whoTurn =1; // 1 means player a turn 2 is player b turn.

	private Box[][] boxes; //array of boxes for the game.
	

	public void setPlayers(String a, String b) { // set the player initials
		playerA =a;
		playerB =b;
	}
	
	public void switchPlayers() { //switch players
		if (whoTurn ==1) {
			whoTurn = 2;
		}else {
			whoTurn =1;
		}
	}
	
	
	
	public int boardClicked(int x, int y) { // returns -1 if no lines were drawn. 0 if line drawn, 1 if boxes were completed.
		
		//get the xy cords of the box the user clicked within.
		 int boxX = x / boxSize; 
         int boxY = y / boxSize;
         
         //we store the scores here in a temp to be able to see if they changed meaning a box was completed.
         int initScoreA = playerAScore;
         int initScoreB = playerBScore;
         char side;
         
         if (boxX > gridSize-1 ) { // edge case user clicks slightly past last box
        	 boxX -=1;
        	 side ='R';
         }
         else if (boxY > gridSize-1) { // edge case mouse slighty beyond bottom box
        	 boxY -=1;
        	 side = 'B';
        	 
         }
         else {
        	 side = whichSide(x, y); //which side will return the side we are closest too.
         }
         
         if (side == 'x') { // the click was right in the middle of a box
        	 return -1;
         }
         
         int result = addLine(side, boxX, boxY); // add line attempts to draw the line the user clicked 
         										 // if the the line was not drawn 0 is returned.
         
         if (result == 0) {
        	 
        	 return -1;
         }
         if (initScoreA != playerAScore || initScoreB != playerBScore) {
        	 return 1;
         }
         
         
         
         return 0;        
		
	}
	
	public void clearScores() {
		playerAScore = 0;
		playerBScore = 0;
	}
	
	public boolean isBoardFull() {
		for (int k = 0; k<gridSize; k++) {
        	for (int l =0; l < gridSize; l++) {
        		if(boxes[k][l].complete < 1) {
        			return false;
        		}
        		
        	}
		}
		return true;
	}
	
	
	public int addLine(char side, int boxX, int boxY) { //returns 1 if a line was drawn. 0 if not drawn.
														// if the line is drawn, the cooresponidng line in the box
														//is set to 1 for player a, 2 for player b.
		
		//for each side we also check to make sure the adjacent box has the current line drawn in also.
		
		int drawn =0; //if a line was drawn set to 1;
		
		
		if (side == 'T' && boxes[boxX][boxY].top ==0) { //top
        	boxes[boxX][boxY].top = whoTurn;
        	
        	if (boxY > 0 && boxes[boxX][boxY-1].bottom <1) { // check that its not already filled in
        		addLine('B', boxX, boxY-1);
        	}
        	drawn = 1;
        	
        	
        }else if (side == 'B' &&boxes[boxX][boxY].bottom ==0) {
        	boxes[boxX][boxY].bottom = whoTurn;
        	if (boxY != gridSize-1 && boxes[boxX][boxY+1].top <1) { // check that its not already filled in
        		addLine('T', boxX, boxY+1);
        	}
        	drawn = 1;
        	
        }else if (side == 'L' &&boxes[boxX][boxY].left ==0) {
        	boxes[boxX][boxY].left = whoTurn;
        	if (boxX != 0 && boxes[boxX-1][boxY].right <1) { // check that its not already filled in
        		addLine('R', boxX-1, boxY);
        	}
        	drawn = 1;
        }else if (side == 'R' && boxes[boxX][boxY].right ==0) {
        	boxes[boxX][boxY].right = whoTurn;
        	if (boxX != gridSize-1 && boxes[boxX+1][boxY].left <1) { // check that its not already filled in
        		addLine('L', boxX+1, boxY);
        	}
        	drawn = 1;
        }
		
		if (drawn == 1) {
			if (isBoxComplete(boxX, boxY) == 1) { //here we check if the box was completed and increment the current players score if so.
        		boxes[boxX][boxY].complete = whoTurn; //mark the box completed by the current player.
        		
        		
        		if (whoTurn == 1) { //increment players score if a box was finished.
        			playerAScore+=1;
        		}else {
        			playerBScore +=1;
        		}
        	}
			
			
		}
		
		
		return drawn;
	}
	
	public int isBoxComplete(int x, int y) { //check if all sides of the box have a line drawn.
		if (boxes[x][y].top > 0 && boxes[x][y].bottom > 0 && boxes[x][y].left > 0 && boxes[x][y].right > 0 ) {
			return 1;
		}
		return 0;
	}
	
	
	public char whichSide(int mouseX, int mouseY) { //calculates which side we of a box the mosue is closest too.
													// returns 'T', 'B', 'L', 'R', respectivly.
													//returns 'x' if the mouse is right in the middle.
		
		
		int x = mouseX / boxSize;
        int y = mouseY / boxSize;
        
        //these are the coords of each edge of current box
        int top = y*boxSize +d2;
        int left = x*boxSize +d2;
        int bottom = (y+1)*boxSize +d2;
        int right = (x +1)*boxSize +d2;
        
        //these are the distances from the mouse position to each edge
        int disT = mouseY - top;
        int disB = bottom - mouseY;
        int disL = mouseX - left;
        int disR = right - mouseX;
        
        //4 if statments that determine which edge is closest to the mouse and draw that edge.
        if (disT <disB && disT<disL && disT < disR) {
        	return 'T';
        }else if (disB < disT && disB < disL && disB < disR) {
        	return 'B';
        }else if (disL < disT && disL < disB && disL < disR) {
        	return 'L';
        }else if (disR < disT && disR< disB && disR < disL) {
        	return 'R';
        }
        return 'x';
		
		
	}
	
	
	
	public void savePoint(Point x) { // saves the last mouse position
		
		
		currentMousePos = x; 
	}
	
	// used to initialize the board size. 
	public void saveBoardSize(int gridSize) {
		boxSize = canvasSize/gridSize;
		this.gridSize = gridSize;
		
		boxes = new Box[gridSize][gridSize]; //the array of boxes initialized.
		for (int i = 0; i < gridSize; i++) {
		    for (int j = 0; j < gridSize; j++) {
		        boxes[i][j] = new Box();

		        
		    }
		}
		
		
		
		
		
	}
	
	
	
	// paint component.
	protected void paintComponent(Graphics g) {
		
        super.paintComponent(g); // select the color for the hover effect based on current player.
        Graphics2D g2d = (Graphics2D) g;
        
        if (whoTurn ==1) {
        	g.setColor(new Color(141, 48, 255));
        }else {
        	g.setColor(new Color(83, 255, 43));
        }
        
		
		// the following code is responsible for drawing the thin hover (preview) line.
		if (currentMousePos != null) {
			//x and y help locate the box we are currently in
            int x = currentMousePos.x / boxSize;
            int y = currentMousePos.y / boxSize;
            
            //these are the coords of each edge of current box
            int top = y*boxSize +d2;
            int left = x*boxSize +d2;
            int bottom = (y+1)*boxSize +d2;
            int right = (x +1)*boxSize +d2;
            
            
        
            char side = whichSide(currentMousePos.x, currentMousePos.y);
            
            //4 if statments that determine which edge is closest to the mouse and draw that edge.
            if (side == 'T') {
            	g.drawLine(left, top, right, top);
            }else if (side == 'B') {
            	g.drawLine(left, bottom, right, bottom);
            }else if (side == 'L') {
            	g.drawLine(left, top, left, bottom);
            }else if (side == 'R') {
            	g.drawLine(right, top, right, bottom);
            }
            
        
            
            
           
        }
			// the following code draws the grid of dots according to the board size selected.
		 	g.setColor(Color.BLACK);
	        for (int i = 0; i <= canvasSize/boxSize; i++) { // draw the dots.
	            for (int j = 0; j <= canvasSize/boxSize; j++) {
	                int x = i * boxSize ;
	                int y = j * boxSize ;
	                
	                
	                g.fillOval(x, y, dotSize, dotSize);
	         
	            }
	        }
	        
	        // here we draw the completed lines as well as the Player initial in the completed boxes.
	        g2d.setStroke(new BasicStroke(3));
	        g2d.setFont(new Font("Arial", Font.BOLD, 36));
	        
	        for (int k = 0; k<gridSize; k++) {
	        	for (int l =0; l < gridSize; l++) { // nesteed for loop that visits each box.
	        		// draw initial if complete
	        		if (boxes[k][l].complete >0) { // check if the box completed and who completed it.
	        									   // draws in the cooresponding initial in the players color.
	        			
	        			if (boxes[k][l].complete == 1) {
	        				
	        				g2d.setColor(new Color(64, 0, 128));
	        				g2d.drawString(playerA, k*boxSize -9 +boxSize/2 , l*boxSize +15 + boxSize/2);
	        			}else {	        				
	        				g2d.setColor(new Color(0, 64, 0));
	        				g2d.drawString(playerB,  k*boxSize -9 +boxSize/2 , l*boxSize +15 + boxSize/2);
	        			}
	        			
	        			
	        			
	        			

	        			
	        			
	        		}
	        		
	        		//draw left line of box.
	        		if (boxes[k][l].left >0) {
	        			if (boxes[k][l].left == 1) {
	        				
	        				g2d.setColor(new Color(64, 0, 128));	 
	        			}else {	        				
	        				g2d.setColor(new Color(0, 64, 0));	        				
	        			}
	        			g2d.drawLine(k*boxSize+d2, l*boxSize+d2, k*boxSize+d2, (l+1)*boxSize+d2);
	        			
	        			
	        		}
	        		//draw top line of box
	        		if (boxes[k][l].top >0) {
	        			if (boxes[k][l].top == 1) {
	        				
	        				g2d.setColor(new Color(64, 0, 128));	 
	        			}else {	        				
	        				g2d.setColor(new Color(0, 64, 0));	        				
	        			}
	        			g2d.drawLine(k*boxSize+d2, l*boxSize+d2, (k+1)*boxSize+d2, l*boxSize+d2);
	        			
	        			
	        		}
	        		
	        		//draw right line of box but only if we are on the last column
	        		if (k== gridSize-1) {
	        			if (boxes[k][l].right >0) {
		        			if (boxes[k][l].right == 1) {
		        				
		        				g2d.setColor(new Color(64, 0, 128));	 
		        			}else {	        				
		        				g2d.setColor(new Color(0, 64, 0));	        				
		        			}
		        			g2d.drawLine((k+1)*boxSize+d2, l*boxSize+d2, (k+1)*boxSize+d2, (l+1)*boxSize+d2);
		        			
		        			
		        		}
	        		}
	        		//draw bottom line but only if we are on the last row
	        		if (l== gridSize-1) {
	        			if (boxes[k][l].bottom >0) {
		        			if (boxes[k][l].bottom == 1) {
		        				
		        				g2d.setColor(new Color(64, 0, 128));	 
		        			}else {	        				
		        				g2d.setColor(new Color(0, 64, 0));	        				
		        			}
		        			g2d.drawLine(k*boxSize+d2, (l+1)*boxSize+d2, (k+1)*boxSize+d2, (l+1)*boxSize+d2);
		        			
		        			
		        		}
	        		}
	        		
	        		
	        	}
	        }
	        
	       
	      
        
        
	}
	
	
	
	
	

}
