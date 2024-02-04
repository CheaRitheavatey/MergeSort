import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class DealerGUI {
    private JFrame frame;
    private JPanel imagePanel;
    private JLabel[] beforeSortLabels;
    private JLabel[] afterSortLabels;

    public DealerGUI(String[] beforeSortPaths, String[] afterSortPaths) {
        frame = new JFrame("Image Sorting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        int numImages = beforeSortPaths.length;

        // Create panels for before sort and after sort rows
        JPanel beforeSortPanel = new JPanel(new GridLayout(1, numImages));
        JPanel afterSortPanel = new JPanel(new GridLayout(1, numImages));

        // Initialize the arrays of labels for before sort and after sort rows
        beforeSortLabels = new JLabel[numImages];
        afterSortLabels = new JLabel[numImages];

        // Create and add labels for before sort row
        for (int i = 0; i < numImages; i++) {
            beforeSortLabels[i] = createImageLabel(beforeSortPaths[i]);
            beforeSortPanel.add(beforeSortLabels[i]);
        }

        // Create and add labels for after sort row
        for (int i = 0; i < numImages; i++) {
            afterSortLabels[i] = createImageLabel(afterSortPaths[i]);
            afterSortPanel.add(afterSortLabels[i]);
        }

        // Add the panels to the main image panel
        imagePanel = new JPanel(new GridLayout(2, 1));
        imagePanel.add(beforeSortPanel);
        imagePanel.add(afterSortPanel);

        frame.add(imagePanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JLabel createImageLabel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        imageIcon = new ImageIcon(image);

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return imageLabel;
    }

    public static void main(String[] args) {
        Dictionary<String, Integer> dict = new Hashtable<>();
        dict.put("images/1.jpg", 1);
        dict.put("images/2.jpg", 2);
        dict.put("images/3.jpg", 3);
        dict.put("images/4.jpg", 4);
        dict.put("images/5.jpg", 5);
        dict.put("images/6.jpg", 6);
        dict.put("images/7.jpg", 7);
        dict.put("images/8.jpg", 8);
        dict.put("images/9.jpg", 9);

        String[] randomFilePaths = generateRandomFilePaths(dict, 5);

        DealerGUI gui = new DealerGUI(randomFilePaths, randomFilePaths.clone());

        mergeSortFilePaths(randomFilePaths, dict, gui);

        gui.displayImages(randomFilePaths);


    }

    private static void mergeSortFilePaths(String[] filePaths, Dictionary<String, Integer> dict, DealerGUI gui) {
        if (filePaths.length <= 1) {
            return;
        }

        int mid = filePaths.length / 2;
        String[] left = Arrays.copyOfRange(filePaths, 0, mid);
        String[] right = Arrays.copyOfRange(filePaths, mid, filePaths.length);

        mergeSortFilePaths(left, dict, gui);
        mergeSortFilePaths(right, dict, gui);

        merge(filePaths, left, right, dict, gui);
    }

    private static void merge(String[] filePaths, String[] left, String[] right, Dictionary<String, Integer> dict, DealerGUI gui) {
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

        gui.displayImages(filePaths);
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

    private void displayImages(String[] filePaths) {
        for (int i = 0; i < filePaths.length; i++) {
            beforeSortLabels[i].setIcon(createImageIcon(filePaths[i]));
            afterSortLabels[i].setIcon(createImageIcon(filePaths[i]));
        }
    }
    private ImageIcon createImageIcon(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        return new ImageIcon(image);
    }
}