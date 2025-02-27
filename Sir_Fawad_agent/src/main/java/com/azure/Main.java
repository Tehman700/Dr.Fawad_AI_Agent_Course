package com.azure;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


class GUI {
    // WE MADE GUI,BUT IT WAS JUST TO MAKE US IMPLEMENT THE FUNCTIONS, BUT THIS ENHANCED GUI IS MADE BY CLAUDE AI, HATS OFF TO IT  25-02-2025

    private JFrame frame;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton chooseFileButton, chooseSaveLocationButton, runMainButton, exitButton, chooseQuantitativeButton;
    private JLabel filePathLabel, savePathLabel, quantPathLabel, logoLabel;
    private String selectedFilePath = ""; // Stores the selected file path
    private String selectedSavePath = ""; // Stores the selected save path for PDF
    public static String selectedQuantitativePath = "";

    // Color scheme
    private final Color PRIMARY_COLOR = new Color(41, 128, 185); // Blue
    private final Color SECONDARY_COLOR = new Color(52, 152, 219); // Lighter blue
    private final Color ACCENT_COLOR = new Color(230, 126, 34); // Orange
    private final Color BG_COLOR = new Color(236, 240, 241); // Light gray
    private final Color TEXT_COLOR = new Color(44, 62, 80); // Dark blue-gray
    private final Color SUCCESS_COLOR = new Color(46, 204, 113); // Green
    private final Color DANGER_COLOR = new Color(231, 76, 60); // Red

