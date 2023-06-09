package yhd6147.blackjackgui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author yhd6147
 */
public class BlackJackViewGUI extends JFrame implements Quitable
{
    // === VARIABLES ==========================================================
    
    private BlackJackModel model;
    
    // GUI Components
    private JPanel     startPanel;
    private JPanel     rulesPanel;
    private JPanel     scoreboardPanel;
    private JPanel     loginPanel;
    private JPanel     gamePanel;
    private JPanel     betPanel;
    
    // To keep track of quitting the game
    private boolean isStartPanel;
    
    // === CONSTRUCTOR ========================================================
    
    public BlackJackViewGUI(BlackJackModel model)
    {
        // Initialises the panel components
        this.model = model;
        
        // Sets the close operation and window closing listener
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent evt)
            {
                quit();
            }
        });
        
        this.setSize(600, 400);
        
        // Initialises all the panels that will be used
        // Aside from Game panel and Bet panel
        this.startPanel      = new StartPanel(this);
        this.rulesPanel      = new RulesPanel(this);
        this.scoreboardPanel = new ScoreboardPanel(this);
        this.loginPanel      = new LoginPanel(this);
        
        this.add(this.startPanel);
        this.isStartPanel = true;
    }
    
    // === GETTERS AND SETTERS ================================================
    
    public BlackJackModel getModel()
    {
        // Returns the model
        return this.model;
    }
    
    public String getScoreboardScores()
    {
        // Returns the scoreboard scores
        return this.model.getScoreboard().displayScoreboard().replace(",", " --> ");
    }
    
    // === METHODS ============================================================
    
    public void startGame()
    {
        // Initialises a new game panel and opens it
        this.gamePanel = new GamePanel(this);
        this.openGamePanel();
    }
    
    public void openPanel(JPanel panel)
    {
        // A basic implementation of opening another panel
        this.getContentPane().removeAll();
        this.add(panel);
        this.revalidate();
        this.repaint();
        
        if (panel == this.startPanel)
            this.isStartPanel = true;
        else
            this.isStartPanel = false;
    }
    
    public void openStartPanel()
    {
        // Removes the other panels and starts the start panel
        ((StartPanel) this.startPanel).updatePlayers();
        this.openPanel(this.startPanel);
    }
    
    public void openRulesPanel()
    {
        // Removes the other panels and starts the rules panel
        this.openPanel(this.rulesPanel);
    }
    
    public void openScoreboardPanel()
    {
        // Removes the other panels and starts the scoreboard panel
        ((ScoreboardPanel) this.scoreboardPanel).updateScores();
        this.openPanel(this.scoreboardPanel);
    }
    
    public void openLoginPanel()
    {
        // Removes the other panels and starts the login panel
        this.openPanel(this.loginPanel);
    }
    
    public void openGamePanel()
    {
        // Removes the other panels and starts the game panel
        this.openPanel(this.gamePanel);
    }
    
    public void openBetPanel()
    {
        // Removes the other panels and starts a new bet panel
        this.betPanel = new BetPanel(this);
        this.openPanel(this.betPanel);
    }
    
    @Override
    public void quit()
    {
        // This method is responsible for making sure the game ends correctly
        if (this.isStartPanel)
        {
            this.model.closeScoreboard();
            System.exit(0);
        }
        else
        {
            this.openStartPanel();
        }
    }
}
