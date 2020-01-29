package gui.fxcontroller;

import gui.NetInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Client;

/**
 * The controller class for res/fxml/signup.fxml, the sign-up prompt for TicTacToe.
 * Adds functionality like sign-up or error messages to the fxml.
 * Gets called by {@link javafx.fxml.FXMLLoader} by {@link Client#loadSignup()}.
 * 
 * @see NetInterface - provides functions which call various other functions from the net package
 * 
 * @author Kilian Friedrich
 */
public class Signup implements NetInterface {

    /**
     * The text field in which the user types his username
     * Gets assigned by the {@link javafx.fxml.FXMLLoader} when the signup prompt gets loaded by {@link Client#loadSignup()}
     * 
     * @see #updateErrorMessage(String) - prints error messages on wrong input (shown in {@link #errField})
     */
    @FXML public TextField usernameField;

    /**
     * The text field in which error messages are displayed.
     * Gets assigned by the {@link javafx.fxml.FXMLLoader} when the signup prompt gets loaded by {@link Client#loadSignup()}
     * 
     * @see #updateErrorMessage(String) - updates this field's content
     */
    @FXML public Text errField;

    /**
     * Gets called when the variables {@link #usernameField} and {@link #errField} are set.
     * Adds a listener to the username text field which checks the validity of user names.
     * 
     * @see #usernameField - the field in which the user types its username
     * 
     * @author Kilian Friedrich
     */
    @FXML public void initialize() {
        
        // ignores the observed TextField (already stored in usernameField) and old value (not needed) on updateErrorMessage(String) call
        usernameField.textProperty().addListener((ignored1, ignored2, newValue) -> updateErrorMessage(newValue));

    }

    /**
     * Gets called on usernameFields update (see res/fxml/signup.fxml) or when "Sign-Up" is pressed.
     * Updates the error message in fx:errField (see res/fxml/signup.fxml).
     *
     * @param value the new value of the username field
     * @return true when the username is invalid / false when the username is valid
     *
     * @see TextField#setText(String) - sets the text of a text field
     * @see #errField - the text field in which the error message is written
     *
     * @author Kilian Friedrich
     */
    private boolean updateErrorMessage(String value) {

        if(value.isEmpty())
            errField.setText("Enter a username.");
        else if(!value.matches("[-._a-zA-Z0-9]+"))  // checks if only valid characters are in the username
            errField.setText("Only use \"-\", \".\", \"_\", numbers and chars.");
        else if(loggedIn(value))  // checks if a user with the username is already logged in (using NetInterface)
            errField.setText("User " + value + " does already exist.");
        else {  // no error
            errField.setText("");
            return false;  // false = no error
        }

        return true;  // true = error

    }

    /**
     * Gets called when "Sign-Up" (see res/fxml/signup.fxml) is pressed.
     * Signs the user up after checking the validity of his username (see {@link #updateErrorMessage(String) updateErrorMessage(value)})
     * and redirects him to the lobby (see res/fxml/lobby.fxml and {@link Client#loadLobby()}.
     *
     * @see #updateErrorMessage(String) - checks the validity of a username
     * @see NetInterface#signup(String) - signs a user up
     * @see Client#loadLobby() - loads the lobby scene
     *
     * @author Kilian Friedrich
     */
    @FXML public void signup() {

        if(!updateErrorMessage(usernameField.getText()) && signup(usernameField.getText())) {

            Client.loadLobby();

        }

    }

}
