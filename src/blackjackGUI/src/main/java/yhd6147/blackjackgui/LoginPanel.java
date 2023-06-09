package yhd6147.blackjackgui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author yhd6147
 */
public class LoginPanel extends JPanel implements Quitable
{
    
    // === VARIABLES ==========================================================
    
    public final int MAX_USERNAME_LENGTH = 15;
    public final int MAX_PASSWORD_LENGTH = 20;
    
    private BlackJackViewGUI   view;
    private GridBagConstraints c;
    
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel loginStatusLabel;
    
    private JTextField usernameInput;
    private JTextField passwordInput;
    
    private JButton loginButton;
    private JButton quitButton;
    
    // === CONSTRUCTOR ========================================================
    
    public LoginPanel(BlackJackViewGUI view)
    {
        // Initialises the login panel components
        
        // Calls the JPanel constructor
        super(new GridBagLayout());
        
        this.view = view;
        this.c = new GridBagConstraints();
        
        this.titleLabel = new JLabel("Log into your account");
        this.titleLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        this.loginStatusLabel = new JLabel("Enter your username and password");
        this.usernameLabel = new JLabel("Username:");
        this.passwordLabel = new JLabel("Password:");
        
        this.usernameInput = new JTextField(MAX_USERNAME_LENGTH);
        this.passwordInput = new JTextField(MAX_PASSWORD_LENGTH);
        
        // Initialises the buttons
        this.loginButton = new JButton("Login");
        this.quitButton  = new JButton("Quit");
        
        // Initialises the button actions
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        // Adds the components to the panel
        addComponent(this.titleLabel,        0,0,3);
        addComponent(this.loginStatusLabel,  0,1,3);
        
        addComponent(this.usernameLabel,     0,2,1);
        addComponent(this.passwordLabel,     0,3,1);
        
        addComponent(this.usernameInput,     1,2,2);
        addComponent(this.passwordInput,     1,3,2);
        addComponent(this.loginButton,       2,4,1);
        addComponent(this.quitButton,        2,5,1);
    }
    
    private void login()
    {
        // Attempts to login the player
        String username = this.usernameInput.getText();
        String password = this.passwordInput.getText();
        
        if (!username.isBlank() && !password.isBlank())
        {
            if (this.view.getModel().login(username, password))
            {
                this.usernameInput.setText("");
                this.passwordInput.setText("");
                this.view.openStartPanel();
                return;
            }
        }

        // If login fails
        this.loginStatusLabel.setText("Invalid login details");
    }
    
    private void addComponent(JComponent component, int x, int y, int width)
    {
        // Adds component to the panel using GridBagLayout
        this.c.fill = GridBagConstraints.HORIZONTAL;
        this.c.gridx = x;
        this.c.gridy = y;
        this.c.gridwidth = width;
        this.add(component, c);
    }

    @Override
    public void quit()
    {
        // Quits the panel
        if (this.view != null)
        {
            this.view.quit();
        }
    }
}
