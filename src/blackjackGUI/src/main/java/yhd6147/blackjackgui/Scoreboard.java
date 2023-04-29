package yhd6147.blackjackgui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author yhd6147
 */
public class Scoreboard
{

    // === VARIABLES ==========================================================

    private SortedSet<Player> players;
    private FileController file;
    private String fileName;
    private int nPlayers;
    
    // === CONSTRUCTOR ========================================================

    public Scoreboard(String fileName)
    {
        // Initialises the players, and creates a FileController object
        this.players = new TreeSet<Player>();
        this.nPlayers = 0;

        this.file = new FileController();
        this.fileName = fileName;
        
        this.importPlayers();
    }
    
    // === METHODS ============================================================
    
    public Player getPlayer(String playerName)
    {
        // Returns the player object based on a player name as an argument
        
        // Ensuring a comma is not included in the player name (ruins export)
        playerName = playerName.replace(",", "");

        Player returnPlayer = new Player(playerName);
        Player current;
        
        Iterator iter = this.players.iterator();
        
        while (iter.hasNext())
        {
            current = (Player) iter.next();
            if (returnPlayer.compareTo(current) == 0)
            {
                // If the name matches a player in the set, return player object
                return current;
            }
        }
        
        // If no player matches the name in the set, a new player is added
        this.setPlayer(returnPlayer);
        return returnPlayer;
    }

    private void setPlayer(Player player)
    {
        // Private method for a player object to be added to the players set
        this.players.add(player);
        this.nPlayers = this.players.size();
    }

    public void setPlayer(String name)
    {
        // Public method for a player to be added to the players set by name

        // Ensuring a comma is not included in the player name (ruins export)
        name = name.replace(",", "");
        Player current = new Player(name);
        this.setPlayer(current);
    }
    
    public void removePlayer(Player player)
    {
        // Removes a player from the players set
        this.players.remove(player);
        this.nPlayers = this.players.size();
    }

    public void importPlayers()
    {
        // Gets players from the default file
        this.importPlayers(this.fileName);
    }
    
    public void importPlayers(String fileName)
    {
        // Gets players from a specified players file
        String playerFileContent = this.file.read(fileName);

        if (playerFileContent == null)
        {
            return;
        }

        // Splits the file contents by newlines
        String[] playerStrings = playerFileContent.split("\n");

        // Go through each line that could be a potential player
        for (String playerString : playerStrings)
        {
            Player current;
            int currentScore = 0;

            // Split line into name and score
            String[] playerElements = playerString.split(",");

            if (playerElements.length > 0)
            {
                current = new Player(playerElements[0]);
                
                if (playerElements.length > 1)
                {
                    currentScore = 0;
                    try
                    {
                        currentScore = (int) Integer.parseInt(playerElements[1]);
                    }
                    catch (NumberFormatException e){}
                }

                // Add player with the potentially parsed scored to players set
                current.addScore(currentScore);
                this.setPlayer(current);
            }
        }
    }
    
    public void exportPlayers()
    {
        // Exports players to the default players file
        this.exportPlayers(this.fileName);
    }
    
    public void exportPlayers(String fileName)
    {
        // Exports players to a specified file
        this.file.write(this.toString(), fileName);
    }
    
    public String displayScoreboard()
    {
        // Displays all of the players and their scores
        String output = "";

        Iterator iter = this.players.iterator();
        Player current;
        
        while (iter.hasNext())
        {
            current = (Player) iter.next();
            if (current != null)
            {
                output += current.toString() + "\n";
            }
        }
        return output;
    }
    
    public Player[] getPlayers()
    {
        // Returns a primitive array of the players
        Player[] returnPlayers = new Player[this.players.size()];
        return this.players.toArray(returnPlayers);
    }
    
    @Override
    public String toString()
    {
        // Returns the String interpretation of the scoreboard
        String outputString = "";
        for (Player player : this.getPlayers())
        {
            outputString += player.toString() + "\n";
        }
        return outputString;
    }
}
