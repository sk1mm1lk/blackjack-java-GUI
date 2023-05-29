/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yhd6147.blackjackgui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 * @author yhd6147
 */
public class RulesPanel extends JPanel implements Quitable
{
    // === VARIABLES ==========================================================
    
    private BlackJackViewGUI view;
    private GridBagConstraints c;
    
    // === CONSTRUCTOR ========================================================
    
    public RulesPanel(BlackJackViewGUI view)
    {
        super(new GridBagLayout());
        
        this.view = view;
        this.c = new GridBagConstraints();
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
