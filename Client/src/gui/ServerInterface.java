package gui;

public interface ServerInterface {

    default boolean loggedIn(String username) {

        //TODO
        return false;

    }

    default boolean login(String username) {

        //TODO
        return true;

    }

}
