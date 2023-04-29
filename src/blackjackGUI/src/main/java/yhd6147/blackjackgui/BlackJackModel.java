package yhd6147.blackjackgui;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author yhd6147
 */
public class BlackJackModel
{

    // === CONSTANTS ==========================================================

    public static final String SCORE_FILE_NAME = "blackjack-scores.txt";
    
    // === VARIABLES ==========================================================

    private Scoreboard scoreboard;
    private ArrayList<Player> players;
    private Player house;
    private Deck deck;
    private int nPlayers;
    
    // === CONSTRUCTOR ========================================================

    public BlackJackModel()
    {
        // Initialises scoreboard, players and the deck
        this.scoreboard = new Scoreboard(SCORE_FILE_NAME);
        this.house = new Player(",,, HOUSE ,,,");
        
        this.players = new ArrayList<Player>();
        this.nPlayers = 0;

        this.deck = new Deck();
        this.deck.shuffle();
    }

    // === METHODS ============================================================
    
    public void setPlayer(String playerName)
    {
        // Adds a player by a specified name, consults the scoreboard for info
        if (this.nPlayers >= 5)
        {
            return; // Too many players
        }
        
        Player newPlayer = this.scoreboard.getPlayer(playerName);
        this.players.add(newPlayer);
        this.nPlayers ++;
    }
    
    public Player getPlayer(int playerIndex)
    {
        return this.players.get(playerIndex);
    }
    
    public Player[] getPlayers()
    {
        // Returns a primitive array of all the players in the game
        Player[] returnPlayers = new Player[this.nPlayers];
        return this.players.toArray(returnPlayers);
    }
 
    public int getNPlayers()
    {
        // Returns the number of players in the game
        return this.nPlayers;
    }

    public void setPlayerBet(Player player, int bet)
    {
        // Sets the bet of the player
        if (player != null)
        {
            player.setBet(bet);
        }
    }
   
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    
    public Player getHouse()
    {
        // Returns the house instance
        return this.house;
    }
   
    public boolean houseCanDraw()
    {
        // Returns a boolean that signifies whether the house will choose to draw
        return this.house.getHand().getValue() < 16;
    }

    public Card houseDraw()
    {
        // Draws a card for the house
        if (this.house.getHand().getSize() < 2)
        {
            return this.drawCard(house);
        }
        else if (this.houseCanDraw())
        {
            return this.drawCard(house);
        }
        return null;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    
    public Card drawCard(Player player)
    {
        // Draws a card from the deck into the hand of the player
        if (!this.deck.isEmpty() && !player.getHand().isBust())
        {
            Card drawnCard = this.deck.nextCard();
            player.getHand().addCard(drawnCard);
            return drawnCard;
        }
        return null;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    
    public Scoreboard getScoreboard()
    {
        // Returns the scoreboard instance
        return this.scoreboard;
    }
    
    public void resetGame()
    {
        // Resets the deck, house and players
        
        this.deck.reset();
        this.deck.shuffle();
        
        this.house.getHand().clearHand();

        Iterator iter = this.players.iterator();
        Player current;
        
        while(iter.hasNext())
        {
            current = (Player) iter.next();
            if (current != null)
            {
                current.getHand().clearHand();
                current.setBet(0);
            }
        }
    }
    
    public int scorePlayer(Player player)
    {
        // Compares the hand of the player to the house and rewards score accordingly

        if (player == null)
        {
            return 0;
        }
        
        /*
        Conditions:
            A = house and player dont bust
            B = only player busts
            C = only house busts
            D = house and player busts
        */
        
        boolean conditionA = (!this.house.getHand().isBust() && !player.getHand().isBust());
        boolean conditionB = (!this.house.getHand().isBust() && player.getHand().isBust());
        boolean conditionC = (this.house.getHand().isBust() && !player.getHand().isBust());
        
        int houseValue = this.house.getHand().getValue();
        int playerValue = player.getHand().getValue();
        
        if (conditionA) // Neither bust
        {
            if (houseValue > playerValue)
            {
                // House wins
                player.removeScore(player.getBet());
                return -(player.getBet());
            }
            else if (houseValue < playerValue)
            {
                // Player wins
                int reward = player.getBet() + 10; // Only declare "magic number once"
                
                player.addScore(reward);
                return reward;
            }
            else // Value is the same
            {} // Player receives nothing
        }
        else if (conditionB)
        {
            // House wins
            player.removeScore(player.getBet());
            return -(player.getBet());
        }
        else if (conditionC)
        {
            // Player wins
            int reward = player.getBet() + 10; // Only declare "magic number once"
            
            player.addScore(reward);
            return reward;
        }
        else // ConditionD
        {} // Nothing happens
        
        return 0;
    }
}
