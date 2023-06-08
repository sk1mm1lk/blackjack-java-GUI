/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package yhd6147.blackjackgui;

import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yhd6147
 */
public class BetPanelTest {
    
    public BetPanelTest()
    {
        
    }
    
    @Before
    public void setUp()
    {
        BlackJackModel model = new BlackJackModel();
        BlackJackViewGUI view = new BlackJackViewGUI(model, null);
        view.setVisible(true);
        view.openBetPanel();
    }
    
    @After
    public void tearDown()
    {
        
    }
    
    @org.junit.Test
    public void openPanel()
    {
        
    }

    /**
     * Test of getCurrentPlayer method, of class BetPanel.
     */
    @org.junit.Test
    public void testGetCurrentPlayer() {
        System.out.println("getCurrentPlayer");
        BetPanel instance = null;
        Player expResult = null;
        Player result = instance.getCurrentPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentPlayerName method, of class BetPanel.
     */
    @org.junit.Test
    public void testGetCurrentPlayerName() {
        System.out.println("getCurrentPlayerName");
        BetPanel instance = null;
        String expResult = "";
        String result = instance.getCurrentPlayerName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increase method, of class BetPanel.
     */
    @org.junit.Test
    public void testIncrease() {
        System.out.println("increase");
        BetPanel instance = null;
        instance.increase();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decrease method, of class BetPanel.
     */
    @org.junit.Test
    public void testDecrease() {
        System.out.println("decrease");
        BetPanel instance = null;
        instance.decrease();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePanel method, of class BetPanel.
     */
    @org.junit.Test
    public void testUpdatePanel() {
        System.out.println("updatePanel");
        BetPanel instance = null;
        instance.updatePanel();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of quit method, of class BetPanel.
     */
    @org.junit.Test
    public void testQuit() {
        System.out.println("quit");
        BetPanel instance = null;
        instance.quit();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
