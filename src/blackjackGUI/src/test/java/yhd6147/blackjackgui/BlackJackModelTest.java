package yhd6147.blackjackgui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author yhd6147
 */
public class BlackJackModelTest
{
    public BlackJackModel model;
    public Scoreboard scoreboard;
    
    /*
     * I did not include methods that simply returns a single value
     * of change a single value.
     *
     * I was also not able to test functions that require long gameplay
     * as I figured it was not worth it.
     */
    
    public BlackJackModelTest()
    {
    }
    
    @Before
    public void setUp()
    {
        this.model = new BlackJackModel();
        this.scoreboard = this.model.getScoreboard();
        this.scoreboard.setFileName("testScoreboardFile.txt");
        this.model.login("TestPlayer1");
        this.model.login("TestPlayer2");
    }
    
    @After
    public void tearDown()
    {
        
    }

    /**
     * Test of startGame method, of class BlackJackModel.
     */
    @Test
    public void testStartGame() {
        // Should be able to take in players, remove cards and reset deck and other values
        System.out.println("startGame");
        this.model.startGame();
        assert(this.model.getCurrentPlayer().toString().equals("TestPlayer1,0"));
        this.model.drawCard();
        this.model.drawCard();
        this.model.nextPlayer();
        assert(this.model.getCurrentPlayer().toString().equals("TestPlayer2,0"));
        this.model.drawCard();
        this.model.startGame();
        assert(this.model.getCurrentPlayer().toString().equals("TestPlayer1,0"));
    }

    /**
     * Test of removePlayer method, of class BlackJackModel.
     */
    @Test
    public void testRemovePlayer() {
        System.out.println("removePlayer");
        String playerName = "TestPlayer1";
        this.model.removePlayer(playerName);
        this.model.startGame();
        assert(this.model.getCurrentPlayer().toString().equals("TestPlayer2,0"));
    }

    /**
     * Test of getPlayers method, of class BlackJackModel.
     */
    @Test
    public void testGetPlayers() {
        System.out.println("getPlayers");
        Player[] expResult = new Player[] {new Player("TestPlayer1"), new Player("TestPlayer2")};
        Player[] result = this.model.getPlayers();
        for (int i = 0; i < result.length; i++)
        {
            assertEquals(expResult[i].toString(), result[i].toString());
        }
    }

    /**
     * Test of getNPlayers method, of class BlackJackModel.
     */
    @Test
    public void testGetNPlayers() {
        System.out.println("getNPlayers");
        int expResult = 2;
        int result = this.model.getNPlayers();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPlayerBet method, of class BlackJackModel.
     */
    @Test
    public void testSetPlayerBet() {
        System.out.println("setPlayerBet");
        Player player = this.model.getPlayer(0);
        player.addScore(10); // So that there is something to bet with
        int bet = 10;
        this.model.setPlayerBet(player, bet);
        assertEquals(player.getBet(), 10);
    }

    /**
     * Test of getCurrentPlayer method, of class BlackJackModel.
     */
    @Test
    public void testGetCurrentPlayer() {
        System.out.println("getCurrentPlayer");
        Player player = this.model.getPlayer("TestPlayer2"); // Will be second player
        this.model.startGame();
        this.model.nextPlayer();
        
        String expResult = player.getName();
        String result = this.model.getCurrentPlayer().getName();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of setCurrentPlayer method, of class BlackJackModel.
     */
    @Test
    public void testSetCurrentPlayer() {
        System.out.println("setCurrentPlayer");
        String playerName = "TestPlayer2";
        Player expResult = new Player("TestPlayer2");
        Player result = this.model.setCurrentPlayer(playerName);
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of nextPlayer method, of class BlackJackModel.
     */
    @Test
    public void testNextPlayer() {
        System.out.println("nextPlayer");
        String expResult = "TestPlayer2,0";
        this.model.startGame();
        this.model.nextPlayer();
        
        String result = this.model.getCurrentPlayer().toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of login method, of class BlackJackModel.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        String username = "TestPlayer1";
        boolean expResult = false;
        boolean result = this.model.login(username);
        assertEquals(expResult, result);
    }
}
