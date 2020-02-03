import java.util.UUID;
/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Game
{
    
    Player guest;
    Player host;
    UUID gameID;
    String name;
    int[] field = new int[9]; // -1: host picked it - 0: nobody picked it - 1: guest picked it
    int active;
    int first, nextFirst; // -1: host - 1: guest
    /**
     * Constructor for objects of class Game
     */
    public Game(Player pHost, UUID pGameID, String pName)
    {
        host = pHost;
        gameID = pGameID;
        name = pName;
    }

    public void setGuest(Player pGuest)
        //Da der Guest erst im Nachhinein in das Spiel geht muss er später erst initialisiert werden.
    {
        guest = pGuest;
    }

    public void pickStarter()
        //Wählt zufällig einen der beiden Spieler aus, der den ersten Zug machen darf. Nachdem das Spielfeld geräumt wurde, sollte diese Methode aufgerufen werden.
    {
        if(nextFirst != 0) {
            first = nextFirst;
            nextFirst = - nextFirst;

            active = first;

        } else if (Math.random()<0.5 )
        {
            active = -1;
            first = -1;
            nextFirst = 1;
        } 
        else 
        {
            active = 1;
            first = 1;
            nextFirst = -1;
        }
    }

    public void reset()
        //Nachdem ein Spiel beendet wurde, aber noch kein Spieler das Spiel verlassen hat, kann hiermit das Feld für eine anfolgende Partie geräumt werden.
    {
        active = first;
        for (int i=0; i<9;i++)
        {
            field[i] = 0;
        }
    }

    public void update(int pField)
        //Setzt ein Zeichen in die ausgewählte Zelle wenn sie frei ist. Wird ein Zeichen gesetzt, so ist der andere Spieler dran
    {
        if (cellEmpty(pField))
        {
            field[pField] = active;
            //Spielerzugwechsel
            active = - active;
        } else {
            throw new RuntimeException();
        }
        win();
    }

    public boolean cellEmpty(int pField)
        //Meldet zurück ob das Feld frei ist
    {
        return field[pField] == 0;
    }

    public void win()
    {
        // Das sind alle Fälle in denen man gewinnen kann.
        if(field[0] == 1 && field[1] == 1 && field[2] == 1 ||
                field[3] == 1 && field[4] == 1 && field[5] == 1 ||
                field[6] == 1 && field[7] == 1 && field[8] == 1 ||
                field[0] == 1 && field[3] == 1 && field[6] == 1 ||
                field[1] == 1 && field[4] == 1 && field[7] == 1 ||
                field[2] == 1 && field[5] == 1 && field[8] == 1 ||
                field[0] == 1 && field[4] == 1 && field[8] == 1 ||
                field[2] == 1 && field[4] == 1 && field[6] == 1 ||
                field[0] == -1 && field[1] == -1 && field[2] == -1 ||
                field[3] == -1 && field[4] == -1 && field[5] == -1 ||
                field[6] == -1 && field[7] == -1 && field[8] == -1 ||
                field[0] == -1 && field[3] == -1 && field[6] == -1 ||
                field[1] == -1 && field[4] == -1 && field[7] == -1 ||
                field[2] == -1 && field[5] == -1 && field[8] == -1 ||
                field[0] == -1 && field[4] == -1 && field[8] == -1 ||
                field[2] == -1 && field[4] == -1 && field[6] == -1) {
            
             reset();
             pickStarter();
            
        } else {
            
            boolean allSet = true;
            for(int i = 0; i < 9; i++)
                if(state[i] == 0)
                    allSet = false;
            
            if(allSet) {
                reset();
                pickStarter();
            }
        }
    }
    
    public Player getGuest(){return guest;}
    public Player getHost(){return host;}
    public UUID getGameID(){return gameID;}
    public String getName(){return name;}
    public int getActive(){return active;}
    public boolean isActive(Player p) {

        return (active == -1 && p == host) || (active == 1 && p == guest);

    }

}
