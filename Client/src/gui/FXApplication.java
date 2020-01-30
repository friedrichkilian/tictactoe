package gui;

import gui.fxcontroller.Signup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.TicTacToeClient;

import java.io.IOException;

/**
 * The starting point of the TicTacToe application.
 * This project is using OpenFX (with SceneBuilder).
 *
 * @see Application - the abstract implementation of a GUI application
 * @see Stage - the window in which the game will take place
 * @see #main(String[]) - the starting point of everything
 * @see #start(Stage) - the starting point of a GUI application
 *
 * @author Kilian Friedrich
 */
public class FXApplication extends Application {

    public static String SERVER_IP = "127.0.0.1";
    public static int SERVER_PORT = 80;

    public static void main(String[] args) { launch(); }

    public void start(Stage primaryStage) {

        primaryStage.setTitle("TicTacToe");
        primaryStage.setResizable(false);

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            fxmlLoader.setController(new Signup(primaryStage, new TicTacToeClient(SERVER_IP, SERVER_PORT)));
            primaryStage.setScene(new Scene(fxmlLoader.load()));

        } catch(IOException e) { e.printStackTrace(); }

        primaryStage.show();

    }

}
