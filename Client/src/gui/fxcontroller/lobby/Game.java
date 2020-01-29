package gui.fxcontroller.lobby;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import net.TicTacToeClient;

import java.util.UUID;

public class Game {

    protected TicTacToeClient serverConnection;
    protected String gameID, gameName, creatorName;

    Game(TicTacToeClient serverConnection, String gameID, String gameName, String creatorName) {

        this.gameID = gameID;
        this.gameName = gameName;
        this.creatorName = creatorName;

    }

    private Lobby lobby;

    @FXML public Text gameNameField;
    @FXML public Text creatorNameField;
    @FXML public AnchorPane parent;

    @FXML public void join() {

        String serverResponse = serverConnection.join(UUID.fromString(gameID));

        if(serverResponse.startsWith("ok")) {

            //TODO join game

        }

    }

    public void setOrigin(Lobby lobby) { this.lobby = lobby; }

    String getGameName() { return gameName; }
    String getCreatorName() { return creatorName; }
    String getGameID() { return gameID; }
    AnchorPane getParent() { return parent; }

}
