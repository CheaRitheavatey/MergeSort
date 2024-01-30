import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {
    // data field
    private String rank;
    private String suit;

    // constructor
    Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // getter
    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class CardGame extends JFrame {
    private List<Card> deck;
    private List<Card> hand;
    private JButton pickButton;
    private JButton sortButton;
    private JPanel cardPanel;

    public CardGame() {
        setTitle("Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  // Using null layout to position components manually
        setSize(500, 400);  // Set a fixed size for the frame
        setLocationRelativeTo(null);

        // Create the deck of cards
        deck = createDeck();

        // Create the hand of cards
        hand = new ArrayList<>();

        // Create the pick button
        pickButton = new JButton("Pick 5 Cards");
        pickButton.setBounds(60, 30, 150, 30);
        pickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pick 5 random cards from the deck
                hand.clear();
                Collections.shuffle(deck);
                for (int i = 0; i < 5; i++) {

                    hand.add(deck.get(i));
                }

                // Display the picked cards in the card panel
                displayCards();
            }
        });

        // Create the sort button
        sortButton = new JButton("Sort Cards");
        sortButton.setBounds(280, 30, 150, 30);
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sort the hand of cards by rank
                mergeSort(hand);

                // Display the sorted cards in the card panel
                displayCards();
            }
        });

        // Create the card panel
        cardPanel = new JPanel();
        cardPanel.setBounds(30, 80, 440, 270);
        cardPanel.setLayout(new FlowLayout()); // Using FlowLayout to display cards in a row

        // Add the components to the frame
        add(pickButton);
        add(sortButton);
        add(cardPanel);

        setVisible(true);
    }

    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();

        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"\u2660", "\u2661", "\u2662", "\u2663"}; // Spades, Hearts, Diamonds, Clubs

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(rank, suit));
            }
        }

        return deck;
    }

    private void displayCards() {
        cardPanel.removeAll();
    
        for (Card card : hand) {
            JLabel cardLabel = new JLabel(card.toString());
            cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add a black rectangle border to each card
            cardPanel.add(cardLabel);
        }
    
        revalidate();
        repaint();
    }

    private void mergeSort(List<Card> cards) {
        if (cards.size() <= 1) {
            return;
        }

        int mid = cards.size() / 2;
        List<Card> left = new ArrayList<>(cards.subList(0, mid));
        List<Card> right = new ArrayList<>(cards.subList(mid, cards.size()));

        mergeSort(left);
        mergeSort(right);

        merge(cards, left, right);
    }

    private void merge(List<Card> cards, List<Card> left, List<Card> right) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.size() && j < right.size()) {
            Card leftCard = left.get(i);
            Card rightCard = right.get(j);

            if (leftCard.getRank().compareTo(rightCard.getRank()) < 0) {
                cards.set(k, leftCard);
                i++;
            } else {
                cards.set(k, rightCard);
                j++;
            }

            k++;
        }

        while (i < left.size()) {
            cards.set(k, left.get(i));
            i++;
            k++;
        }

        while (j < right.size()) {
            cards.set(k, right.get(j));
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CardGame();
            }
        });
    }
}