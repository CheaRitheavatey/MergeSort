// import javax.swing.*;
// import javax.swing.border.Border;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.geom.RoundRectangle2D;
// // import java.lang.reflect.Array;
// // import java.util.ArrayList;
// // import java.util.List;
// import java.util.Arrays;


// public class Card {
//     // data field
//     private String rank;
//     private String suit;

//     // constructor
//     Card(String rank, String suit) {
//         this.rank = rank;
//         this.suit = suit;
//     }

//     // getter
//     public String getRank() {
//         return rank;
//     }

//     public String getSuit() {
//         return suit;
//     }

//     @Override
//     public String toString() {
//         return rank + suit;
//     }
// }

// class CardGame extends JFrame {
//     private Card[] deck;
//     private Card[] hand;
//     private JButton pickButton;
//     private JButton sortButton;
//     private JPanel cardPanel;

//     CardGame() {
//         setTitle("Card Game");
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(null);  // Using null layout to position components manually
//         setSize(500, 350);  // Set a fixed size for the frame
//         setLocationRelativeTo(null);

//         // Create the deck of cards
//         deck = createDeck();

//         // Create the hand of cards
//         hand = new Card[5];

//         // Create the pick button
//         pickButton = new JButton("Pick 5 Cards");
//         pickButton.setBounds(60, 30, 150, 30);
//         pickButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Pick 5 random cards from the deck
//                 for (int i = 0; i < 5; i++) {
//                     int randomIndex = (int) (Math.random() * deck.length);
//                     hand[i] = deck[randomIndex];
//                     deck[randomIndex] = deck[deck.length - 1];
//                     deck = Arrays.copyOf(deck, deck.length - 1);
//                 }

//                 // Display the picked cards in the card panel
//                 displayCards();
//             }
//         });

//         // Create the sort button
//         sortButton = new JButton("Sort Cards");
//         sortButton.setBounds(250, 30, 150, 30);
//         sortButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Sort the hand of cards using merge sort
//                 mergeSort(hand, 0, hand.length - 1);

//                 // Display the sorted cards in the card panel
//                 displayCards();
//             }
//         });

//         // Create the card panel
//         cardPanel = new JPanel();
//         cardPanel.setBounds(30, 80, 440, 270);
//         cardPanel.setLayout(new FlowLayout()); // Using FlowLayout to display cards in a row

//         // Add the components to the frame
//         add(pickButton);
//         add(sortButton);
//         add(cardPanel);

//         setVisible(true);
//     }

//     private Card[] createDeck() {
//         Card[] deck = new Card[52];

//         String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
//         String[] suits = {" ♠", "♥️", "♦", "♣️"}; // Spades, Hearts, Diamonds, Clubs

//         int index = 0;
//         for (String suit : suits) {
//             for (String rank : ranks) {
//                 deck[index] = new Card(rank, suit);
//                 index++;
//             }
//         }

//         return deck;
//     }

//     private void displayCards() {
//         cardPanel.removeAll();

//         for (Card card : hand) {
//             JLabel cardLabel = new JLabel(card.toString());
//             cardLabel.setPreferredSize(new Dimension(80, 150));
//             cardLabel.setBackground(Color.WHITE); // Set the background color to white
//             cardLabel.setOpaque(true); // Make the label opaque to show the background color
//             cardLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
//             cardLabel.setVerticalAlignment(SwingConstants.CENTER);
//             cardLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font size to 16

//             // Create a custom border with rounded corners
//             Border roundedBorder = new Border() {
//                 @Override
//                 public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
//                     Graphics2D g2d = (Graphics2D) g.create();
//                     g2d.setColor(Color.BLACK);
//                     g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, 10, 10));
//                     g2d.dispose();
//                 }

//                 @Override
//                 public Insets getBorderInsets(Component c) {
//                     return new Insets(2, 2, 2, 2);
//                 }

//                 @Override
//                 public boolean isBorderOpaque() {
//                     return true;
//                 }
//             };

//             cardLabel.setBorder(roundedBorder);
//             cardPanel.add(cardLabel);
//         }
// a
//         cardPanel.revalidate();
//         cardPanel.repaint();
//     }

//     private void mergeSort(Card[] cards) {
//         if (cards.length < 2) {
//             return;
//         }
//         int midIndex = cards.length /2 ;
//         int[] leftCards = new int[midIndex];
//         int[] rightCards = new int[cards.length - midIndex];

//         for (int i = 0; i < midIndex; i++) {
//             leftCards[i] = cards[i];
//         }
        

//         for (int i = midIndex; i < cards.length; i ++) {
//             rightCards[i - midIndex] = cards[i];
//         }

//         mergeSort(leftCards);
//         mergeSort(rightCards);

//         merge(cards, leftCards, rightCards);
//     }

//     private void merge(Card[] cards, int[] leftCards, int[] rightCards) {
//         int i = 0;
//         int j = 0;
//         int k = 0;
    
//         while (i < leftCards.length && j < rightCards.length) {
//             if (leftCards[i] <= rightCards[j]) {
//                 cards[k] = leftCards[i];
//                 i++;
//             } else {
//                 cards[k] = rightCards[j];
//                 j++
//             }
//             k++
//         }
    
//         while (i < leftCards.length) {
//             cards[k] = leftCards[i];
//             i++;
//             k++;
//         }
    
//         while (j < rightCards.length) {
//             cards[k] = rightCards[j];
//             j++;
//             k++;
//         }
//     }

//     public static void main(String[] args) {
//         new CardGame();
//     }
// }