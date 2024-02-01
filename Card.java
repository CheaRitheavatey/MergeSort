import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
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

    CardGame() {
        setTitle("Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  // Using null layout to position components manually
        setSize(500, 350);  // Set a fixed size for the frame
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
                for (int i = 0; i < 5; i++) {
                    int randomIndex = (int) (Math.random() * deck.size());
                    hand.add(deck.remove(randomIndex));
                }

                // Display the picked cards in the card panel
                displayCards();
            }
        });

        // Create the sort button
        sortButton = new JButton("Sort Cards");
        sortButton.setBounds(250, 30, 150, 30);
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sort the hand of cards using merge sort
                mergeSort(hand, 0, hand.size() - 1);

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
        String[] suits = {"\u2660", "\u2764", "\u2662", "\u2663"}; // Spades, Hearts, Diamonds, Clubs

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
            cardLabel.setPreferredSize(new Dimension(80, 150));
            cardLabel.setBackground(Color.WHITE); // Set the background color to white
            cardLabel.setOpaque(true); // Make the label opaque to show the background color
            cardLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
            cardLabel.setVerticalAlignment(SwingConstants.CENTER);
            cardLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font size to 16

            // Create a custom border with rounded corners
            Border roundedBorder = new Border() {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(Color.BLACK);
                    g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, 10, 10));
                    g2d.dispose();
                }

                @Override
                public Insets getBorderInsets(Component c) {
                    return new Insets(2, 2, 2, 2);
                }

                @Override
                public boolean isBorderOpaque() {
                    return true;
                }
            };

            cardLabel.setBorder(roundedBorder);
            cardPanel.add(cardLabel);
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private void mergeSort(List<Card> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);

            merge(list, left, mid, right);
        }
    }

    private void merge(List<Card> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Card> leftList = new ArrayList<>();
        List<Card> rightList = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            leftList.add(list.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightList.add(list.get(mid + 1 + j));
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftList.get(i).getRank().compareTo(rightList.get(j).getRank()) <= 0) {
                list.set(k, leftList.get(i));
                i++;
            } else {
                list.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            list.set(k, leftList.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, rightList.get(j));
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        new CardGame();
    }
}