import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DeckOfCardGui {
    // data field
    private Dictionary<String, Integer> dict;
    private ImageIcon[] imageList;
    private List<JLabel> labelList;
    private JPanel frame;
    private JButton shuffleButton;
    private JButton sortButton;
    private int cardWidth = 80;  // Width of the resized card image
    private int cardHeight = 120;  // Height of the resized card image
    private String[] randomFilePaths;  // Add this line as a member variable

    // constructor
    public DeckOfCardGui() {
        JFrame window = new JFrame();
        window.setTitle("Pick Five Cards Randomly");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(480, 250);
        window.setLocationRelativeTo(null);

        dict = new Hashtable<>();
        dict.put("images/1.jpg", 1);
        dict.put("images/2.jpg", 2);
        dict.put("images/3.jpg", 3);
        dict.put("images/4.jpg", 4);
        dict.put("images/5.jpg", 5);
        dict.put("images/6.jpg", 6);
        dict.put("images/7.jpg", 7);
        dict.put("images/8.jpg", 8);
        dict.put("images/9.jpg", 9);

        // lisdt to hold image icon and resize the images to fit in frame
        imageList = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            String imagePath = "images/" + (i + 1) + ".jpg";
            ImageIcon imageIcon = resizeImage(imagePath, cardWidth, cardHeight);
            imageList[i] = imageIcon;
        }

        frame = new JPanel();
        frame.setLayout(null);

        // position 5 labels and put in array
        labelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel();
            label.setBounds(10 + (i * 90), 10, cardWidth, cardHeight);
            labelList.add(label);
            frame.add(label);
        }

        // shuffle button 
        shuffleButton = new JButton("Shuffle");
        shuffleButton.setBounds(100, 150, 100, 30);
        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffle();
            }
        });
        frame.add(shuffleButton);

        // sort button
        sortButton = new JButton("Sort");
        sortButton.setBounds(250, 150, 100, 30);
        sortButton.addActionListener(new ActionListener() {
            @Override
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
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Choose five random cards
    private void shuffle() {
        randomFilePaths = generateRandomFilePaths(dict, 5);
        for (int i = 0; i < 5; i++) {
            String filePath = randomFilePaths[i];
            ImageIcon card = resizeImage(filePath, cardWidth, cardHeight);
            labelList.get(i).setIcon(card);
        }
        frame.revalidate();
        frame.repaint();
    }
    // Generate random file paths
    private String[] generateRandomFilePaths(Dictionary<String, Integer> dict, int numPaths) {
        List<String> keysList = Collections.list(dict.keys());
        String[] filePaths = new String[numPaths];

        Random random = new Random();

        for (int i = 0; i < numPaths; i++) {
            int randomIndex = random.nextInt(keysList.size());
            String randomFilePath = keysList.get(randomIndex);
            filePaths[i] = randomFilePath;
            keysList.remove(randomIndex);
        }

        return filePaths;
    }

    // Sort the cards using merge sort
    private void sort() {
        mergeSortFilePaths(randomFilePaths, dict);
        for (int i = 0; i < 5; i++) {
            String filePath = randomFilePaths[i];
            ImageIcon card = resizeImage(filePath, cardWidth, cardHeight);
            labelList.get(i).setIcon(card);
        }
        frame.revalidate();
        frame.repaint();
    }

    
    // Merge sort file paths based on their corresponding values
    private void mergeSortFilePaths(String[] filePaths, Dictionary<String, Integer> dict) {
        if (filePaths.length <= 1) {
            return;
        }

        int mid = filePaths.length / 2;
        String[] left = new String[mid];
        String[] right = new String[filePaths.length - mid];

        System.arraycopy(filePaths, 0, left, 0, mid);
        System.arraycopy(filePaths, mid, right, 0, filePaths.length - mid);

        mergeSortFilePaths(left, dict);
        mergeSortFilePaths(right, dict);
        merge(filePaths, left, right, dict);
    }

    // Merge the two sorted arrays
    private void merge(String[] filePaths, String[] left, String[] right, Dictionary<String, Integer> dict) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            int leftValue = dict.get(left[i]);
            int rightValue = dict.get(right[j]);

            if (leftValue <= rightValue) {
                filePaths[k++] = left[i++];
            } else {
                filePaths[k++] = right[j++];
            }
        }

        while (i < left.length) {
            filePaths[k++] = left[i++];
        }

        while (j < right.length) {
            filePaths[k++] = right[j++];
        }
    }

    public static void main(String[] args) {
        new DeckOfCardGui();
    }
}