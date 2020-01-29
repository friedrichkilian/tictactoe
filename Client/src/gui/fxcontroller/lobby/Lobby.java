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

public class Lobby {

    private String startGames;
    private HashSet<Game> games;
    private AnchorPane noGamePane;

    @FXML public TextField gameNameField;
    @FXML public VBox gameContainer;

    protected TicTacToeClient serverConnection;

    public Lobby(TicTacToeClient serverConnection, String gamelobbies) {

        this.serverConnection = serverConnection;

    }

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
