package com.mycompany.electbudz;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

public class ElectBudz {
    private static final String ADMIN_PASSWORD = "Admin123"; // Admin password
    private static LinkedHashMap<String, Integer> voteCount = new LinkedHashMap<>();
    private static int totalVoters = 0;
    private static int currentVoter = 0;

    public static void main(String[] args) {
        showAdminVoterSelectionScreen();
    }

    private static void showAdminVoterSelectionScreen() {
        JFrame selectionFrame = new JFrame("ElectBudz - Admin/Voter");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setSize(400, 200);
        selectionFrame.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel promptLabel = new JLabel("Welcome to ElectBudz! Select your role:", SwingConstants.CENTER);
        selectionFrame.add(promptLabel);

        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(e -> {
            selectionFrame.dispose();
            promptAdminPassword();
        });

        JButton voterButton = new JButton("Voter");
        voterButton.addActionListener(e -> {
            if (voteCount.isEmpty()) {
                JOptionPane.showMessageDialog(selectionFrame, "No candidates available. Please ask the admin to add candidates.");
            } else {
                selectionFrame.dispose();
                showVoterCountInputScreen();
            }
        });

        selectionFrame.add(adminButton);
        selectionFrame.add(voterButton);

        selectionFrame.setLocationRelativeTo(null);
        selectionFrame.setVisible(true);
    }

    private static void promptAdminPassword() {
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(
                null,
                passwordField,
                "Enter Admin Password",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            if (password.equals(ADMIN_PASSWORD)) {
                showAdminPage();
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect password. Returning to selection screen.");
                showAdminVoterSelectionScreen();
            }
        } else {
            showAdminVoterSelectionScreen();
        }
    }

    private static void showAdminPage() {
        JFrame adminFrame = new JFrame("ElectBudz - Admin Panel");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(400, 300);
        adminFrame.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel promptLabel = new JLabel("Admin Panel: Manage Candidates", SwingConstants.CENTER);
        adminFrame.add(promptLabel);

        JTextField candidateField = new JTextField();
        candidateField.setHorizontalAlignment(SwingConstants.CENTER);
        adminFrame.add(candidateField);

        JButton addCandidateButton = new JButton("Add Candidate");
        addCandidateButton.addActionListener(e -> {
            String candidateName = candidateField.getText().trim();
            if (candidateName.isEmpty()) {
                JOptionPane.showMessageDialog(adminFrame, "Candidate name cannot be empty.");
            } else if (voteCount.containsKey(candidateName)) {
                JOptionPane.showMessageDialog(adminFrame, "Candidate already exists.");
            } else {
                voteCount.put(candidateName, 0);
                JOptionPane.showMessageDialog(adminFrame, "Candidate added successfully.");
                candidateField.setText("");
            }
        });

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> {
            if (voteCount.isEmpty()) {
                JOptionPane.showMessageDialog(adminFrame, "Please add at least one candidate before proceeding.");
            } else {
                adminFrame.dispose();
                showAdminVoterSelectionScreen();
            }
        });

        adminFrame.add(addCandidateButton);
        adminFrame.add(doneButton);

        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

    private static void showVoterCountInputScreen() {
        JFrame inputFrame = new JFrame("ElectBudz - Voter Counter");
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setSize(400, 200);
        inputFrame.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel promptLabel = new JLabel("Enter the total number of voters:", SwingConstants.CENTER);
        JTextField voterCountField = new JTextField();
        voterCountField.setHorizontalAlignment(SwingConstants.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                totalVoters = Integer.parseInt(voterCountField.getText().trim());
                if (totalVoters <= 0) {
                    JOptionPane.showMessageDialog(inputFrame, "Please enter a valid positive number.");
                } else {
                    inputFrame.dispose();
                    showVotingScreen();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(inputFrame, "Invalid input. Please enter a number.");
            }
        });

        inputFrame.add(promptLabel);
        inputFrame.add(voterCountField);
        inputFrame.add(submitButton);

        inputFrame.setLocationRelativeTo(null);
        inputFrame.setVisible(true);
    }

    private static void showVotingScreen() {
        if (currentVoter < totalVoters) {
            JFrame votingFrame = new JFrame("ElectBudz - Voter " + (currentVoter + 1));
            votingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            votingFrame.setSize(400, 400);
            votingFrame.setLayout(new GridLayout(voteCount.size() + 1, 1, 10, 10));

            JLabel promptLabel = new JLabel("Voter " + (currentVoter + 1) + ": Select your candidate", SwingConstants.CENTER);
            votingFrame.add(promptLabel);

            for (String candidate : voteCount.keySet()) {
                JButton voteButton = new JButton("Vote for " + candidate);
                voteButton.setBackground(Color.LIGHT_GRAY);
                voteButton.setFocusPainted(false);
                voteButton.addActionListener(e -> castVote(candidate, votingFrame));
                votingFrame.add(voteButton);
            }

            votingFrame.setLocationRelativeTo(null);
            votingFrame.setVisible(true);
        } else {
            showResultsScreen();
        }
    }

    private static void castVote(String candidate, JFrame frame) {
        voteCount.put(candidate, voteCount.get(candidate) + 1);
        currentVoter++;
        frame.dispose();
        showVotingScreen();
    }

    private static void showResultsScreen() {
        JFrame resultsFrame = new JFrame("ElectBudz - Results");
        resultsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultsFrame.setSize(500, 400);
        resultsFrame.setLayout(new GridLayout(voteCount.size() + 2, 1, 10, 10));

        JLabel resultLabel = new JLabel("Election Results (Highest to Lowest):", SwingConstants.CENTER);
        resultsFrame.add(resultLabel);

        int totalVotes = voteCount.values().stream().mapToInt(Integer::intValue).sum();

        voteCount.entrySet().stream()
            .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
            .forEach(entry -> {
                String candidate = entry.getKey();
                int votes = entry.getValue();
                String percentage = totalVotes > 0 ? String.format("%.2f%%", (votes * 100.0) / totalVotes) : "0.00%";

                JProgressBar progressBar = new JProgressBar(0, totalVotes);
                progressBar.setValue(votes);
                progressBar.setString(candidate + ": " + votes + " votes (" + percentage + ")");
                progressBar.setStringPainted(true);
                resultsFrame.add(progressBar);
            });

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> resultsFrame.dispose());
        resultsFrame.add(closeButton);

        resultsFrame.setLocationRelativeTo(null);
        resultsFrame.setVisible(true);
    }
}
