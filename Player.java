
/**
 * Write a description of class Player here.
 * Class holds info like position, money, and property for an object Player.
 * When called updates the information and declares bankruptcy when needed
 * Player numbers range from 0-numberOfPlayer(in game class) but output as +1 of range
 *
 * @Daniel Jones & Johnathan Evans
 * @3/28/19
 */
import java.util.*;
public class Player
{
    private int position,money; //position 0-39, money is updatable variable
    private int STARTINGMONEY=1500; //money will start at 1,500
    private ArrayList<Space> propertyOwned = new ArrayList<>(); //list of properties for the player
    private int jailTime,freeCard;//counts how many rolls hav passed since going to jail and the famous get out of jail free card
    private String name;
    private final int GO=200;
    public Player(String n)
    {   //sets values at player creation
        position=0;
        money=STARTINGMONEY;
        jailTime=0;
        name=n;
        freeCard=0;
    }

    public String getName()
    {return name;}

    public int getPos()
    {    //return position
        return position;
    }

    public Space getProperty(int index)
    {
        return propertyOwned.get(index);
    }

    public int getMon()
    {   //returns amount of money
        return money;
    }

    public ArrayList<Space> getProperties()
    {
        return propertyOwned;
    }

    public void setPosition(int p)
    {
        position=p;
    }

    //moves player according to dice roll from Game class
    public void updatePos(int movement)
    {   
        if(position+movement<40) 
        {   //used before player passes go
            position+=movement;
        }
        else
        {   //used for passing go
            movement-=40-position;
            position=0;
            position+=movement;
            passGo();
        }
    }

    public void passGo()
    {
        money+=GO;
    }

    //takes an integer of money (positive for deposit and neg for withdraw)
    public void updateMon(int $)
    {   
        money+=$;
    }

    public String buyProperty()
    {
        String ret;
        Scanner kb=new Scanner(System.in);
        ret= kb.nextLine();

        return ret;
    }

    /*
     * 
     * checks for any monopoly
     * if has monopoly then asks player to buy a house on something with a monopoly
     * adds houses or asks again depending on user input
     */
    public void buyHouse()
    {
        boolean hasMonopoly=false;
        for (Space i:propertyOwned)
        {
            if (checkMonopoly(i))
                hasMonopoly=true;
        }
        if (hasMonopoly)
        {

            Scanner kb=new Scanner(System.in);
            System.out.println("Would you like to buy houses? y/n");
            if (kb.nextLine().equals("y"))
            {
                System.out.println("Pick something you have a monopoly on");
                int choice=pickProperty();
                if (checkMonopoly(propertyOwned.get(choice-1)))
                {
                    System.out.println("How many houses? price: "+propertyOwned.get(choice-1).housePrice()+" each (buying a fifth automatically puts a hotel down)");
                    int ct=kb.nextInt();
                    for (int i=0;i<ct;i++)
                    {
                        propertyOwned.get(choice-1).addHouse();
                        money-=propertyOwned.get(choice-1).housePrice();
                    }
                }
                else
                {
                    System.out.println("you dont have a monopoly here");
                    buyHouse();
                }
            }

        }
    }

    /*
     * given a space input, checks for all properties with same color
     * if property is a valid place for monpoly and owns all of same color then returns true
     */
    public boolean checkMonopoly(Space s)
    {
        String signature=s.getColor();
        int ct=0;
        if ((signature.equals("UTILITY")||signature.equals("RAIL")))
            return false;
        for (Space i: propertyOwned)
        {
            if (i.getColor().equals(signature))
            {
                ct++;
            }
        }
        if ((signature.equals("BROWN")||signature.equals("BLUE"))&&ct==2)
            return true;

        if (ct==3)
            return true;
        return false;
    }

