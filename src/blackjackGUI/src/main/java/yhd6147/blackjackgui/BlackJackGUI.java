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
        // Initialises the model and view and passes the model to the view
        model = new BlackJackModel();
        view = new BlackJackViewGUI(model);
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
        // Creates a BlackJackGUI instance and calls start to start the program
        BlackJackGUI app = new BlackJackGUI();
        app.start();
    }
}
