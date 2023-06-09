package yhd6147.blackjackgui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author yhd6147
 */
public class ScoreboardDBTest {
    
    private ScoreboardDB scores;
    
    /*
     * This testing file I used while testing the scoreboard functionality.
     * There are some functions that were tested by using the Derby external
     * DBMS commandline which is why some of the tests don't have an assert.
     *
     * There were functions I added purely to test that have since been deleted.
     * 
     * I believe the real application of using this testing file was more important
     * to me than having perfectly written tests that I would only use once.
     * This is why this file is not perfectly written.
     *
     * Thank you for your consideration.
     */
    
    public ScoreboardDBTest() {
    }
    
    @Before
    public void setUp() {
        this.scores = new ScoreboardDB();
    }
    
    @After
    public void tearDown() {
        if (this.scores != null)
        {
            this.scores.closeConnection();
        }
    }

    /**
     * Test of connectDB method, of class ScoreboardDB.
     */
    @Test
    public void testConnectDB() {
        System.out.println("connectDB");
        this.scores.connectDB();
        assert(this.scores.isConnected());
    }
    
    /**
     * Test of testCreateTable method, of class ScoreboardDB.
     */
    @Test
    public void testCreateTable() {
        System.out.println("createTable");
        this.scores.connectDB();
        if (this.scores.isConnected())
        {
            this.scores.createTable();
        }
        else
        {
            fail("Failed to connect to DB");
        }
    }

    /**
     * Test of importPlayers method, of class ScoreboardDB.
     */
    @Test
    public void testImportPlayers() {
        System.out.println("importPlayers");
        this.scores.connectDB();
        if(!this.scores.isConnected())
            fail("Failed to connect");
        this.scores.importPlayers();
    }

    /**
     * Test of exportPlayers method, of class ScoreboardDB.
     */
    @Test
    public void testExportPlayers() {
        System.out.println("exportPlayers");
        this.scores.connectDB();
        if(!this.scores.isConnected())
            fail("Failed to connect");
        this.scores.importPlayers();
        this.scores.getPlayer("TestPlayer");
        System.out.println(this.scores.displayScoreboard());
    }

    /**
     * Test of displayScoreboard method, of class ScoreboardDB.
     */
    @Test
    public void testDisplayScoreboard() {
        System.out.println("displayScoreboard");
        this.scores.connectDB();
        if(!this.scores.isConnected())
            fail("Failed to connect");
        this.scores.getPlayer("TestPlayer");
        System.out.println(this.scores.displayScoreboard());
    }
}
