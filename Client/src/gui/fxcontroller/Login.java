package gui.fxcontroller;

import gui.ServerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Client;

public class Login implements ServerInterface {

    public static final String ALREADY_LOGGED_IN = "User %s is already logged in.";

    @FXML public TextField usernameField;
    @FXML public Text errText;

    @FXML public void initialize() {

        usernameField.textProperty().addListener((field, oldValue, newValue) -> {

            if(loggedIn(newValue))
                errText.setText(String.format(ALREADY_LOGGED_IN, usernameField.getText()));
            else if(newValue.isEmpty())
                errText.setText("Please enter a username.");
            else if(!newValue.matches("[-._a-zA-Z0-9]+"))
                errText.setText("Only use \"-\", \".\", \"_\", numbers and chars.");
            else
                errText.setText("");

        });

    }

    @FXML public void login() {

        if(login(usernameField.getText())) {

            Client.loadLobby();

        }

    }

}
