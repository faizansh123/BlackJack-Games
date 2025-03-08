import java.awt.*;
import javax.swing.*;

/**  BlackJack
*    lets users play a game of blackJack
*    @since: 17/06/2024
*    @author // Faizan, Momo, Aashier*/ 

public class BlackJack {

    // Static reference to the view associated with the Blackjack game
    private static BlackjackView view;

    /**
     * Starts a new round of Blackjack.
     * If a view exists, it closes the current frame before creating a new model and view,
     * and setting up the controller.
     */
    public static void startNewRound() {
        if (view != null) {
            view.closeFrame();  // Close the current frame if it exists
        }
        
        // Create a new model and view for the round
        BlackjackModel model = new BlackjackModel();
        view = new BlackjackView();
        
        // Set up the controller with the new model and view
        BlackJackController controller = new BlackJackController(model, view);
    }
    
    /**
     * Starts a new game of Blackjack.
     * This method closes the current view if one exists and opens the main screen.
     */
    public static void startNewGame() {
        if (view != null) {
            view.closeFrame();  // Close the current frame if it exists
        }
        
        // Create and show the main screen
        MainScreen mainScreen = new MainScreen();
    }
    
    /**
     * The main method that serves as the entry point for the application.
     * It delegates to the mainScreen's main method to start the application.
     * 
     * @param args command line arguments passed to the application (not used here)
     */
    public static void main(String[] args) {
        MainScreen.main(args);
    }
}
