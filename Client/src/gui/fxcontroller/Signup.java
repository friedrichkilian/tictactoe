package gui.fxcontroller;

import gui.fxcontroller.lobby.Lobby;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gui.FXApplication;
import net.TicTacToeClient;

import java.io.IOException;

/**
 * The controller class for res/fxml/signup.fxml, the sign-up prompt for TicTacToe.
 * Adds functionality like sign-up or error messages to the fxml.
 * Gets called by {@link FXApplication#start(Stage)}.
 * 
 * @author Kilian Friedrich
 */
public class Signup {

    /**
     * The text field in which the user types his username
     * Gets assigned by the {@link javafx.fxml.FXMLLoader} when the signup prompt gets loaded by {@link FXApplication#start(Stage)}
     * 
     * @see #updateErrorMessage(String) - prints error messages on wrong input (shown in {@link #errField})
     */
    @FXML public TextField usernameField;

    /**
     * The text field in which error messages are displayed.
     * Gets assigned by the {@link javafx.fxml.FXMLLoader} when the signup prompt gets loaded by {@link FXApplication#start(Stage)}
     * 
     * @see #updateErrorMessage(String) - updates this field's content
     */
    @FXML public Text errField;

    /**
     * The window in which the Tic Tac Toe game takes place.
     * The lobby scene will be loaded in that object.
     *
     * @see #signup() - loading the {@link Lobby} object into the window
     */
    protected Stage primaryStage;

    /**
     * Represents connection to the Tic Tac Toe host server.
     * Requests will be send via this object, the object itself will send request using the TTTP (see {@link TicTacToeClient}).
     *
     * @see TicTacToeClient - describes the TTTP (TicTacToe Protocol)
     */
    protected TicTacToeClient serverConnection;

    /**
     * Default constructor.
     * Stores assigned parameters in local variables
     *
     * @param primaryStage - the window in which the TicTacToe game takes place
     * @param serverConnection - represents the connection to the Tic Tac Toe host server
     *
     * @see #primaryStage - the window in which the TicTacToe game takes place
     * @see #serverConnection - represents the connection to the Tic Tac Toe host server
     */
    public Signup(Stage primaryStage, TicTacToeClient serverConnection) {

        this.primaryStage = primaryStage;
        this.serverConnection = serverConnection;

    } // END constructor Signup(String, TicTacToeClient)

    /**
     * Gets called when the variables {@link #usernameField} and {@link #errField} are set.
     * Adds a listener to the username text field which checks the validity of user names.
     * 
     * @see #usernameField - the field in which the user types its username
     */
    @FXML public void initialize() {
        
        // ignores the observed TextField (already stored in usernameField) and old value (not needed) on updateErrorMessage(String) call
        usernameField.textProperty().addListener((ignored1, ignored2, newValue) -> updateErrorMessage(newValue));

    } // END function initialize()

    /**
     * Gets called on usernameFields update (see res/fxml/signup.fxml) or when "Sign-Up" is pressed.
     * Updates the error message in fx:errField (see res/fxml/signup.fxml).
     *
     * @param value the new value of the username field
     * @return true when the username is invalid / false when the username is valid
     *
     * @see TextField#setText(String) - sets the text of a text field
     * @see #errField - the text field in which the error message is written
     */
    private boolean updateErrorMessage(String value) {

        if(value.isEmpty())
            errField.setText("Enter a username.");
        else {
            errField.setText("");
            return false;  // false = no error
        }

        return true;  // true = error

    } // END function updateErrorMessage(String)

    /**
     * Gets called when "Sign-Up" (see res/fxml/signup.fxml) is pressed.
     * Signs the user up after checking the validity of his username (see {@link #updateErrorMessage(String) updateErrorMessage(value)})
     * and redirects him to the lobby (see res/fxml/lobby.fxml).
     *
     * @see #updateErrorMessage(String) - checks the validity of a username
     */
    @FXML public void signup() {

        if(!updateErrorMessage(usernameField.getText())) {

            String serverResponse = serverConnection.signup(usernameField.getText());

            if(serverResponse != null && serverResponse.startsWith("gamelobbies")) {

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/lobby/lobby.fxml"));
                    fxmlLoader.setController(new Lobby(primaryStage, serverConnection, serverResponse));

                    primaryStage.setScene(new Scene(fxmlLoader.load()));

                } catch (IOException e) {
                    e.printStackTrace();
                    primaryStage.close();
                }

            } // END if(serverResponse != null && ...)

        } // END if(!updateErrorMessage(...))

    } // END function signup()

} // END class Signup
