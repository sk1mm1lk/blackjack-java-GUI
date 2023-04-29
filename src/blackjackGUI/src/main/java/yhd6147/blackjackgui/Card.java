package yhd6147.blackjackgui;

/**
 * @author yhd6147
 */
public class Card implements Comparable<Card>
{

// === VARIABLES ==============================================================

    private final Suit suit;
    private final Face face;

// === CONSTRUCTOR ============================================================
    
    public Card(Suit suit, Face face)
    {
        // Initialises the suit and face variables given by passed arguments
        this.suit = suit;
        this.face = face;
    }
  
// === GETTERS AND SETTERS ====================================================
    
    public Suit getSuit()
    {
        // Returns the Suit object
        return this.suit;
    }
    
    public Face getFace()
    {
        // Returns the Face object
        return this.face;
    }
    
    public int getValue()
    {
        // Returns the integer value of the card where Ace is 1 and King is 13
        return this.face.getValue();
    }

// === METHODS ================================================================
    
    @Override
    public String toString()
    {
        // Returns the String representation of the card
        return this.face.getName() + " of " + this.suit.getName() + "s";
    }

    @Override
    public int compareTo(Card o)
    {
        // Returns the difference between this card and a target card o
        return (this.face.getValue() - o.face.getValue());
    }
}
