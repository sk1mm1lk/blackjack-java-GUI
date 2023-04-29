package yhd6147.blackjackgui;

/**
 * @author yhd6147
 */
public enum Face
{

    // === ELEMENTS ===========================================================

    // Each face element has a name and a value attatched to it
    ACE("Ace", 1),
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 11),
    QUEEN("Queen", 12),
    KING("King", 13);

    // === VARIABLES ==========================================================
    
    private final String name;
    private final int value;
    
    // === CONSTRUCTOR ========================================================

    private Face(String name, int value)
    {
        // Initialises the name and the value variables
        this.name = name;
        this.value = value;
    }

    // === GETTERS AND SETTERS ================================================
    
    public String getName()
    {
        // Returns a String of the name of the Face
        return this.name;
    }
    
    public int getValue()
    {
        // Returns the integer value of the Face
        return this.value;
    }

    // === METHODS ============================================================
    
    public static Face getFaceByValue(int value)
    {
        // Returns the Face object that has the same value as the argument
        for (Face face : Face.values())
        {
            if (face.value == value)
            {
                return face;
            }
        }
        
        return null;
    }
    
    public static Face getFaceByName(String name)
    {
        // Returns the Face object that has the same name as the argument
        for (Face face : Face.values())
        {
            if (face.name.equals(name))
            {
                return face;
            }
        }
        
        return null;
    }
}
