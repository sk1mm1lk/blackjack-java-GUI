package yhd6147.blackjackgui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author yhd6147
 */
public class RulesPanel extends JPanel implements Quitable
{
    // === VARIABLES ==========================================================
    
    private BlackJackViewGUI   view;
    private FileController     fc;
    
    private JTextArea rulesTextArea;
    private JScrollPane rulesScrollPane;
    
    private JButton quitButton;
    
    private String rulesText;
    
    // === CONSTRUCTOR ========================================================
    
    public RulesPanel(BlackJackViewGUI view)
    {
        // Initialises the components for the panel

        // Calls JPanel constructor
        super(new BorderLayout());
        
        this.view = view;
        this.fc = new FileController();
        
        // Initialises the rules text
        this.initRules();
        
        this.rulesTextArea = new JTextArea(this.rulesText);
        this.rulesTextArea.setEditable(false);
        this.rulesScrollPane = new JScrollPane(this.rulesTextArea);
        
        // Initialises the quit button
        this.quitButton = new JButton("Back");
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        // Adds the components to the panel using BorderLayout
        this.add(this.quitButton, BorderLayout.PAGE_START);
        this.add(this.rulesScrollPane, BorderLayout.CENTER);
    }
    
    private void initRules()
    {
        // Gets the rules content from the assets folder and substitutes with a default if non existent
        String rules = fc.read("assets/rules.txt");
        if (rules != null)
        {
            this.rulesText = rules;
        }
        else
        {
            this.rulesText = "=== Rules ===";
            this.rulesText += "1. You can have 1 to 5 players for this game";
            this.rulesText += "2. Any betting in this game puts you against the house, not other players";
            this.rulesText += "3. If you don't bet, you can still win 10 points but you will lose 0 points";
            this.rulesText += "4. Each game consists of 5 stages, which are:";
            this.rulesText += "     Betting";
            this.rulesText += "       You are asked if you want to place a bet, you can choose not to.";
            this.rulesText += "       Bets can reach a maximum of the amount of points you have";
            this.rulesText += "     Starting 2 cards are dealt";
            this.rulesText += "       Each player, including the house, is dealt two cards face up";
            this.rulesText += "       Each card has a value ranging from 1 (Ace) to 13 (King)";
            this.rulesText += "       Each hand has a sum value that cannot go above 21";
            this.rulesText += "       Going above 21 causes you to bust (lose)";
            this.rulesText += "     Keep adding cards";
            this.rulesText += "       You can choose to top your hand up with another card to get closer to 21";
            this.rulesText += "       You will be allowed to top as many cards as you would like (before bust) or none at all";
            this.rulesText += "     Scoring";
            this.rulesText += "       As stated previously, each card has a value ranging from 1 being and Ace to 13 being a King";
            this.rulesText += "       The higher the value of your hand, the better, but as long as it isn't greater than 21";
            this.rulesText += "     Rewarding points";
            this.rulesText += "       If you placed no bet and you win, you receive only 10 points";
            this.rulesText += "       If you placed a bet and you did win, you receive 10 points plus your bet amount";
            this.rulesText += "       If you placed no bet and you lose, you don't lose any points";
            this.rulesText += "       If you placed a bet and you lose, you will only lose the amount you bet";
        }
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
