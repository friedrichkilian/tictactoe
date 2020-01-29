package net;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class TicTacToeClient extends Client {

    protected String serverResponse = null;

    public TicTacToeClient(String pServerIP, int pServerPort) { super(pServerIP, pServerPort); }

    public synchronized void processMessage(String pMessage) {

        serverResponse = pMessage;
        notify();

    }

    public synchronized String signup(String username) {

        send("login " + username);

        try {
            wait(5000);
        } catch(InterruptedException ignored) {}

        if(serverResponse == null)
            new TimeoutException("Couldn't sign up: Timeout").printStackTrace();

        String returnString = serverResponse;
        serverResponse = null;
        return returnString;

    }

    public String join(UUID gameID) {

        send("join " + gameID);

        try {
            wait(5000);
        } catch(InterruptedException ignored) {}

        if(serverResponse == null)
            new TimeoutException("Couldn't join game " + gameID + ": Timeout").printStackTrace();

        String returnString = serverResponse;
        serverResponse = null;
        return returnString;

    }

    public String create(String game) {

        send ("create " + game);

        try {
            wait(5000);
        } catch(InterruptedException ignored) {}

        if(serverResponse == null)
            new TimeoutException("Couldn't create game " + game + ": Timeout").printStackTrace();

        String returnString = serverResponse;
        serverResponse = null;
        return returnString;

    }

    public String pick(int field) {

        this.send("pick " + field);

        try {
            wait(5000);
        } catch(InterruptedException ignored) {}

        if(serverResponse == null)
            new TimeoutException("Couldn't pick field " + field + ": Timeout").printStackTrace();

        String returnString = serverResponse;
        serverResponse = null;
        return returnString;

    }

    public void leave () { send("leave"); }

}
