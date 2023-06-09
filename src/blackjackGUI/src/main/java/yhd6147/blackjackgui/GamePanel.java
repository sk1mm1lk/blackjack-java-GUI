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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * @author yhd6147
 */
public class GamePanel extends JPanel implements Quitable
{
    
    // === VARIABLES ==========================================================
    
    private BlackJackViewGUI   view;
    private GridBagConstraints c;
    private FileController fc;
    
    private JLabel currentPlayerLabel;
    private JLabel handValueLabel;
    
    private JTextArea cardTextArea;
    private JScrollPane cardPane;
    
    private JButton startGameButton;
    private JButton drawCardButton;
    private JButton endTurnButton;
    private JButton quitButton;
    
    private int turnsPlayed;
    
    // === CONSTRUCTOR ========================================================

    public GamePanel(BlackJackViewGUI view)
    {
        // Initialises the game components
        super(new GridBagLayout());
        
        this.view = view;
        this.c = new GridBagConstraints();
        this.fc = new FileController();
        
        this.view.getModel().startGame();
        
        this.currentPlayerLabel = new JLabel("Dudov's Black Jack", SwingConstants.CENTER);
        this.currentPlayerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        this.handValueLabel = new JLabel("");
        
        this.cardTextArea = new JTextArea();
        this.cardTextArea.setEditable(false);
        this.cardTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        this.cardPane = new JScrollPane(this.cardTextArea);
        this.cardPane.setSize(100, 100);
        this.cardPane.setVisible(false);
        
        // Initialises all the button components
        this.startGameButton  = new JButton("Start");

        this.drawCardButton  = new JButton("Draw");
        this.drawCardButton.setEnabled(false);
        this.drawCardButton.setVisible(false);

        this.endTurnButton  = new JButton("End Turn");
        this.endTurnButton.setEnabled(false);
        this.endTurnButton.setVisible(false);

        this.quitButton  = new JButton("Quit");

        // Initialises the button actions
        this.startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        this.drawCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawCard();
            }
        });
        this.endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTurn();
            }
        });
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        // Adds the components to the panel
        addComponent(this.currentPlayerLabel, 0,0,3);
        addComponent(this.cardPane,           0,1,3);
        addComponent(this.handValueLabel,     0,2,3);
        addComponent(this.startGameButton,    0,3,3);
        addComponent(this.drawCardButton,     0,4,2);
        addComponent(this.endTurnButton,      2,4,1);
        addComponent(this.quitButton,         0,5,3);
        
        // Sets the turns played to 0 (number of times a card has been drawn)
        this.turnsPlayed = 0;
    }
    
    public void updateCurrentPlayer()
    {
        // Updates the current player's game being displayed
        Player currentPlayer = this.view.getModel().getCurrentPlayer();
        this.turnsPlayed = 0;
        
        // Removes the cardpane until a card has been drawn
        this.cardPane.setVisible(false);
        
        if (currentPlayer == null)
        {
            this.currentPlayerLabel.setText("Failed getting current player");
            this.updateCardPane();
            this.updateButtons(false, true);
            return;
        }
        
        // Updates card pane (to remove previous game)
        this.currentPlayerLabel.setText(currentPlayer.getName());
        this.updateCardPane();

        // Allows a user to draw a card but not end their turn
        this.updateButtons(true, false);
        
        if (this.view.getModel().isHouseTurn())
        {
            // If all players have had their turn it becomes the house's turn
            this.houseTurn();
        }
        else
        {
            // If it is a player's turn open the betting panel
            this.playerBet();
        }
    }
    
    public void startGame()
    {
        // Action for the start game button
        if (!this.view.getModel().isGameOver())
        {
            this.updateCurrentPlayer();
            this.startGameButton.setVisible(false);
            this.drawCardButton.setVisible(true);
            this.endTurnButton.setVisible(true);
            this.updateButtons(true,false);
        }
        else
        {
            // If the game is over the start button becomes a restart button
            this.newGame();
            return;
        }
    }
    
    public void newGame()
    {
        // Calls view to start a new instance of the game panel
        this.view.startGame();
    }

    public void playerBet()
    {
        // Opens the betting panel
        this.view.openBetPanel();
    }
    
    public void drawCard()
    {
        // Draws a card
        this.cardPane.setVisible(true);
        
        if (this.view.getModel().canDraw())
        {
            this.view.getModel().drawCard();
            this.updateCardPane();
            this.turnsPlayed ++;
        }
        
        if (this.turnsPlayed < 2)
        {
            // A player is forced to draw two cards to begin with
            this.updateButtons(true, false);
        }
        else
        {
            if (this.view.getModel().canDraw())
            {
                this.updateButtons(true, true);
            }
            else
            {
                this.updateButtons(false, true);
            }
        }
    }
    
    private void updateButtons(boolean draw, boolean end)
    {
        // Sets the draw and end turn buttons to be enabled or not
        this.drawCardButton.setEnabled(draw);
        this.endTurnButton.setEnabled(end);
    }
    
    public void updateCardPane()
    {
        // Updates the cards displayed on the panel
        Card[] cardTable = this.view.getModel().getCardTable();
        boolean isBust = this.view.getModel().isBust();
        int handValue = this.view.getModel().handValue();
        
        if (cardTable == null)
        {
            this.cardTextArea.setText("");
            this.handValueLabel.setText("");
            return;
        }
        
        String cardTableText = "";
        String cardArt = "";
        
        for (Card card : cardTable)
        {
            // Attempts to read the card asset from assets/ and if not just prints the name
            cardArt = fc.read("assets/" + card.toString() + ".txt");
            if (cardArt == null)
            {
                cardArt = "";
            }
            cardArt = cardArt + card.toString() + "\n";
            
            cardTableText = cardTableText + cardArt;
        }
        
        // Sets the hand value label
        this.handValueLabel.setText("Total hand value: " + handValue);
        
        if(isBust)
        {
            this.handValueLabel.setText("BUST");
        }
        
        // Sets the cardTextArea
        this.cardTextArea.setText(cardTableText);
    }
    
    public void houseTurn()
    {
        // Plays the house's turn all done automatically
        this.cardPane.setVisible(true);
        this.updateButtons(false, false);
        
        while (this.view.getModel().houseCanDraw())
        {
            // While house can draw: draw
            this.view.getModel().houseDraw();
            this.updateCardPane();
        }
        
        this.updateButtons(false, true);
        this.view.getModel().endGame();
        this.view.getModel().scoreGame();
        this.endTurnButton.setText("Go to scoreboard");
        this.startGameButton.setText("Play again");
        this.startGameButton.setVisible(true);
    }
    
    public void endTurn()
    {
        // Ends the turn and goes to the scoreboard if the game is completely over
        if (!this.view.getModel().isGameOver())
        {
            this.view.getModel().nextPlayer();
            this.updateCurrentPlayer();
        }
        else
        {
            this.view.openScoreboardPanel();
        }
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
        // Quits the game
        if (this.view != null)
        {
            this.view.quit();
        }
    }
}
