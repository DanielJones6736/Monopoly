
/**
 * Write a description of class Utility here.
 * uses same info for property but with different getRent method 
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @3/26/19)
 */
public class Utility extends Property
{
    public Utility(String n)
    {
        //price, rent, color/type, name, mortgage
        super(150,0,"UTILITY",n,75);
        
    }
    //override getRent method
    public int getRent (int numOwned, int diceRoll)
    {   //applies a rent according to diceRoll depending on number of utilities owned
    if (numOwned==1)
     return 4*diceRoll;
    if (numOwned==2)
     return 10*diceRoll;
    return 0;
    }
}
