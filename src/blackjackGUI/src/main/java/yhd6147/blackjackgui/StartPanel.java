package yhd6147.blackjackgui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;


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
    
    //private JTextArea playersPlaying;
    private DefaultListModel listModel;
    private JList playingList;
    
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
        
        this.titleLabel = new JLabel("Dudov's BlackJack", SwingConstants.CENTER);
        this.titleLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        this.statusLabel  = new JLabel("Not enough players to start", SwingConstants.CENTER);
        
        this.listModel = new DefaultListModel();
        this.playingList = new JList(this.listModel);
        this.playingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.rulesButton        = new JButton("Rules");
        this.scoreboardButton   = new JButton("Scoreboard");
        this.addPlayerButton    = new JButton("Add Player");
        this.removePlayerButton = new JButton("Remove Player");
        this.startButton        = new JButton("Start");
        this.quitButton         = new JButton("Quit");
        
        this.startButton.setEnabled(false);
        this.removePlayerButton.setEnabled(false);
        
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
        this.startButton.setEnabled(false);
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        addComponent(this.titleLabel,         0,0,2);
        addComponent(this.statusLabel,        0,1,2);
        
        addComponent(this.playingList,        0,2,2);
        
        addComponent(this.addPlayerButton,    0,3,1);
        addComponent(this.removePlayerButton, 1,3,1);
        addComponent(this.startButton,        0,4,2);
        
        this.c.insets = new Insets(30,0,0,0);
        addComponent(this.rulesButton,        0,5,1);
        addComponent(this.scoreboardButton,   1,5,1);
        this.c.insets = new Insets(0,0,0,0);
        addComponent(this.quitButton,         0,6,2);
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
        if (this.view != null)
        {
            this.view.openScoreboardPanel();
        }
    }
    
    private void addPlayer()
    {
        this.view.openLoginPanel();
    }
    
    private void removePlayer()
    {
        // Removes player from list
        int nPlayers = this.view.getModel().getNPlayers();
        
        if (nPlayers > 0)
        {
            String playerName = (String) this.playingList.getSelectedValue();
            this.view.getModel().removePlayer(playerName);
        }
        
        this.updatePlayers();
    }
    
    public void updatePlayers()
    {
        Player[] playerList = this.view.getModel().getPlayers();
        int nPlayers = this.view.getModel().getNPlayers();
        
        this.listModel.clear();
        
        if (nPlayers > 0 && nPlayers <= 5)
        {
            this.statusLabel.setText("Currently Playing");
            this.startButton.setEnabled(true);
            
            for (int i = 0; i < playerList.length; i++)
            {
                listModel.addElement(playerList[i].getName());
            }
        }
        
        if (nPlayers >= 5) // Max or more
        {
            // No more adding players
            this.addPlayerButton.setEnabled(false);
            this.removePlayerButton.setEnabled(true);
            
            if (nPlayers > 5)
            {
                // This shouldn't happen as there is a check in the model
                this.statusLabel.setText("Too many players to start game");
                this.startButton.setEnabled(false);
            }
        }
        else // Less than max
        {
            // Can add more players
            this.addPlayerButton.setEnabled(true);
            
            if (nPlayers > 0)
            {
                this.removePlayerButton.setEnabled(true);
            }
            else // Zero players
            {
                this.statusLabel.setText("Not enough players to start game");
                this.startButton.setEnabled(false);
                this.removePlayerButton.setEnabled(false);
            }
        }
    }
    
    private void start()
    {
        // Starts game panel
        if (this.view.getModel().getNPlayers() > 0)
        {
            this.view.startGame();
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
