
/**
 * Write a description of class Tester here.
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @version (3/28/19)
 */
import java.lang.*;
import java.util.*;
public class Tester
{
    //Game game=new Game();
    Board board=new Board();
    public void checkBoard()
    {
        Space[] spaces=board.getSpots();
        for (Space i:spaces)
            System.out.println(i.toString());

    }

    public void troubleshootPlayerNames()
    {
        Scanner startUp=new Scanner(System.in);
        String ret="";
        for (int i=0;i<4;i++)
        {
            System.out.println("Player "+(i+1)+", Write your name");
            String name=startUp.nextLine();
           ret+=name;
        }
        System.out.println(ret);
    }
}
