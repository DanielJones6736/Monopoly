
/**
 * this class is the main runner for the whole game.
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @version (4/13/19)
 */
import java.util.*;
import java.lang.*;
public class Game
{
    private Board board;
    private int freeParking;//for free parking
    public static ArrayList<Player> players;
    private int playerTurn;//tracks who's turn it is
    private int playerNumber, numberOfAI;   //tracks number of human players and AI
    private int numberOfPlayers;    //tracks number of total players
    public static int numPlayerTurns=0;
    private TextUI userInt=new TextUI();
    public Game()//for now, assume 4 players
    {
        //chooses total players including players
        System.out.println("How many total players would you like? ");
        Scanner startUp=new Scanner(System.in);
        numberOfPlayers=startUp.nextInt();
        //chooses number of AI out of total players
        System.out.println("How many of the total players would you like to be an AI? ");
        numberOfAI=startUp.nextInt();


        board=new Board();
        freeParking=0;
        players=new ArrayList<>();
        playerTurn=0;
        playerNumber=numberOfPlayers-numberOfAI; 
        Scanner kb=new Scanner(System.in);
        for (int i=0;i<playerNumber;i++)
        {
                System.out.println("Player "+(i+1)+", Write your name");
                String name=kb.nextLine();
                players.add(new Player(name));
            
        }
        for (int j=0;j<numberOfAI;j++)
        {

            players.add(new AI("AI"+(j+1)));
        }

        main();
    }
    

    public static ArrayList<Player> getPlayers()//for outside classes like tester and UI
    {return players;}

    public static int getTurns()
    {return numPlayerTurns;}
    
    public void main()//runs game
    {
        //initialize players and names for later versions
        Scanner kb=new Scanner(System.in);
        while (players.size()>1)
        {
            System.out.println("\n\n"+players.get(playerTurn).getName()+" it's your turn!");
            //dice
            Random d1=new Random();
            Random d2=new Random();
            boolean doubles=false;
            int roll1=(d1.nextInt(6)+1);
            int roll2=(d2.nextInt(6)+1);
            int roll=roll1+roll2;

            if (roll1==roll2)
            {
                doubles=true;
                System.out.println("Congrats! You rolled "+roll1+" twice! move "+roll+" spaces");
            }
            else
            System.out.println("You rolled a "+roll);
            //movement checks jail status too

            if (players.get(playerTurn).getJailTime()>1&&players.get(playerTurn).getFreeCard()>0)
            {
                players.get(playerTurn).useFreeCard();
            }

            if (players.get(playerTurn).getJailTime()>1)//try and symplify
            {
                if (doubles)
                {
                    System.out.println("You got out of Jail");
                    players.get(playerTurn).updatePos(roll);
                    players.get(playerTurn).leaveJail();
                }
                else
                {
                    System.out.println("Still in jail");
                    players.get(playerTurn).spendJailTime();
                }
            }
            else if (players.get(playerTurn).getJailTime()==1)
            {
                if (doubles)
                {
                    System.out.println("You got out of Jail");
                    players.get(playerTurn).updatePos(roll);
                    players.get(playerTurn).leaveJail();
                }
                else
                {
                    System.out.println("Time is up! pay 50$");
                    players.get(playerTurn).updateMon(-50);
                    freeParking+=50;
                    players.get(playerTurn).leaveJail();
                }
            }
            else if (players.get(playerTurn).getJailTime()==0)
                players.get(playerTurn).updatePos(roll);
            //the player class adds money if it passes go
            //calls corresponding method for space landed on
            spaceFunction(board.getSpot(players.get(playerTurn).getPos()),roll);//finish all of the function code
            //prints out player data
            System.out.println(players.get(playerTurn).toString()); 
            //mortgages
            players.get(playerTurn).mortgageProperty();

            //add houses/hotels
            players.get(playerTurn).buyHouse();

            if (players.get(playerTurn).toTrade())
                trade();

            //this stays at the bottom of the code
            int lastTurn=playerTurn;//for avoiding array index logic errors
            if (playerTurn>=players.size()-1)
                playerTurn=0;
            else
                playerTurn=playerTurn+1;
            if (doubles)
            {
                playerTurn=lastTurn;
                System.out.println("You rolled doubles, go again");
                continue;
            }
            if (players.get(lastTurn).checkBankruptcy())
            {
                players.get(lastTurn).bankrupt();
                System.out.println(players.get(lastTurn).getName()+" You are OUT!");
                players.remove(lastTurn);
                if (playerTurn>=players.size())
                {
                    playerTurn=0;//to aviod index errors
                }
            }
            if (players.size()==1)
            {
                System.out.println(players.get(playerTurn).getName()+" You Won!!!");
                System.out.println("It took " + numPlayerTurns + " turns to win");
                
                break;
            }

            numPlayerTurns++;
            if(numPlayerTurns%4==0){    //prints out board & player info every 4 turns
                //if there is a better way to fix this I'm all ears
                userInt.boardPrinter();
                userInt.playerPositionPrinter();
             
            }
        }
    }

