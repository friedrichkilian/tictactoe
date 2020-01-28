package gui.fxcontroller.gameentry;

import gui.fxcontroller.Lobby;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GameController {

    private Lobby lobby;

    @FXML public Text gameName;
    @FXML public Text creatorName;
    @FXML public void join() { lobby.join(this); }

    public void setGameName(String gameName) { this.gameName.setText(gameName);}
    public void setCreatorName(String creatorName) { this.creatorName.setText(creatorName);}

    public void setOrigin(Lobby lobby) { this.lobby = lobby; }


}
