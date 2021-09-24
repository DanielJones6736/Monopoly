
/**
 * Write a description of class AI here.
 * subclass of Player Class so uses mostly same methods
 * ovrrides decision making methods in favor of AI choices (mostly logic, small randomness)
 *
 * Much of the logic for buying property comes from https://www.wikihow.com/Win-at-Monopoly
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @version (4/13/19)
 */

import java.util.*;
import java.lang.*;
public class AI extends Player
{
    private double TURNSPERGO=5.714;
    private Board tracker=new Board();
    private ArrayList<ArrayList<String>> enemyMonopoly=new ArrayList<ArrayList<String>>(4);
    /**
     * Constructor for objects of class AI
     */
    public AI(String n)
    {
        // initialise instance variables
        super(n);
    }

    /*
     * overrides monopoly in player class to check if buying a property gets it a monopoly
     * takes in property to buy and if AI also owns 2 other color then will have monopoly
     */
    public boolean checkMonopoly(Space s)
    {
        String signature=s.getColor();
        int ct=0;
        if(super.checkMonopoly(s)) return true;
        if ((signature.equals("UTILITY")||signature.equals("RAIL")))
            return false;
        for (Space i: super.getProperties())
        {
            if (i.getColor().equals(signature))
            {
                ct++;
            }
        }
        if ((signature.equals("BROWN")||signature.equals("BLUE"))&&ct==1)   //changed to one from 2
            return true;
        if (ct==2)  //changed to two from 3
            return true;
        return false;
    }

    /*
     * Uses advanced logic to buy properties:
     * doesn't buy utilites
     * buys Orange and Red
     * buys for monopolies
     * buys often at start
     * randomly rolls for weaker properties in mid-late game
     */
    //ovrrides method in Player class
    public String buyProperty()
    {

        Space thingProperty = tracker.getSpot(super.getPos());

        if(thingProperty.getColor()=="UTILITY"){    //in majority cases Utilities just suck with max roll and both owned 120 rent
            return "n";
        }

        else if(thingProperty.getColor()=="ORANGE" || thingProperty.getColor()=="RED"){ //super quick ROI
            if(super.getMon()<250) return "n";
            return "y";
        }

        else if(checkMonopoly(thingProperty)){     //checks if monopoly using Space (gets space from board class)
            //implement denying others monopolies
            if(super.getMon()-thingProperty.getPrice()<0){  //if property forces mortgage then go for it
                mortgageProperty(Math.abs(super.getMon()-thingProperty.getPrice()));    //mortgages property for price needed
                return "y";
            }
            else{   //otherwise just straight buy
                return "y";
            }
        }

        else if(Game.getTurns()<(2*TURNSPERGO*(Game.getPlayers().size()))-1){   //if total current turns is less than all players around board twice
            if(super.getMon()-thingProperty.getPrice()<0){  //don't buy if makes bankrupt
                return "n";
            }
            else{   //otherwise buy
                return "y";
            }
        }

        else if(thingProperty.getColor()=="RAIL"){  //RR logic
            if(super.getProperties().size()<5){    //buy if total properties<5
                return "y";
            }
            //implement if other player owns 3RR
        }

        else if(thingProperty.getColor()=="YELLOW" || thingProperty.getColor()=="GREEN"){   //preferable but not always buy
            Random toBuyOrNot=new Random();
            int chooseP=toBuyOrNot.nextInt(8);  //5out8 chance to buy in late game
            if(chooseP<5) return "y";
            else return "n";
        }
        else{
            Random toBuyOrNot=new Random();
            int chooseP=toBuyOrNot.nextInt(10);  //4out10 chance to buy in late game
            if(chooseP<4) return "y";
            else return "n";
        }
        return "n"; //return no in case all else somehow fails
    }

    /*
     * used to not deal with mortgages at end of every turn
     * if bankrupt just mortgages everything it owns
     */
    public void mortgageProperty(){
        if(super.getMon()>1250)
        {
            for (Space i: super.getProperties())
            {
                if(super.getMon()<=1000){
                    return;
                }
                if(i.getIsMortgaged()==true){
                    i.mortgage();
                    super.updateMon(-((11*i.getMortgage())/10));
                }
            }
        }
        if(super.getMon()<200 && Game.getTurns()>3*TURNSPERGO*(Game.getPlayers().size())-1){   //if money less than 1000 and players have statistically gone around the board 3times
            for (Space i: super.getProperties())
            {
                if(super.getMon()>=1000){
                    return;
                }
                if(i.getIsMortgaged()==false){
                    i.mortgage();
                    super.updateMon(i.getMortgage());
                }
            }
        }
        if(super.getMon()<0) 
        {
            for (Space i: super.getProperties())
            {
                if(i.getIsMortgaged()==false){
                    i.mortgage();
                    super.updateMon(i.getMortgage());
                }
            }
        }
    }

    /*
     * used for mortgaging to buy something else
     * given amount needed for buying mortgages:
     * a lone property that has no monpoly
     * the weakest properties like brown, babyblue, and pink
     * weaker properties like dark blue, green, yellow
     * if all else then mortgages any other properties
     * 
     */
    public void mortgageProperty(int needed)
    {
        int possibleMort=0;
        //mortgages single property that fufills need
        for (Space i: super.getProperties())
        {
            if(i.getMortgage()>=needed && !super.checkMonopoly(i) && i.getIsMortgaged()==false){ //if property is not monopoly and fufills 
                i.mortgage();
                super.updateMon(i.getMortgage());
                return;
            }
        }

        //mortgages weakest properties
        for (Space i: super.getProperties())
        {
            if(possibleMort>=needed){
                return;
            }
            if((i.getColor()=="BROWN" || i.getColor()=="BABYBLUE" || i.getColor()=="PINK") && i.getIsMortgaged()==false){
                i.mortgage();
                super.updateMon(i.getMortgage());
                possibleMort+=i.getMortgage();
            }
        }

        //mortgages weak properties
        for (Space i: super.getProperties())
        {
            if(possibleMort>=needed){
                return;
            }
            if((i.getColor()=="BLUE" || i.getColor()=="GREEN" || i.getColor()=="YELLOW") && i.getIsMortgaged()==false){
                i.mortgage();
                super.updateMon(i.getMortgage());
                possibleMort+=i.getMortgage();
            }
        }

        //mortgages rest of properties as last resort
        for (Space i: super.getProperties())
        {
            if(possibleMort>=needed){
                return;
            }
            if(i.getIsMortgaged()==false){
                i.mortgage();
                super.updateMon(i.getMortgage());
                possibleMort+=i.getMortgage();
            }
        }
    }