    //this reads the var color to determine what kind of space a player lands on
    //others include: GO, CHEST, TAX, RAIL, utility, CHANCE, PARK, JAIL, GOJAIL
    public void spaceFunction(Space s, int roll)
    {
        System.out.println(s.toString());
        switch (s.getColor())
        {
            case "GO":
            go();
            break;
            case "CHEST":
            card(false,roll);//isChance=false
            break;
            case "CHANCE":
            card(true,roll);//isChance=true
            break;
            case "TAX":
            tax();
            break;
            case "PARK":
            park();
            break;
            case "JAIL":
            jail();
            break;
            case "GOJAIL":
            goJail();
            break;
            case "RAIL":
            rail(s);//was made seperate from other properties because it had diferent parameters to be passed on
            break;
            case "UTILITY":
            utility(s,roll);
            break;
            default:
            property(s);
            break;
        }
    }

    public void go()
    {
        System.out.println("You landed on GO");
    }

    public void card(boolean chance,int roll)//to diferentiate from chest and chance, the roll is needed to call space function again
    {
        Deck deck=new Deck();
        Card draw;
        do
        {
            draw=deck.draw();
        }
        while (draw.isChance()!=chance);
        System.out.println(draw.getDescription());
        switch (draw.getSignature())
        {//TAX, MOVE, JAIL, JAILFREE
            case "TAX":
            players.get(playerTurn).updateMon(draw.getValue());
            if (draw.getValue()<0)
                freeParking-=draw.getValue();
            break;
            case "MOVE":
            int hold=players.get(playerTurn).getPos();
            players.get(playerTurn).setPosition(draw.getValue());
            if (draw.getValue()<hold)
            {
                players.get(playerTurn).passGo();
            }
            spaceFunction(board.getSpot(players.get(playerTurn).getPos()),roll);//so if they land on a space it offers to buy
            break;
            case "JAIL":
            players.get(playerTurn).goToJail();
            players.get(playerTurn).setPosition(10);
            break;
            case "JAILFREE":
            players.get(playerTurn).addFreeCard();
            break;
        }
    }

    public void tax()
    {
        //say statement and take 200 and add to freeParking
        System.out.println("Darn, You have been Taxed 200$");
        players.get(playerTurn).updateMon(-200);
        freeParking+=200;
    }

    public int isOwned(Space s)//checks if anyone ownes a property
    {
        int owned=-1;//-1 means unowned, 0=owned by current player, 1, owned by other player
        for (int i=0;i<players.size();i++)
        {
            ArrayList<Space> properties= new ArrayList<>(players.get(i).getProperties());
            for (int p=0;p<properties.size();p++)
            {
                if (properties.get(p).toString().equals(s.toString()))
                {
                    if (i==playerTurn)
                        owned=0;
                    else
                        owned=1;
                }
            }
        }
        return owned;
    }

