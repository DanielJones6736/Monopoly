
/**
 * Write a description of class Property here.
 * contains info (rent, num of houses, hotels, price, and name) of a singular Property object
 * can return rent and price as info. Also adds a house/hotel when called
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @version (3/28/19)
 */
public class Property extends Space
{
    private int rent,houses,hotels, price, mortgage;//for simplist version skip the mortgage, houses, and hotels
    private String name;//color is used to determine rent and house price
    private boolean isMortgaged;
    public Property(int p, int r,String c,String n, int m)
    {   
        //order of parameters is price, rent, color(also signature like "RAIL"), name of property, mortgage
        super(c,n); //calls Space class with color and name
        price=p;
        rent=r;
        houses=0;
        hotels=0;
        mortgage=m;
        isMortgaged=false;
    }

    //returns rent of property
    public int getRent()  
    {  
        int houseValue=houses*price;
        int hotelValue=hotels*5*price;
        //impliment code for mortgage with houses here
        return rent+houseValue+hotelValue;
    }
    //returns price of property
    public int getPrice()
    {   
        return price;
    }

    //returns how much money for mortgaging
    public int getMortgage()
    {
        return mortgage;
    }

    //returns if mortgages
    public boolean getIsMortgaged()
    {
        return isMortgaged;
    }

    //returns number of houses
    public int getHouses()
    {
        return houses;
    }
    
    //returns number of hotel
    public int getHotel()
    {
        return hotels;
    }
    
    //adds house or hotel to property
    public void addHouse()
    {   
        if (houses==4&&hotels==0)
        {
            hotels++;
            houses=0;
        } else if (houses<4&&hotels==0)
        {
            houses++;
        }
        else
        {
            System.out.println("ERROR: the maximum of houses and hotels has already been met");
        }
    }

    //mortgages/unmortgages when called
    public void mortgage()
    {
        if (isMortgaged)
            isMortgaged=false;
        else
            isMortgaged=true;
    }

    /*
     * returns house price of property given color
     */
    public int housePrice()
    {
        int ret=0;
        switch (super.getColor())
        {
            case "BROWN":
            ret=50;
            break;
            case "BABYBLUE":
            ret=50;
            break;
            case "PINK":
            ret=100;
            break;
            case "ORANGE":
            ret=100;
            break;
            case "RED":
            ret=150;
            break;
            case "YELLOW":
            ret=150;
            break;
            case "GREEN":
            ret=200;
            break;
            case "BLUE":
            ret=200;
            break;
        }
        return ret;
    }

    /*
     * clears all data regarding ownership for a property
     */
    public void clear()
    {
        houses=0;
        hotels=0;
        isMortgaged=false;
    }

    public String toString()
    {   
        String realestate="";
        if (hotels==1)
        realestate="HOTELS: 1";
        else if (houses>0)
        realestate="HOUSES: "+houses;
        return (super.toString()+" PRICE: "+price+" RENT: "+getRent()+" MORTGAGE: "+mortgage+" IS MORTGAGED: "+isMortgaged+" "+realestate+"\n");
    }
}
