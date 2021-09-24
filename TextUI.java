
/**
 * Write a description of class TextUI here.
 * new basic class to run the game in a more user friendly manner
 * to be replaced by GUI (or not upto user)
 * 
 * @author (Johnathan Evans, Daniel Jones)
 * @version (4/13/19)
 */
import java.util.*;
public class TextUI
{
    private int BOARDROW=4;    //holds size of monopoly board
    private int BOARDCOL=10;
    
    
    String output="";
    String[][] board=new String[BOARDROW][BOARDCOL];    //creates String Board
    /**
     * Constructor for objects of class TextUI
     */
    public TextUI()
    {   
        boardPrinter();
    }
    
    /*
     * Prints out board
     * hard set table that looks better
     * --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * | 21:Free Parking | 22:Kentucky Av. | 23:Chance | 24:Indiana Av. | 25:Illinois Av. | 26:B&O RR | 27:Atlantic Av. | 28: Vermont Av. | 29: Water Works | 30: Marvin Gardens | 31: Go to Jail |
     * | 20: New York Av |                                                                                                                                                       | 32: Pacific Av |
     * | 19: Tennessee Av|                                                                                                                                                       | 33: N Carolina |
     * | 18: Community   |                                                                                                                                                       | 34: Community  |
     * | 17: St. James Pl|                                                                                                                                                       | 35: Penn Av    |
     * | 16: Penn. RR    |                                                                                                                                                       | 36: Short Line |
     * | 15: Virginia Av.|                                                                                                                                                       | 37: Chance     |
     * | 14: States Av.  |                                                                                                                                                       | 38: Park Place |
     * | 13: Electric CO.|                                                                                                                                                       | 39: Luxury Tax |
     * | 12: St. Charles |                                                                                                                                                       | 40: Boardwalk  |
     * | 11: Jail        | 10: Connecticut Av. | 9: Vermont Av. | 8: Chance | 7: Oriental Av. | 6: Reading RR | 5: Income Tax | 4: Baltic Av. | 3: Community Chest | 2: Mediterranean Av .| 1: GO!|
     * --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

     */
    
    public void boardPrinter()
    { 
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("| 21:Free Parking | 22:Kentucky Av. | 23:Chance | 24:Indiana Av. | 25:Illinois Av. | 26:B&O RR | 27:Atlantic Av. | 28: Vermont Av. | 29: Water Works | 30: Marvin Gardens | 31: Go to Jail |");
        System.out.println("| 20: New York Av |                                                                                                                                                       | 32: Pacific Av |");
        System.out.println("| 19: Tennessee Av|                                                                                                                                                       | 33: N Carolina |");
        System.out.println("| 18: Community   |                                                                                                                                                       | 34: Community  |");
        System.out.println("| 17: St. James Pl|                                                                                                                                                       | 35: Penn Av    |");
        System.out.println("| 16: Penn. RR    |                                                                                                                                                       | 36: Short Line |");
        System.out.println("| 15: Virginia Av.|                                                                                                                                                       | 37: Chance     |");
        System.out.println("| 14: States Av.  |                                                                                                                                                       | 38: Park Place |");
        System.out.println("| 13: Electric CO.|                                                                                                                                                       | 39: Luxury Tax |");
        System.out.println("| 12: St. Charles |                                                                                                                                                       | 40: Boardwalk  |");
        System.out.println("| 11: Jail        | 10: Connecticut Av. | 9: Vermont Av. | 8: Chance | 7: Oriental Av. | 6: Reading RR | 5: Income Tax | 4: Baltic Av. | 3: Community Chest | 2: Mediterranean Av .| 1: GO!|");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
  
        /*Board copier=new Board();
        //copies Board copier to String board
        for(int r=0; r<board.length; r++){    //4 long 10 down currently
            for(int c=0; c<board[r].length; c++){
                board[r][c]="" + copier.getSpot((r*10)+c).getName(); 
            }
        }
        //prints out String board in readably manner
        for(int r2=0; r2<board.length; r2++){    //4 long 10 down currently
            System.out.println("");
            System.out.println("");
            for(int c2=0; c2<board[r2].length; c2++){
                System.out.print("" + board[r2][c2] + " | ");
                
            }
            
        }
        */
    }
    
    /*
     * Prints out info for all players in game
     */
    public void playerPositionPrinter()
    {
        ArrayList<Player> copyPlayer = new ArrayList(Game.getPlayers());
        System.out.println("");
        for(int i=0; i<copyPlayer.size(); i++){
            System.out.println("Player " + (i+1) + ": ");
            System.out.println(copyPlayer.get(i).toString());
            
        }
    }

    
}
