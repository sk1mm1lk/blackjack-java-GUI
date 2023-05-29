/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yhd6147.blackjackgui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
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
        super(new GridBagLayout());
        this.view = view;
        this.c = new GridBagConstraints();
        
        this.titleLabel = new JLabel("Dudov's BlackJack");
        // TODO find a better way to change font.
        this.titleLabel.setFont(new Font(this.titleLabel.getFont().getName(), Font.PLAIN, this.titleLabel.getFont().getSize()*2));
        this.loginStatusLabel = new JLabel("Enter your username and password");
        this.usernameLabel = new JLabel("Username:");
        this.passwordLabel = new JLabel("Password:");
        
        this.usernameInput = new JTextField(MAX_USERNAME_LENGTH);
        this.passwordInput = new JTextField(MAX_PASSWORD_LENGTH);
        
        this.loginButton = new JButton("Login");
        this.quitButton  = new JButton("Quit");
        
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
        String username = this.usernameInput.getText();
        String password = this.passwordInput.getText();
        
        if (!username.isBlank() && !password.isBlank())
        {
            // continue
            //this.model.login(username, password);
            this.view.openGamePanel();
        }
        else
        {
            this.loginStatusLabel.setText("Invalid login details");
        }
    }
    
    private void addComponent(Component component, int x, int y, int width)
    {
        this.c.fill = GridBagConstraints.HORIZONTAL;
	this.c.gridx = x;
	this.c.gridy = y;
        this.c.gridwidth = width;
        this.add(component, c);
    }

    @Override
    public void quit()
    {
        if (this.view != null)
        {
            this.view.quit();
        }
    }
    
}
