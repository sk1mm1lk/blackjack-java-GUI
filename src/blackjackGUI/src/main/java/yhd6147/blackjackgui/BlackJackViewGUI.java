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
    
    // === METHODS ============================================================
    
    public void openStartPanel()
    {
        // Removes the other panels and starts the start panel
        this.getContentPane().removeAll();
        this.add(this.startPanel);
        this.revalidate();
        this.repaint();
        this.isStartPanel = true;
    }
    
    public void openRulesPanel()
    {
        // Removes the other panels and starts the start panel
        this.getContentPane().removeAll();
        this.add(this.rulesPanel);
        this.revalidate();
        this.repaint();
        this.isStartPanel = false;
    }
    
    public void openScoreboardPanel()
    {
        // Removes the other panels and starts the start panel
        this.getContentPane().removeAll();
        this.add(this.scoreboardPanel);
        this.revalidate();
        this.repaint();
        this.isStartPanel = false;
    }
    
    public void openLoginPanel()
    {
        // Removes the other panels and starts the login panel
        this.getContentPane().removeAll();
        this.add(this.loginPanel);
        this.revalidate();
        this.repaint();
        this.isStartPanel = false;
    }
    
    public void openGamePanel()
    {
        // Removes the other panels and starts the game panel
        this.getContentPane().removeAll();
        this.add(this.gamePanel);
        this.revalidate();
        this.repaint();
        this.isStartPanel = false;
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