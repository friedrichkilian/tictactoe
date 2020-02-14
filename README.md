# tictactoe
A simple server-client-based TicTacToe-Game.

## Setup
### 1. Install dependencies

JavaFX is needed for clients.

This is a part of the standard libraries until Java 12.

### 2. Adjust the server port

You have to edit the constructor of the TicTacToeServer to do so. Default is 80.

If using 0 for a dynamic port, you can change the Server class to print the port at startup (in Server -> NewConnectionHandler -> Constructor(int)''' via serverSocket.getLocalPort()).


## Credits
This project is using classes from the Standardsicherung NRW.
Server/src/* mainly by Christian P.
