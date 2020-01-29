package gui;

public interface NetInterface {

    default boolean loggedIn(String username) {

        //TODO
        return false;

    }

    default boolean signup(String username) {

        //TODO
        return true;

    }

}
