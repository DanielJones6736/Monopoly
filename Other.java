
/**
 * Write a description of class Jail here.
 * jail not fully implemented but will hold player for 3 turns and pay $50 at end of 3rd turn
 * 
 * @author (Johnathan Evans, Daniel Jones)
 * @version (3/29/19)
 */
public class Other extends Space
{
    public Other(String c,String n)
    {
        super(c,n);
        //color/type, name
    }

    //below are classes that must be implemented since the game class is calling classes under Space, not properties
    public int getRent()
    {return 0;}

    public int getPrice()
    {return 0;}

    public boolean getIsMortgaged()
    {return false;}

    public int getMortgage()
    {return 0;}

    public int getHouses()
    {return 0;}
    
    public int getHotel()
    {return 0;}
    
    public void mortgage()
    {}

    public void addHouse()
    {}

    public int housePrice()
    {return 0;}

    public void clear()
    {}

    public String toString()
    {
        return super.toString()+"\n\n";
    }
}
