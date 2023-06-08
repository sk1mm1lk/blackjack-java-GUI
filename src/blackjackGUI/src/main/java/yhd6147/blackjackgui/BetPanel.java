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
        super(new GridBagLayout());
        
        this.view = view;
        this.c = new GridBagConstraints();
        
        this.titleLabel = new JLabel("Time to bet");
        // TODO find a better way to change font.
        this.titleLabel.setFont(new Font(this.titleLabel.getFont().getName(), Font.PLAIN, this.titleLabel.getFont().getSize()*2));
        this.currentPlayerLabel = new JLabel(getCurrentPlayerName());
        
        this.playerBet = 0;
        this.playerBetLabel = new JLabel("" + this.playerBet);
        
        this.increaseButton  = new JButton("+1");
        this.decreaseButton  = new JButton("-1");
        this.quitButton  = new JButton("Set");
        
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
        
        addComponent(this.titleLabel,         0,0,3);
        addComponent(this.currentPlayerLabel, 0,1,3);
        addComponent(this.decreaseButton,     0,2,1);
        addComponent(this.increaseButton,     2,2,1);
        addComponent(this.playerBetLabel,     1,2,1);
        addComponent(this.quitButton,         0,3,3);
        
        this.updatePanel();
    }
    
    private void addComponent(Component component, int x, int y, int width)
    {
        this.c.fill = GridBagConstraints.HORIZONTAL;
	this.c.gridx = x;
	this.c.gridy = y;
        this.c.gridwidth = width;
        this.add(component, c);
    }
    
    public Player getCurrentPlayer()
    {
        // Gets the current player from the model
        return this.view.getModel().getCurrentPlayer();
    }
    
    public String getCurrentPlayerName()
    {
        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer != null)
            return currentPlayer.getName();
        return "No Name";
    }
    
    private boolean canIncrease()
    {
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
        // Increases the players bet
        if (canIncrease())
        {
            this.playerBet++;
        }
        updatePanel();
    }
    
    public void decrease()
    {
        if (canDecrease())
        {
            this.playerBet--;
        }
        updatePanel();
    }
    
    public void updatePanel()
    {
        // Updates the panel values
        this.currentPlayerLabel.setText(getCurrentPlayerName());
        
        this.playerBetLabel.setText("" + this.playerBet);
        if (!canDecrease())
            this.decreaseButton.setEnabled(false);
        else
            this.decreaseButton.setEnabled(true);
        
        if (!canIncrease())
            this.increaseButton.setEnabled(false);
        else
            this.increaseButton.setEnabled(true);
    }
    
    @Override
    public void quit()
    {
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
