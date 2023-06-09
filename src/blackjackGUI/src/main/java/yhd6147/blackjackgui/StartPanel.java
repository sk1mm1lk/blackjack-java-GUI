package yhd6147.blackjackgui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
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
        // Initialises all the components for the start panel

        // Calls JPanel constructor
        super(new GridBagLayout());
        
        this.view = view;
        this.c = new GridBagConstraints();
        
        // The label at the top of the screen
        this.titleLabel = new JLabel("Dudov's BlackJack", SwingConstants.CENTER);
        this.titleLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        
        // The label showing if game can start
        this.statusLabel  = new JLabel("Not enough players to start", SwingConstants.CENTER);
        
        // The list of currently playing players
        this.listModel = new DefaultListModel();
        this.playingList = new JList(this.listModel);
        this.playingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // The buttons for interacting with the start panel
        this.rulesButton        = new JButton("Rules");
        this.scoreboardButton   = new JButton("Scoreboard");
        this.addPlayerButton    = new JButton("Add Player");
        this.removePlayerButton = new JButton("Remove Player");
        this.startButton        = new JButton("Start");
        this.quitButton         = new JButton("Quit");
        
        // Disabling the start and remove buttons as there are no players yet
        this.startButton.setEnabled(false);
        this.removePlayerButton.setEnabled(false);
        
        // Initialising the button press action for each button
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
        
        // Adding the top half of components to the panel
        addComponent(this.titleLabel,         0,0,2);
        addComponent(this.statusLabel,        0,1,2);
        addComponent(this.playingList,        0,2,2);
        addComponent(this.addPlayerButton,    0,3,1);
        addComponent(this.removePlayerButton, 1,3,1);
        addComponent(this.startButton,        0,4,2);
        
        // Adding the bottom half of components to the panel
        this.c.insets = new Insets(30,0,0,0);
        addComponent(this.rulesButton,        0,5,1);
        addComponent(this.scoreboardButton,   1,5,1);
        this.c.insets = new Insets(0,0,0,0);
        addComponent(this.quitButton,         0,6,2);
    }
    
    // === METHODS ============================================================
    
    private void addPlayer()
    {
        // Opens the login panel to add another player
        this.view.openLoginPanel();
    }

    private void removePlayer()
    {
        // Removes selected player from the list
        int nPlayers = this.view.getModel().getNPlayers();
        
        if (nPlayers > 0)
        {
            String playerName = (String) this.playingList.getSelectedValue();
            this.view.getModel().removePlayer(playerName);
        }
        
        this.updatePlayers();
    }

    private void rules()
    {
        // Opens the rules panel (used as the method for the rules button)
        if (this.view != null)
        {
            this.view.openRulesPanel();
        }
    }
    
    private void scoreboard()
    {
        // Opens the scoreboard panel (used as the method for the scoreboard button)
        if (this.view != null)
        {
            this.view.openScoreboardPanel();
        }
    }
    
    public void updatePlayers()
    {
        // Updates the list representing the players in the game
        Player[] playerList = this.view.getModel().getPlayers();
        int nPlayers = this.view.getModel().getNPlayers();
        
        // First clears the list
        this.listModel.clear();
        
        if (nPlayers > 0 && nPlayers <= 5)
        {
            // If there are enough players then will add each player to the list
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
        // Starts game panel if there are enough players
        if (this.view.getModel().getNPlayers() > 0)
        {
            this.view.startGame();
        }
    }
    
    private void addComponent(JComponent component, int x, int y, int width)
    {
        // Adds a component to the panel using a GridBagLayout
        this.c.fill = GridBagConstraints.HORIZONTAL;
        this.c.gridx = x;
        this.c.gridy = y;
        this.c.gridwidth = width;
        this.add(component, c);
    }

    @Override
    public void quit()
    {
        // Quits out of the start panel (quits completely)
        if (this.view != null)
        {
            this.view.quit();
        }
    }
}
