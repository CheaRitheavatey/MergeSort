import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DeckOfCardGui {
    private ImageIcon[] imageList;
    private List<JLabel> labelList;
    private JPanel frame;
    private JButton shuffleButton;
    private JButton sortButton;
    private int cardWidth = 80;  // Width of the resized card image
    private int cardHeight = 120;  // Height of the resized card image
    private Dictionary<String, Integer> dict;

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
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffle();
            }
        });
        frame.add(shuffleButton);

        sortButton = new JButton("Sort");
        sortButton.setBounds(160, 140, 100, 30);
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
        String[] randomFilePaths = generateRandomFilePaths(dict, 5);
        for (int i = 0; i < 5; i++) {
            String filePath = randomFilePaths[i];
            int index = dict.get(filePath);
            ImageIcon resizedIcon = resizeImage(filePath, cardWidth, cardHeight);
            labelList.get(i).setIcon(resizedIcon);
        }
        frame.revalidate();
        frame.repaint();
    }

   // Sort the cards based on their corresponding values
    private void sort() {
        String[] filePaths = new String[5];
        for (int i = 0; i < 5; i++) {
            JLabel label = labelList.get(i);
            ImageIcon icon = (ImageIcon) label.getIcon();
            String filePath = getFilePathFromIcon(icon);
            filePaths[i] = filePath;
        }

        mergeSortFilePaths(filePaths, dict);

        for (int i = 0; i < 5; i++) {
            String filePath = filePaths[i];
            ImageIcon resizedIcon = resizeImage(filePath, cardWidth, cardHeight);
            labelList.get(i).setIcon(resizedIcon);
        }
        frame.revalidate();
        frame.repaint();
    }

    // Convert the file path of an image to a corresponding icon
    private String getFilePathFromIcon(ImageIcon icon) {
        for (Map.Entry<String, Integer> entry : dict.keys()) {
            String filePath = entry.getKey();
            ImageIcon imageIcon = resizeImage(filePath, cardWidth, cardHeight);
            if (imageIcon.getImage().equals(icon.getImage())) {
                return filePath;
            }
        }
        return null;
    }

    // Sort the file paths based on their corresponding values
    private static void mergeSortFilePaths(String[] filePaths, Dictionary<String, Integer> dict) {
        if (filePaths.length <= 1) {
            return;
        }

        int mid = filePaths.length / 2;
        String[] left = Arrays.copyOfRange(filePaths, 0, mid);
        String[] right = Arrays.copyOfRange(filePaths, mid, filePaths.length);

        mergeSortFilePaths(left, dict);
        mergeSortFilePaths(right, dict);

        merge(filePaths, left, right, dict);
    }

    private static void merge(String[] filePaths, String[] left, String[] right, Dictionary<String, Integer> dict) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            int leftValue = dict.get(left[i]);
            int rightValue = dict.get(right[j]);

            if (leftValue <= rightValue) {
                filePaths[k] = left[i];
                i++;
            } else {
                filePaths[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length) {
            filePaths[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            filePaths[k] = right[j];
            j++;
            k++;
        }
    }

    private static String[] generateRandomFilePaths(Dictionary<String, Integer> dict, int numPaths) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DeckOfCardGui();
            }
        });
    }
}