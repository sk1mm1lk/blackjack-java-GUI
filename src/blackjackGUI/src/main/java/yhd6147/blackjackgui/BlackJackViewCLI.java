package yhd6147.blackjackgui;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author yhd6147
 */
public class BlackJackViewCLI
{

    // === VARIABLES ==========================================================
    
    private final BlackJackModel model;
    private final FileController fc;
    private final Scanner scan;

    private String introText;
    private String outroText;
    private String rulesText;
    private String scoreboardText;
    private String bustText;
    private HashMap<String, String> cardText;
    
    // === CONSTRUCTOR ========================================================
    
    public BlackJackViewCLI(BlackJackModel model)
    {
        // Initialises all the variables and runs initAssets
        this.model = model;
        this.fc = new FileController();
        this.scan = new Scanner(System.in);
        this.cardText = new HashMap<String, String>();

        this.initAssets();
    }
    
    // === METHODS ============================================================

    private void initAssets()
    {
        // Initialises all the assets
        this.initIntro();
        this.initRules();
        this.initCards();
        this.initBust();
        this.initScoreboard();
        this.initOutro();
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    
    public void displayIntro()
    {
        // Prints the intro text
        System.out.println(this.introText);
        this.wait(3);
    }
    
    public void displayOutro()
    {
        // Prints the outro text
        System.out.println(this.outroText);
    }
    
    public void displayRules()
    {
        // Prints the rules
        System.out.println(this.rulesText);
        this.wait(3);
    }
    
    public void displayCard(Card card)
    {
        // Prints out a card (ASCII Art if the file exists)
        System.out.println(this.cardText.get(card.toString()));
    }
    
    public void displayScoreboard()
    {
        // Prints out the total scoreboard and formats the commas with arrows instead
        System.out.println(this.scoreboardText);
        System.out.println(this.model.getScoreboard().displayScoreboard().replace(",", " --> "));
        this.wait(3);
    }
    
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    
    public void getPlayers()
    {
        // Gets the user's information for the players (number of and player names)
        int nPlayers = 0;

        while (nPlayers < 1 || nPlayers > 5)
        {
            nPlayers = this.getInt("How many players will be playing this game? [1 - 5]");
            if (nPlayers < 1 || nPlayers > 5)
                System.out.println("Please stick to the bounds specified");
        }
        
        for (int i = 0; i < nPlayers; i++)
        {
            System.out.println("\n===== Player " + (i+1) + " =====");
            this.model.setPlayer(this.getString("What is your name? "));
        }
    }
    
    public void getBets()
    {
        // Gets the user's information about how much they want to bet
        System.out.println("\n\n========== BETTING ==========");
        
        // Go through each player
        for (Player player : this.model.getPlayers())
        {
            System.out.println("\n===== "+player.getName()+" ["+player.getScore()+"]"+" =====");
            int playerBet = 0;
            
            // Only allow players with score to bet
            if (player.getScore() > 0)
            {
                // Ask if they want to bet first
                if (this.getBool("Will you be placing a bet?"))
                {
                    playerBet = this.getInt("How much would you like to bet?");
                    this.model.setPlayerBet(player, playerBet);
                    // Tells them incase they bet more than their score so they are aware
                    System.out.println("You are now betting " + player.getBet() + " points this round");
                }
                else
                {
                    this.model.setPlayerBet(player, 0);
                    System.out.println("You will not be betting this round");
                }
            }
            else
            {
                System.out.println("You cannot bet without any points");
                this.wait(1);
            }
        }
    }

    public boolean isPlaying()
    {
        // Asks the user if they would like to continue playing
        return getBool("Do you want to continue playing?");
    }
    
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

    public void playersTurn()
    {
        // Displays the players turn and takes in their input
        for (Player player : this.model.getPlayers())
        {
            Card current;
            System.out.println("\n===== " + player.getName() + " =====");
            
            boolean isPlaying = true;
            
            while(isPlaying)
            {
                System.out.println("Drawing a card...");
                current = this.model.drawCard(player);
                this.wait(3);
                this.displayCard(current);
                System.out.println("Hand total: " + player.getHand().getValue());
                
                if (player.getHand().isBust())
                    isPlaying = false;
                else if (player.getHand().getSize()<2)
                    isPlaying = true;
                else
                    isPlaying = this.getBool("Would you like to draw another card?");
            }
            
            // Displays if the player is bust or if not, their hand total value
            if (player.getHand().isBust())
                System.out.println(this.bustText);
            else
                System.out.println("Hand total: " + player.getHand().getValue());
        }
    }

    public void houseTurn()
    {
        // Displays the house taking it's turn
        System.out.println("\n===== HOUSE'S TURN =====");
        this.wait(3);
        
        Hand houseHand = this.model.getHouse().getHand();
        
        while(this.model.houseCanDraw())
        {
            System.out.println("====================");
            System.out.println("Drawing a card...");
            Card drawnCard = this.model.houseDraw();
            this.wait(2);
            this.displayCard(drawnCard);
            System.out.println("Hand total: " + houseHand.getValue());
        }
        
        if (houseHand.isBust())
        {
            System.out.println(this.bustText);
            this.wait(3);
        }
        else
        {
            System.out.println("The house total hand value is " + houseHand.getValue());
            this.wait(3);
        }
    }
    
    public void scoreGame()
    {
        // Displays the scoring results for every player who scored or lost points
        System.out.println("\n========== SCORING ==========");
        for (Player player : this.model.getPlayers())
        {
            int result = this.model.scorePlayer(player);
            
            // Does not display a score of 0
            if (result > 0)
            {
                System.out.println(result+" points have been awarded to " + player.getName());
            }
            else if (result < 0)
            {
                System.out.println(player.getName()+" is going to miss "+result+" points");
            }
        }
    }

    public void saveGame()
    {
        // Prompts the user to save the game data to a default of specified file
        if (this.getBool("Would you like to save the results of the game?"))
        {
            if (this.getBool("Would you like to save it to the default file?"))
            {
                this.model.getScoreboard().exportPlayers();
            }
            else
            {
                String fileName = this.getString("Where would you like to save it?");
                this.model.getScoreboard().exportPlayers(fileName);
            }
        }
    }

    public void wait(int seconds)
    {
        // Puts the main thread to sleep for a specified number of seconds
        // Waiting method used to make the gameplay a little easier to keep up with
        try
        {
            Thread.sleep(seconds*1000);
        }
        catch (InterruptedException e){}
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

    public boolean getBool(String prompt)
    {
        // Prompts the user for a yes or a no to a specified prompt
        boolean returnBool = true;
        String userInput = null;
        
        System.out.println(prompt);
        
        while (userInput == null)
        {
            System.out.print("[y/n]> ");
            userInput = scan.nextLine().strip();
            
            if (userInput != null && userInput.length() >= 1)
            {
                // Only checks the first non-whitespace char
                if (userInput.toLowerCase().charAt(0) == 'y')
                {
                    returnBool = true;
                }
                else if (userInput.toLowerCase().charAt(0) == 'n')
                {
                    returnBool = false;
                }
                else
                {
                    // Handles any failed input
                    System.out.println("I didn't catch that, please input a [y]es or [n]o");
                    userInput = null;
                }
            }
        }
        return returnBool;
    }
    
    public int getInt(String prompt)
    {
        // Prompts the user for an integer value to a specified prompt
        int returnInt = 0;
        String userInput = null;
        
        System.out.println(prompt);
        
        while (userInput == null)
        {
            System.out.print("[Integer]> ");
            userInput = scan.nextLine().strip();
            
            if (userInput != null)
            {
                // Attempts to parse int and handles exception
                try
                {
                    returnInt = (int) Integer.parseInt(userInput);
                }
                catch(NumberFormatException e)
                {
                    System.out.println("I didn't catch that, please input a valid integer");
                    userInput = null;
                }
            }
        }
        return returnInt;
    }
    
    public String getString(String prompt)
    {
        // Prompts the user for a string input to a specified prompt
        // Strips the string of any commas that are used for exporting players
        String returnString = "";
        String userInput = null;
        
        System.out.println(prompt);
        
        while (userInput == null || userInput.length() <= 1)
        {
            System.out.print("[String]> ");
            userInput = scan.nextLine().strip();
            userInput = userInput.replace(",", "");
            returnString = userInput;
        }
        return returnString;
    }
    
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

    private void initIntro()
    {
        // Gets the intro art from the assets folder and substitutes with a default if non existent
        String intro = fc.read("assets/intro.txt");
        if (intro != null)
            this.introText = intro;
        else
            this.introText = "Welcome to the commandline version of Black Jack\n";
    }
    
    private void initRules()
    {
        // Gets the rules content from the assets folder and substitutes with a default if non existent
        String rules = fc.read("assets/rules.txt");
        if (rules != null)
        {
            this.rulesText = rules;
        }
        else
        {
            this.rulesText = "=== Rules ===";
            this.rulesText += "1. You can have 1 to 5 players for this game";
            this.rulesText += "2. Any betting in this game puts you against the house, not other players";
            this.rulesText += "3. If you don't bet, you can still win 10 points but you will lose 0 points";
            this.rulesText += "4. Each game consists of 5 stages, which are:";
            this.rulesText += "     Betting";
            this.rulesText += "       You are asked if you want to place a bet, you can choose not to.";
            this.rulesText += "       Bets can reach a maximum of the amount of points you have";
            this.rulesText += "     Starting 2 cards are dealt";
            this.rulesText += "       Each player, including the house, is dealt two cards face up";
            this.rulesText += "       Each card has a value ranging from 1 (Ace) to 13 (King)";
            this.rulesText += "       Each hand has a sum value that cannot go above 21";
            this.rulesText += "       Going above 21 causes you to bust (lose)";
            this.rulesText += "     Keep adding cards";
            this.rulesText += "       You can choose to top your hand up with another card to get closer to 21";
            this.rulesText += "       You will be allowed to top as many cards as you would like (before bust) or none at all";
            this.rulesText += "     Scoring";
            this.rulesText += "       As stated previously, each card has a value ranging from 1 being and Ace to 13 being a King";
            this.rulesText += "       The higher the value of your hand, the better, but as long as it isn't greater than 21";
            this.rulesText += "     Rewarding points";
            this.rulesText += "       If you placed no bet and you win, you receive only 10 points";
            this.rulesText += "       If you placed a bet and you did win, you receive 10 points plus your bet amount";
            this.rulesText += "       If you placed no bet and you lose, you don't lose any points";
            this.rulesText += "       If you placed a bet and you lose, you will only lose the amount you bet";
        }
    }
    
    private String initCardText(Card initCard)
    {
        // Gets the ASCII Art for the card and returns an alternative just incase
        if (initCard == null)
        {
            return null;
        }
        
        String cardDisplay = fc.read("assets/" + initCard.toString() + ".txt");
        if (cardDisplay != null)
        {
            return cardDisplay + initCard.toString();
        }
        
        return initCard.toString();
    }
    
    private void initCards()
    {
        // Sets each card ASCII art to the hashmap from the assets folder and substitutes with a default if non existent
        for (Card initCard : (new Deck()).getCards())
        {
            this.cardText.put(initCard.toString(), initCardText(initCard));
        }
    }
    
    private void initBust()
    {
        // Gets the bust art from the assets folder and substitutes with a default if non existent
        String bust = fc.read("assets/bustText.txt");
        if (bust != null)
        {
            this.bustText = bust;
        }
        else
        {
            this.bustText = "\n";
            this.bustText += "====================\n";
            this.bustText += "        BUST        \n";
            this.bustText += "====================\n";
        }
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

    private void initOutro()
    {
        // Gets the outro art from the assets folder and substitutes with a default if non existent
        String outro = fc.read("assets/outro.txt");
        if (outro != null)
        {
            this.outroText = outro;
        }
        else
        {
            this.outroText = "";
            this.outroText += "Game created by:";
            this.outroText += "    David Dudov";
            this.outroText += "    yhd6147@autuni.ac.nz";
            this.outroText += "    25-04-2023";
        }
    }
}