    public GUI() {
        // Set look and feel to system default for better integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create Frame
        frame = new JFrame("Course Automated Analyzer");
        frame.setSize(1000, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(BG_COLOR);

        // Create header panel with logo
        JPanel headerPanel = createHeaderPanel();
        frame.add(headerPanel, BorderLayout.NORTH);

        // Create Text Area with Scroll Pane
        JPanel contentPanel = createContentPanel();
        frame.add(contentPanel, BorderLayout.CENTER);

        // Create Side Panel
        JPanel sidePanel = createSidePanel();
        frame.add(sidePanel, BorderLayout.WEST);

        // Create Bottom Panel
        JPanel bottomPanel = createBottomPanel();
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Make Frame Visible
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add Logo
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\tehma\\Desktop\\Sir_Fawad_agent\\Sir_Fawad_agent\\src\\main\\resources\\uet.png");    Image img = logoIcon.getImage();
        Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(resizedImg);

        // Set the logo image
        logoLabel = new JLabel(logoIcon);

        JLabel titleLabel = new JLabel("Course Automated Analyzer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JPanel logoTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoTitlePanel.setBackground(PRIMARY_COLOR);
        logoTitlePanel.add(logoLabel);
        logoTitlePanel.add(titleLabel);

        headerPanel.add(logoTitlePanel, BorderLayout.WEST);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        // Path display panel
        JPanel pathPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        pathPanel.setBackground(BG_COLOR);

        filePathLabel = createInfoLabel("Source: No folder selected");
        savePathLabel = createInfoLabel("Save Location: Not selected");
        quantPathLabel = createInfoLabel("Quantitative Source: Not selected");

        pathPanel.add(filePathLabel);
        pathPanel.add(savePathLabel);
        pathPanel.add(quantPathLabel);

        contentPanel.add(pathPanel, BorderLayout.NORTH);

        // Create Text Area with Scroll Pane
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(TEXT_COLOR);
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 1));

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(TEXT_COLOR);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        return label;
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel(new GridLayout(3, 1, 0, 20));
        sidePanel.setBackground(BG_COLOR);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        chooseFileButton = createStyledButton("Choose Path to Analyze", new ImageIcon(), PRIMARY_COLOR);
        chooseSaveLocationButton = createStyledButton("Where to Save", new ImageIcon(), PRIMARY_COLOR);
        chooseQuantitativeButton = createStyledButton("Quantitative Search", new ImageIcon(), PRIMARY_COLOR);

        // Add Action Listeners
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int returnValue = fileChooser.showOpenDialog(frame);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null) {
                        selectedFilePath = selectedFile.getAbsolutePath();
                        filePathLabel.setText("Source: " + selectedFilePath);
                        chooseFileButton.setBackground(SUCCESS_COLOR);
                        updateRunButtonState();
                    }
                }
            }
        });

        chooseSaveLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int returnValue = fileChooser.showOpenDialog(frame);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null) {
                        selectedSavePath = selectedFile.getAbsolutePath();
                        savePathLabel.setText("Save Location: " + selectedSavePath);
                        chooseSaveLocationButton.setBackground(SUCCESS_COLOR);
                        updateRunButtonState();
                    }
                }
            }
        });

        chooseQuantitativeButton.addActionListener(e -> new SecondGUI());

        sidePanel.add(chooseFileButton);
        sidePanel.add(chooseSaveLocationButton);
        sidePanel.add(chooseQuantitativeButton);

        return sidePanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(BG_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        runMainButton = createStyledButton("Analyze", new ImageIcon(), ACCENT_COLOR);
        runMainButton.setFont(new Font("Arial", Font.BOLD, 16));
        runMainButton.setEnabled(false);

        exitButton = createStyledButton("Exit", new ImageIcon(), DANGER_COLOR);
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Add Action Listeners
        runMainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!selectedFilePath.isEmpty() && !selectedSavePath.isEmpty()) {
                    textArea.append("Starting analysis...\n");
                    runMainButton.setEnabled(false);

                    // Use SwingWorker to run the analysis in the background
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            try {
                                Main.main(new String[]{selectedFilePath, selectedSavePath});
                                return null;
                            } catch (Exception ex) {
                                SwingUtilities.invokeLater(() -> {
                                    showError("Error: " + ex.getMessage());
                                });
                                return null;
                            }
                        }

                        @Override
                        protected void done() {
                            textArea.append("Analysis completed.\n");
                            runMainButton.setEnabled(true);
                        }
                    };

                    worker.execute();
                } else {
                    showWarning("Please select a directory and a save location first.");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        bottomPanel.add(runMainButton);
        bottomPanel.add(exitButton);

        return bottomPanel;
    }

    private JButton createStyledButton(String text, ImageIcon icon, Color color) {
        JButton button = new JButton(text);
        if (icon != null && icon.getIconWidth() > 0) {
            button.setIcon(icon);
        }

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        // Add a bit of padding
        button.setMargin(new Insets(10, 15, 10, 15));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(darken(color, 0.1f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private Color darken(Color color, float fraction) {
        int red = Math.max(0, Math.round(color.getRed() * (1 - fraction)));
        int green = Math.max(0, Math.round(color.getGreen() * (1 - fraction)));
        int blue = Math.max(0, Math.round(color.getBlue() * (1 - fraction)));
        return new Color(red, green, blue);
    }

    private void updateRunButtonState() {
        runMainButton.setEnabled(!selectedFilePath.isEmpty() && !selectedSavePath.isEmpty());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                frame,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                frame,
                message,
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // Method to add text to the log
    public void appendToLog(String text) {
        textArea.append(text + "\n");
        // Auto-scroll to bottom
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    // Method to update a custom logo if needed
    public void setCustomLogo(ImageIcon logo) {
        if (logo != null) {
            logoLabel.setIcon(logo);
            logoLabel.setText(""); // Remove text when using image
        }
    }

    public static void main(String[] args) {
        // Start GUI
        SwingUtilities.invokeLater(() -> new GUI());
    }
}

class SecondGUI extends JFrame {
    public static String originalFolderPath = "";
    public static String sampleFolderPath = "";
    private JTextArea logTextArea;
    private JLabel originalPathLabel;
    private JLabel samplePathLabel;
    private JButton doQuantitativeBtn;

    // Color scheme
    private final Color PRIMARY_COLOR = new Color(75, 119, 190);
    private final Color SECONDARY_COLOR = new Color(92, 145, 232);
    private final Color ACCENT_COLOR = new Color(245, 166, 35);
    private final Color BG_COLOR = new Color(240, 245, 250);
    private final Color TEXT_COLOR = new Color(50, 63, 87);
    private final Color SUCCESS_COLOR = new Color(46, 204, 113);

    public SecondGUI() {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Basic frame setup
        setTitle("Quantitative Analysis Tool");
        setSize(850, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(10, 10));

        // Create and add main panels
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        // Set initial button state
        updateButtonState();

        // Show the frame
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Quantitative Analysis Tool");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Path selection panel
        JPanel pathPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        pathPanel.setBackground(BG_COLOR);

        // Original path panel
        JPanel originalPanel = new JPanel(new BorderLayout(10, 0));
        originalPanel.setBackground(BG_COLOR);
        originalPathLabel = new JLabel("No original folder selected");
        originalPathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        originalPathLabel.setBorder(createPathLabelBorder("Original Folder"));

        JButton chooseOriginalBtn = createStyledButton("Browse", SECONDARY_COLOR);
        chooseOriginalBtn.addActionListener(e -> {
            String path = openFileChooser();
            if (path != null) {
                originalFolderPath = path;
                originalPathLabel.setText(truncatePath(path));
                originalPathLabel.setToolTipText(path);
                updateButtonState();
                logAction("Original folder selected: " + path);
            }
        });

        originalPanel.add(originalPathLabel, BorderLayout.CENTER);
        originalPanel.add(chooseOriginalBtn, BorderLayout.EAST);

        // Sample path panel
        JPanel samplePanel = new JPanel(new BorderLayout(10, 0));
        samplePanel.setBackground(BG_COLOR);
        samplePathLabel = new JLabel("No sample folder selected");
        samplePathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        samplePathLabel.setBorder(createPathLabelBorder("Sample Folder"));

        JButton chooseSampleBtn = createStyledButton("Browse", SECONDARY_COLOR);
        chooseSampleBtn.addActionListener(e -> {
            String path = openFileChooser();
            if (path != null) {
                sampleFolderPath = path;
                samplePathLabel.setText(truncatePath(path));
                samplePathLabel.setToolTipText(path);
                updateButtonState();
                logAction("Sample folder selected: " + path);
            }
        });

        samplePanel.add(samplePathLabel, BorderLayout.CENTER);
        samplePanel.add(chooseSampleBtn, BorderLayout.EAST);

        pathPanel.add(originalPanel);
        pathPanel.add(samplePanel);

        // Log text area panel
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBackground(BG_COLOR);
        logPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR),
                "Activity Log",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14),
                SECONDARY_COLOR
        ));

        logTextArea = new JTextArea();
        logTextArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        logTextArea.setEditable(false);
        logTextArea.setBackground(Color.WHITE);
        logTextArea.setForeground(TEXT_COLOR);
        logTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        logPanel.add(scrollPane, BorderLayout.CENTER);

        // Add both panels to main panel
        mainPanel.add(pathPanel, BorderLayout.NORTH);
        mainPanel.add(logPanel, BorderLayout.CENTER);

        logAction("Application started. Please select folders to proceed.");

        return mainPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(BG_COLOR);
        logTextArea.setEditable(false);

        doQuantitativeBtn = createStyledButton("Run Quantitative Analysis", ACCENT_COLOR);
        doQuantitativeBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        doQuantitativeBtn.setEnabled(false);
        Quantitative_analysis obj = new Quantitative_analysis(logTextArea);

        doQuantitativeBtn.addActionListener(e -> obj.starter());

        JButton clearBtn = createStyledButton("Clear Selections", new Color(150, 150, 150));
        clearBtn.addActionListener(e -> {
            originalFolderPath = null;
            sampleFolderPath = null;
            originalPathLabel.setText("No original folder selected");
            samplePathLabel.setText("No sample folder selected");
            logAction("Selections cleared.");
            updateButtonState();
            logTextArea.setText("");
        });

        buttonPanel.add(doQuantitativeBtn);
        buttonPanel.add(clearBtn);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setMargin(new Insets(8, 15, 8, 15));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(darken(color, 0.1f));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(color);
                }
            }
        });

        return button;
    }

    private Border createPathLabelBorder(String title) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(SECONDARY_COLOR),
                        title,
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14),
                        SECONDARY_COLOR
                ),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );
    }

    private Color darken(Color color, float fraction) {
        int red = Math.max(0, Math.round(color.getRed() * (1 - fraction)));
        int green = Math.max(0, Math.round(color.getGreen() * (1 - fraction)));
        int blue = Math.max(0, Math.round(color.getBlue() * (1 - fraction)));
        return new Color(red, green, blue);
    }

    private String openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            return selectedFolder.getAbsolutePath();
        }
        return null;
    }

    private void updateButtonState() {
        doQuantitativeBtn.setEnabled(originalFolderPath != null && sampleFolderPath != null);

        if (doQuantitativeBtn.isEnabled()) {
            doQuantitativeBtn.setBackground(ACCENT_COLOR);
        } else {
            doQuantitativeBtn.setBackground(new Color(180, 180, 180));
        }
    }

    private void performQuantitative() {
        logAction("Starting quantitative analysis...");
        logAction("Original Folder: " + originalFolderPath);
        logAction("Sample Folder: " + sampleFolderPath);

        // Show working animation
        doQuantitativeBtn.setEnabled(false);
        doQuantitativeBtn.setText("Processing...");

        // Simulate work with SwingWorker
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulate processing
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(500);
                    publish("Processing step " + (i+1) + " of 5...");
                }
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                // Update log with progress
                for (String chunk : chunks) {
                    logAction(chunk);
                }
            }

            @Override
            protected void done() {
                // Complete the operation
                logAction("Quantitative analysis completed successfully!");
                doQuantitativeBtn.setText("Run Quantitative Analysis");
                doQuantitativeBtn.setEnabled(true);

                // Show completion message
                JOptionPane.showMessageDialog(
                        SecondGUI.this,
                        "Quantitative analysis has been completed successfully.\n\n" +
                                "Original Folder: " + originalFolderPath + "\n" +
                                "Sample Folder: " + sampleFolderPath,
                        "Analysis Complete",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        };

        worker.execute();
    }

    private void logAction(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        logTextArea.append("[" + timestamp + "] " + message + "\n");
        // Auto-scroll to the bottom
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    private String truncatePath(String path) {
        if (path == null) return "";
        if (path.length() <= 40) return path;

        File file = new File(path);
        String fileName = file.getName();
        String parent = file.getParent();

        if (parent != null && parent.length() > 30) {
            parent = "..." + parent.substring(parent.length() - 30);
        }

        return parent + File.separator + fileName;
    }
}


