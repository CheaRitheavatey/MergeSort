import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class Dealer {
    private JFrame frame;
    private JLabel beforeSortLabel;
    private JLabel afterSortLabel;
    private JPanel imagePanel;

    public Dealer(String[] beforeSortPaths, String[] afterSortPaths) {
        frame = new JFrame("Image Sorting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        beforeSortLabel = new JLabel("Before sorting:");
        afterSortLabel = new JLabel("After sorting:");

        imagePanel = new JPanel(new GridLayout(1, beforeSortPaths.length));

        displayImages(beforeSortPaths, beforeSortLabel);
        displayImages(afterSortPaths, afterSortLabel);

        frame.add(beforeSortLabel, BorderLayout.NORTH);
        frame.add(imagePanel, BorderLayout.CENTER);
        frame.add(afterSortLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void displayImages(String[] paths, JLabel label) {
        for (String path : paths) {
            ImageIcon imageIcon = new ImageIcon(path);
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
            imageIcon = new ImageIcon(image);

            JLabel imageLabel = new JLabel(imageIcon);
            imagePanel.add(imageLabel);
        }

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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

        Dealer gui = new Dealer(randomFilePaths, randomFilePaths.clone());

        // Display the randomly generated file paths before sorting
        System.out.println("Before sorting:");
        for (String filePath : randomFilePaths) {
            System.out.println(filePath);
        }

        // Sort the file paths based on their corresponding values using merge sort
        mergeSortFilePaths(randomFilePaths, dict, gui);

        // Display the file paths after sorting
        System.out.println("\nAfter sorting:");
        for (String filePath : randomFilePaths) {
            System.out.println(filePath);
        }
    }

    private static void mergeSortFilePaths(String[] filePaths, Dictionary<String, Integer> dict, Dealer gui) {
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

    private static void merge(String[] filePaths, String[] left, String[] right, Dictionary<String, Integer> dict, Dealer gui) {
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

        gui.displayImages(filePaths, gui.afterSortLabel);
    }

    private static String[]generateRandomFilePaths(Dictionary<String, Integer> dict, int numPaths) {
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
}