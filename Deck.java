
/**
 * holds cards and draws random cards for the game class
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @version (4/13/19)
 */
import java.util.*;
import java.lang.*;
public class Deck
{
    private ArrayList<Card> deck;
    private final boolean CHANCE=true;
    private final boolean CHEST=false;
    public Deck()
    {//TAX, MOVE, JAIL, JAILFREE
        deck=new ArrayList<>();
        deck.add(new Card("Advance to Go. Collect $200",CHANCE,"MOVE",0));
        deck.add(new Card("Get out if jail free card!",CHANCE,"JAILFREE",0));
        deck.add(new Card("Advance to St. Charles Place. If you pass Go, collect $200",CHANCE,"MOVE",11));
        deck.add(new Card("Go to Jail. Go directly to Jail",CHANCE,"JAIL",0));
        deck.add(new Card("Advance to Boardwalk. If you pass Go, collect $200",CHANCE,"MOVE",39));
        deck.add(new Card("Take a trip to Reading Railroad. If you pass Go, collect $200",CHANCE,"MOVE",5));
        deck.add(new Card("Advance to Go. Collect $200",CHEST,"MOVE",0));
        deck.add(new Card("Bank error in your favor. Collect $200",CHEST,"TAX",200));
        deck.add(new Card("Doctor's fees. Pay $50.",CHEST,"TAX",-50));
        deck.add(new Card("From sale of stock you get $50.",CHEST,"TAX",50));
        deck.add(new Card("Life insurance matures – Collect $100",CHEST,"TAX",100));
        deck.add(new Card("Go to Jail. Go directly to Jail",CHEST,"JAIL",0));
        deck.add(new Card("Get out if jail free card!",CHEST,"JAILFREE",0));
    }

    public Card draw()
    {
        Random rndm=new Random();
        return deck.get(rndm.nextInt(deck.size()));
    }
}
