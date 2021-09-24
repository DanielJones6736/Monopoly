
/**
 * Write a description of class Space here.
 * initializes basic data for anything using this class
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @version (3/28/19)
 */
public abstract class Space
{
    private String name;//used to store name
    private String color;//used mainly to group spaces and also
    private int rent;
    // to identify the type of space 
    public Space(String c,String n)
    {
        name=n;
        color=c;
    }

    public String getName()
    {
        return name;
    }

    public String getColor()
    {
        return color;
    }

    public abstract int getRent();
    public abstract int getPrice();
    public abstract boolean getIsMortgaged();
    public abstract int getMortgage();
    public abstract int getHouses();
    public abstract int getHotel();
    public abstract void mortgage();
    public abstract void addHouse();
    public abstract int housePrice();
    public abstract void clear();
   
    public String toString()
    {
        return ("NAME: "+name+" TYPE: "+color);
    }
}
