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
    private Player currentPlayer;
    private int currentPlayerIndex;
    
    private boolean isGameOver;
    
    // === CONSTRUCTOR ========================================================

    public BlackJackModel()
    {
        // Initialises scoreboard, players and the deck
        this.scoreboard = new Scoreboard(SCORE_FILE_NAME);
        this.house = new Player(",,, HOUSE ,,,");
        
        this.players = new ArrayList<Player>();
        this.nPlayers = 0;
        
        this.currentPlayerIndex = 0;
        this.currentPlayer = null;

        this.deck = new Deck();
        this.deck.shuffle();
        
        this.isGameOver = false;
    }

    // === METHODS ============================================================
    
    public void startGame()
    {
        // Start the game by reseting all values
        this.currentPlayerIndex = 0;
        this.currentPlayer = this.players.get(this.currentPlayerIndex);
        this.isGameOver = false;
        
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
    
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

    public void setPlayer(String playerName)
    {
        // Adds a player by a specified name, consults the scoreboard for info
        if (this.nPlayers >= 5)
        {
            return; // Too many players
        }
        
        Player newPlayer = this.scoreboard.getPlayer(playerName);
        this.players.add(newPlayer);
        this.nPlayers = this.players.size();
    }
    
    public Player getPlayer(int playerIndex)
    {
        // Gets the player at index playerIndex
        return this.players.get(playerIndex);
    }
    
    public void removePlayer(String playerName)
    {
        // Removes player based on name
        Iterator iter = this.players.iterator();
        Player current = null;

        boolean isFound = false;

        while(iter.hasNext() && !isFound)
        {
            current = (Player) iter.next();
            if (current.getName().equals(playerName))
            {
                isFound = true;
            }
        }

        if (current != null)
        {
            this.players.remove(current);
        }

        this.nPlayers = this.players.size();
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

    public Player getCurrentPlayer()
    {
        // Returns current player
        return this.currentPlayer;
    }
    
    public Player setCurrentPlayer(String playerName)
    {
        // Sets the current player based on player's name
        Iterator iter = this.players.iterator();
        Player current = null;

        boolean isFound = false;

        while(iter.hasNext() && !isFound)
        {
            current = (Player) iter.next();
            if (current.getName().equals(playerName))
            {
                return current;
            }
        }

        return null;
    }
    
    public void nextPlayer()
    {
        // Sets the current player to the next player
        this.currentPlayerIndex++;
        
        if (this.currentPlayerIndex < this.nPlayers)
        {
            this.currentPlayer = this.players.get(this.currentPlayerIndex);
        }
        else if (this.currentPlayerIndex == this.nPlayers)
        {
            this.currentPlayer = this.house;
        }
        else
        {
            this.isGameOver = true;
        }
    }
    
    public boolean canDraw()
    {
        // Returns true if the current player is allowed to draw another card
        if (this.currentPlayer == null)
        {
            return false;
        }
        return this.currentPlayer.getHand().getValue() < 21;
    }
 
    public void drawCard()
    {
        // Draws a card for the current player
        this.drawCard(this.currentPlayer);
    }
    
    public boolean isBust()
    {
        // Returns true if the current player's hand is a bust
        if (this.currentPlayer != null)
        {
            return this.currentPlayer.getHand().isBust();
        }
        
        return true;
    }

    public int handValue()
    {
        // Returns the current player's hand value
        if (this.currentPlayer != null)
        {
            return this.currentPlayer.getHand().getValue();
        }
        
        return 0;
    }
    
        public boolean login(String username, String password)
    {
        // TODO
        this.setPlayer(username);
        return true;
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
    
    public boolean isHouseTurn()
    {
        return this.currentPlayerIndex == this.nPlayers;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    
    public Card drawCard(Player player)
    {
        if (player == null)
        {
            return null;
        }
        
        // Draws a card from the deck into the hand of the player
        if (!this.deck.isEmpty() && !player.getHand().isBust())
        {
            Card drawnCard = this.deck.nextCard();
            player.getHand().addCard(drawnCard);
            return drawnCard;
        }
        return null;
    }
    
    public Card[] getCardTable()
    {
        if (this.currentPlayer != null)
        {
            return this.currentPlayer.getHand().getCards();
        }
        
        return null;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    
    public Scoreboard getScoreboard()
    {
        // Returns the scoreboard instance
        return this.scoreboard;
    }

    public void saveScores()
    {
        // TODO
    }
    
    public boolean isGameOver()
    {
        return this.isGameOver;
    }
    
    public void endGame()
    {
        this.isGameOver = true;
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
    
    public void scoreGame()
    {
        // Displays the scoring results for every player who scored or lost points
        System.out.println("\n========== SCORING ==========");
        for (Player player : this.getPlayers())
        {
            this.scorePlayer(player);
        }
    }
}