public class Main {
    String vectorStoreId = "vs_p0tRLzmvpGhtMCim0PuAxgDM"; // Replace with your vector store ID
    // Method to list all files and directories in a given directory
    public static void listAllFilesAndDirectories(String directoryPath) {
        try {
            Files.walkFileTree(Paths.get(directoryPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    System.out.println("File: " + file.toString());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    System.out.println("Directory: " + dir.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to check for specific files in the directory
    public static boolean checkForFiles(String directoryPath, String... fileNames) throws IOException {
        boolean allFilesPresent = true;
        for (String fileName : fileNames) {
            Path filePath = Paths.get(directoryPath, fileName);
            if (!Files.exists(filePath)) {
                System.out.println("File missing: " + fileName);
                allFilesPresent = false;
            }
        }
        return allFilesPresent;
    }
    /// file creation for report/////// // THis portion is used to append to the file that is appended when response is generated
    ///  30-01-2025 at 1:34 PM
    private static void appendToFile(String filePath, String content, String fileType) {
        // Append content to the specified file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), true))) {
            writer.write(content);
            writer.newLine(); // Ensure new content is on a new line
            System.out.println(fileType + " file updated successfully at: " + new File(filePath).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error appending content to the " + fileType + " file: " + e.getMessage());
        }
    }
    private static final String FILE_PATHSS = "file_ids.txt";
    // Method to show auto-closing dialog
    private static void showAutoCloseDialog(String message, String title, int messageType) {
        JOptionPane optionPane = new JOptionPane(message, messageType);
        JDialog dialog = optionPane.createDialog(title);
        Timer timer = new Timer(2000, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }
    // Main method
    public static void main(String[] args) throws Exception {
        String qwer = "Analysis Report.md";
        for (int i = 0; i < 2; i++) {
            try (FileWriter writer = new FileWriter(qwer)) {
                writer.write("");
                showAutoCloseDialog("Analysis Report File Deleted for New Changes.!!! Task 01 Completed", "Deletion Complete", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Cleared all contents of: " + qwer);
            } catch (IOException e) {
                showAutoCloseDialog("Some Error Occurred while deleting Analysis Report File.!!! Task 01 Exception", "Deletion Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("An error occurred while clearing the file " + qwer);
                e.printStackTrace();
            }
            qwer = "Analysis Report.txt";
        }
        String directoryPath = null;
        String savePath = null; // Add a variable to store the save path
        if (args.length > 0) {
            directoryPath = args[0];
            if (args.length > 1) {
                savePath = args[1]; // Get the save path from the arguments
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Provide Path for Files. It must be a Folder!!!", "Incomplete Path", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("No File Path Provided");
        }
        Main main = new Main();
        OpenAISetup run_bot = new OpenAISetup();
        OpenAIFileUpload upload = new OpenAIFileUpload();
        VectorStoreFileDeleter delete_file = new VectorStoreFileDeleter();
        // BELOW IS THE IMPORTANT PORTION FOR APPENDING FILE'S IN A FILE AND THEN DELETING AGAIN, HANDLING
        // IF PROGRAM STOPS IN BETWEEN LIKE INTERNET OR BREAKING EXCEPTIONS  **** Changes made on 28-01-2025 at 6:06AM *****
        try {
            // Read all lines from the file
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATHSS))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
            // Process lines: Remove each line after reading
            while (!lines.isEmpty()) {
                String lineToProcess = lines.get(0);
                System.out.println("YES THERE WAS PREVIOUS FILES IN VECTOR DATABASE!!!!");
                // Show pop-up before deletion
                showAutoCloseDialog("Starting deletion of file: " + lineToProcess, "Deletion Started", JOptionPane.INFORMATION_MESSAGE);
                delete_file.deleteVectorStoreFile(main.vectorStoreId, lineToProcess);

                // Show pop-up after deletion
                showAutoCloseDialog("Successfully deleted file: " + lineToProcess, "Deletion Complete", JOptionPane.INFORMATION_MESSAGE);
                // Remove the line from the list
                lines.remove(0);

                // Write the remaining lines back to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATHSS))) {
                    for (String remainingLine : lines) {
                        writer.write(remainingLine);
                        writer.newLine();
                    }
                }
            }
            System.out.println("Everything is Free in Vector Database");
            // Show pop-up when all files are deleted
            showAutoCloseDialog("All files have been deleted from the vector database.", "Deletion Complete", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            // Show pop-up if an error occurs
            JOptionPane.showMessageDialog(null, "An error occurred during deletion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        com.azure.DirectoryParser directoryParser = new DirectoryParser();
        ArrayList<String> filtered_Array = directoryParser.process_filteration(directoryPath);
        System.out.println("files\n" + filtered_Array);
        String file_1 = "";
        String file_2 = "";
        for (String S : filtered_Array) {
            String pair_files = S;
            String[] Pair_file_array = pair_files.split(",");
            file_1 = directoryParser.getFilesInFolder(directoryPath, Pair_file_array[0].trim());
            file_2 = directoryParser.getFilesInFolder(directoryPath, Pair_file_array[1].trim());
            System.out.println("\nFile_one_path: " + file_1);
            System.out.println("\nfile_two_path" + file_2);
            String fileId_1 = upload.associate_with_assistant(new File(file_1));
            String fileId_2 = upload.associate_with_assistant(new File(file_2));
            run_bot.process_bot();
            String response = "# " + Pair_file_array[0].trim() + "\n\n" + run_bot.contentValue + "\n\n# ---------------------------------------------------------------------\n";
            run_bot.contentValue = "";
            delete_file.deleteVectorStoreFile(main.vectorStoreId, fileId_1);
            delete_file.deleteVectorStoreFile(main.vectorStoreId, fileId_2);
            Thread.sleep(10000);
            // Append content to the specified file
            String markdownFilePath = "Analysis Report.md"; // Markdown file
            String textFilePath = "Analysis Report.txt"; // Text file
            String newContent = response + "\n\n\n";
            // Append the content to the Markdown file and Text file
            appendToFile(markdownFilePath, newContent, "Markdown");
            appendToFile(textFilePath, newContent, "Text");
            /// We also have to empty the text file which contains ID's   changes made on 28-01-2025      6:01 AM by Tehman
            try {
                FileWriter obj = new FileWriter(FILE_PATHSS);
                obj.write("");
                obj.close();
                showAutoCloseDialog("******************  Everything is Cleared in teh ID's File!!! *****************", "Deletion Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                System.out.println("An Error Occurred while Clearing the ID's File, Well System won't Stop!!!" + e.getMessage());
            }
            // In this portion we will transform the MD file to PDF File and then delete all the content of MD File, Implemented by Tehman on 29-01-2025 2:26AM
            MarkdownToPdfConverter objss = new MarkdownToPdfConverter();
            objss.changerss(savePath);
        }
    }
}
