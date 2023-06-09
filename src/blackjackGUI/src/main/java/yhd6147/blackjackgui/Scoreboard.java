package yhd6147.blackjackgui;

/**
 * @author yhd6147
 */
public abstract class Scoreboard implements Quitable
{
    /*
     * Imports players from the scoreboard
     */
    public abstract void importPlayers();
    
    /*
     * Exports players from the scoreboard
     */
    public abstract void exportPlayers();
    
    /*
     * Returns String of the scoreboard information
     */
    public abstract String displayScoreboard();
    
    /*
     * Returns the players in the scoreboard
     */
    public abstract Player[] getPlayers();
    
    /*
     * Return a player
     */
    public abstract Player getPlayer(String playerName);
}
