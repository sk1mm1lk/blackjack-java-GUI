/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yhd6147.blackjackgui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author yhd6147
 */
public class ScoreboardPanel extends JPanel implements Quitable
{
    
    private BlackJackViewGUI   view;
    private GridBagConstraints c;
    private FileController     fc;
    
    private JTextArea scoreboardTextArea;
    
    private JButton quitButton;
    
    private String scoreboardText;
    
    // === CONSTRUCTOR ========================================================
    
    public ScoreboardPanel(BlackJackViewGUI view)
    {
        super(new GridBagLayout());
        
        this.view = view;
        this.c = new GridBagConstraints();
        this.fc = new FileController();
        
        this.initScoreboard();
        
        this.scoreboardTextArea = new JTextArea(this.scoreboardText);
        this.scoreboardTextArea.setEditable(false);
        
        this.quitButton = new JButton("Back");
        
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        this.addComponent(this.scoreboardTextArea, 0, 0, 2);
        this.addComponent(this.quitButton,         1, 1, 1);
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
