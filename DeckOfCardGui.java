import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Arrays;
import java.util.Random;

public class DeckOfCardGui {
    private ImageIcon[] imageList;
    private JLabel[] labelList;
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

        imageList = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            String imagePath = "images\\card " + (i + 1) + ".jpg";
            ImageIcon imageIcon = resizeImage(imagePath, cardWidth, cardHeight);
            imageList[i] = imageIcon;
        }

        frame = new JPanel();
        frame.setLayout(null);

        labelList = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel(imageList[i]);
            label.setBounds(10 + (i * 90), 10, cardWidth, cardHeight);
            labelList[i] = label;
            frame.add(label);
        }

        shuffleButton = new JButton("Shuffle");
        shuffleButton.setBounds(10, 140, 100, 30);
        shuffleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shuffle();
            }
        });
        frame.add(shuffleButton);

        sortButton = new JButton("Sort");
        sortButton.setBounds(120, 140, 100, 30);
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
            labelList[i].setIcon(imageList[randomIndex]);
        }
    }

    // Sort the cards using merge sort
    private void sort() {
        int[] cardIndices = {0, 1, 2, 3, 4};
        mergeSort(cardIndices);
        JLabel[] sortedLabels = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            sortedLabels[i] = labelList[cardIndices[i]];
        }
        frame.removeAll();
        for (int i = 0; i < 5; i++) {
            sortedLabels[i].setBounds(10 + (i * 90), 10, cardWidth, cardHeight);
            frame.add(sortedLabels[i]);
        }
        frame.add(shuffleButton);
        frame.add(sortButton);
        frame.revalidate();
        frame.repaint();
    }

    // Merge sort implementation for sorting the card indices
    public static void mergeSort(int[] array) {
        if (array.length < 2)
            return;

        int midIndex = array.length / 2;
        int[] leftArray = Arrays.copyOfRange(array, 0, midIndex);
        int[] rightArray = Arrays.copyOfRange(array, midIndex, array.length);

        mergeSort(leftArray);
        mergeSort(rightArray);
        merge(array, leftArray, rightArray);
    }

    // Merge the sorted arrays
    public static void merge(int[] array, int[] leftArray, int[] rightArray) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else{
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < leftArray.length) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < rightArray.length) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DeckOfCardGui();
            }
        });
    }
}