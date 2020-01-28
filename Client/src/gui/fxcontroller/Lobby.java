package gui.fxcontroller;

import gui.fxcontroller.gameentry.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.Client;

import java.io.IOException;
import java.util.HashSet;

public class Lobby {

    private HashSet<Game> games;
    private AnchorPane noGamePane;

    @FXML public TextField gameNameField;
    @FXML public VBox gameContainer;

    @FXML public void initialize() {

        try {
            noGamePane = new FXMLLoader(Lobby.class.getResource("/nogames.fxml")).load();
        } catch(IOException e) {
            e.printStackTrace();
        }

        gameContainer.getChildren().add(noGamePane);

    }


    public void join(GameController requestFrom) {

        for(Game g : games)
            if(requestFrom == g.getGameController()) {
                Client.loadGame(g.getID());
                break;
            }

    }
    @FXML public void createGame() { Client.loadNewGame(gameNameField.getText()); }

    private void addGameEntry(String gameName, String creatorName, String id) {

        Game g = new Game(gameName, creatorName, id);
        games.add(g);
        gameContainer.getChildren().remove(noGamePane);
        gameContainer.getChildren().add(g.getEntry());

    }
    private void removeGameEntry(String id) {

        for(Game g : games)
            if(g.getID().equals(id)) {

                games.remove(g);
                gameContainer.getChildren().remove(g.getEntry());
                break;

            }

        if(games.size() == 0)
            gameContainer.getChildren().add(noGamePane);

    }

    public static class Game {

        public GameController gameController;
        public AnchorPane entry;
        private String id;

        public Game(String gameName, String creatorName, String id) {

            this.id = id;

            try {

                FXMLLoader l = new FXMLLoader(Lobby.class.getResource("/gameentry.fxml"));
                entry = l.load();
                GameController ctrl = l.getController();
                ctrl.setGameName(gameName);
                ctrl.setCreatorName(creatorName);

            } catch(IOException e) {
                e.printStackTrace();
            }

        }

        public String getID() { return id; }
        public AnchorPane getEntry() { return entry; }
        public GameController getGameController() { return gameController; }

    }

}

