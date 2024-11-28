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
    private static final String[] positions = {
        "Mayor", "Vice Mayor", "Member, Sangguniang Panlungsod/Bayan (Councilor)"
    };
    private static int totalVoters = 0;
    private static int currentVoter = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ElectBudz::showAdminVoterSelectionScreen);
    }

    private static void showAdminVoterSelectionScreen() {
        JFrame selectionFrame = new JFrame("ElectBudz - Main Menu");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setSize(400, 200);
        selectionFrame.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel promptLabel = new JLabel("Welcome to ElectBudz!", SwingConstants.CENTER);
        selectionFrame.add(promptLabel);

        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(e -> {
            selectionFrame.dispose();
            promptAdminPassword();
        });

        JButton voterButton = new JButton("Vote now!");
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

        selectionFrame.add(voterButton);
        selectionFrame.add(adminButton);

        selectionFrame.setLocationRelativeTo(null);
        selectionFrame.setVisible(true);
    }

    private static void promptAdminPassword() {
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, passwordField, "Enter Admin Password",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            if (password.equals(ADMIN_PASSWORD)) {
                // Check if election results exist
                if (positionVoteCount.isEmpty()) {
                    showAdminOptionSelectionScreen();
                } else {
                    showAdminOptionsWithResults(); // for existing results
                }
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect password. Returning to selection screen.");
                showAdminVoterSelectionScreen();
            }
        } else {
            showAdminOptionsWithResults(); // for existing results
        }

    }

    private static void showAdminOptionSelectionScreen() {
        JFrame adminOptionFrame = new JFrame("ElectBudz - Admin");
        adminOptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminOptionFrame.setSize(400, 400);
        adminOptionFrame.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel promptLabel = new JLabel("Admin Panel: Choose an option", SwingConstants.CENTER);
        adminOptionFrame.add(promptLabel);

        JButton manageCandidatesButton = new JButton("Manage Candidates");
        manageCandidatesButton.addActionListener(e -> {
            adminOptionFrame.dispose();
            showAdminCandidateScreen();
        });

        // Button to set number of voters
        JButton setVoterCountButton = new JButton("Set Number of Voters");
        setVoterCountButton.addActionListener(e -> {
            if (positionVoteCount.isEmpty()) {
                JOptionPane.showMessageDialog(
                        adminOptionFrame,
                        "No candidates have been added yet. Please add at least one candidate before setting the voter count.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                adminOptionFrame.dispose();
                showAdminVoterCountScreen();
            }
        });

        // Button to start election
        JButton startElectionButton = new JButton("Start Election");
        startElectionButton.addActionListener(e -> {
            if (totalVoters == 0 || positionVoteCount.isEmpty()) {
                JOptionPane.showMessageDialog(
                        adminOptionFrame,
                        "Election setup incomplete. Ensure candidates are added and voter count is set before starting the election.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        adminOptionFrame,
                        "Election has started. Voters can now proceed to vote!",
                        "Election Started",
                        JOptionPane.INFORMATION_MESSAGE
                );
                adminOptionFrame.dispose();
                showAdminVoterSelectionScreen();
            }
        });

        adminOptionFrame.add(manageCandidatesButton);
        adminOptionFrame.add(setVoterCountButton);
        adminOptionFrame.add(startElectionButton);

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
                displayPanel.removeAll();  // Clear the existing list
                positionVoteCount.forEach((position, candidates) -> {
                    JLabel positionLabel = new JLabel(position);
                    positionLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    positionLabel.setForeground(new Color(70, 130, 180));  // SteelBlue for the position label
                    displayPanel.add(positionLabel);

                    candidates.forEach((candidate, votes) -> {
                        JPanel candidatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        candidatePanel.setBackground(new Color(245, 245, 245));  // Light gray background

                        JLabel candidateLabel = new JLabel(candidate);
                        candidateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                        candidateLabel.setPreferredSize(new Dimension(200, 20));
                        candidatePanel.add(candidateLabel);

                        // Edit Button
                        JButton editButton = new JButton("Edit");
                        editButton.setBackground(new Color(255, 215, 0));  // Gold for edit button
                        editButton.setForeground(Color.BLACK);
                        editButton.setFocusPainted(false);
                        editButton.addActionListener(e -> {
                            String newCandidateName = JOptionPane.showInputDialog(
                                    adminCandidateFrame,
                                    "Edit candidate name:",
                                    candidate
                            );
                            if (newCandidateName != null && !newCandidateName.trim().isEmpty()) {
                                candidates.remove(candidate);
                                candidates.put(newCandidateName.trim(), votes);
                                JOptionPane.showMessageDialog(adminCandidateFrame, "Candidate name updated!");
                            }
                        });

                        // Delete Button
                        JButton deleteButton = new JButton("Delete");
                        deleteButton.setBackground(new Color(255, 69, 0));  // Red for delete button
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
                                candidates.remove(candidate);
                                JOptionPane.showMessageDialog(adminCandidateFrame, "Candidate deleted successfully!");
                            }
                        });

                        candidatePanel.add(editButton);
                        candidatePanel.add(deleteButton);
                        displayPanel.add(candidatePanel);
                    });
                });

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
        voterCountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        voterCountFrame.setSize(300, 150);
        voterCountFrame.setLayout(new FlowLayout());

        JLabel promptLabel = new JLabel("Enter number of voters:");
        voterCountFrame.add(promptLabel);

        JTextField voterCountField = new JTextField(10);
        voterCountFrame.add(voterCountField);

        JButton setButton = new JButton("Set Voter Count");
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
                JOptionPane.showMessageDialog(voterCountFrame, "Invalid number format.");
            }
        });
        voterCountFrame.add(setButton);

        voterCountFrame.setLocationRelativeTo(null);
        voterCountFrame.setVisible(true);
    }

    private static void showVotingScreen() {
        if (currentVoter < totalVoters) {
            JFrame votingFrame = new JFrame("ElectBudz - Voting " + (currentVoter + 1));
            votingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            votingFrame.setSize(500, 600);
            votingFrame.setLayout(new GridLayout(0, 1, 10, 10));

            JLabel promptLabel = new JLabel("Voter " + (currentVoter + 1) + ": Select your candidates", SwingConstants.CENTER);
            votingFrame.add(promptLabel);

            // Store the selected candidates for each position
            LinkedHashMap<String, LinkedHashMap<String, JCheckBox>> positionGroups = new LinkedHashMap<>();

            positionVoteCount.forEach((position, candidates) -> {
                JLabel positionLabel = new JLabel(position, SwingConstants.CENTER);
                positionLabel.setFont(new Font("Arial", Font.BOLD, 14));
                votingFrame.add(positionLabel);

                LinkedHashMap<String, JCheckBox> checkBoxes = new LinkedHashMap<>();

                // If the position is Mayor or Vice Mayor, use a ButtonGroup
                ButtonGroup group = (position.equals("Mayor") || position.equals("Vice Mayor")) ? new ButtonGroup() : null;

                candidates.forEach((candidate, votes) -> {
                    JCheckBox checkBox = new JCheckBox(candidate);

                    // For Mayor and Vice Mayor, handle exclusive selection and focus navigation
                    if (group != null) {
                        group.add(checkBox);
                        checkBox.addItemListener(e -> {
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                checkBox.requestFocus(); // Navigate to the selected checkbox
                            }
                        });
                    }

                    checkBoxes.put(candidate, checkBox);
                    votingFrame.add(checkBox);
                });

                positionGroups.put(position, checkBoxes);
            });

            JButton submitButton = new JButton("Submit Votes");
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
                        case "Mayor" ->
                            1;
                        case "Vice Mayor" ->
                            1;
                        case "Member, Sangguniang Panlungsod/Bayan (Councilor)" ->
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
                    if (selectedCandidates.isEmpty()) {
                        // Increment empty votes for this position
                        LinkedHashMap<String, Integer> candidates = positionVoteCount.get(position);
                        candidates.put("N/A", candidates.getOrDefault("N/A", 0) + 1);
                    } else {
                        votes.put(position, selectedCandidates);
                    }
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
            votingFrame.add(submitButton);
            votingFrame.setLocationRelativeTo(null);
            votingFrame.setVisible(true);
        } else {
            showResultsScreen();
        }
    }

    private static void showResultsScreen() {
        JFrame resultsFrame = new JFrame("ElectBudz - Election Results");
        resultsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultsFrame.setSize(700, 600);
        resultsFrame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Election Results (Sorted by Votes)", SwingConstants.CENTER);
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

            int totalVotesForPosition = candidates.values().stream().mapToInt(Integer::intValue).sum();

            candidates.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort by votes (descending)
                    .forEach(entry -> {
                        String candidate = entry.getKey();
                        int votes = entry.getValue();

                        if (candidate.equals("Empty Votes")) {
                            candidate = "Empty Votes (No selection)";
                        }

                        String percentage = totalVotesForPosition > 0
                                ? String.format("%.2f%%", (votes * 100.0) / totalVotesForPosition)
                                : "0.00%";

                        JPanel candidatePanel = new JPanel(new BorderLayout());
                        candidatePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                        JLabel candidateLabel = new JLabel(candidate + ": " + votes + " votes (" + percentage + ")");
                        JProgressBar progressBar = new JProgressBar(0, totalVotesForPosition);
                        progressBar.setValue(votes);
                        progressBar.setStringPainted(true);

                        candidatePanel.add(candidateLabel, BorderLayout.WEST);
                        candidatePanel.add(progressBar, BorderLayout.CENTER);
                        resultsPanel.add(candidatePanel);
                    });

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
    }

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
        viewResultsButton.addActionListener(e -> showResultsScreen());
        adminFrame.add(viewResultsButton);

        // Button to start a new election
        JButton newElectionButton = new JButton("Start New Election");
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
        exitButton.addActionListener(e -> adminFrame.dispose());
        adminFrame.add(exitButton);

        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

// Method to reset election data
    private static void resetElectionData() {
        positionVoteCount.clear();
        currentVoter = 0;
        totalVoters = 0;
    }

}
