import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * MainScreen Class
 * This is the main menu screen for the Blackjack game.
 * @since 08/03/2025
 * @author YourName
 */
public class MainScreen {
    private JFrame frame;
    private JPanel panel;
    private JButton startGameButton;
    private JButton exitButton;

    /**
     * Constructor - Initializes the main screen UI.
     */
    public MainScreen() {
        frame = new JFrame("Blackjack - Main Menu");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel titleLabel = new JLabel("Welcome to Blackjack!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel);

        startGameButton = new JButton("Start New Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        panel.add(startGameButton);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Starts a new Blackjack game.
     */
    private void startNewGame() {
        frame.dispose();  // Close the main menu
        BlackJack.startNewRound();  // Start a new round of Blackjack
    }

    /**
     * Main method - Entry point for the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainScreen();
            }
        });
    }
}