    /*
     * if low on money doesn't buy a house
     * tries to buy upto 3 houses in order:
     * tries to buy a house on good properties like orange, red, yellow
     * tries to buy house on other properties
     * tries to buy a 4th house
     * tries to buy a hotel (this would mean it is really ahead in the game)
     */
    public void buyHouse()
    {
        //if low on funds just don't buy houses at all
        if(super.getMon()<=250)
        {
            return;
        }
        //for loops could be more efficient but
        //intentionally slightly inefficient to slow down AI to human reading perception

        //tries to buy house on stronger properties
        for (Space i: super.getProperties())
        {
            if(buyHouseRules(i) && (i.getHouses()<=3 && i.getHotel()==0)){  
                if(i.getColor()=="ORANGE" || i.getColor()=="RED"){
                    i.addHouse();
                    super.updateMon(-(i.housePrice()));
                }
                else if(i.getColor()=="YELLOW"){
                    i.addHouse();
                    super.updateMon(-(i.housePrice()));
                }

            }
        }

        //tries to buy houses on weaker properties
        for (Space i: super.getProperties())
        {
            if(buyHouseRules(i) && (i.getHouses()<=3 && i.getHotel()==0)){
                if(i.getColor()=="GREEN" || i.getColor()=="BLUE"){
                    i.addHouse();
                    super.updateMon(-(i.housePrice()));
                }
                else if(i.getColor()=="PINK" || i.getColor()=="BROWN" || i.getColor()=="BABYBLUE"){
                    i.addHouse();
                    super.updateMon(-(i.housePrice()));
                }
            }
        }

        //buys 4th house
        for (Space i: super.getProperties())
        {
            if(buyHouseRules(i) && (i.getHouses()<=4 && i.getHotel()==0))
            {
                i.addHouse();
                super.updateMon(-(i.housePrice()));
            }
        }

        //buys hotel
        for (Space i: super.getProperties())
        {
            if(buyHouseRules(i) && i.getHotel()==0)
            {
                i.addHouse();
                super.updateMon(-(i.housePrice()));
            }
        }
        //if it reaches this it somehow managed to put a hotel on every propery
        //or was low on funds
    }

    /*
     * logic for buying houses
     * if no monopoly then don't buy houses
     * if near end of board and lowish on funds don't buy house
     * if middle of board and low on funds don't buy house
     * if really low don't buy houses
     * otherwise buy ahead
     */
    public boolean buyHouseRules(Space checker){
        //if near end of board save a lot of money
        if(!super.checkMonopoly(checker)){
            return false;
        }
        else if(super.getPos()>27 && super.getMon()-checker.housePrice()<1500)
        {
            return false;
        }
        //if in mid of board save a good amount of money
        else if(super.getPos()>15 && super.getMon()-checker.housePrice()<750)
        {
            return false;
        }
        //if near beginning just don't bankrupt self
        else if(super.getMon()-checker.housePrice()<250)
        {
            return false;
        }
        //otherwise buy away
        return true;
    }

    //overrides goToJail player method
    public void goToJail(){
        super.goToJail();
        if(super.getFreeCard()>0){
            useFreeCard();
        }
        if(Game.getTurns()<3*TURNSPERGO*(Game.getPlayers().size())-1){  //theoretical problem if AI has less than 50 but extremely unlikely
            super.updateMon(-50);
            super.leaveJail();
        }
    }

    public void useFreeCard()
    {
        if(Game.getTurns()<3*TURNSPERGO*(Game.getPlayers().size())-1){
            leaveJail();
            super.loseFreeCard();
        }
        else{
            return;
        }
    }

    public boolean checkOthers(){
        ArrayList<Space> temp;
        Space holder;
        ArrayList<Player> cop = new ArrayList(Game.getPlayers());
        for(int i = 0; i<cop.size()-1; i++){
            temp = new ArrayList(cop.get(i).getProperties());
            for(int j = 0; j<temp.size()-1; j++){
                holder=temp.get(j);
                if(cop.get(i).checkMonopoly(holder)==true){
                    if(holder.getName()=="BROWN") enemyMonopoly.get(i).add("BROWN");
                    else if(holder.getName()=="BABYBLUE") enemyMonopoly.get(i).add("BABYBLUE");
                    else if(holder.getName()=="PINK") enemyMonopoly.get(i).add("PINK");
                    else if(holder.getName()=="ORANGE") enemyMonopoly.get(i).add("ORANGE");
                    else if(holder.getName()=="RED") enemyMonopoly.get(i).add("RED");
                    else if(holder.getName()=="YELLOW") enemyMonopoly.get(i).add("YELLOW");
                    else if(holder.getName()=="GREEN") enemyMonopoly.get(i).add("GREEN");
                    else if(holder.getName()=="BLUE") enemyMonopoly.get(i).add("BLUE");
                }
            }
        }
        return false;
    }

    public boolean toTrade(){
        checkOthers();

        return false;
    }
}