    public void property(Space s)
    {
        int owned=isOwned(s);
        if (owned==1)//owned
        {
            if (s.getIsMortgaged())
                System.out.println("Someone ownes this but has mortgaged it, lucky you!");
            else
            {
                System.out.println("Oh no! someone ownes this, pay "+s.getRent());
                players.get(playerTurn).updateMon(-1*s.getRent());
                //finds the player who owns the spot
                for (int i=0;i<players.size();i++)
                {
                    ArrayList<Space> properties= new ArrayList<>(players.get(i).getProperties());
                    for (int p=0;p<properties.size();p++)
                    {
                        if (properties.get(p).toString().equals(s.toString()))
                        {
                            players.get(i).updateMon(s.getRent());
                        }
                    }
                }
            }
            //then pay
        }
        else if (owned==0)//owned by current player
        {
            System.out.println("You own this property");
        }
        else if (owned==-1)//unowned
        {
            System.out.println("Congrats, you have landed on "+s.getName()+
                    " Would you like to buy for "+s.getPrice()+"?\nBalance: "+players.get(playerTurn).getMon()+"\n y/n?");

            String decision=players.get(playerTurn).buyProperty();
            if (decision.equals("y"))
            {
                System.out.println("You chose to buy");
                players.get(playerTurn).updateMon(-1*s.getPrice());
                players.get(playerTurn).addProperty(s);
            }         
            else if (decision.equals("n"))
            {
                System.out.println("You chose not to buy");
            }

        }

    }

    public void utility(Space s,int r)
    {
        //find out how many of these are owned by any player
        int owned=isOwned(s);
        if (owned==1)//owned
        {
            if (s.getIsMortgaged())
                System.out.println("Someone ownes this but has mortgaged it, lucky you!");
            else
            {
                //must find how many of these are owned and by who
                int owner=-1;
                for (int i=0;i<players.size();i++)
                {
                    ArrayList<Space> properties= new ArrayList<>(players.get(i).getProperties());
                    for (int p=0;p<properties.size();p++)
                    {
                        if (properties.get(p).toString().equals(s.toString()))
                        {
                            owner=i;
                        }
                    }
                }
                ArrayList<Space> prop= new ArrayList<>(players.get(owner).getProperties());
                int numOwned=0;
                for (Space i:prop)
                {
                    if (i.getColor().equals("UTILITY"))
                        numOwned++;
                }
                int rent=0;
                if (numOwned==1)
                    rent=4*r;
                else if (numOwned==2)
                    rent=10*r;
                System.out.println("Oh no! someone ownes this, pay "+rent);
                players.get(playerTurn).updateMon(-1*rent);
                //finds the player who owns the spot
                players.get(owner).updateMon(rent);
            }
            //then pay
        }
        else if (owned==0)//owned by current player
        {
            System.out.println("You own this property");
        }
        else if (owned==-1)//unowned
        {
            System.out.println("Congrats, you have landed on "+s.getName()+
                    " Would you like to buy for "+s.getPrice()+"?\nBalance: "+players.get(playerTurn).getMon()+"\n y/n?");
            String decision=players.get(playerTurn).buyProperty();
            if (decision.equals("y"))
            {
                System.out.println("You chose to buy");
                players.get(playerTurn).updateMon(-1*s.getPrice());
                players.get(playerTurn).addProperty(s);
            }         
            else if (decision.equals("n"))
            {
                System.out.println("You chose not to buy");
            }
        }
    }

    public void rail(Space s)//in progress
    {
        int owned=isOwned(s);
        if (owned==1)//owned
        {
            if (s.getIsMortgaged())
                System.out.println("Someone ownes this but has mortgaged it, lucky you!");
            else
            {
                //must find how many of these are owned and by who
                int owner=-1;
                for (int i=0;i<players.size();i++)
                {
                    ArrayList<Space> properties= new ArrayList<>(players.get(i).getProperties());
                    for (int p=0;p<properties.size();p++)
                    {
                        if (properties.get(p).toString().equals(s.toString()))
                        {
                            owner=i;
                            System.out.println("Owner: "+i);
                        }
                    }
                }
                ArrayList<Space> prop= new ArrayList<>(players.get(owner).getProperties());
                int numOwned=0;
                for (Space i:prop)
                {
                    if (i.getColor().equals("RAIL"))
                        numOwned++;
                }
                int rent=0;
                if (numOwned==1)
                    rent=25;
                else if (numOwned==2)
                    rent=50;
                else if (numOwned==3)
                    rent=100;
                else if (numOwned==4)
                    rent=200;
                System.out.println("Oh no! someone ownes this, pay "+rent);
                players.get(playerTurn).updateMon(-1*rent);
                //finds the player who owns the spot
                players.get(owner).updateMon(rent);
            }
            //then pay
        }
        else if (owned==0)//owned by current player
        {
            System.out.println("You own this property");
        }
        else if (owned==-1)//unowned
        {
            System.out.println("Congrats, you have landed on "+s.getName()+
                    " Would you like to buy for "+s.getPrice()+"?\nBalance: "+players.get(playerTurn).getMon()+"\n y/n?");
            String decision=players.get(playerTurn).buyProperty();
            if (decision.equals("y"))
            {
                System.out.println("You chose to buy");
                players.get(playerTurn).updateMon(-1*s.getPrice());
                players.get(playerTurn).addProperty(s);
            }         
            else if (decision.equals("n"))
            {
                System.out.println("You chose not to buy");
            }
        }
    }

