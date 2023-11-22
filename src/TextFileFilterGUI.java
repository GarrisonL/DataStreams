import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataStreamsGUI extends JFrame {
    private JTextArea originalTextArea;
    private JTextArea filteredTextArea;
    private JTextField searchTextField;

    public DataStreamsGUI() {
        setTitle("Data Streams Processing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        originalTextArea = new JTextArea(20, 30);
        filteredTextArea = new JTextArea(20, 30);
        searchTextField = new JTextField(20);
        JButton loadFileButton = new JButton("Load File");
        JButton searchButton = new JButton("Search");
        JButton quitButton = new JButton("Quit");

        loadFileButton.addActionListener(e -> loadFile());
        searchButton.addActionListener(e -> searchFile());
        quitButton.addActionListener(e -> System.exit(0));

        JPanel panel = new JPanel();
        panel.add(new JLabel("Search String:"));
        panel.add(searchTextField);
        panel.add(loadFileButton);
        panel.add(searchButton);
        panel.add(quitButton);

        JPanel mainPanel = new JPanel(new GridLayout(1, 3));
        mainPanel.add(new JScrollPane(originalTextArea));
        mainPanel.add(panel);
        mainPanel.add(new JScrollPane(filteredTextArea));

        add(mainPanel);
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            Path filePath = fileChooser.getSelectedFile().toPath();
            try {
                List<String> lines = Files.readAllLines(filePath);
                originalTextArea.setText(String.join("\n", lines));
            } catch (IOException e) {
                showError("Error reading file.");
            }
        }
    }

    private void searchFile() {
        String searchString = searchTextField.getText().trim();
        if (!searchString.isEmpty()) {
            try {
                Path filePath = Paths.get("path/to/your/file.txt");  // Update with your file path
                List<String> filteredLines = filterFileLines(filePath, searchString);
                filteredTextArea.setText(String.join("\n", filteredLines));
            } catch (IOException e) {
                showError("Error filtering file.");
            }
        }
    }

    private List<String> filterFileLines(Path filePath, String searchString) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.filter(line -> line.contains(searchString)).collect(Collectors.toList());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
