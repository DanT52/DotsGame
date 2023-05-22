# DotsGame

The Dots Game is a classic board game implemented in Java. The game is developed by Daniel Basarab and was last updated on May 21, 2023. The game allows two players to interactively draw lines on a board, aiming to complete a box. The player who completes a box gets a point.


![](https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExYmRkNWZjMDU0YjIxNjBmMDliOGQ3ZjdiYTg4MmY5MTRiYmVjNzU4NCZlcD12MV9pbnRlcm5hbF9naWZzX2dpZklkJmN0PWc/whU9bGw6jHdLdONazG/giphy.gif)




## DotsGameBoard Class

This class represents the game board for the Dots Game. It contains the functionality for game play, tracking scores, switching turns, and detecting mouse clicks. The class includes the following methods:

- `setPlayers(String a, String b)`: Sets the initials of the players.
- `switchPlayers()`: Switches the active player.
- `boardClicked(int x, int y)`: Determines if a line can be drawn at the mouse click point.
- `clearScores()`: Resets the scores of both players.
- `isBoardFull()`: Checks if the game board is full.
- `addLine(char side, int boxX, int boxY)`: Adds a line on the specified side of the box.
- `isBoxComplete(int x, int y)`: Checks if all sides of a box have a line drawn.
- `whichSide(int mouseX, int mouseY)`: Determines which side of a box the mouse is closest to.
- `savePoint(Point x)`: Saves the current mouse position.
- `saveBoardSize(int gridSize)`: Initializes the board size.
- `paintComponent(Graphics g)`: Responsible for rendering the game board.

## Game Play

Each player takes turns drawing a line between two dots. The goal of the game is to complete a box. Completing a box scores a point and allows the player to take another turn. The game continues until all boxes are filled. The player with the most points (i.e., boxes) wins the game.

## How to Run

To play the game, compile and run the Dots class. Please ensure you have Java installed on your machine. A GUI will pop up for the game play. Enter the players' initials and select the grid size to start playing.

## Future Enhancements

Future enhancements to this game could include options for playing against a computer player, saving and loading game state, and adjusting the difficulty level.
