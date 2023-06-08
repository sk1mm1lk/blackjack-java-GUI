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
public class ScoreboardPanel extends JPanel implements Quitable
{
    
    private BlackJackViewGUI   view;
    private FileController     fc;
    
    private JTextArea scoreboardTextArea;
    private JScrollPane scoreboardScrollPane;
    
    private JButton quitButton;
    
    private String scoreboardText;
    
    // === CONSTRUCTOR ========================================================
    
    public ScoreboardPanel(BlackJackViewGUI view)
    {
        super(new BorderLayout());
        
        this.view = view;
        this.fc = new FileController();
        
        this.initScoreboard();
        
        this.scoreboardTextArea = new JTextArea(this.scoreboardText);
        this.scoreboardTextArea.setEditable(false);
        this.scoreboardScrollPane = new JScrollPane(this.scoreboardTextArea);
        
        this.quitButton = new JButton("Back");
        
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        this.add(this.quitButton, BorderLayout.PAGE_START);
        this.add(this.scoreboardScrollPane, BorderLayout.CENTER);
    }
    
    private void initScoreboard()
    {
        // Gets the scoreboard art from the assets folder and substitutes with a default if non existent
        String scoreboard = fc.read("assets/scoreboardText.txt");
        if (scoreboard != null)
        {
            this.scoreboardText = scoreboard;    
        }
        else
        {
            this.scoreboardText = "\n";
            this.scoreboardText += "====================\n";
            this.scoreboardText += "     SCOREBOARD     \n";
            this.scoreboardText += "====================";
        }
    }
    
    public void updateScores()
    {
        this.scoreboardTextArea.setText(this.scoreboardText + "\n" + this.view.getScoreboardScores());
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
