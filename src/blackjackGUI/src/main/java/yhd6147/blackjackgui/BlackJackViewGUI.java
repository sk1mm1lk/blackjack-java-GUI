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
public class BlackJackViewGUI extends JFrame
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
                close();
            }
        });
        
        this.setSize(600, 400);
        
        this.titleLogin = new JLabel("Dudov BlackJack");
        // TODO
        this.titleLogin.setFont(new Font(this.titleLogin.getFont().getName(), Font.PLAIN, this.titleLogin.getFont().getSize()*2));
        this.titleGame  = new JLabel("Dudov BlackJack");
        this.loginState = new JLabel("Enter your username and password");
        this.usernameLabel = new JLabel("Username");
        this.passwordLabel = new JLabel("Password");
        
        this.nameInput = new JTextField(15);
        this.passInput = new JTextField(15);
        
        this.loginButton     = new JButton("Login");
        this.quitButtonLogin = new JButton("Quit");
        this.quitButtonGame  = new JButton("Quit");
        
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        this.quitButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        this.quitButtonGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginPanel();
            }
        });
        
        this.loginPanel = new JPanel(new GridBagLayout());
        this.gamePanel  = new JPanel();
        
        // GridBagLayout settings
        this.c = new GridBagConstraints();
        
        // Adding components to login panel
        addComponent(this.loginPanel, this.titleLogin,  0,0,3);
        addComponent(this.loginPanel, this.loginState,  0,1,3);
        
        addComponent(this.loginPanel, this.usernameLabel,  0,2,1);
        addComponent(this.loginPanel, this.passwordLabel,  0,3,1);
        
        addComponent(this.loginPanel, this.nameInput,   1,2,2);
        addComponent(this.loginPanel, this.passInput,   1,3,2);
        addComponent(this.loginPanel, this.loginButton, 2,4,1);
        addComponent(this.loginPanel, this.quitButtonLogin, 2,5,1);
        
        this.add(this.loginPanel);
        
        this.gamePanel.add(this.titleGame);
        this.gamePanel.add(this.quitButtonGame);
    }
    
    // === METHODS ============================================================
    
    private void login()
    {
        String username = this.nameInput.getText();
        String password = this.passInput.getText();
        
        if (!username.isBlank() && !password.isBlank())
        {
            // continue
            //this.model.login(username, password);
            openGamePanel();
        }
        else
        {
            this.loginState.setText("Invalid login details");
        }
    }
    
    private void addComponent(JPanel panel, Component component, int x, int y, int width)
    {
        this.c.fill = GridBagConstraints.HORIZONTAL;
	this.c.gridx = x;
	this.c.gridy = y;
        this.c.gridwidth = width;
        panel.add(component, c);
    }
    
    private void openGamePanel()
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
    
    private void close()
    {
        // This method is responsible for making sure to safely end the game
        System.exit(0);
    }
}