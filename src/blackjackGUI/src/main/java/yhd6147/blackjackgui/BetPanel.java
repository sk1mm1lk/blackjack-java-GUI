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

/**
 * @author yhd6147
 */
public class BetPanel extends JPanel implements Quitable
{
    
    // === VARIABLES ==========================================================
    
    private BlackJackViewGUI   view;
    private GridBagConstraints c;
    
    private JLabel titleLabel;
    private JLabel currentPlayerLabel;
    private JLabel playerBetLabel;
    
    private JButton increaseButton;
    private JButton decreaseButton;
    private JButton quitButton;
    
    private int playerBet;
    
    // === CONSTRUCTOR ========================================================

    public BetPanel(BlackJackViewGUI view)
    {
        // Initialises the panel components

        // Call the JPanel constructor
        super(new GridBagLayout());
        
        this.view = view;
        this.c = new GridBagConstraints();
        
        this.currentPlayerLabel = new JLabel(getCurrentPlayerName());
        this.currentPlayerLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        
        this.playerBet = 0;
        this.playerBetLabel = new JLabel("Betting: " + this.playerBet);
        this.playerBetLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        
        this.increaseButton  = new JButton("+1");
        this.decreaseButton  = new JButton("-1");
        // The set button doubles as the quit button
        this.quitButton  = new JButton("Set");
        
        // Initialises the button actions
        this.increaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                increase();
            }
        });
        this.decreaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decrease();
            }
        });
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        // Adds the components to panel
        addComponent(this.currentPlayerLabel, 0,0,2);
        addComponent(this.playerBetLabel,     0,1,2);
        addComponent(this.decreaseButton,     0,2,1);
        addComponent(this.increaseButton,     1,2,1);
        addComponent(this.quitButton,         0,3,2);
        
        // Updates the panel values
        this.updatePanel();
    }

    // === METHODS ============================================================

    public void updatePanel()
    {
        // Updates the panel values
        this.currentPlayerLabel.setText(this.getCurrentPlayerName());
        this.playerBetLabel.setText("Betting: " + this.playerBet + "/" + this.getCurrentPlayerMax());
        
        if (!canDecrease())
            this.decreaseButton.setEnabled(false);
        else
            this.decreaseButton.setEnabled(true);
        
        if (!canIncrease())
            this.increaseButton.setEnabled(false);
        else
            this.increaseButton.setEnabled(true);
    }

    public Player getCurrentPlayer()
    {
        // Gets the current player from the model
        return this.view.getModel().getCurrentPlayer();
    }
    
    public String getCurrentPlayerName()
    {
        // Gets the current players name from the model
        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer != null)
            return currentPlayer.getName();
        return "No Name";
    }
    
    public int getCurrentPlayerMax()
    {
        // Gets the maximum bet that a player can set
        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer != null)
            return currentPlayer.getScore();
        return 0;
    }
    
    private boolean canIncrease()
    {
        // Returns true if the player is able to increase their bet
        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer == null)
            return false;
        
        if (this.playerBet < this.getCurrentPlayer().getScore())
        {
            return true;
        }
        return false;
    }
    
    private boolean canDecrease()
    {
        // Returns true if the player is able to decrease their bet
        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer == null)
            return false;
        
        if (this.playerBet > 0)
        {
            return true;
        }
        return false;
    }
    
    public void increase()
    {
        // Increases the players bet and updates the panel
        if (canIncrease())
        {
            this.playerBet++;
        }
        updatePanel();
    }
    
    public void decrease()
    {
        // Decreases the players bet and updates the panel
        if (canDecrease())
        {
            this.playerBet--;
        }
        updatePanel();
    }

    private void addComponent(JComponent component, int x, int y, int width)
    {
        // Adds component to the panel following the GridBagLayout
        this.c.fill = GridBagConstraints.HORIZONTAL;
        this.c.gridx = x;
        this.c.gridy = y;
        this.c.gridwidth = width;
        this.add(component, c);
    }

    @Override
    public void quit()
    {
        // Quits the panel and attempts to update the bet of the current player
        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer != null)
        {
            this.view.getModel().setPlayerBet(currentPlayer, this.playerBet);
        }
        
        if (this.view != null)
        {
            this.view.openGamePanel();
        }
    }
}
