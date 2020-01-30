package net;

import gui.fxcontroller.TicTacToeMatch;
import gui.fxcontroller.lobby.Game;
import gui.fxcontroller.lobby.Lobby;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * The TTTP (Tic Tac Toe Protocol):
 * Possible Client request:
 *  - login (username)
 *    --> signs the user up / assigns the username to its IP and port
 *  - join (gameid)
 *    --> joins a game with the UUID (gameid)
 *  - leave
 *    --> leaves the current game
 *  - create (gamename)
 *    --> creates a new game called (gamename)
 *  - pick (field)
 *    --> picks a field (0..8) in a game
 *
 * Possible Server answers/notifications:
 *  - gamelobbies (gamename 1) (gameid 1) (creator 1) ... (gamename n) (gameid n) (creator n)
 *    --> gives a list of open games, is sent after a user's 'login' request
 *  - ok | error
 *    --> response to a 'leave' or 'pick' request
 *  - ok (start) | error
 *    --> response to a 'join' request, (start) is either 0 (opponent starts) or 1 (you start)
 *  - ok (gameid)
 *    --> response to a 'create' request, (gameid) is an UUID
 *  - addGame (gamename) (gameid) (creator)
 *    --> when a user created a new game to display in the lobby
 *  - delGame (gameid)
 *    --> when a game is full or everyone left, so it doesn't have to be listed any longer
 *  - updatefield (field)
 *    --> when the opponent picked a field (field)
 *  - playerjoinedmatch (opponent) (start)
 *    --> when (opponent) joined your game, (start) is either 0 (opponent starts) or 1 (you start)
 *
 */
public class TicTacToeClient extends Client {

    protected String serverResponse = null;

    protected TicTacToeMatch currentGame;
    protected Lobby lobbyObject;

    public TicTacToeClient(String pServerIP, int pServerPort) { super(pServerIP, pServerPort); }

    public void setCurrentGame(TicTacToeMatch game) { this.currentGame = currentGame; }
    public void setLobbyObject(Lobby lobbyObject) { this.lobbyObject = lobbyObject; }

    public synchronized void processMessage(String pMessage) {

        switch(pMessage.split(" ", 2)[0]) {

            case "updatefield": currentGame.opponentPicked(Integer.parseInt(pMessage.split(" ", 2)[1])); break;
            case "opponentjoined": currentGame.opponentJoined(pMessage.split(" ", 3)[1], Integer.parseInt(pMessage.split(" ", 3)[2]) == 1 ? (byte)1 : -1); break;
            case "addGame": lobbyObject.addGameEntry(pMessage.split(" ", 4)[1], pMessage.split(" ", 4)[2], pMessage.split(" ", 4)[3]); break;
            case "delGame": lobbyObject.removeGameEntry(pMessage.split(" ", 2)[1]); break;
            default: serverResponse = pMessage; notify();

        }

        //TODO kein updatefield nach pick request!!!!!!!!!
        //TODO playerjoinedmatch
        //TODO win / lose after pick ?????
        //TODO opponentleft

    }

    public synchronized String signup(String username) {

        return "gamelobbies game1 1234 opponent1 game2 5678 opponent2";

        /*send("login " + username);

        try {
            wait(5000);
        } catch(InterruptedException ignored) {}

        if(serverResponse == null)
            new TimeoutException("Couldn't sign up: Timeout").printStackTrace();

        String returnString = serverResponse;
        serverResponse = null;
        return returnString;*/

    }

    public String join(String gameID) {

        return "ok 1";

        /*send("join " + gameID);

        try {
            wait(5000);
        } catch(InterruptedException ignored) {}

        if(serverResponse == null)
            new TimeoutException("Couldn't join game " + gameID + ": Timeout").printStackTrace();

        String returnString = serverResponse;
        serverResponse = null;
        return returnString;*/

    }

    public String create(String game) {

        return "ok 9012";

        /*send ("create " + game);

        try {
            wait(5000);
        } catch(InterruptedException ignored) {}

        if(serverResponse == null)
            new TimeoutException("Couldn't create game " + game + ": Timeout").printStackTrace();

        String returnString = serverResponse;
        serverResponse = null;
        return returnString;*/

    }

    public String pick(int field) {

        return "ok";

        /*this.send("pick " + field);

        try {
            wait(5000);
        } catch(InterruptedException ignored) {}

        if(serverResponse == null)
            new TimeoutException("Couldn't pick field " + field + ": Timeout").printStackTrace();

        String returnString = serverResponse;
        serverResponse = null;
        return returnString;*/

    }

    public void leave () { /*send("leave");*/ }

}
