
/**
 * Write a description of class Rail here.
 * uses same info for property but with different getRent method 
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @3/29/19
 */
public class Rail extends Property
{
    public Rail(String n)
    {
        //price, rent, color/type, name, mortgage
        super(200,0,"RAIL",n,100);
        
    }
    //override getRent method
    //depending on number of RR owned rent is different amounts
    public int getRent (int numOwned)
    {
        if (numOwned==1)
            return 25;
        if (numOwned==2)
            return 50;
        if (numOwned==3)
            return 100;
        if (numOwned==4)
            return 200;
        return 0;
    }
}