    /*
     * asks player if they want to mortgage
     * if yes they pick a propety
     * if property can be mortgaged gives player money and mortgages property
     * if already mortgaged umortgages property and takes money from player
     */
    public void mortgageProperty()
    {
        if (propertyOwned.size()>0)
        {
            //text UI stuff
            System.out.println("would you like to deal with mortgages? y/n");
            Scanner kb=new Scanner(System.in);
            if (kb.nextLine().equals("y"))
            {
                int choice=pickProperty();
                if (propertyOwned.get(choice-1).getIsMortgaged()==false)
                {
                    propertyOwned.get(choice-1).mortgage();
                    System.out.println("You mortgaged:\n"+propertyOwned.get(choice-1).getName());
                    money+=propertyOwned.get(choice-1).getMortgage();
                }
                else
                {
                    propertyOwned.get(choice-1).mortgage();
                    System.out.println("You unmortgaged:\n"+propertyOwned.get(choice-1).getName());
                    money-=(11*propertyOwned.get(choice-1).getPrice())/10;  //extra 10% for unmortgaging
                }
                System.out.println(toString());
            }

        }

    }

    /*
     * asks for property number
     * finds property in property owned
     * returns the property's index+1
     */
    public int pickProperty()
    {
        Scanner kb=new Scanner(System.in);
        System.out.println("Which property do you pick? (choose the number)");
        for (int i=0;i<propertyOwned.size();i++)
        {
            System.out.println(i+1+". \n"+propertyOwned.get(i).toString());
        }
        return kb.nextInt();

    }

    public void addProperty(Space propertyGained)
    { //adds property to list
        propertyOwned.add(propertyGained);
    }

    public void removeProperty(Space propertyLost)
    { //removes property from list
        propertyOwned.remove(propertyLost);
    }

    public void goToJail()  
    {   //updates inJail variable to true
        jailTime=3;
    }

    public void leaveJail() 
    {   //updates inJail variable to false
        jailTime=0;
    }

    public int getJailTime()
    {
        return jailTime;
    }

    public void spendJailTime()//adds a roll to the jail time
    {
        jailTime--;
    }

    public void useFreeCard()
    {

        Scanner kb=new Scanner(System.in);
        System.out.println("Use get out of jail free card? y/n");
        if (kb.nextLine().equals("y"))
        {
            leaveJail();
            loseFreeCard();
            System.out.println("Used Free Card, you're out of jail!");
        }

    }

    public void loseFreeCard()
    {
        freeCard--;
    }

    public int getFreeCard()
    {
        return freeCard;
    }

    public void addFreeCard()
    {
        freeCard++;
    }

    public boolean checkBankruptcy()    
    {   //when Player has no money Bankruptcy is true
        if (money<0)    //add logic later for mortgage
            return true;
        return false;
    }

    /*
     * clears all propertyOwned
     */
    public void bankrupt()
    {
        for (Space i:propertyOwned)
        {
            i.clear();
        }
        while (propertyOwned.size()>0)
        {
            propertyOwned.remove(0);
        }
    }

    //trading will start as only money for property, no property for property, money for money,etc.
    public boolean toTrade()
    {
        if (propertyOwned.size()>0)
        {

            System.out.println("Would you like to trade? y/n");
            Scanner kb=new Scanner(System.in);
            if (kb.nextLine().equals("y"))
                return true;

        }
        return false;
    }

    public Trade makeTrade()
    {
        int choice=1;
        int ammount=-1;
        Scanner kb=new Scanner(System.in);
        System.out.println("Enter a money ammount (put 0 for nothing)");
        ammount=kb.nextInt();
        System.out.println("Enter a property ammount (put 0 for nothing)");
        choice=pickProperty()-1;

        if (choice==-1)
        {
            return new Trade(null,ammount);
        }
        return new Trade(propertyOwned.get(choice),ammount);
    }

    public int pickPlayer()
    {
        Scanner kb=new Scanner(System.in);
        System.out.println("Pick the player by the number in front of their name:");
        return kb.nextInt();

    }

    public boolean acceptTrade()
    {
        Scanner kb=new Scanner(System.in);
        System.out.println(name+" do you accept? y/n");
        if (kb.nextLine().equals("y"))
            return true;
        return false;
    }

    public String toString()
    {   //debugging method for easy access of information (to-be removed)
        String owned="";
        for (Space i:propertyOwned)
        {
            owned+=i.toString()+" ";
        }
        return (name+":\nPOSITION: "+position+" MONEY: "+money+" JAILSTATUS: "+jailTime+" \nOWNED: \n"+owned);
    }
}
