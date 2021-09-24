
/**
 * Write a description of class Trade here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Trade
{
    private int ammount;
    private Space property;
    //t
    public Trade(Space s,int a)
    {
        property=s;
        ammount=a;
    }

    public int getAmmount()
    {return ammount;}

    public Space getProperty()
    {return property;}
    
    public String toString()
    {
        if (property==null)
        return "Amount: "+ammount;
        return "Amount: "+ammount+"\nProperty: "+property.toString();
    }
    
}
