package yhd6147.blackjackgui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yhd6147
 */
public class ScoreboardDB extends Scoreboard
{

    // === VARIABLES ==========================================================
    
    private SortedSet<Player> players;
    private String playersTableName;
    private int nPlayers;
    private boolean isConnected;
    
    private Connection conn;
    private Statement statement;
    private static final String USER_NAME = "APP";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:derby:scoresDB; create=true";
    
    // === CONSTRUCTOR ========================================================
    
    public ScoreboardDB()
    {
        // Initialises the players, and creates a FileController object
        this.players = new TreeSet<Player>();
        this.nPlayers = 0;
        
        this.playersTableName = "PLAYERS";
        
        this.connectDB();
        if (this.isConnected())
        {
            this.importPlayers();
        }
    }
    
    // === GETTERS AND SETTERS ================================================
    
    public void setPlayer(Player player)
    {
        // Sets a player given as parameter
        this.players.add(player);
        this.nPlayers = this.players.size();
    }
    
    public void setPlayer(String playerName, int playerScore)
    {
        // Sets a player given a name and score
        playerName = playerName.replace(",", "");
        Player current = new Player(playerName, playerScore);
        this.setPlayer(current);
    }
    
    // === METHODS ============================================================
    
    public boolean isConnected()
    {
        // Returns if connection is established
        return this.isConnected;
    }
    
    public void connectDB()
    {
        // Connects to the database
        if (this.conn == null) {
            try
            {
                try {
                    Class.forName("org.apache.derby.jdbc.ClientDriver");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ScoreboardDB.class.getName()).log(Level.SEVERE, null, ex);
                }
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                
                boolean connected = false;
                int attempts = 0;
                
                // Makes 3 attempts to connect to the database
                while (!connected || attempts > 3)
                {
                    connected = this.createStatement();
                    attempts ++;
                }
                
                this.isConnected = true;
            } catch (SQLException ex)
            {
                this.isConnected = false;
            }
        }
    }
    
    private boolean createStatement()
    {
        // Creates the statement instance
        if (conn == null)
            return false;

        try {
            this.statement = conn.createStatement();
        } catch (SQLException ex)
        {
            this.statement = null;
            return false;
        }
        return true;
    }
    
    private boolean tableExists(String tableName)
    {
        // Returns true if the table exists
        if (this.statement == null)
            return false;
        
        try
        {
            ResultSet rs = this.statement.executeQuery("SELECT * FROM " + tableName);
        }
        catch (SQLException ex)
        {
            if (ex.getSQLState().equals("42X05")) // Table doesn't exist
            {
                return false;
            }
        }
        return true;
    }
    
    public void createTable()
    {
        // Creates a table if it doesn't already exist
        if (this.statement == null)
            return;
        
        if (this.tableExists(this.playersTableName))
        {
            // Table exists continue
            return;
        }
        else
        {
            String sqlCreateTable="CREATE TABLE " + this.playersTableName + " (NAME VARCHAR(50), SCORE INT)";
            try
            {
                this.statement.executeUpdate(sqlCreateTable);
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    private boolean clearTable()
    {
        // Clears all the rows from the table
        if (this.statement == null)
            return false;
        if (!this.tableExists(this.playersTableName))
        {
            this.createTable();
            return true;
        }
        
        try
        {
            String clearTableString = "DELETE FROM " + this.playersTableName;
            this.statement.executeUpdate(clearTableString);
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    public void closeConnection()
    {
        // Closes the connection
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @Override
    public void importPlayers()
    {
        // Imports players from the database
        if (this.tableExists(this.playersTableName))
        {
            ResultSet rs = null;
            
            try
            {
                rs = this.statement.executeQuery("SELECT * FROM " + this.playersTableName);
                while(rs.next())
                {
                    String playerName = rs.getString("NAME");
                    int playerScore = rs.getInt("SCORE");
                    this.setPlayer(playerName, playerScore);
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            System.out.println("Does exist");
        }
    }

    @Override
    public void exportPlayers()
    {
        // Exports the players into the database
        if (this.statement == null)
            return;
        
        if(!this.clearTable())
            return;
        
        for (Player player: this.getPlayers())
        {
            try
            {
                String playerName = player.getName();
                String playerScore = "" + player.getScore();
                String addPlayerString = "INSERT INTO "+this.playersTableName+" (NAME, SCORE) VALUES ('"+playerName+"', "+playerScore+")";
                this.statement.executeUpdate(addPlayerString);
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
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

    @Override
    public Player[] getPlayers()
    {
        // Returns an array of all the players
        Player[] returnPlayers = new Player[this.players.size()];
        return this.players.toArray(returnPlayers);
    }

    @Override
    public Player getPlayer(String playerName)
    {
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

    @Override
    public void quit()
    {
        // Closes the connection
        this.closeConnection();
    }
}
