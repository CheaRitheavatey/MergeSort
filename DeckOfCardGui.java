import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DeckOfCardGui {
    private ImageIcon[] imageList;
    private List<JLabel> labelList;
    private JPanel frame;
    private JButton shuffleButton;
    private JButton sortButton;
    private int cardWidth = 80;  // Width of the resized card image
    private int cardHeight = 120;  // Height of the resized card image

    public DeckOfCardGui() {
        JFrame window = new JFrame();
        window.setTitle("Pick Five Cards Randomly");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 400);
        window.setLocationRelativeTo(null);

        imageList = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            String imagePath = "images/" + (i + 1) + ".jpg";
            ImageIcon imageIcon = resizeImage(imagePath, cardWidth, cardHeight);
            imageList[i] = imageIcon;
        }

        frame = new JPanel();
        frame.setLayout(null);

        labelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel();
            label.setBounds(10 + (i * 90), 10, cardWidth, cardHeight);
            labelList.add(label);
            frame.add(label);
        }

        shuffleButton = new JButton("Shuffle");
        shuffleButton.setBounds(50, 140, 100, 30);
        shuffleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shuffle();
            }
        });
        frame.add(shuffleButton);

        sortButton = new JButton("Sort");
        sortButton.setBounds(160, 140, 100, 30);
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sort();
            }
        });
        frame.add(sortButton);

        window.add(frame);
        window.setVisible(true);
    }

    // Resizes the image to the specified width and height
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Choose five random cards
    private void shuffle() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(imageList.length);
            labelList.get(i).setIcon(imageList[randomIndex]);
        }
    }

    // Sort the cards using merge sort
    private void sort() {
        Card[] cardArray = new Card[5];
        for (int i = 0; i < labelList.size(); i++) {
            String imageName = "images/" + (i + 1) + ".jpg";
            int number = i + 1;
            cardArray[i] = new Card(imageName, number);
        }

        mergeSort(cardArray, 0, cardArray.length - 1);

        frame.removeAll();
        for (int i = 0; i < cardArray.length; i++) {
            labelList.get(i).setIcon(cardArray[i].getImageIcon());
            labelList.get(i).setBounds(10 + (i * 90), 10, cardWidth, cardHeight);
            frame.add(labelList.get(i));
        }
        frame.add(shuffleButton);
        frame.add(sortButton);
        frame.revalidate();
        frame.repaint();
    }

    // Merge sort implementation for Card array
    private void mergeSort(Card[] arr, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(arr, low, mid);
            mergeSort(arr, mid + 1, high);
            merge(arr, low, mid, high);
        }
    }

    // Merge two sorted Card arrays
    private void merge(Card[] arr, int low, int mid, int high) {
        Card[] merged = new Card[high - low + 1];
        int i = low, j = mid + 1, k = 0;

        while (i <= mid && j <= high) {
            if (arr[i].getNumber() <= arr[j].getNumber()) {
                merged[k++] = arr[i++];
            } else {
                merged[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            merged[k++] = arr[i++];
        }
        while (j <= high) {
            merged[k++] = arr[j++];
        }
        System.arraycopy(merged, 0, arr, low, merged.length);
    }

    public static void main(String[] args) {
        new DeckOfCardGui();
    }
}
class Card {
    private ImageIcon imageIcon;
    private int number;

    public Card(String imageName, int number) {
        this.imageIcon = new ImageIcon(imageName);
        this.number = number;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public int getNumber() {
        return number;
    }
}