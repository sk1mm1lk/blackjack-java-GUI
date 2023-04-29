package yhd6147.blackjackgui;

/**
 * @author yhd6147
 */
public class Player implements Comparable<Player>
{

    // === VARIABLES ==========================================================

    private String name;
    private int score;
    private Hand hand;
    private int activeBet;
    
    // === CONSTRUCTORS =======================================================

    public Player(String name)
    {
        // Initialises the name variable and sets the rest to default values
        this.name = name;
        this.score = 0;
        this.hand = new Hand();
        this.activeBet = 0;
    }
    
    public Player(String name, int score)
    {
        // Initialises the name and score variables, with the rest as default values
        this(name);
        this.score = score;
    }

    // === GETTERS AND SETTERS ================================================
    
    public String getName()
    {
        // Returns the name variable
        return this.name;
    }
    
    public void setName(String name)
    {
        // Sets the name variable
        this.name = name;
    }
    
    public int getScore()
    {
        // Returns the player score
        return this.score;
    }
    
    public void setBet(int bet)
    {
        // Sets the player bet
        
        // Ensures that the player has score to bet
        if (bet < 0)
        {
            this.activeBet = 0;
        }
        else if(bet > this.score)
        {
            // The bet is set to the total score if bet is greater than score
            this.activeBet = this.score;
        }
        else
        {
            this.activeBet = bet;
        }
    }
    
    public int getBet()
    {
        // Returns the bet variable
        return this.activeBet;
    }

    public Hand getHand()
    {
        // Returns the hand variable
        return this.hand;
    }
    
    // === METHODS ============================================================

    public void addScore(int addScore)
    {
        // Adds the argument to the score
        if (addScore > 0)
        {
            this.score += addScore;
        }
    }
    
    public void removeScore(int removeScore)
    {
        // Removes the argument from the score
        if (removeScore > 0)
        {
            this.score -= removeScore;
        }
    }
    
    public void addCard(Card card)
    {
        // Adds a card to the players hand
        if (card != null)
        {
            this.hand.addCard(card);
        }
    }

    @Override
    public String toString()
    {
        // Returns the String interpretation of the player
        return this.name + "," + this.score;
    }

    @Override
    public int compareTo(Player o)
    {
        // Returns the compared names of the player and target player o
        return this.name.compareTo(o.name);
    }
}
