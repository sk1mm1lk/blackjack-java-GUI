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
    
    //private ArrayList<JLabel> cardLabels;
    private JTextArea cardTextArea;
    private JScrollPane cardPane;
    
    //private JList playerHands;
    
    //private JButton betButton;
    private JButton startGameButton;
    private JButton drawCardButton;
    private JButton endTurnButton;
    private JButton quitButton;
    
    private int turnsPlayed;
    
    // === CONSTRUCTOR ========================================================

    public GamePanel(BlackJackViewGUI view)
    {
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
        
        this.startGameButton  = new JButton("Start");
        this.startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        
        this.drawCardButton  = new JButton("Draw");
        this.drawCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawCard();
            }
        });
        this.drawCardButton.setEnabled(false);
        this.drawCardButton.setVisible(false);
        
        this.endTurnButton  = new JButton("End Turn");
        this.endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTurn();
            }
        });
        this.endTurnButton.setEnabled(false);
        this.endTurnButton.setVisible(false);
        
        this.quitButton  = new JButton("Quit");
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        addComponent(this.currentPlayerLabel, 0,0,3);
        addComponent(this.cardPane,           0,1,3);
        addComponent(this.handValueLabel,     0,2,3);
        addComponent(this.startGameButton,    0,3,3);
        addComponent(this.drawCardButton,     0,4,2);
        addComponent(this.endTurnButton,      2,4,1);
        addComponent(this.quitButton,         0,5,3);
        
        this.turnsPlayed = 0;
        
        
        //this.updateCurrentPlayer();
    }
    
    public void updateCurrentPlayer()
    {
        Player currentPlayer = this.view.getModel().getCurrentPlayer();
        this.turnsPlayed = 0;
        
        this.cardPane.setVisible(false);
        
        if (currentPlayer == null)
        {
            this.currentPlayerLabel.setText("Failed getting current player");
            this.updateCardPane();
            this.updateButtons(false, true);
            return;
        }
        
        this.currentPlayerLabel.setText(currentPlayer.getName());
        this.updateCardPane();

        this.updateButtons(true, false);
        
        if (this.view.getModel().isHouseTurn())
        {
            this.houseTurn();
        }
        else
        {
            this.playerBet();
        }
    }
    
    public void startGame()
    {
        if (!this.view.getModel().isGameOver())
        {
            this.updateCurrentPlayer();
            this.startGameButton.setVisible(false);
            this.drawCardButton.setVisible(true);
            this.endTurnButton.setVisible(true);
            //this.cardPane.setVisible(true);
            this.updateButtons(true,false);
        }
        else
        {
            this.newGame();
            return;
        }
    }
    
    public void newGame()
    {
        this.view.startGame();
    }
    
    public void drawCard()
    {
        // Draws a card
        this.cardPane.setVisible(true);
        
        if (this.view.getModel().canDraw())
        {
            // TODO draw card
            this.view.getModel().drawCard();
            this.updateCardPane();
            this.turnsPlayed ++;
        }
        
        if (this.turnsPlayed < 2)
        {
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
        this.drawCardButton.setEnabled(draw);
        this.endTurnButton.setEnabled(end);
    }
    
    public void playerBet()
    {
        this.view.openBetPanel();
    }
    
    public void updateCardPane()
    {
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
            cardArt = fc.read("assets/" + card.toString() + ".txt");
            if (cardArt == null)
            {
                cardArt = "";
            }
            cardArt = cardArt + card.toString() + "\n";
            
            cardTableText = cardTableText + cardArt;
        }
        
        this.handValueLabel.setText("Total hand value: " + handValue);
        
        if(isBust)
        {
            this.handValueLabel.setText("BUST");
        }
        
        this.cardTextArea.setText(cardTableText);
    }
    
    public void houseTurn()
    {
        // Plays the house's turn
        this.cardPane.setVisible(true);
        this.updateButtons(false, false);
        
        while (this.view.getModel().houseCanDraw())
        {
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
        // TODO end turn
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
