package yhd6147.blackjackgui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * @author yhd6147
 */
public class GamePanel extends JPanel implements Quitable
{
    
    // === VARIABLES ==========================================================
    
    private BlackJackViewGUI   view;
    private GridBagConstraints c;
    private FileController fc;
    private Timer timer;
    private final int sleepTime = 100;
    
    private JLabel titleLabel;
    private JLabel currentPlayerLabel;
    
    //private ArrayList<JLabel> cardLabels;
    private JTextArea cardTextArea;
    private JScrollPane cardPane;
    
    //private JList playerHands;
    
    //private JButton betButton;
    private JButton drawCardButton;
    private JButton endTurnButton;
    private JButton quitButton;
    
    private int turnsPlayed;
    private boolean gameStarted;
    
    // === CONSTRUCTOR ========================================================

    public GamePanel(BlackJackViewGUI view)
    {
        super(new GridBagLayout());
        
        this.view = view;
        this.c = new GridBagConstraints();
        this.fc = new FileController();
        this.gameStarted = false;
        
        this.timer = new Timer(this.sleepTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Do nothing
            }
            
        });

        this.timer.setRepeats(false);
        
        this.titleLabel = new JLabel("Dudov's BlackJack");
        // TODO find a better way to change font.
        this.titleLabel.setFont(new Font(this.titleLabel.getFont().getName(), Font.PLAIN, this.titleLabel.getFont().getSize()*2));
        
        this.currentPlayerLabel = new JLabel("To start the game press draw");
        
        this.cardTextArea = new JTextArea();
        this.cardTextArea.setEditable(false);
        this.cardPane = new JScrollPane(this.cardTextArea);
        
        this.drawCardButton  = new JButton("Draw");
        this.drawCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawCard();
            }
        });
        
        this.endTurnButton  = new JButton("End Turn");
        this.endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTurn();
            }
        });
        this.endTurnButton.setEnabled(false);
        
        this.quitButton  = new JButton("Quit");
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        addComponent(this.titleLabel,    0,0,3);
        addComponent(this.currentPlayerLabel, 0,1,3);
        addComponent(this.cardPane, 0,2,3);
        addComponent(this.drawCardButton, 0,3,2);
        addComponent(this.endTurnButton, 2,3,1);
        addComponent(this.quitButton, 0,4,3);
        
        this.turnsPlayed = 0;
        
        this.view.getModel().startGame();
        //this.updateCurrentPlayer();
    }
    
    public void updateCurrentPlayer()
    {
        Player currentPlayer = this.view.getModel().getCurrentPlayer();
        this.turnsPlayed = 0;
        
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
        this.gameStarted = true;
        this.updateCurrentPlayer();
    }
    
    public void drawCard()
    {
        // Draws a card, also doubles as game start
        if (!this.gameStarted)
        {
            this.startGame();
            return;
        }
        
        System.out.println("CARD DRAW");
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
        
        cardTableText = cardTableText + "\n\nHand Value: " + handValue;
        
        if(isBust)
        {
            cardTableText = cardTableText + "\n\nBUST";
        }
        
        this.cardTextArea.setText(cardTableText);
    }
    
    public void houseTurn()
    {
        // Plays the house's turn
        this.updateButtons(false, false);
        
        while (this.view.getModel().houseCanDraw())
        {
            this.view.getModel().houseDraw();
            this.pause();
            this.updateCardPane();
        }
        
        this.updateButtons(false, true);
        this.view.getModel().endGame();
    }
    
    public void pause()
    {
        // Pauses game play for dramatic effect
        this.timer.start();
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
            this.quit();
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
            if (this.view.getModel().isGameOver())
            {
                this.view.getModel().scoreGame();
                this.view.openScoreboardPanel();
            }
            else
            {
                this.view.quit();
            }
        }
    }
}
