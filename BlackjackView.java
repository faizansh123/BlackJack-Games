import java.awt.*;
import javax.swing.*;

/**BlackjackView Class
  * The GUI View for blackjack
  * @since  25/05/2023
  * @author Faizan*/

public class BlackjackView {
    JFrame frame;            // Main window for the Blackjack game
    JPanel gamePanel;        // Panel to display the game, including cards
    JPanel buttonPanel;      // Panel for buttons and status label
    JButton hitButton;       // Button to "hit" (request another card)
    JButton stayButton;      // Button to "stay" (end turn)
    JButton exitButton;      // Button to exit the game
    JButton newGameButton;   // Button to start a new game
    JLabel statusLabel;      // Label to display game status messages
    JButton playButton;      // Button to start playing (not used in current layout)
    JButton quitButton;      // Button to quit the game (not used in current layout)

    private Image[] playerCardImages = new Image[10];  // Array to hold images of the player's cards
    private int playerCardCount = 0;                  // Count of player's cards displayed
    private Image[] dealerCardImages = new Image[10];  // Array to hold images of the dealer's cards
    private int dealerCardCount = 0;                  // Count of dealer's cards displayed
    private boolean showHiddenCard = false;           // Flag to indicate whether to show dealer's hidden card

    /**
     * Constructor sets up the UI components for the Blackjack game.
     */
    public BlackjackView() {
        frame = new JFrame("Black Jack");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Set up the game panel to display cards
        gamePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCards(g);  // Custom painting of cards
            }
        };
        gamePanel.setBackground(new Color(53, 101, 77));  // Set a green background

        // Set up the button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        hitButton = new JButton("Hit");
        buttonPanel.add(hitButton, BorderLayout.NORTH);

        stayButton = new JButton("Stay");
        buttonPanel.add(stayButton, BorderLayout.SOUTH);
        
        exitButton = new JButton("Exit");
        buttonPanel.add(exitButton, BorderLayout.WEST);

        newGameButton = new JButton("New Game");
        buttonPanel.add(newGameButton, BorderLayout.EAST);

        // Status label at the center
        statusLabel = new JLabel("");
        buttonPanel.add(statusLabel, BorderLayout.CENTER);

        // Add panels to the frame
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }

    /**
     * Draws the cards on the game panel.
     * @param g Graphics context for drawing within the panel.
     */
    private void drawCards(Graphics g) {
        int xOffset = 20;  // Horizontal offset for the first card
        int yOffset = 20;  // Vertical offset for dealer's cards

        // Draw dealer's cards
        for (int i = 0; i < dealerCardCount; i++) {
            if (i == 1 && !showHiddenCard) {
                // Draw the back of a card for hidden card
                g.drawImage(new ImageIcon(getClass().getResource("/cards/BACK.png")).getImage(), xOffset, yOffset, 110, 154, null);
            } else {
                // Draw the card face up
                g.drawImage(dealerCardImages[i], xOffset, yOffset, 110, 154, null);
            }
            xOffset += 120;  // Increase horizontal offset for the next card
        }

        // Reset offsets for player's cards
        xOffset = 20;
        yOffset = 200;

        // Draw player's cards
        for (int i = 0; i < playerCardCount; i++) {
            g.drawImage(playerCardImages[i], xOffset, yOffset, 110, 154, null);
            xOffset += 120;  // Increase horizontal offset for the next card
        }
    }

    /**
     * Updates the images for the player's cards.
     * @param cardPaths Array of image paths for the player's cards.
     */
    public void updatePlayerCards(String[] cardPaths) {
        playerCardCount = 0;
        for (String path : cardPaths) {
            playerCardImages[playerCardCount++] = new ImageIcon(getClass().getResource(path)).getImage();
        }
        gamePanel.repaint();  // Repaint the panel to show updated cards
    }

    /**
     * Updates the images for the dealer's cards.
     * @param cardPaths Array of image paths for the dealer's cards.
     * @param showHiddenCard Whether to show the dealer's hidden card.
     */
    public void updateDealerCards(String[] cardPaths, boolean showHiddenCard) {
        this.showHiddenCard = showHiddenCard;
        dealerCardCount = 0;
        for (String path : cardPaths) {
            dealerCardImages[dealerCardCount++] = new ImageIcon(getClass().getResource(path)).getImage();
        }
        gamePanel.repaint();  // Repaint the panel to show updated cards
    }

    /**
     * Sets the status message in the status label.
     * @param status The message to be displayed.
     */
    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    /**
     * Closes the main frame, effectively hiding the UI.
     */
    public void closeFrame() {
        if (frame != null) {
            frame.setVisible(false);  // Hide the frame
        }
    }
}
