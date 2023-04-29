package yhd6147.blackjackgui;

/**
 * @author yhd6147
 */
public class BlackJackCLI
{
    public static void main(String[] args)
    {
        // Creates the model, view and runs the game
        BlackJackModel model = new BlackJackModel();
        BlackJackViewCLI view = new BlackJackViewCLI(model);
        
        // Initial information  displayed, and player information obtained
        view.displayIntro();
        view.displayRules();
        view.getPlayers();
        
        boolean isPlaying = true;
        
        // Game iterates for as long as player is playing
        while (isPlaying)
        {
            model.resetGame();
            view.getBets();
            view.playersTurn();
            view.houseTurn();
            view.scoreGame();
            view.displayScoreboard();
            isPlaying = view.isPlaying();
        }
        
        // Saves game files and displays outro
        view.saveGame();
        view.displayOutro();
    }
}
