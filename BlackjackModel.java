import java.util.Collections;
import java.util.Arrays;
import java.awt.*;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**BlackModelView Class
  * The logic and model for blackjack
  * @since  25/05/2023
  * @author Aashir*/

public class BlackjackModel {
    // Nested class to represent a Card with a value and type (suit)
    public class Card {
        String value;  // The face value of the card (e.g., "A", "2", ..., "K")
        String type;   // The suit of the card (e.g., "H" for Hearts)

        // Constructor to initialize a card with its value and type
        Card(String value, String type) {
            this.value = value;
            this.type = type;
        }

        // Returns a string representation of the card, combining value and type
        public String toString() {
            return value + "-" + type;
        }

        // Retrieves the numeric value of the card used in the game logic
        public int getValue() {
            if ("JQK".contains(value)) {
                return 10;  // Face cards (J, Q, K) are worth 10 points
            }
            if (value.equals("A")) {
                return 11;  // Ace is worth 11 points, but can be 1 if necessary (handled later)
            }
            return Integer.parseInt(value);  // Numeric cards are worth their face value
        }

        // Checks if the card is an Ace
        public boolean isAce() {
            return value.equals("A");
        }

        // Generates the path to the image for this card
        public String getImagePath() {
            return "/cards/" + toString() + ".png";
        }
    }

    // Static reference to the view associated with this model
    private static BlackjackView view;
    private Card[] deck;           // Array to hold the deck of cards
    private int deckIndex = 0;     // Index to track the next card to be dealt from the deck

    // Arrays to hold cards dealt to the dealer and player
    private Card[] dealerHand = new Card[10];
    private int dealerHandSize = 0;

    private Card[] playerHand = new Card[10];
    private int playerHandSize = 0;

    // Constructor initializes the deck, shuffles it, and deals initial cards to player and dealer
    public BlackjackModel() {
        initializeDeck();
        shuffleDeck();
        dealInitialCards();
    }

    // Initializes the deck with one card of each value and suit
    private void initializeDeck() {
        deck = new Card[52];
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] types = {"H", "D", "S", "C"};
        int index = 0;
        for (String value : values) {
            for (String type : types) {
                deck[index++] = new Card(value, type);
            }
        }
    }

    // Shuffles the deck using Java's Collections utility
    private void shuffleDeck() {
        Collections.shuffle(Arrays.asList(deck));
    }

    // Deals the initial two cards to both the player and dealer
    private void dealInitialCards() {
        dealCardToPlayer();
        dealCardToDealer();
        dealCardToPlayer();
        dealCardToDealer();
    }

    // Deals one card to the player from the deck
    public void dealCardToPlayer() {
        playerHand[playerHandSize++] = deck[deckIndex++];
    }

    // Deals one card to the dealer from the deck
    public void dealCardToDealer() {
        dealerHand[dealerHandSize++] = deck[deckIndex++];
    }

    // Calculates the total value of a hand, adjusting for Aces as necessary
    public int calculateHandValue(Card[] hand, int handSize) {
        int sum = 0;
        int aceCount = 0;
        for (int i = 0; i < handSize; i++) {
            Card card = hand[i];
            sum += card.getValue();
            if (card.isAce()) {
                aceCount++;
            }
        }
        // Adjust for Aces when total exceeds 21
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }
        return sum;
    }

    // Getters for the sum of the player's and dealer's hands
    public int getPlayerSum() {
        return calculateHandValue(playerHand, playerHandSize);
    }

    public int getDealerSum() {
        return calculateHandValue(dealerHand, dealerHandSize);
    }

    // Methods to check game state conditions
    public boolean isPlayerBusted() {
        return getPlayerSum() > 21;
    }

    public boolean isDealerBusted() {
        return getDealerSum() > 21;
    }

    public boolean isPlayerBlackjack() {
        return getPlayerSum() == 21 && playerHandSize == 2;
    }

    public boolean isDealerBlackjack() {
        return getDealerSum() == 21 && dealerHandSize == 2;
    }

    // Methods to retrieve copies of the player's and dealer's hands
    public Card[] getPlayerHand() {
        return Arrays.copyOf(playerHand, playerHandSize);
    }

    public Card[] getDealerHand() {
        return Arrays.copyOf(dealerHand, dealerHandSize);
    }

    // Game flow control methods
    private static int totalRounds;
    private static int currentRound;
    public static void startGame(int rounds) {
        totalRounds = rounds;
        currentRound = 1;
        BlackJack.startNewRound();
    }

    private static void showEndGameOptions() {
        int option = JOptionPane.showOptionDialog(null, "All rounds completed. What would you like to do?", "Game Over",
                                                  JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Start New Game", "Exit"}, "Start New Game");
        if (option == 0) {
            BlackjackModel model = new BlackjackModel();
            model.logGameResult();
            BlackJack.startNewGame();
        } else {
            System.exit(0);
        }
    }

    public static void endRound() {
        if (currentRound < totalRounds) {
            int option = JOptionPane.showConfirmDialog(null, "Do you want to continue to the next round?", "Next Round", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                BlackjackModel model = new BlackjackModel();
                currentRound++;
                model.logGameResult();
                BlackJack.startNewRound();
            } else {
                showEndGameOptions();
            }
        } else {
            showEndGameOptions();
        }
    }

    // Logs the game result to a file
    public void logGameResult() {
        String result = "Player Score: " + getPlayerSum() + ", Dealer Score: " + getDealerSum();
        if (isPlayerBusted()) {
            result += ", Result: Player Busted";
        } else if (isDealerBusted()) {
            result += ", Result: Dealer Busted";
        } else {
            result += ", Result: " + (getPlayerSum() > getDealerSum() ? "Player Wins" : "Dealer Wins");
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("game_results.txt", true))) {
            writer.write(result + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to game results file: " + e.getMessage());
        }
    }
    
    // Clears the game results file
    public void clearGameResultsFile() {
        try (FileWriter writer = new FileWriter("game_results.txt", false)) {
            writer.write("");  
        } catch (IOException e) {
            System.err.println("Error clearing the game results file: " + e.getMessage());
        }
    }
}
