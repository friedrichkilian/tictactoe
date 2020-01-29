package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static Client instance;

    public Stage stage;

    public void start(Stage stage) {

        Client.instance = this;
        this.stage = stage;

        stage.setTitle("TicTacToe");

        loadSignup();
        stage.show();

    }

    public static void loadSignup() { instance.lSignup(); }
    public void lSignup() {

        try {
            stage.setScene(new Scene(new FXMLLoader(Client.class.getResource("/fxml/signup.fxml")).load()));
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadLobby() { instance.lLobby(); }
    public void lLobby() {

        try {
            stage.setScene(new Scene(new FXMLLoader(Client.class.getResource("/fxml/lobby.fxml")).load()));
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadNewGame(String name) { instance.lNewGame(name); }
    public void lNewGame(String name) {

        //TODO

    }

    public static void loadGame(String id) { instance.lGame(id); }
    public void lGame(String id) {

        //TODO

    }
}
