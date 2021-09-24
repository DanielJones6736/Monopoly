/**
 * Write a description of class Board here.
 * Contains instance variable spots of all Space on the Board and what they do.
 * Has methods for accessing variable spots or individual Space
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @version (3/28/19)
 */
import java.lang.*;
import java.util.*;
public class Board
{
    private Space[] spots;  //declares instance variable of properties on board

    /*
     * initializes spots with hard data for all locations
     * has data like type, color, price,etc.
     */
    public Board()
    {
        spots=new Space[40];    //initializes Board to blank. 
        //spots filled with actual data of spaces according to Object properties
        //others include: GO, CHEST, TAX, RAIL, UTILITY, CHANCE, PARK, JAIL, GOJAIL
        spots[0]=new Other("GO","GO");
        spots[1]=new Property(60,2,"BROWN","Mediterranean Avenue",30);
        spots[2]=new Other("CHEST", "Community Chest");
        spots[3]=new Property(80,4,"BROWN","Baltic Avenue",30);
        spots[4]=new Other("TAX","Income Tax");
        spots[5]=new Rail("Reading RR");
        spots[6]=new Property(100,6,"BABYBLUE","Oriental Avenue",50);
        spots[7]=new Other("CHANCE","Chance");
        spots[8]=new Property(100,6,"BABYBLUE","Vermont Avenue",50);
        spots[9]=new Property(120,8,"BABYBLUE","Connecticut Avenue",60);
        spots[10]=new Other("JAIL","Jail");
        spots[11]=new Property(140,10,"PINK","St. Charles Place",70);
        spots[12]=new Utility("Electric Company");
        spots[13]=new Property(140,10,"PINK","States Avenue",70);
        spots[14]=new Property(160,12,"PINK","Virginia Avenue",80);
        spots[15]=new Rail("Pennsylvania RR");
        spots[16]=new Property(180,14,"ORANGE","St. James Place",90);
        spots[17]=new Other("CHEST","Community Chest");
        spots[18]=new Property(180,14,"ORANGE", "Tennessee Avenue",90);
        spots[19]=new Property(200,16,"ORANGE","New York Avenue",100);
        spots[20]=new Other("PARK","Free Parking");
        spots[21]=new Property(220,18,"RED", "Kentucky Avenue",110);
        spots[22]=new Other("CHANCE","Chance");
        spots[23]=new Property(220,18,"RED","Indiana Avenue",110);
        spots[24]=new Property(240,20,"RED","Illinois Avenue",120);
        spots[25]=new Rail("B&O RR");
        spots[26]=new Property(260,22,"YELLOW","Atlantic Avenue",130);
        spots[27]=new Property(260,22,"YELLOW","Ventnor Avenue",130);
        spots[28]=new Utility("Water Works");
        spots[29]=new Property(280,24,"YELLOW","Marvin Gardens",140);
        spots[30]=new Other("GOJAIL","Go To Jail");
        spots[31]=new Property(300,26,"GREEN","Pacific Avenue",150);
        spots[32]=new Property(300,26,"GREEN","Pacific Avenue",150);
        spots[33]=new Other("CHEST", "Community Chest");
        spots[34]=new Property(320,28,"GREEN","Pennsylvania Avenue",160);
        spots[35]=new Rail("Short Line");
        spots[36]=new Other("CHANCE","Chance");
        spots[37]=new Property(350,35,"BLUE","Park Place",175);
        spots[38]=new Other("TAX","Luxury Tax");
        spots[39]=new Property(400,50,"BLUE","Boardwalk",200);
    }

    //returns instance variable full of Space data
    public Space[] getSpots()
    {   
        return spots;
    }

    //gets individual Space data
    public Space getSpot(int n)
    {   
        return spots[n];
    }
}
