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
    private BlackJackGUI   app;
    
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
    
    public BlackJackViewGUI(BlackJackModel model, BlackJackGUI app)
    {
        this.model = model;
        this.app   = app;
        
        // Set the close operation and window closing listener
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent evt)
            {
                quit();
            }
        });
        
        this.setSize(600, 400);
        
        this.startPanel      = new StartPanel(this);
        this.rulesPanel      = new RulesPanel(this);
        this.scoreboardPanel = new ScoreboardPanel(this);
        this.loginPanel      = new LoginPanel(this);
        this.gamePanel       = new GamePanel(this);
        
        this.add(this.startPanel);
        this.isStartPanel = true;
    }
    
    // === GETTERS AND SETTERS ================================================
    
    public BlackJackModel getModel()
    {
        return this.model;
    }
    
    public String getScoreboardScores()
    {
        return this.model.getScoreboard().displayScoreboard().replace(",", " --> ");
    }
    
    // === METHODS ============================================================
    
    public void openPanel(JPanel panel)
    {
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
        // Removes the other panels and starts the start panel
        this.openPanel(this.rulesPanel);
    }
    
    public void openScoreboardPanel()
    {
        // Removes the other panels and starts the start panel
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
        // Removes the other panels and starts the bet panel
        this.betPanel = new BetPanel(this);
        this.openPanel(this.betPanel);
    }
    
    @Override
    public void quit()
    {
        // This method is responsible for making sure to game ends correctly
        if (this.isStartPanel)
        {
            System.exit(0);
        }
        else
        {
            this.openStartPanel();
        }
    }
}