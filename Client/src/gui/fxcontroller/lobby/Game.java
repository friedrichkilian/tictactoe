package gui.fxcontroller.lobby;

import gui.fxcontroller.TicTacToeMatch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.TicTacToeClient;

import java.io.IOException;

public class Game {

    protected TicTacToeClient serverConnection;
    protected String gameID, gameName, creatorName;

    Game(Lobby lobbyObject, TicTacToeClient serverConnection, String gameID, String gameName, String creatorName) {

        this.lobbyObject = lobbyObject;
        this.gameID = gameID;
        this.gameName = gameName;
        this.creatorName = creatorName;

    }

    private Lobby lobbyObject;

    @FXML public Text gameNameField;
    @FXML public Text creatorNameField;
    @FXML public AnchorPane parent;

    @FXML public void join() {

        String serverResponse = serverConnection.join(gameID);

        if(serverResponse.startsWith("ok")) {

            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/match.fxml"));
                fxmlLoader.setController(new TicTacToeMatch(lobbyObject, serverConnection, gameID, null, Byte.parseByte(serverResponse.split(" ", 2)[1])));

                ((Stage) parent.getScene().getWindow()).setScene(new Scene(fxmlLoader.load()));

            } catch(IOException e) {
                e.printStackTrace();
                ((Stage) parent.getScene().getWindow()).close();
            }

        }

    }
    String getGameID() { return gameID; }
    AnchorPane getParent() { return parent; }

}
