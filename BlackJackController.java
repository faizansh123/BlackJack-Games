import java.awt.event.*;

/**Blackjack Controller Class
  * The controller for blackjack
  * @since  25/05/2023
  * @author Momo*/

public class BlackJackController {
    private BlackjackModel model;  // Model part of the MVC pattern, handles the game logic
    private BlackjackView view;    // View part of the MVC pattern, handles UI updates

    /**
     * Constructor initializes the controller, setting up the model and view,
     * and configuring the listeners for the UI buttons.
     *
     * @param model the game model
     * @param view  the game view
     */
    public BlackJackController(BlackjackModel model, BlackjackView view) {
        this.model = model;
        this.view = view;

        // Add action listener for 'Hit' button
        view.hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerHits();
            }
        });

        // Add action listener for 'Stay' button
        view.stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dealerPlays();
            }
        });

        // Add action listener for 'Exit' button
        view.exitButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                  System.exit(0);  // Exit the application
             }
        });

        // Add action listener for 'New Game' button
        view.newGameButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                  BlackJack.startNewGame();  // Start a new game
             }
        });

        updateView(false);  // Initial view update, no cards shown
    }

    /**
     * Handles player's decision to hit (take another card).
     * Updates the view and checks for end of round conditions.
     */
    private void playerHits() {
        model.dealCardToPlayer();  // Deal another card to the player
        updateView(false);  // Update view without showing hidden dealer card
        if (model.isPlayerBusted()) {
            view.setStatus("Player Busts! Dealer Wins!");  // Update status if player busts
            endRound();
        } else if (model.isPlayerBlackjack()) {
            view.setStatus("Player Blackjack! Player Wins!");  // Update status if player hits blackjack
            endRound();
        }
    }

    /**
     * Handles dealer's play according to Blackjack rules.
     */
    private void dealerPlays() {
        while (model.getDealerSum() < 17) {
            model.dealCardToDealer();  // Dealer must hit until reaching at least 17
        }
        if (model.isDealerBusted()) {
            view.setStatus("Dealer Busts! Player Wins!");  // Update status if dealer busts
        } else if (model.isDealerBlackjack()) {
            view.setStatus("Dealer Blackjack! Dealer Wins!");  // Update status if dealer hits blackjack
        } else {
            determineWinner();  // Determine the winner if no immediate blackjack or bust
        }
        updateView(true);  // Final update to show all cards
        endRound();
    }

    /**
     * Determines the winner by comparing the hands of the player and dealer.
     */
    private void determineWinner() {
        int playerSum = model.getPlayerSum();
        int dealerSum = model.getDealerSum();
        if (playerSum > dealerSum) {
            view.setStatus("Player Wins!");
        } else if (dealerSum > playerSum) {
            view.setStatus("Dealer Wins!");
        } else {
            view.setStatus("It's a Tie!");
        }
    }

    /**
     * Ends the current round, disabling buttons and showing the dealer's hidden card.
     */
    private void endRound() {
        view.hitButton.setEnabled(false);
        view.stayButton.setEnabled(false);
        updateView(true); // Show hidden card when game ends
        BlackjackModel.endRound();
    }

    /**
     * Updates the game view with the current state of the hands.
     *
     * @param showHiddenCard whether to show the dealer's hidden card
     */
    private void updateView(boolean showHiddenCard) {
        BlackjackModel.Card[] playerHand = model.getPlayerHand();
        String[] playerCardPaths = new String[playerHand.length];
        for (int i = 0; i < playerHand.length; i++) {
            playerCardPaths[i] = playerHand[i].getImagePath();
        }
        view.updatePlayerCards(playerCardPaths);

        BlackjackModel.Card[] dealerHand = model.getDealerHand();
        String[] dealerCardPaths = new String[dealerHand.length];
        for (int i = 0; i < dealerHand.length; i++) {
            dealerCardPaths[i] = dealerHand[i].getImagePath();
        }
        view.updateDealerCards(dealerCardPaths, showHiddenCard);
    }
}
