package yhd6147.blackjackgui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author yhd6147
 */
public class BlackJackViewGUI extends JFrame implements Quitable
{
    // === VARIABLES ==========================================================
    
    private BlackJackModel model;
    private BlackJackGUI   app;
    
    // GUI Components
    //private JPanel     startPage;
    private JPanel     loginPanel;
    private JPanel     gamePanel;
    private GridBagConstraints c;
    
    private JLabel     titleLogin;
    private JLabel     titleGame;
    private JTextField nameInput;
    private JTextField passInput;
    private JLabel     loginState;
    private JLabel     usernameLabel;
    private JLabel     passwordLabel;
    
    private JButton    loginButton;
    private JButton    quitButtonLogin;
    private JButton    quitButtonGame;
    
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
        
        this.loginPanel = new LoginPanel(this);
        this.gamePanel  = new GamePanel(this);
        
        this.add(this.loginPanel);
    }
    
    // === METHODS ============================================================
    
    public void openGamePanel()
    {
        // Removes the login panel and starts the game panel
        this.getContentPane().removeAll();
        this.add(this.gamePanel);
        this.revalidate();
        this.repaint();
    }
    
    private void openLoginPanel()
    {
        // Removes the game panel and starts the game panel
        this.getContentPane().removeAll();
        this.add(this.loginPanel);
        this.revalidate();
        this.repaint();
    }
    
    @Override
    public void quit()
    {
        // This method is responsible for making sure to game ends correctly
        System.exit(0);
    }
}