    public void park()
    {
        //give tax money
        if (freeParking==0)
        {
            System.out.println("FREE PARKING!!! Gain 100$");
            players.get(playerTurn).updateMon(100);
        }
        else
        {
            System.out.println("FREE PARKING!!! Gain "+freeParking+"$");
            players.get(playerTurn).updateMon(freeParking);
        }
        freeParking=0;

    }

    public void jail()
    {
        //if visiting, nothing
        if (players.get(playerTurn).getJailTime()>1)//try and symplify
        {
            System.out.println("Still in jail");
        }
        else 
        {
            System.out.println("Dont worry... youre just visiting");
        }
    }

    public void goJail()
    {
        //send to jail
        System.out.println("Uh Oh... youre in jail. Good luck rolling doubles!");
        players.get(playerTurn).goToJail();
        players.get(playerTurn).setPosition(10);
    }

    public void trade()//will only work for player to player trades
    {
        /**
        Scanner kb=new Scanner(System.in);

        System.out.println("pick the property to trade:");
        Space offer=players.get(playerTurn).getProperty(players.get(playerTurn).pickProperty()-1);
        System.out.println("Pick the player by name:(type the name exactly)");
        int player=-1;
        String choice=kb.nextLine();
        for (int i=0;i<players.size();i++)
        {
        if (players.get(i).getName().equals(choice))
        {
        player=i;
        break;
        }
        }
        if (player!=-1)
        {
        System.out.println("Pick the player's Property:");
        Space trade=players.get(player).getProperty(players.get(player).pickProperty()-1);
        System.out.println("The offer is:\n"+offer.toString()+" For:\n"+trade.toString()+"\nDo you accept? y/n");
        if (kb.nextLine().equals("y"))
        {
        players.get(playerTurn).addProperty(trade);
        players.get(playerTurn).removeProperty(offer);
        players.get(player).addProperty(offer);
        players.get(player).removeProperty(trade);
        }
        }

         */
        System.out.println("Time to make your offer...");
        Trade offer=players.get(playerTurn).makeTrade();//make trade

        int player=-1;
        String playerListHolder="\n";
        for (int i=0;i<players.size();i++)
        {            
           System.out.println((i)+":\n"+players.get(i).toString()+"\n");
        }
        player=players.get(playerTurn).pickPlayer();//takes int that is parallel to players arraylist
        System.out.println("Time to make your request...");
        Trade request=players.get(player).makeTrade();//make trade
        System.out.println("The trade is:\n"+offer.toString()+"\nFor:\n"+request.toString());
        if (players.get(playerTurn).acceptTrade()==true&&players.get(player).acceptTrade()==true)//players choose to trade or not
        {
            players.get(playerTurn).updateMon(offer.getAmmount()*-1);
            players.get(playerTurn).updateMon(request.getAmmount());
            players.get(player).updateMon(offer.getAmmount());
            players.get(player).updateMon(request.getAmmount()*-1);
            if (offer.getProperty()!=null)
            {
                players.get(playerTurn).removeProperty(offer.getProperty());
                players.get(player).addProperty(offer.getProperty());
            }
            if (request.getProperty()!=null)
            {
                players.get(playerTurn).addProperty(request.getProperty());
                players.get(player).removeProperty(request.getProperty());
            }
        }
    }

}
