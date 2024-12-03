/**
 * TEAM JAVA RICE
 */
package com.mycompany.electbudz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElectBudz {

    private static final String ADMIN_PASSWORD = "Admin123";
    private static final LinkedHashMap<String, LinkedHashMap<String, Integer>> positionVoteCount = new LinkedHashMap<>();
    private static int currentVoter = 0;
    private static int totalVoters = 0;

    static {
        initializeDefaultCandidates();
    }

    // Method to initialize default candidates
    private static void initializeDefaultCandidates() {
        positionVoteCount.put("Governor", createCandidateList("Recto, Rosa Vilma Tuazon S.", "Leviste, Jose Antonio S."));
        positionVoteCount.put("Vice Governor", createCandidateList("Mandanas, Hermilando I.", "Manzano, Luis Philippe S."));
        positionVoteCount.put("Provincial Board Member", createCandidateList("Balba, Rodolfo M.", "Corona, Alfredo C.", "Macalintal, Dennis C."));
        positionVoteCount.put("Mayor", createCandidateList("Ilagan, Janet M.", "Collantes, Nelson P.", "Africa, Eric B."));
        positionVoteCount.put("Vice Mayor", createCandidateList("Trinidad Jr., Herminigildo G.", "Lopez, Camille Angeline M.", "Ilagan, Jay M."));
        positionVoteCount.put("City/Town Councilor", createCandidateList(
                "Alice Green", "Bob White", "Carol Black", "David Lee",
                "Eve Harris", "Frank Adams", "Grace Clark", "Hank Turner",
                "Ivy Wilson", "Jack Morgan"));
    }

    // Utility method to create candidate lists with vote count initialized to 0
    private static LinkedHashMap<String, Integer> createCandidateList(String... candidates) {
        LinkedHashMap<String, Integer> candidateMap = new LinkedHashMap<>();
        for (String candidate : candidates) {
            candidateMap.put(candidate, 0); // Initialize each candidate with 0 votes
        }
        return candidateMap;
    }
    // Array implementation for each Positions
    private static final String[] positions = {
        "Governor", "Vice Governor", "Provincial Board Member", "Mayor", "Vice Mayor", "City/Town Councilor"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ElectBudz::showAdminVoterSelectionScreen); // method for the Event Dispatch Thread (EDT)
    }

    // Show the Admin/Voter Selection Screen
    private static void showAdminVoterSelectionScreen() {
        JFrame selectionFrame = new JFrame("ElectBudz - Main Menu");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setSize(1000, 700); // Adjusted size for layout flexibility
        selectionFrame.setLayout(new BorderLayout());
        selectionFrame.getContentPane().setBackground(Color.WHITE);

        // Set the application icon
        String iconPath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/Elect Budz Logo.png";
        ImageIcon icon = new ImageIcon(iconPath);
        selectionFrame.setIconImage(icon.getImage());

        // Main Panel to Stack Components Vertically
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        // Load the image and resize it
        String imagePath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/App logo.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image img = imageIcon.getImage();
        Image resizedImage = img.getScaledInstance(400, 400, Image.SCALE_SMOOTH); // Resizes the App logo image
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align in BoxLayout

        // Add title label
        JLabel promptLabel = new JLabel("Welcome to ElectBudz!", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Segoe UI", Font.BOLD, 32)); // Bold title
        promptLabel.setForeground(Color.BLACK);
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the "Vote Now!" button below the prompt
        JButton voterButton = new JButton("Vote Now!");
        voterButton.setBackground(Color.decode("#BF0D3E"));  // Red background
        voterButton.setForeground(Color.WHITE);
        voterButton.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Larger font
        voterButton.setFocusPainted(false);
        voterButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        voterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // if-else for voting
        voterButton.addActionListener(e -> {
            if (positionVoteCount.isEmpty() || totalVoters == 0) {
                JOptionPane.showMessageDialog(selectionFrame,
                        //Alert design
                        "<html><div style='text-align: center;'>"
                        + "<span style='color: #BF0D3E; font-size: 14px;'>Election setup incomplete!</span><br>"
                        + "Please ask the admin to start the election."
                        + "</div></html>",
                        "Setup Incomplete",
                        JOptionPane.WARNING_MESSAGE);
            } else if (currentVoter >= totalVoters) {
                JOptionPane.showMessageDialog(selectionFrame,
                        "<html><div style='text-align: center;'>"
                        + "<span style='color: #0032A0; font-size: 14px;'>All voters have cast their votes.</span><br>"
                        + "Election is complete!"
                        + "</div></html>",
                        "Voting Complete",
                        JOptionPane.INFORMATION_MESSAGE);
                showResultsScreen();
            } else {
                selectionFrame.dispose();
                showVotingScreen();
            }
        });

        // Add components to the main panel in order
        mainPanel.add(Box.createVerticalStrut(40)); // Spacing
        mainPanel.add(promptLabel);
        mainPanel.add(Box.createVerticalStrut(20)); // Spacing
        mainPanel.add(imageLabel);
        mainPanel.add(Box.createVerticalStrut(0)); // Spacing
        mainPanel.add(voterButton);

        selectionFrame.add(mainPanel, BorderLayout.CENTER);

        // Bottom Panel for "Admin Panel" Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align to bottom-right
        bottomPanel.setBackground(Color.WHITE);

        JButton adminButton = new JButton("Admin Panel");
        adminButton.setBackground(Color.decode("#0032A0"));  // Blue background
        adminButton.setForeground(Color.WHITE);
        adminButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        adminButton.setFocusPainted(false);
        adminButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        adminButton.addActionListener(e -> {
            selectionFrame.dispose();
            promptAdminPassword();
        });

        bottomPanel.add(adminButton);
        selectionFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Display the Frame
        selectionFrame.setLocationRelativeTo(null);
        selectionFrame.setVisible(true);
    }

    private static void promptAdminPassword() {
        JDialog passwordDialog = new JDialog((JFrame) null, "Admin Password", true);
        passwordDialog.setSize(450, 250);
        passwordDialog.setLayout(new GridBagLayout());
        passwordDialog.getContentPane().setBackground(new Color(250, 250, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Set the application icon
        String iconPath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/Elect Budz Logo.png";
        ImageIcon icon = new ImageIcon(iconPath);
        passwordDialog.setIconImage(icon.getImage());

        // Title and Password Field
        JLabel titleLabel = new JLabel("Enter Admin Password", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(45, 45, 45));
        gbc.gridy = 0;
        passwordDialog.add(titleLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBackground(new Color(240, 240, 240));
        passwordField.setForeground(Color.BLACK);
        gbc.gridy = 1;
        passwordDialog.add(passwordField, gbc);

        // Show Password Checkbox
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        showPasswordCheckBox.setBackground(new Color(250, 250, 250));
        showPasswordCheckBox.setForeground(new Color(80, 80, 80));
        showPasswordCheckBox.addActionListener(e -> passwordField.setEchoChar(showPasswordCheckBox.isSelected() ? (char) 0 : '*'));
        gbc.gridy = 2;
        passwordDialog.add(showPasswordCheckBox, gbc);

        // OK and Cancel Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(250, 250, 250));
        JButton okButton = new JButton("OK");
        okButton.setBackground(Color.decode("#0032A0"));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        okButton.addActionListener(e -> checkPassword(passwordDialog, passwordField));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.decode("#BF0D3E"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.addActionListener(e -> {
            passwordDialog.dispose();
            showAdminVoterSelectionScreen();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        gbc.gridy = 3;
        passwordDialog.add(buttonPanel, gbc);

        passwordField.addActionListener(e -> okButton.doClick()); // Trigger OK button on Enter

        passwordDialog.setLocationRelativeTo(null);
        passwordDialog.setVisible(true);
    }

    // checkPassword method
    private static void checkPassword(JDialog passwordDialog, JPasswordField passwordField) {
        String password = new String(passwordField.getPassword());

        if (password.equals(ADMIN_PASSWORD)) {
            passwordDialog.dispose();

            // Check if any votes have been cast
            boolean hasVotes = positionVoteCount.values().stream()
                    .flatMap(candidates -> candidates.values().stream())
                    .anyMatch(voteCount -> voteCount > 0);

            if (hasVotes) {
                showAdminOptionsWithResults(); // Show admin options with results
            } else {
                showAdminOptionSelectionScreen(); // Show option selection screen for a new election
            }
        } else {
            JOptionPane.showMessageDialog(passwordDialog,
                    //design alert mess
                    "<html><div style='text-align: center;'>"
                    + "<span style='color: #BF0D3E; font-size: 14px;'>Incorrect password!</span><br>"
                    + "Please try again."
                    + "</div></html>",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText(""); // Clear the password field for retry
        }
    }

    private static void showAdminOptionSelectionScreen() {
        JFrame adminOptionFrame = new JFrame("ElectBudz - Admin Panel");
        adminOptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminOptionFrame.setSize(1000, 700);
        adminOptionFrame.setLayout(new GridBagLayout());
        adminOptionFrame.getContentPane().setBackground(Color.WHITE);

        // Set the application icon
        String iconPath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/Elect Budz Logo.png";
        ImageIcon icon = new ImageIcon(iconPath);
        adminOptionFrame.setIconImage(icon.getImage());

        // Load and resize the image
        String imagePath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/Elect Budz Logo v.1.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image img = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));

        // Title label
        JLabel promptLabel = new JLabel("Admin Panel", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        promptLabel.setForeground(Color.BLACK);

        // Title label
        JLabel promptOption = new JLabel("Choose an option:", SwingConstants.CENTER);
        promptOption.setFont(new Font("Segoe UI", Font.BOLD, 16));
        promptOption.setForeground(Color.BLACK);

        // Buttons
        JButton manageCandidatesButton = createButton("Manage Candidates", "#0032A0", Color.WHITE);
        manageCandidatesButton.addActionListener(e -> {
            adminOptionFrame.dispose();
            showAdminCandidateScreen();
        });

        JButton setVoterCountButton = createButton("Set Number of Voters", "#FED141", Color.BLACK);
        setVoterCountButton.addActionListener(e -> {
            if (positionVoteCount.isEmpty()) {
                JOptionPane.showMessageDialog(adminOptionFrame,
                        //Design for Alert message
                        "<html><div style='text-align: center;'>"
                        + "<span style='color: #BF0D3E;'>No candidates added!</span><br>"
                        + "Please add at least one candidate before setting the voter count."
                        + "</div></html>",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {

                adminOptionFrame.dispose();
                showAdminVoterCountScreen();
            }
        });

        JButton startElectionButton = createButton("Start Election!", "#BF0D3E", Color.WHITE);
        startElectionButton.addActionListener(e -> {
            if (totalVoters == 0 || positionVoteCount.isEmpty()) {
                JOptionPane.showMessageDialog(adminOptionFrame,
                        // Design alert message
                        "<html><div style='text-align: center;'>"
                        + "<span style='color: #BF0D3E;'>Election setup incomplete!</span><br>"
                        + "Ensure candidates are added and voter count is set before starting the election."
                        + "</div></html>",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(adminOptionFrame,
                        "<html><div style='text-align: center;'>"
                        + "<span style='color: #28A745; font-size: 16px;'>Election is starting...</span><br>"
                        + "<span style='font-size: 14px;'>Voters can now proceed to vote!</span>"
                        + "</div></html>",
                        "Election Started",
                        JOptionPane.INFORMATION_MESSAGE);
                adminOptionFrame.dispose(); // Close the current frame
                showAdminVoterSelectionScreen(); // Show the voter selection screen
            }
        });

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 15, 15, 15);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Add components in the desired order
        gbc.gridy = 0;
        adminOptionFrame.add(imageLabel, gbc);
        gbc.gridy = 1;
        adminOptionFrame.add(promptLabel, gbc);
        gbc.gridy = 2;
        adminOptionFrame.add(promptOption, gbc);
        gbc.gridy = 3;
        adminOptionFrame.add(manageCandidatesButton, gbc);
        gbc.gridy = 4;
        adminOptionFrame.add(setVoterCountButton, gbc);
        gbc.gridy = 5;
        adminOptionFrame.add(startElectionButton, gbc);

        // Center the frame on the screen and make it visible
        adminOptionFrame.setLocationRelativeTo(null);
        adminOptionFrame.setVisible(true);
    }

// Helper method to create a button with custom styling
    private static JButton createButton(String text, String bgColorHex, Color textColor) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode(bgColorHex));
        button.setForeground(textColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(300, 80));
        button.setFocusPainted(false);
        return button;
    }

    // Method for showAdminCandidateScreen
    private static void showAdminCandidateScreen() {
        JFrame adminCandidateFrame = new JFrame("ElectBudz - Manage Candidates");
        adminCandidateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminCandidateFrame.setSize(1000, 700);
        adminCandidateFrame.setLayout(new BorderLayout(10, 10));

        // Set the application icon
        String iconPath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/Elect Budz Logo.png";
        ImageIcon icon = new ImageIcon(iconPath);
        adminCandidateFrame.setIconImage(icon.getImage());

        // Input Panel: Add candidates
        JPanel inputPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));  // Add padding around the input panel

        JLabel positionPromptLabel = new JLabel("Select position for the candidate:", SwingConstants.CENTER);
        inputPanel.add(positionPromptLabel);

        JComboBox<String> positionComboBox = new JComboBox<>(positions);
        inputPanel.add(positionComboBox);

        JTextField candidateField = new JTextField();
        candidateField.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(candidateField);

        JButton addCandidateButton = new JButton("Add Candidate");
        addCandidateButton.setBackground(new Color(34, 139, 34));  // Green background
        addCandidateButton.setForeground(Color.WHITE);
        addCandidateButton.setFocusPainted(false);  // Remove focus border
        inputPanel.add(addCandidateButton);

        JButton doneButton = new JButton("Done");
        doneButton.setBackground(Color.decode("#BF0D3E"));  // Red background
        doneButton.setForeground(Color.WHITE);
        doneButton.setFocusPainted(false);
        inputPanel.add(doneButton);

        adminCandidateFrame.add(inputPanel, BorderLayout.WEST);

        // Right Panel: Display Candidates
        JPanel displayPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        adminCandidateFrame.add(scrollPane, BorderLayout.CENTER);

        // Refresh Button on the top-right
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(Color.decode("#0032A0"));  // Blue background
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);

        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.add(refreshButton);
        adminCandidateFrame.add(refreshPanel, BorderLayout.NORTH);

        // Runnable to update the candidate list
        Runnable updateDisplayPanel = new Runnable() {
            @Override
            public void run() {
                displayPanel.removeAll(); // Clear the existing list

                // Iterate over position and candidate map (initializeDefaultCandidates)
                positionVoteCount.forEach((position, candidates) -> {
                    // Add a label for the position
                    JPanel positionPanel = new JPanel(new BorderLayout());
                    positionPanel.setBackground(new Color(245, 245, 245)); // Light gray

                    JLabel positionLabel = new JLabel(position);
                    positionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    positionLabel.setForeground(new Color(70, 130, 180)); // SteelBlue color
                    positionLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align text to the right

                    positionPanel.add(positionLabel, BorderLayout.CENTER); // Position the label to the left
                    displayPanel.add(positionPanel);

                    // Sort candidates alphabetically
                    candidates.keySet().stream()
                            .sorted()
                            .forEach(candidate -> {
                                // Create a panel for the candidate entry
                                JPanel candidatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                                candidatePanel.setBackground(new Color(245, 245, 245)); // Light gray

                                // Add candidate label
                                JLabel candidateLabel = new JLabel(candidate);
                                candidateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                                candidateLabel.setPreferredSize(new Dimension(200, 20));
                                candidatePanel.add(candidateLabel);

                                // Edit Button
                                JButton editButton = new JButton("Edit");
                                editButton.setBackground(Color.decode("#FED141"));  // Yellow background
                                editButton.setForeground(Color.BLACK);
                                editButton.setFocusPainted(false);
                                editButton.addActionListener(e -> {
                                    String newCandidateName = JOptionPane.showInputDialog(
                                            adminCandidateFrame,
                                            "Edit candidate name:",
                                            candidate
                                    );
                                    if (newCandidateName != null && !newCandidateName.trim().isEmpty() && !candidates.containsKey(newCandidateName)) {
                                        candidates.remove(candidate); // Remove old candidate name
                                        candidates.put(newCandidateName, candidates.getOrDefault(candidate, 0)); // Preserve votes if applicable
                                        JOptionPane.showMessageDialog(
                                                adminCandidateFrame,
                                                "<html><div style='text-align: center;'>"
                                                + "<span style='color: #28A745; font-size: 14px;'>Candidate name updated successfully!</span>"
                                                + "</div></html>",
                                                "Update Successful",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                        SwingUtilities.invokeLater(this); // Refresh the display panel
                                    } else if (candidates.containsKey(newCandidateName)) {
                                        JOptionPane.showMessageDialog(
                                                adminCandidateFrame,
                                                "<html><div style='text-align: center;'>"
                                                + "<span style='color: #BF0D3E; font-size: 14px;'>Candidate name already exists!</span>"
                                                + "</div></html>",
                                                "Duplicate Candidate",
                                                JOptionPane.WARNING_MESSAGE
                                        );
                                    }
                                });

                                // Delete Button
                                JButton deleteButton = new JButton("Delete");
                                deleteButton.setBackground(Color.decode("#BF0D3E")); // Red
                                deleteButton.setForeground(Color.WHITE);
                                deleteButton.setFocusPainted(false);
                                deleteButton.addActionListener(e -> {
                                    int confirm = JOptionPane.showConfirmDialog(
                                            adminCandidateFrame,
                                            "<html><div style='text-align: center;'>"
                                            + "<span style='color: #BF0D3E; font-size: 14px;'>Are you sure you want to delete:</span><br>"
                                            + "<b style='color: #0032A0; font-size: 16px;'>" + candidate + "</b>"
                                            + "</div></html>",
                                            "Confirm Delete",
                                            JOptionPane.YES_NO_OPTION,
                                            JOptionPane.WARNING_MESSAGE
                                    );
                                    if (confirm == JOptionPane.YES_OPTION) {
                                        candidates.remove(candidate); // Remove the candidate
                                        JOptionPane.showMessageDialog(
                                                adminCandidateFrame,
                                                "<html><div style='text-align: center;'>"
                                                + "<span style='color: #28A745; font-size: 14px;'>Candidate deleted successfully!</span><br>"
                                                + "</div></html>",
                                                "Delete Successful",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                        SwingUtilities.invokeLater(this); // Refresh the display panel
                                    }
                                });

                                // Add buttons to the candidate panel
                                candidatePanel.add(editButton);
                                candidatePanel.add(deleteButton);

                                // Add the candidate panel to the display panel
                                displayPanel.add(candidatePanel);
                            });
                });

                // Refreshes the UI
                displayPanel.revalidate();
                displayPanel.repaint();
            }
        };

        // Refresh Button Action
        refreshButton.addActionListener(e -> updateDisplayPanel.run());

        // Add Candidate Button Action
        addCandidateButton.addActionListener(e -> {
            String selectedPosition = (String) positionComboBox.getSelectedItem();
            String candidateName = candidateField.getText().trim();

            if (candidateName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        adminCandidateFrame,
                        "<html><div style='text-align: center;'>"
                        + "<span style='color: #BF0D3E; font-size: 14px;'>Candidate name cannot be empty.</span>"
                        + "</div></html>",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                positionVoteCount.putIfAbsent(selectedPosition, new LinkedHashMap<>());
                LinkedHashMap<String, Integer> candidates = positionVoteCount.get(selectedPosition);

                if (candidates.containsKey(candidateName)) {
                    JOptionPane.showMessageDialog(
                            adminCandidateFrame,
                            "<html><div style='text-align: center;'>"
                            + "<span style='color: #BF0D3E; font-size: 14px;'>Candidate already exists</span><br>"
                            + "for the position: <b>" + selectedPosition + "</b>."
                            + "</div></html>",
                            "Duplicate Candidate",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    candidates.put(candidateName, 0);
                    JOptionPane.showMessageDialog(
                            adminCandidateFrame,
                            "<html><div style='text-align: center;'>"
                            + "<span style='color: #28A745; font-size: 14px;'>Candidate added successfully!</span><br>"
                            + "Under position: <b>" + selectedPosition + "</b>"
                            + "</div></html>",
                            "Candidate Added",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    candidateField.setText("");
                    updateDisplayPanel.run();  // Refresh display
                }
            }
        });

        // Trigger Add Candidate with Enter key
        candidateField.addActionListener(e -> addCandidateButton.doClick());

        // Done Button Action
        doneButton.addActionListener(e -> {
            adminCandidateFrame.dispose();
            showAdminOptionSelectionScreen();
        });

        // Initial display update
        updateDisplayPanel.run();

        adminCandidateFrame.setLocationRelativeTo(null);
        adminCandidateFrame.setVisible(true);
    }

    // Method for setting No. of voters
    private static void showAdminVoterCountScreen() {
        JFrame voterCountFrame = new JFrame("ElectBudz - Set Number of Voters");
        voterCountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        voterCountFrame.setSize(400, 300);
        voterCountFrame.setLayout(new GridBagLayout());  // Use GridBagLayout for overall alignment
        voterCountFrame.getContentPane().setBackground(new Color(245, 245, 245));

        // Set the application icon
        String iconPath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/Elect Budz Logo.png";
        ImageIcon icon = new ImageIcon(iconPath);
        voterCountFrame.setIconImage(icon.getImage());

        // Create GridBagConstraints for alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;  // Center alignment

        // Title Label
        JLabel promptLabel = new JLabel("Enter Number of Voters", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        promptLabel.setForeground(new Color(0, 51, 102));
        voterCountFrame.add(promptLabel, gbc);

        // Voter Count Input Field
        JTextField voterCountField = new JTextField(10);  // Width ensures the cursor starts in the middle
        voterCountField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        voterCountField.setHorizontalAlignment(JTextField.CENTER);  // Align text and cursor to center
        voterCountField.setBackground(new Color(240, 240, 240));
        voterCountField.setForeground(Color.BLACK);
        voterCountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridy = 1;  // Move to next row
        voterCountFrame.add(voterCountField, gbc);

        // Set Button
        JButton setButton = new JButton("Set Voter Count");
        setButton.setBackground(new Color(0, 123, 255));
        setButton.setForeground(Color.WHITE);
        setButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        setButton.setFocusPainted(false);
        gbc.gridy = 2;  // Move to next row
        voterCountFrame.add(setButton, gbc);

        // Add Action Listener for Enter Key Trigger
        voterCountField.addActionListener(e -> setButton.doClick());

        // Set Button Action
        setButton.addActionListener(e -> {
            try {
                // Attempt to parse the entered voter count from the text field
                totalVoters = Integer.parseInt(voterCountField.getText().trim());  // Remove leading/trailing spaces
                if (totalVoters <= 0) {  // Ensure that the number of voters is positive
                    // Use custom alert for invalid number with HTML formatting
                    JOptionPane.showMessageDialog(voterCountFrame, "<html><div style='text-align: center;'>"
                            + "<span style='color: #D32F2F;font-size: 14px;'>Number of voters must be greater than zero.</span>"
                            + "</div></html>", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Use custom alert for success with HTML formatting
                    JOptionPane.showMessageDialog(voterCountFrame, "<html><div style='text-align: center;'>"
                            + "<span style='color: #28A745;font-size: 14px;'>Voter count set successfully.</span>"
                            + "</div></html>", "Success", JOptionPane.INFORMATION_MESSAGE);
                    voterCountFrame.dispose();  // Close the current frame
                    showAdminOptionSelectionScreen();  // Show the next screen (admin options)
                }
            } catch (NumberFormatException ex) {  // Handle invalid number format (non-numeric input)
                // Use custom alert for invalid number format with HTML formatting
                JOptionPane.showMessageDialog(voterCountFrame, "<html><div style='text-align: center;'>"
                        + "<span style='color: #D32F2F;font-size: 14px;'>Invalid number format. Please enter a valid number.</span>"
                        + "</div></html>", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Center the window and make it visible
        voterCountFrame.setLocationRelativeTo(null);
        voterCountFrame.setVisible(true);

        // Request focus to ensure the cursor starts blinking immediately
        SwingUtilities.invokeLater(voterCountField::requestFocusInWindow);
    }

    // Method for Voting Screen
    private static void showVotingScreen() {
        if (currentVoter < totalVoters) {
            JFrame votingFrame = new JFrame("ElectBudz - Voting " + (currentVoter + 1));
            votingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            votingFrame.setSize(1000, 800);

            // Set the application icon
            String iconPath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/Elect Budz Logo.png";
            ImageIcon icon = new ImageIcon(iconPath);
            votingFrame.setIconImage(icon.getImage());

            // Main panel to hold all components
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBackground(new Color(220, 240, 255)); // Light blue background

            // Title
            JLabel promptLabel = new JLabel("Voter " + (currentVoter + 1) + ": Select your candidates");
            promptLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
            promptLabel.setForeground(new Color(0, 102, 204)); // Blue color for title
            promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            promptLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            mainPanel.add(promptLabel);

            // Center panel to hold the two columns
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new GridLayout(1, 2, 100, 0)); // space between two
            centerPanel.setBackground(new Color(220, 240, 255));

            // Left column panel (Governor, Vice Governor, Provincial Board Member, Mayor)
            JPanel leftColumnPanel = new JPanel();
            leftColumnPanel.setLayout(new BoxLayout(leftColumnPanel, BoxLayout.Y_AXIS));
            leftColumnPanel.setBackground(new Color(220, 240, 255));

            // Right column panel (Remaining positions)
            JPanel rightColumnPanel = new JPanel();
            rightColumnPanel.setLayout(new BoxLayout(rightColumnPanel, BoxLayout.Y_AXIS));
            rightColumnPanel.setBackground(new Color(220, 240, 255));

            // Store selected candidates for each position
            LinkedHashMap<String, LinkedHashMap<String, JCheckBox>> positionGroups = new LinkedHashMap<>();

            positionVoteCount.forEach((position, candidates) -> {
                // Determine which column the position belongs to
                JPanel targetColumn = (position.equals("Governor") || position.equals("Vice Governor")
                        || position.equals("Provincial Board Member") || position.equals("Mayor"))
                        ? leftColumnPanel : rightColumnPanel;

                // Create a sub-panel for the position
                JPanel positionPanel = new JPanel();
                positionPanel.setLayout(new BoxLayout(positionPanel, BoxLayout.Y_AXIS));
                positionPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align text
                positionPanel.setBackground(new Color(220, 240, 255));
                positionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Space around position

                // Add position label
                JLabel positionLabel = new JLabel(position);
                positionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Larger font for position
                positionLabel.setForeground(new Color(70, 130, 180)); // SteelBlue color
                positionPanel.add(positionLabel);

                // Add indicator for the maximum selection
                int maxVotes;
                if (position.equals("Governor") || position.equals("Vice Governor")
                        || position.equals("Mayor") || position.equals("Vice Mayor")) {
                    maxVotes = 1;
                } else if (position.equals("Provincial Board Member")) {
                    maxVotes = 2;
                } else if (position.equals("City/Town Councilor")) {
                    maxVotes = 10;
                } else {
                    maxVotes = Integer.MAX_VALUE; // Default: No limit
                }

                JLabel indicatorLabel = new JLabel("Choose " + maxVotes);
                indicatorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Smaller font for indicator
                indicatorLabel.setForeground(new Color(100, 100, 100)); // Gray color for subtle text
                positionPanel.add(indicatorLabel);

                LinkedHashMap<String, JCheckBox> checkBoxes = new LinkedHashMap<>();

                // Add candidates for this position
                candidates.keySet().stream()
                        .sorted()
                        .forEach(candidate -> {
                            JCheckBox checkBox = new JCheckBox(candidate);
                            checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Larger font for candidates
                            checkBox.setBackground(new Color(220, 240, 255)); // Match panel background
                            checkBox.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align checkboxes

                            // Allow only one selection for specific positions
                            if (position.equals("Governor") || position.equals("Vice Governor")
                                    || position.equals("Mayor") || position.equals("Vice Mayor")) {
                                checkBox.addItemListener(e -> {
                                    if (e.getStateChange() == ItemEvent.SELECTED) {
                                        checkBoxes.forEach((otherCandidate, otherCheckBox) -> {
                                            if (otherCheckBox != checkBox) {
                                                otherCheckBox.setSelected(false);
                                            }
                                        });
                                    }
                                });
                            }

                            checkBoxes.put(candidate, checkBox);
                            positionPanel.add(Box.createVerticalStrut(10)); // Add space between checkboxes
                            positionPanel.add(checkBox);
                        });

                positionGroups.put(position, checkBoxes);
                targetColumn.add(positionPanel); // Add the position panel to the appropriate column
            });

            centerPanel.add(leftColumnPanel);
            centerPanel.add(rightColumnPanel);

            // Wrapper panel to center the two-column layout
            JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Center-align the columns
            wrapperPanel.setBackground(new Color(220, 240, 255));
            wrapperPanel.add(centerPanel);

            mainPanel.add(wrapperPanel);

            // Submit Button
            JButton submitButton = new JButton("Submit Votes");
            submitButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
            submitButton.setForeground(Color.WHITE);
            submitButton.setBackground(new Color(34, 139, 34)); // Green button
            submitButton.setFocusPainted(false);
            submitButton.setPreferredSize(new Dimension(200, 50));
            submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            submitButton.addActionListener(e -> {
                LinkedHashMap<String, java.util.List<String>> votes = new LinkedHashMap<>();
                boolean validVotes = true;

                for (var entry : positionGroups.entrySet()) {
                    String position = entry.getKey();
                    LinkedHashMap<String, JCheckBox> checkBoxes = entry.getValue();

                    java.util.List<String> selectedCandidates = new java.util.ArrayList<>();
                    checkBoxes.forEach((candidate, checkBox) -> {
                        if (checkBox.isSelected()) {
                            selectedCandidates.add(candidate);
                        }
                    });

                    int maxVotes;
                    if (position.equals("Governor") || position.equals("Vice Governor")
                            || position.equals("Mayor") || position.equals("Vice Mayor")) {
                        maxVotes = 1;
                    } else if (position.equals("Provincial Board Member")) {
                        maxVotes = 1;
                    } else if (position.equals("City/Town Councilor")) {
                        maxVotes = 10;
                    } else {
                        maxVotes = Integer.MAX_VALUE; // Default: No limit
                    }

                    if (selectedCandidates.size() > maxVotes) {
                        JOptionPane.showMessageDialog(
                                votingFrame,
                                "<html><div style='text-align: left;'>"
                                + "<span style='color: #BF0D3E;'>You can select a maximum of " + maxVotes
                                + " candidate(s) for the position of <b>" + position + "</b>.</span>"
                                + "</div></html>",
                                "Vote Limit Exceeded",
                                JOptionPane.WARNING_MESSAGE
                        );
                        validVotes = false;
                        break;
                    }
                    votes.put(position, selectedCandidates);
                }

                if (validVotes) {
                    votes.forEach((position, selectedCandidates) -> {
                        LinkedHashMap<String, Integer> candidates = positionVoteCount.get(position);
                        selectedCandidates.forEach(candidate -> candidates.put(candidate, candidates.get(candidate) + 1));
                    });

                    currentVoter++;
                    JOptionPane.showMessageDialog(
                            votingFrame,
                            "<html><div style='text-align: left;'>"
                            + "<span style='color: #28A745;'>Your votes have been submitted successfully!</span><br>"
                            + "Thank you for participating in the election."
                            + "</div></html>",
                            "Vote Submitted",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    votingFrame.dispose();
                    if (currentVoter < totalVoters) {
                        showVotingScreen();
                    } else {
                        showResultsScreen();
                    }
                }
            });

            // Add spacing and the button
            mainPanel.add(Box.createVerticalStrut(20)); // Adjust this value to control the height
            mainPanel.add(submitButton);

            // Scrollable frame
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            votingFrame.add(scrollPane);
            votingFrame.setLocationRelativeTo(null);
            votingFrame.setVisible(true);
        } else {
            showResultsScreen();
        }
    }

    // Method Results Screen
    private static void showResultsScreen() {
        JFrame resultsFrame = new JFrame("ElectBudz - Election Results");
        resultsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultsFrame.setSize(1000, 700);
        resultsFrame.setLayout(new BorderLayout(10, 10));

        // Set the application icon
        String iconPath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/Elect Budz Logo.png";
        ImageIcon icon = new ImageIcon(iconPath);
        resultsFrame.setIconImage(icon.getImage());

        // Title label with improved design
        JLabel titleLabel = new JLabel("Election Results (Sorted by total of " + totalVoters + " voters)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180)); // Steel blue background
        titleLabel.setForeground(Color.WHITE); // White text
        resultsFrame.add(titleLabel, BorderLayout.NORTH);

        // Panel to display results
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resultsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        resultsFrame.add(scrollPane, BorderLayout.CENTER);

        // Calculate and display results for each position
        positionVoteCount.forEach((position, candidates) -> {

            // Position title
            JLabel positionLabel = new JLabel(position, SwingConstants.CENTER);
            positionLabel.setFont(new Font("Arial", Font.BOLD, 18));
            positionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            positionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            resultsPanel.add(positionLabel);

            // Calculate the total votes and skipped votes
            int totalVotesForPosition = candidates.values().stream().mapToInt(Integer::intValue).sum();
            int skippedVotes = totalVoters - totalVotesForPosition;

            // Sort candidates by votes (descending)
            List<Map.Entry<String, Integer>> sortedCandidates = candidates.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .collect(Collectors.toList());

            // Determine if there is a tie for the highest votes
            boolean isTie = sortedCandidates.size() > 1
                    && sortedCandidates.get(0).getValue().equals(sortedCandidates.get(1).getValue());

            // Display each candidate's results
            sortedCandidates.forEach(entry -> {
                String candidate = entry.getKey();
                int votes = entry.getValue();

                String percentage = totalVotesForPosition > 0
                        ? String.format("%.2f%%", (votes * 100.0) / totalVoters)
                        : "0.00%";

                // Candidate result panel
                JPanel candidatePanel = new JPanel();
                candidatePanel.setLayout(new BoxLayout(candidatePanel, BoxLayout.Y_AXIS));
                candidatePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                candidatePanel.setBackground(new Color(240, 248, 255)); // Alice blue

                JLabel candidateTitleLabel = new JLabel(candidate, SwingConstants.CENTER);
                candidateTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
                candidateTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel candidateResultLabel = new JLabel(votes + " votes (" + percentage + ")", SwingConstants.CENTER);
                candidateResultLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                candidateResultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JProgressBar progressBar = new JProgressBar(0, totalVoters);
                progressBar.setValue(votes);
                progressBar.setStringPainted(true);

                // Set progress bar color to yellow if there's a tie; otherwise, sea green
                if (isTie && votes == sortedCandidates.get(0).getValue()) {
                    progressBar.setForeground(new Color(255, 223, 0)); // Yellow
                } else {
                    progressBar.setForeground(new Color(60, 179, 113)); // Sea green
                }
                progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

                candidatePanel.add(candidateTitleLabel);
                candidatePanel.add(candidateResultLabel);
                candidatePanel.add(progressBar);
                resultsPanel.add(candidatePanel);
            });

            // Add skipped votes (if any)
            if (skippedVotes > 0) {
                JLabel skippedLabel = new JLabel("Skipped Votes (No selection): " + skippedVotes + " votes");
                skippedLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                skippedLabel.setForeground(Color.GRAY);
                skippedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(skippedLabel);
            }
        });

        // Buttons with improved design
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(255, 69, 0)); // Red button
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> resultsFrame.dispose());

        JButton mainMenuButton = new JButton("Go to Main Menu");
        mainMenuButton.setBackground(new Color(30, 144, 255)); // Dodger blue button
        mainMenuButton.setForeground(Color.WHITE);
        mainMenuButton.setFocusPainted(false);
        mainMenuButton.addActionListener(e -> {
            resultsFrame.dispose();
            showAdminVoterSelectionScreen(); // Return to the main menu
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(closeButton);
        resultsFrame.add(buttonPanel, BorderLayout.SOUTH);

        resultsFrame.setLocationRelativeTo(null);
        resultsFrame.setVisible(true);
    }

    // Method for displaying Admin options only if results are available
    private static void showAdminOptionsWithResults() {
        if (positionVoteCount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No results available. Start an election first!");
            return; // Exit the method if there are no results
        }

        // Create the main frame for the Admin Options
        JFrame adminFrame = new JFrame("ElectBudz - Admin Panel");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(1000, 700); // Adjusted size for larger buttons
        adminFrame.setLayout(new GridBagLayout()); // Use GridBagLayout for better control
        adminFrame.getContentPane().setBackground(Color.WHITE); // Set white background

        // Layout constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Buttons will stretch horizontally
        gbc.gridx = 0; // Single column layout
        gbc.gridwidth = 1;

        // Set the application icon
        String iconPath = "C:/Users/erich/OneDrive/Documents/NetBeansProjects/ElectBudz/src/main/java/com/mycompany/electbudz/ElectBudz Logo/Elect Budz Logo.png";
        ImageIcon icon = new ImageIcon(iconPath);
        adminFrame.setIconImage(icon.getImage());

        // Title Label at the top
        JLabel titleLabel = new JLabel("Admin Panel: Choose an option", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Set font style and size
        titleLabel.setForeground(Color.BLACK); // Black font color for readability
        gbc.gridy = 0; // Place label at the first row
        adminFrame.add(titleLabel, gbc);

        // Button to view election results
        JButton viewResultsButton = new JButton("View Election Results");
        viewResultsButton.setBackground(Color.decode("#0032A0")); // Blue background
        viewResultsButton.setForeground(Color.WHITE); // White text for contrast
        viewResultsButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold and readable text
        viewResultsButton.setPreferredSize(new Dimension(300, 80)); // Adjust size for visibility
        viewResultsButton.setFocusPainted(false); // Remove the white focus box
        viewResultsButton.addActionListener(e -> showResultsScreen());
        gbc.gridy = 1; // Place button at the second row
        adminFrame.add(viewResultsButton, gbc);

        // Button to start a new election
        JButton newElectionButton = new JButton("Start New Election");
        newElectionButton.setBackground(Color.decode("#FED141")); // Yellow background
        newElectionButton.setForeground(Color.BLACK); // Black text for contrast
        newElectionButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold and readable text
        newElectionButton.setPreferredSize(new Dimension(300, 80)); // Adjust size for visibility
        newElectionButton.setFocusPainted(false); // Remove focus border
        newElectionButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    adminFrame,
                    //Design for alert message
                    "<html><div style='text-align: center;'>"
                    + "<span style='color: #BF0D3E; font-size: 14px;'>This will clear all previous election data.</span><br>"
                    + "Are you sure you want to start a new election?"
                    + "</div></html>",
                    "Confirm New Election",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                resetElectionData(); // Reset election data
                JOptionPane.showMessageDialog(
                        adminFrame,
                        "<html><div style='text-align: center;'>"
                        + "<span style='color: #28A745; font-size: 14px;'><b>Election reset successful!</b></span><br>"
                        + "Ready for a new election."
                        + "</div></html>",
                        "Election Reset",
                        JOptionPane.INFORMATION_MESSAGE
                );
                adminFrame.dispose();
                showAdminVoterSelectionScreen(); // Redirect to the main menu
            }
        });

        gbc.gridy = 2; // Place button at the third row
        adminFrame.add(newElectionButton, gbc);

        // Exit button
        JButton exitButton = new JButton("Exit Admin Panel");
        exitButton.setBackground(Color.decode("#BF0D3E")); // Red background
        exitButton.setForeground(Color.WHITE); // White text for contrast
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold and readable text
        exitButton.setPreferredSize(new Dimension(300, 80)); // Adjust size for visibility
        exitButton.setFocusPainted(false); // Remove focus border
        exitButton.addActionListener(e -> adminFrame.dispose());
        gbc.gridy = 3; // Place button at the fourth row
        adminFrame.add(exitButton, gbc);

        // Center the frame on the screen and make it visible
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

    // Method to reset election data
    private static void resetElectionData() {
        // Clear vote counts but retain the default candidates by reinitializing them
        positionVoteCount.clear();
        initializeDefaultCandidates(); // Reinitializes default candidates with zero vote count
        currentVoter = 0;
        totalVoters = 0;
    }

}
