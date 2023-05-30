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
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * @author yhd6147
 */
public class StartPanel extends JPanel implements Quitable
{
    
    // === VARIABLES ==========================================================
    
    private BlackJackViewGUI   view;
    private GridBagConstraints c;
    
    private JLabel titleLabel;
    private JLabel playingLabel;
    private JLabel statusLabel;
    
    private JTextArea playersPlaying;
    
    private JButton rulesButton;
    private JButton scoreboardButton;
    private JButton addPlayerButton;
    private JButton removePlayerButton;
    private JButton startButton;
    private JButton quitButton;
    
    // === CONSTRUCTOR ========================================================
    
    public StartPanel(BlackJackViewGUI view)
    {
        super(new GridBagLayout());
        
        this.view = view;
        this.c = new GridBagConstraints();
        
        this.view = view;
        this.c = new GridBagConstraints();
        
        this.titleLabel = new JLabel("Welcome to Dudov's BlackJack");
        // TODO find a better way to change font.
        this.titleLabel.setFont(new Font(this.titleLabel.getFont().getName(), Font.PLAIN, this.titleLabel.getFont().getSize()*2));
        this.statusLabel  = new JLabel("");
        this.playingLabel = new JLabel("Currently Playing:");
        
        // TODO change text area to a list instead with an add and remove button
        this.playersPlaying = new JTextArea("");
        this.playersPlaying.setEditable(false);
        
        this.rulesButton        = new JButton("Rules");
        this.scoreboardButton   = new JButton("Scoreboard");
        this.addPlayerButton    = new JButton("Add Player");
        this.removePlayerButton = new JButton("Remove Player");
        this.startButton        = new JButton("Start");
        this.quitButton         = new JButton("Quit");
        
        this.rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rules();
            }
        });
        this.scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreboard();
            }
        });
        this.addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });
        this.removePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePlayer();
            }
        });
        this.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        addComponent(this.titleLabel,         0,0,2);
        addComponent(this.rulesButton,        0,1,1);
        addComponent(this.scoreboardButton,   1,1,1);
        addComponent(this.statusLabel,        0,2,2);
        
        addComponent(this.playingLabel,       0,3,2);
        addComponent(this.playersPlaying,     0,4,2);
        addComponent(this.addPlayerButton,    0,5,1);
        addComponent(this.removePlayerButton, 1,5,1);
        
        addComponent(this.startButton,        0,6,1);
        addComponent(this.quitButton,         1,6,1);
    }
    
    // === METHODS ============================================================
    
    private void rules()
    {
        if (this.view != null)
        {
            this.view.openRulesPanel();
        }
    }
    
    private void scoreboard()
    {
        // TODO display scoreboard panel
    }
    
    private void addPlayer()
    {
        // TODO addPlayer (open login panel)
    }
    
    private void removePlayer()
    {
        // TODO removePlayer (remove from list (that doesnt exist yet))
    }
    
    private void start()
    {
        // TODO start game (open game panel)
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
