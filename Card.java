
/**
 * Write a description of class Card here.
 *used for chance and community chst cards
 *
 * @author (Johnathan Evans, Daniel Jones)
 * @version (4/11/2019)
 */
public class Card
{
    private String signature, description;
    private boolean chance;
    //signature indicates if the card is for moving a character, get out of jail, etc
    //chance is community chest or chance
    private int value;//determing on the signature, this could be a dollar value or a space on the board
    public Card(String d,boolean c,String s,int v)
    {
        signature=s;
        chance=c;
        description=d;
        value=v;
    }

    public String getSignature()
    {//TAX, MOVE, JAIL, JAILFREE
        return signature;
    }

    public boolean isChance()
    {
        return chance;
    }

    public String getDescription()
    {
        return description;
    }

    public int getValue()
    {
        return value;
    }
}
