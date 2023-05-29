/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yhd6147.blackjackgui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author yhd6147
 */
public class GamePanel extends BlackJackPanel
{
    
    // === VARIABLES ==========================================================
    
    private BlackJackViewGUI   view;
    private GridBagConstraints c;
    
    private JLabel titleLabel;
    
    private JButton quitButton;
    
    
    // === CONSTRUCTOR ========================================================

    public GamePanel(BlackJackViewGUI view)
    {
        super();
        this.view = view;
        this.c = new GridBagConstraints();
        
        this.titleLabel = new JLabel("Dudov's BlackJack");
        // TODO find a better way to change font.
        this.titleLabel.setFont(new Font(this.titleLabel.getFont().getName(), Font.PLAIN, this.titleLabel.getFont().getSize()*2));
        
        this.quitButton  = new JButton("Quit");
        
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        
        addComponent(this.titleLabel, 0,0,3);
        addComponent(this.quitButton, 0,1,3);
    }
    
    @Override
    protected void addComponent(Component component, int x, int y, int width)
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
