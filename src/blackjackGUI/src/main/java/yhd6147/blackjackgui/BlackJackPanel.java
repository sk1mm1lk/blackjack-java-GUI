/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yhd6147.blackjackgui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;

/**
 *
 * @author yhd6147
 */
public abstract class BlackJackPanel extends JPanel implements Quitable
{
    protected abstract void addComponent(Component component, int x, int y, int width);
}
