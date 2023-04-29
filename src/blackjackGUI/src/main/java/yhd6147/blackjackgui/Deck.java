package yhd6147.blackjackgui;

import java.util.Random;
import java.util.Stack;

/**
 *
 * @author yhd6147
 */
public class Deck
{

    // === VARIABLES ==========================================================

    private int size;
    private Stack<Card> deck;

    // === CONSTRUCTOR ========================================================
    
    public Deck()
    {
        // Initialises the size of the deck to 0 and resests the deck
        this.size = 0;
        this.reset();
    }

    // === GETTERS ============================================================
    
    public int getSize()
    {
        // Returns the size of the deck
        return this.size;
    }
    
    // === METHODS ============================================================
    
    public void reset()
    {
        // Initialises the deck with 52 cards
        this.size = 0;
        this.deck = new Stack<Card>();
        Card current;
        
        // For every suit in a card deck (4)
        for (int i = 0; i < 4; i++)
        {
            // For every face in a card deck (13)
            for (int j = 0; j < 13; j++)
            {
                // Add the card with the appropriate face and suit to the deck
                current = new Card(Suit.getSuitByValue(4 - i), Face.getFaceByValue(j + 1));
                this.deck.add(current);
            }
        }
        
        this.size = this.deck.size();
    }
    
    public void shuffle()
    {
        // Randomise the order of the cards in the deck

        // There is no need to shuffle one card
        if (this.size < 2)
        {
            return;
        }
        
        // Get the cards in a primitive array
        Card[] cards = this.getCards();
        
        Random rand = new Random();
        
        // Do two passes to ensure the cards are sufficiently random
        for (int passes = 0; passes < 2; passes++)
        {
            // Take every position in the array of card
            for (int i = 0; i < cards.length; i++)
            {
                // And swap the value at that index with another index
                int swapIndex = rand.nextInt(cards.length);

                Card temp = cards[i];
                cards[i] = cards[swapIndex];
                cards[swapIndex] = temp;
            }
        }
        
        // Initialises a new instance of the deck
        this.deck = new Stack<Card>();
        
        for (Card card : cards)
        {
            this.deck.add(card);
        }
        
        this.size = this.deck.size();
    }
    
    public Card nextCard()
    {
        // Returns the top card in the deck

        if (this.size <= 0)
        {
            return null;
        }

        Card returnCard = this.deck.pop();
        
        if (returnCard == null)
        {
            return null;
        }
        
        this.size --;
        
        return returnCard;
    }
    
    public boolean isEmpty()
    {
        // Returns true if the deck is empty
        return (this.size == 0);
    }
    
    public Card[] getCards()
    {
        // Returns a primitive array of Card objects
        if (this.size == 0)
        {
            return null;
        }
        
        Card[] cards = new Card[this.size];
        return this.deck.toArray(cards);
    }
}
