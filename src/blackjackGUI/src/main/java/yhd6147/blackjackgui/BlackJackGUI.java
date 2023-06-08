package yhd6147.blackjackgui;

/**
 * @author yhd6147
 */
public class BlackJackGUI
{
    // === VARIABLES ==========================================================
    
    private BlackJackModel   model;
    private BlackJackViewGUI view;
    
    // === CONSTRUCTOR ========================================================
    
    public BlackJackGUI()
    {
        model = new BlackJackModel();
        view = new BlackJackViewGUI(model, this);
    }
    
    // === METHODS ============================================================
    
    public void start()
    {
        // Starts the GUI
        this.view.setVisible(true);
    }
    
    // === MAIN ===============================================================
    
    public static void main(String[] args)
    {
        BlackJackGUI app = new BlackJackGUI();
        app.start();
    }
}
