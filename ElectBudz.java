/**
 * TEAM JAVA RICE
 */
package com.mycompany.electbudz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.LinkedHashMap;

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
        positionVoteCount.put("Provincial Board Member", createCandidateList("Balba, Rodolfo", "Corona, Alfredo", "Macalintal, Dennis"));
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

    private static final String[] positions = {
        "Governor", "Vice Governor", "Provincial Board Member", "Mayor", "Vice Mayor", "City/Town Councilor"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ElectBudz::showAdminVoterSelectionScreen);
    }

    private static void showAdminVoterSelectionScreen() {
        JFrame selectionFrame = new JFrame("ElectBudz - Main Menu");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setSize(600, 400);  // Increased size for better layout
        selectionFrame.setLayout(new BorderLayout());
        selectionFrame.getContentPane().setBackground(Color.WHITE);

        // Title Label at the Top
        JLabel promptLabel = new JLabel("Welcome to ElectBudz!", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));  // Bold Title
        promptLabel.setForeground(Color.BLACK);
        selectionFrame.add(promptLabel, BorderLayout.NORTH);

        // Center Panel for "Vote Now!" Button
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new GridBagLayout()); // Center the button in the panel

        JButton voterButton = new JButton("Vote Now!");
        voterButton.setBackground(Color.decode("#BF0D3E"));  // Red background
        voterButton.setForeground(Color.WHITE);
        voterButton.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Larger font
        voterButton.setFocusPainted(false);
        voterButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30)); // Padding for size
        voterButton.addActionListener(e -> {
            if (positionVoteCount.isEmpty() || totalVoters == 0) {
                JOptionPane.showMessageDialog(selectionFrame,
                        "Election setup incomplete. Please ask the admin to add candidates and set the voter count.");
            } else if (currentVoter >= totalVoters) {
                JOptionPane.showMessageDialog(selectionFrame,
                        "All voters have cast their votes. Election is complete!");
                showResultsScreen();
            } else {
                selectionFrame.dispose();
                showVotingScreen();
            }
        });
        centerPanel.add(voterButton);
        selectionFrame.add(centerPanel, BorderLayout.CENTER);

        // Bottom-Right Panel for "Admin Panel" Button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        JButton adminButton = new JButton("Admin Panel");
        adminButton.setBackground(Color.decode("#0032A0"));  // Blue background
        adminButton.setForeground(Color.WHITE);
        adminButton.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Smaller font
        adminButton.setFocusPainted(false);
        adminButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding for size
        adminButton.addActionListener(e -> {
            selectionFrame.dispose();
            promptAdminPassword();
        });

        bottomPanel.add(adminButton, BorderLayout.EAST);
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
        okButton.setBackground(new Color(0, 123, 255));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        okButton.addActionListener(e -> checkPassword(passwordDialog, passwordField));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 53, 69));
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
            JOptionPane.showMessageDialog(passwordDialog, "Incorrect password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText(""); // Clear the password field for retry
        }
    }

    private static void showAdminOptionSelectionScreen() {
        // Create the main frame for the Admin Panel
        JFrame adminOptionFrame = new JFrame("ElectBudz - Admin Panel");
        adminOptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminOptionFrame.setSize(450, 500);  // Adjusted size for larger buttons
        adminOptionFrame.setLayout(new GridBagLayout());  // Use GridBagLayout for better control
        adminOptionFrame.getContentPane().setBackground(Color.WHITE);  // Set white background

        // Layout constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);  // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Buttons will stretch horizontally
        gbc.gridx = 0; // Single column layout
        gbc.gridwidth = 1;

        // Title Label at the top
        JLabel promptLabel = new JLabel("Admin Panel: Choose an option", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Set font style and size
        promptLabel.setForeground(Color.BLACK); // Black font color for readability
        gbc.gridy = 0; // Place label at the first row
        adminOptionFrame.add(promptLabel, gbc);

        // Manage Candidates Button
        JButton manageCandidatesButton = new JButton("Manage Candidates");
        manageCandidatesButton.setBackground(Color.decode("#0032A0"));  // Blue background
        manageCandidatesButton.setForeground(Color.WHITE);  // White text for contrast
        manageCandidatesButton.setFont(new Font("Segoe UI", Font.BOLD, 16));  // Bold and readable text
        manageCandidatesButton.setPreferredSize(new Dimension(300, 80));  // Adjust size for visibility
        manageCandidatesButton.setFocusPainted(false);  // Remove the white focus box
        manageCandidatesButton.addActionListener(e -> {
            // Action to navigate to the Manage Candidates screen
            adminOptionFrame.dispose(); // Close the current frame
            showAdminCandidateScreen(); // Show the candidate management screen
        });
        gbc.gridy = 1; // Place button at the second row
        adminOptionFrame.add(manageCandidatesButton, gbc);

        // Set Voter Count Button
        JButton setVoterCountButton = new JButton("Set Number of Voters");
        setVoterCountButton.setBackground(Color.decode("#FED141"));  // Yellow background
        setVoterCountButton.setForeground(Color.BLACK);  // Black text for contrast
        setVoterCountButton.setFont(new Font("Segoe UI", Font.BOLD, 16));  // Bold and readable text
        setVoterCountButton.setPreferredSize(new Dimension(300, 80));  // Adjust size for visibility
        setVoterCountButton.setFocusPainted(false);  // Remove focus border
        setVoterCountButton.addActionListener(e -> {
            // Check if candidates exist before setting the voter count
            if (positionVoteCount.isEmpty()) {
                JOptionPane.showMessageDialog(adminOptionFrame,
                        "No candidates have been added yet. Please add at least one candidate before setting the voter count.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                adminOptionFrame.dispose(); // Close the current frame
                showAdminVoterCountScreen(); // Show the voter count setup screen
            }
        });
        gbc.gridy = 2; // Place button at the third row
        adminOptionFrame.add(setVoterCountButton, gbc);

        // Start Election Button
        JButton startElectionButton = new JButton("Start Election");
        startElectionButton.setBackground(Color.decode("#BF0D3E"));  // Red background
        startElectionButton.setForeground(Color.WHITE);  // White text for contrast
        startElectionButton.setFont(new Font("Segoe UI", Font.BOLD, 16));  // Bold and readable text
        startElectionButton.setPreferredSize(new Dimension(300, 80));  // Adjust size for visibility
        startElectionButton.setFocusPainted(false);  // Remove focus border
        startElectionButton.addActionListener(e -> {
            // Check if election setup is complete before starting
            if (totalVoters == 0 || positionVoteCount.isEmpty()) {
                JOptionPane.showMessageDialog(adminOptionFrame,
                        "Election setup incomplete. Ensure candidates are added and voter count is set before starting the election.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(adminOptionFrame,
                        "Election is starting... Voters can now proceed to vote!",
                        "Election Started",
                        JOptionPane.INFORMATION_MESSAGE);
                adminOptionFrame.dispose(); // Close the current frame
                showAdminVoterSelectionScreen(); // Show the voter selection screen
            }
        });
        gbc.gridy = 3; // Place button at the fourth row
        adminOptionFrame.add(startElectionButton, gbc);

        // Center the frame on the screen and make it visible
        adminOptionFrame.setLocationRelativeTo(null);
        adminOptionFrame.setVisible(true);
    }

    private static void showAdminCandidateScreen() {
        JFrame adminCandidateFrame = new JFrame("ElectBudz - Manage Candidates");
        adminCandidateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminCandidateFrame.setSize(900, 500);
        adminCandidateFrame.setLayout(new BorderLayout(10, 10));

        // Input Panel: Add candidates
        JPanel inputPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Add padding around the input panel

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
        doneButton.setBackground(new Color(200, 50, 50));  // Red background for "Done"
        doneButton.setForeground(Color.WHITE);
        doneButton.setFocusPainted(false);
        inputPanel.add(doneButton);

        adminCandidateFrame.add(inputPanel, BorderLayout.WEST);

        // Right Panel: Display Candidates
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        adminCandidateFrame.add(scrollPane, BorderLayout.CENTER);

        // Refresh Button on the top-right
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(70, 130, 180));  // SteelBlue background
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

                // Iterate over position and candidate map
                positionVoteCount.forEach((position, candidates) -> {
                    // Add a label for the position
                    JLabel positionLabel = new JLabel(position);
                    positionLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    positionLabel.setForeground(new Color(70, 130, 180)); // SteelBlue color
                    displayPanel.add(positionLabel);

                    // Sort candidates alphabetically
                    candidates.keySet().stream()
                            .sorted()
                            .forEach(candidate -> {
                                // Create a panel for the candidate entry
                                JPanel candidatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                                candidatePanel.setBackground(new Color(245, 245, 245)); // Light gray

                                // Add candidate label
                                JLabel candidateLabel = new JLabel(candidate);
                                candidateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                                candidateLabel.setPreferredSize(new Dimension(200, 20));
                                candidatePanel.add(candidateLabel);

                                // Edit Button
                                JButton editButton = new JButton("Edit");
                                editButton.setBackground(new Color(255, 215, 0)); // Gold
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
                                        JOptionPane.showMessageDialog(adminCandidateFrame, "Candidate name updated!");
                                        SwingUtilities.invokeLater(this); // Refresh the display panel
                                    } else if (candidates.containsKey(newCandidateName)) {
                                        JOptionPane.showMessageDialog(adminCandidateFrame, "Candidate name already exists!");
                                    }
                                });

                                // Delete Button
                                JButton deleteButton = new JButton("Delete");
                                deleteButton.setBackground(new Color(255, 69, 0)); // Red
                                deleteButton.setForeground(Color.WHITE);
                                deleteButton.setFocusPainted(false);
                                deleteButton.addActionListener(e -> {
                                    int confirm = JOptionPane.showConfirmDialog(
                                            adminCandidateFrame,
                                            "Are you sure you want to delete " + candidate + "?",
                                            "Confirm Delete",
                                            JOptionPane.YES_NO_OPTION
                                    );
                                    if (confirm == JOptionPane.YES_OPTION) {
                                        candidates.remove(candidate); // Remove the candidate
                                        JOptionPane.showMessageDialog(adminCandidateFrame, "Candidate deleted successfully!");
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
                JOptionPane.showMessageDialog(adminCandidateFrame, "Candidate name cannot be empty.");
            } else {
                positionVoteCount.putIfAbsent(selectedPosition, new LinkedHashMap<>());
                LinkedHashMap<String, Integer> candidates = positionVoteCount.get(selectedPosition);

                if (candidates.containsKey(candidateName)) {
                    JOptionPane.showMessageDialog(adminCandidateFrame, "Candidate already exists for this position.");
                } else {
                    candidates.put(candidateName, 0);
                    JOptionPane.showMessageDialog(adminCandidateFrame, "Candidate added successfully under " + selectedPosition);
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

    private static void showAdminVoterCountScreen() {
        JFrame voterCountFrame = new JFrame("ElectBudz - Set Number of Voters");
        voterCountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        voterCountFrame.setSize(400, 300);
        voterCountFrame.setLayout(new GridBagLayout());  // Use GridBagLayout for overall alignment
        voterCountFrame.getContentPane().setBackground(new Color(245, 245, 245));

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
                totalVoters = Integer.parseInt(voterCountField.getText().trim());
                if (totalVoters <= 0) {
                    JOptionPane.showMessageDialog(voterCountFrame, "Number of voters must be greater than zero.");
                } else {
                    JOptionPane.showMessageDialog(voterCountFrame, "Voter count set successfully.");
                    voterCountFrame.dispose();
                    showAdminOptionSelectionScreen();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(voterCountFrame, "Invalid number format. Please enter a valid number.");
            }
        });

        // Center the window and make it visible
        voterCountFrame.setLocationRelativeTo(null);
        voterCountFrame.setVisible(true);

        // Request focus to ensure the cursor starts blinking immediately
        SwingUtilities.invokeLater(voterCountField::requestFocusInWindow);
    }

    private static void showVotingScreen() {
        if (currentVoter < totalVoters) {
            JFrame votingFrame = new JFrame("ElectBudz - Voting " + (currentVoter + 1));
            votingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            votingFrame.setSize(500, 700);
            // Main panel to hold all components
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBackground(new Color(240, 240, 240));  // Light background for better contrast

            // Title
            JLabel promptLabel = new JLabel("Voter " + (currentVoter + 1) + ": Select your candidates", SwingConstants.CENTER);
            promptLabel.setFont(new Font("Arial", Font.BOLD, 18));
            promptLabel.setForeground(new Color(0, 102, 204));  // Blue color for the title
            promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            promptLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));  // Padding
            mainPanel.add(promptLabel);

            // Store the selected candidates for each position
            LinkedHashMap<String, LinkedHashMap<String, JCheckBox>> positionGroups = new LinkedHashMap<>();

            positionVoteCount.forEach((position, candidates) -> {
                // Add a label for the position
                JLabel positionLabel = new JLabel(position, SwingConstants.CENTER);
                positionLabel.setFont(new Font("Arial", Font.BOLD, 14));
                positionLabel.setForeground(new Color(70, 130, 180));  // SteelBlue for positions
                positionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                positionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                mainPanel.add(positionLabel);

                LinkedHashMap<String, JCheckBox> checkBoxes = new LinkedHashMap<>();

                // Sort candidates by name in alphabetical order
                candidates.keySet().stream()
                        .sorted() // Sort candidate names alphabetically
                        .forEach(candidate -> {
                            JCheckBox checkBox = new JCheckBox(candidate);

                            // logic for allowing unchecking
                            if (position.equals("Mayor") || position.equals("Vice Mayor") || position.equals("Governor") || position.equals("Vice Governor")) {
                                checkBox.addItemListener(e -> {
                                    if (e.getStateChange() == ItemEvent.SELECTED) {
                                        // Unselect all other checkboxes in this group
                                        checkBoxes.forEach((otherCandidate, otherCheckBox) -> {
                                            if (otherCheckBox != checkBox) {
                                                otherCheckBox.setSelected(false);
                                            }
                                        });
                                    }
                                });
                            }

                            checkBoxes.put(candidate, checkBox);
                            mainPanel.add(checkBox);
                        });

                positionGroups.put(position, checkBoxes);
            });

            JButton submitButton = new JButton("Submit Votes");
            submitButton.setFont(new Font("Arial", Font.BOLD, 16));
            submitButton.setForeground(Color.WHITE);
            submitButton.setBackground(new Color(34, 139, 34));  // Green color for submit button
            submitButton.setFocusPainted(false);
            submitButton.setPreferredSize(new Dimension(150, 40));
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

                    int maxVotes = switch (position) {
                        case "Governor" ->
                            1;
                        case "Vice Governor" ->
                            1;
                        case "Provincial Board Member" ->
                            2;
                        case "Mayor" ->
                            1;
                        case "Vice Mayor" ->
                            1;
                        case "City/Town Councilor" ->
                            10;
                        default ->
                            Integer.MAX_VALUE; // Default: No limit
                    };

                    if (selectedCandidates.size() > maxVotes) {
                        JOptionPane.showMessageDialog(votingFrame,
                                "You can select a maximum of " + maxVotes + " candidate(s) for " + position + ".");
                        validVotes = false;
                        break;
                    }
                    votes.put(position, selectedCandidates);
                }

                if (validVotes) {
                    // Cast votes for selected candidates
                    votes.forEach((position, selectedCandidates) -> {
                        LinkedHashMap<String, Integer> candidates = positionVoteCount.get(position);
                        for (String candidate : selectedCandidates) {
                            candidates.put(candidate, candidates.get(candidate) + 1);
                        }
                    });

                    currentVoter++;
                    JOptionPane.showMessageDialog(votingFrame, "Your votes have been submitted!");

                    votingFrame.dispose();

                    // Proceed to the next voter or show results
                    if (currentVoter < totalVoters) {
                        showVotingScreen();
                    } else {
                        showResultsScreen();
                    }
                }
            });
            mainPanel.add(submitButton);

            // Add a scroll pane to the frame
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

    private static void showResultsScreen() { // SID DITO KUPAL KA
        JFrame resultsFrame = new JFrame("ElectBudz - Election Results");
        resultsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultsFrame.setSize(700, 600);
        resultsFrame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Election Results (Sorted by total of " + totalVoters + " voters)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultsFrame.add(titleLabel, BorderLayout.NORTH);

        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        resultsFrame.add(scrollPane, BorderLayout.CENTER);

        // Calculate and display results for each position
        positionVoteCount.forEach((position, candidates) -> {
            JLabel positionLabel = new JLabel(position, SwingConstants.LEFT);
            positionLabel.setFont(new Font("Arial", Font.BOLD, 16));
            positionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            resultsPanel.add(positionLabel);

            // Calculate the total votes for the position
            int totalVotesForPosition = candidates.values().stream().mapToInt(Integer::intValue).sum();

            // Calculate skipped votes (voters who didn't vote for this position)
            int skippedVotes = totalVoters - totalVotesForPosition;

            candidates.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort by votes (descending)
                    .forEach(entry -> {
                        String candidate = entry.getKey();
                        int votes = entry.getValue();

                        String percentage = totalVotesForPosition > 0
                                ? String.format("%.2f%%", (votes * 100.0) / totalVoters)
                                : "0.00%";

                        JPanel candidatePanel = new JPanel(new BorderLayout());
                        candidatePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                        JLabel candidateLabel = new JLabel(candidate + ": " + votes + " votes (" + percentage + ")");
                        JProgressBar progressBar = new JProgressBar(0, totalVoters); // Use totalVoters as the max value
                        progressBar.setValue(votes);
                        progressBar.setStringPainted(true);

                        candidatePanel.add(candidateLabel, BorderLayout.WEST);
                        candidatePanel.add(progressBar, BorderLayout.CENTER);
                        resultsPanel.add(candidatePanel);
                    });

            // Add skipped votes (if any)
            if (skippedVotes > 0) {
                JLabel skippedLabel = new JLabel("Skipped Votes (No selection): " + skippedVotes + " votes");
                skippedLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                skippedLabel.setForeground(Color.GRAY); // Gray color for skipped votes
                resultsPanel.add(skippedLabel);
            }
        });

        // Close and Main Menu buttons
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> resultsFrame.dispose());

        JButton mainMenuButton = new JButton("Go to Main Menu");
        mainMenuButton.addActionListener(e -> {
            resultsFrame.dispose();
            showAdminVoterSelectionScreen(); // Return to the main menu
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(closeButton);
        resultsFrame.add(buttonPanel, BorderLayout.SOUTH);

        resultsFrame.setLocationRelativeTo(null);
        resultsFrame.setVisible(true);
    } // HANGGANG D2 LANG KUPAL

// Updated method to show Admin options only if results are available
    private static void showAdminOptionsWithResults() {
        if (positionVoteCount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No results available. Start an election first!");
            return; // Exit the method if there are no results
        }

        JFrame adminFrame = new JFrame("ElectBudz - Admin Options");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(400, 300);
        adminFrame.setLayout(new GridLayout(0, 1, 10, 10));

        JLabel titleLabel = new JLabel("Admin Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        adminFrame.add(titleLabel);

        // Button to view election results
        JButton viewResultsButton = new JButton("View Election Results");
        viewResultsButton.setBackground(Color.decode("#0032A0"));
        viewResultsButton.setForeground(Color.WHITE);
        viewResultsButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        viewResultsButton.setFocusPainted(false);
        viewResultsButton.addActionListener(e -> showResultsScreen());
        adminFrame.add(viewResultsButton);

        // Button to start a new election
        JButton newElectionButton = new JButton("Start New Election");
        newElectionButton.setBackground(Color.decode("#FED141"));
        newElectionButton.setForeground(Color.BLACK);
        newElectionButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        newElectionButton.setFocusPainted(false);
        newElectionButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    adminFrame,
                    "This will clear all previous election data. Are you sure you want to start a new election?",
                    "Confirm New Election",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                resetElectionData(); // Reset election data
                JOptionPane.showMessageDialog(adminFrame, "Election reset successful! Ready for a new election.");
                adminFrame.dispose();
                showAdminVoterSelectionScreen(); // Redirect to the main menu
            }
        });
        adminFrame.add(newElectionButton);

        // Exit button
        JButton exitButton = new JButton("Exit Admin Panel");
        exitButton.setBackground(Color.decode("#BF0D3E"));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> adminFrame.dispose());
        adminFrame.add(exitButton);

        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

// Method to reset election data
    private static void resetElectionData() {
        // Clear vote counts but retain the default candidates by reinitializing them
        positionVoteCount.clear();
        initializeDefaultCandidates(); // Reinitialize default candidates with zero vote count
        currentVoter = 0;
        totalVoters = 0;
    }

}
