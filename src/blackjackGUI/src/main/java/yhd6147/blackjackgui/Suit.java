package yhd6147.blackjackgui;

/**
 * @author yhd6147
 */
public enum Suit
{

    // === ELEMENTS ===========================================================

    // Each suit element has a name and a value attatched to it
    SPADE("Spade", 4),
    HEART("Heart", 3),
    DIAMOND("Diamond", 2),
    CLUB("Club", 1);
    
    // === VARIABLES ==========================================================

    private final String name;
    private final int value;
    
    // === CONSTRUCTOR ========================================================

    private Suit(String name, int value)
    {
        // Initialises the name and value variables
        this.name = name;
        this.value = value;
    }

    // === GETTERS AND SETTERS ================================================
    
    public String getName()
    {
        // Returns a String of the name of the Suit
        return this.name;
    }
    
    public int getValue()
    {
        // Returns the integer value of the Suit
        return this.value;
    }
    
    // === METHODS ============================================================
    
    public static Suit getSuitByValue(int value)
    {
        // Returns the Suit object that has the same value as the argument
        for (Suit suit : Suit.values())
        {
            if (suit.value == value)
            {
                return suit;
            }
        }
        return null;
    }
    
    public static Suit getSuitByName(String name)
    {
        // Returns the Suit object that has the same name as the argument
        for (Suit suit : Suit.values())
        {
            if (suit.name.equals(name))
            {
                return suit;
            }
        }
        return null;
    }
}
