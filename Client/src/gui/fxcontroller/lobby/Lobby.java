package gui.fxcontroller.lobby;

import gui.fxcontroller.TicTacToeMatch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.TicTacToeClient;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The controller class for /res/lobby/lobby.fxml.
 * Gets created and loaded by the {@link gui.fxcontroller.Signup} prompt.
 *
 * The lobby is used to find games or create new ones.
 * It consists of two parts: The game list (listing all available games, see /res/fxml/lobby/gameentry.fxml) and a prompt to create a new list.
 *
 * This object stays loaded when matches are running so that the game list stay updated.
 *
 * @author Kilian Friedrich
 */
public class Lobby {

    /**
     * Represents connection to the Tic Tac Toe host server.
     * Requests will be send via this object, the object itself will send request using the TTTP (see {@link TicTacToeClient}).
     *
     * @see TicTacToeClient - describes the TTTP (TicTacToe Protocol)
     */
    protected TicTacToeClient serverConnection;

    /**
     * The window in which the Tic Tac Toe game takes place.
     * The TicTacToeMatch scene will be loaded in that object.
     *
     * @see #joinNewGame() - loading a new game into the window
     * @see Game#join() - loading a {@link Game} into the window
     */
    protected Stage primaryStage;

    /**
     * The server response from the login, starting with "gamelobbies ".
     * It represents a list of available games.
     *
     * The String is passed in the constructor but can't be initialized until all FXML fields are assigned.
     * Hence, it needs to be stored.
     *
     * The list may change over time, when games are {@link #addGameEntry(String, String, String) added} or {@link #removeGameEntry(String) removed}.
     *
     * @see #Lobby(Stage, TicTacToeClient, String) - declares this String
     * @see #initialize() - uses this String to build a list of available games
     */
    private String startGames;

    /**
     * Stores the list of available games.
     * This variable is changed on {@link #initialize() initialization} or when games are {@link #addGameEntry(String, String, String) added} or {@link #removeGameEntry(String) removed}.
     *
     * @see #startGames - the list of available games at the start
     * @see #addGameEntry(String, String, String) - adds new games to this list
     * @see #removeGameEntry(String) - removes games from this list
     */
    private Set<Game> games = new HashSet<>();

    /**
     * This pane (see /res/fxml/lobby/nogames.fxml) is displayed when no game is available.
     * It simply says "No games available. You can create one below.".
     *
     * @see #initialize() - loads the resource /res/fxml/lobby/nogames.fxml into this variable
     */
    private AnchorPane noGamePane;

    /**
     * The field in which the user can type the name of a game which is created.
     *
     * @see #joinNewGame() - creates a new game
     */
    @FXML public TextField gameNameField;

    /**
     * The UI list of available games.
     * Holds a lot of {@link Game game entries} as stored in {@link #games}.
     *
     * @see #games - the list of available games
     */
    @FXML public VBox gameContainer;

    /**
     * Default constructor.
     * Stores assigned parameters in local variables.
     *
     * @param primaryStage - the window in which the TicTacToe game takes place
     * @param serverConnection - represents the connection to the Tic Tac Toe host server
     * @param startGames - a list of available games at lobby initialization (in String format)
     *
     * @see #primaryStage - the window in which the TicTacToe game takes place
     * @see #serverConnection - represents the connection to the Tic Tac Toe host server
     * @see #startGames - a list of available games at lobby initialization
     *
     * @author Kilian Friedrich
     */
    public Lobby(Stage primaryStage, TicTacToeClient serverConnection, String startGames) {

        this.primaryStage = primaryStage;
        this.serverConnection = serverConnection;
        this.startGames = startGames;

        // register object so that it can receive server requests to add / remove games
        serverConnection.setLobbyObject(this);

    } // END constructor Lobby(String, TicTacToeClient, String)

    @FXML public void initialize() {

        try {
            noGamePane = FXMLLoader.load(this.getClass().getResource("/fxml/lobby/nogames.fxml"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        gameContainer.getChildren().add(noGamePane);

        String[] games = startGames.substring(12).split(" ");
        for(int i = 0; i < games.length / 3; i++)
            addGameEntry(games[i * 3], games[i * 3 + 1], games[i * 3 + 2]);

    }

    @FXML public void joinNewGame() {

        String serverResponse = serverConnection.create(gameNameField.getText());

        if(serverResponse != null && serverResponse.startsWith("ok")) {

            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/match.fxml"));
                fxmlLoader.setController(new TicTacToeMatch(this, serverConnection, serverResponse.split(" ", 2)[1], null, (byte) 0));

                ((Stage) gameContainer.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));

            } catch(IOException e) {
                e.printStackTrace();
                ((Stage) gameContainer.getScene().getWindow()).close();
            }

        }

    }

    public void addGameEntry(String gameName, String id, String creatorName) {

        try {

            Game game = new Game(this, serverConnection, gameName, creatorName, id);
            games.add(game);

            FXMLLoader fxmlLoader = new FXMLLoader(Lobby.class.getResource("/fxml/lobby/gameentry.fxml"));
            fxmlLoader.setController(game);

            gameContainer.getChildren().remove(noGamePane);
            gameContainer.getChildren().add(fxmlLoader.load());

        } catch(IOException e) {

            e.printStackTrace();
            ((Stage)gameContainer.getScene().getWindow()).close();

        }

    }

    public Scene getScene() { return gameContainer.getScene(); }


    public void removeGameEntry(String id) {

        for(Game game : games)
            if(game.getGameID().equals(id)) {

                games.remove(game);
                gameContainer.getChildren().remove(game.getParent());
                break;

            }

        if(games.size() == 0)
            gameContainer.getChildren().add(noGamePane);

    }

}
