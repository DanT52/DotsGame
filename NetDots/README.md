# NetDots
This is an extension of the Dots game. This version is created to be played across a network.

## Demo

![NetDotDemo](https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExNDAyMGYwZDFjZTBmNjEyYTM4ZjJkYThiYzJhYjFjYjUyNDQxMDVhYyZlcD12MV9pbnRlcm5hbF9naWZzX2dpZklkJmN0PWc/CkHYqT20oEuaw2CSL1/giphy.gif)



## Running the Game
have have 1.8 or newer installed. Run the NetDots.jar file.
When you create a server it will use port 1234.

## About

This project utilizes Java's `ServerSocket` and `Socket` classes to establish a network connection between a server and a client. It also uses threads to handle the server and client operations concurrently.

# NetThread Class

The `NetThread` class is a Java program that sets up a server and client for a multiplayer game. It allows the server and client to communicate with each other over a network, using the Java networking API.

## NetThread Class Methods

- `public NetThread(NetDot parent)`: Constructor for the `NetThread` class. It initializes the `NetThread` with a reference to the parent `NetDot` instance.

- `public void sendMessage(String message)`: This method sends a message to the connected client.

- `public void startServer()`: This method starts the server in a new thread.

- `public void stopServer()`: This method stops the server from listening for new connections.

- `public void createServer()`: This method creates the server, listening for connections and handling client interaction.

- `public void createClient()`: This method creates the client, connecting to a server and handling server interaction.

- `public void run()`: This method runs the main communication loop, reading messages from the client and performing actions based on those messages.

- `public void cleanup()`: This method cleans up resources such as the `ServerSocket`, `Socket`, `Scanner`, and `PrintWriter`.

