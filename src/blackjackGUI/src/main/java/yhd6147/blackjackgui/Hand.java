package yhd6147.blackjackgui;

import java.util.ArrayList;

/**
 * @author yhd6147
 */
public class Hand
{
    
    // === VARIABLES ==========================================================

    private ArrayList<Card> cards;
    private int size;
    private int value;

    // === CONSTRUCTORS =======================================================
    
    public Hand()
    {
        // Initialises the variables to default values
        this.cards = new ArrayList<Card>();
        this.size = 0;
        this.value = 0;
    }
    
    public Hand(Card[] cards)
    {
        // Initialises the variables to defaults values and populates cards list
        this();
        for (Card card : cards)
        {
            if (card != null)
            {
                this.cards.add(card);
                this.value =+ card.getValue();
                this.size ++;
            }
        }
    }
    
    // === GETTERS AND SETTERS ================================================
    
    public int getSize()
    {
        //  Returns the number of cards in hand
        return this.size;
    }
    
    public int getValue()
    {
        // Returns the sum value of all the cards in the hand
        return this.value;
    }
    
    public Card[] getCards()
    {
        // Returns a primitive array of all the cards in the hand
        if (this.size == 0)
        {
            return null;
        }
        
        Card[] returnCards = new Card[this.size];
        return this.cards.toArray(returnCards);
    }

    // === METHODS ============================================================
    
    public void clearHand()
    {
        // Removes all the cards in the hand and sets the size and value to 0
        this.cards.clear();
        this.size = 0;
        this.value = 0;
    }
    
    public void addCard(Card card)
    {
        // Adds a card to the list
        if (card != null)
        {
            this.cards.add(card);
            this.size ++;
            this.value += card.getValue();
        }
    }
    
    public void removeCard(Card card)
    {
        // Removes a card from the list
        if (card != null)
        {
            int sizeBefore = this.cards.size();
            this.cards.remove(card);
            int change = sizeBefore - this.cards.size();
            
            if (change > 0)
            {
                this.size = this.cards.size();
                this.value -= card.getValue();
            }
        }
    }
    
    public boolean isEmpty()
    {
        // Returns true if the hand is empty
        return (this.size == 0);
    }
    
    public boolean isBust()
    {
        // Returns true if the hand value is over 21
        return (this.value > 21);
    }
}
