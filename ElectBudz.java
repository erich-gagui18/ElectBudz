/**
TEAM JAVA RICE
 */

package com.mycompany.electbudz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ElectBudz {
    private static LinkedHashMap<String, Integer> voteCount = new LinkedHashMap<>();
    private static int totalVoters = 0;
    private static int currentVoter = 0;
    
    public static void main(String[] args) {
        // Initialize candidate list
        voteCount.put("Candidate 1", 0);
        voteCount.put("Candidate 2", 0);
        voteCount.put("Candidate 3", 0);

        showVoterCountInputScreen();
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

    // Calculate the total number of votes
    int totalVotes = voteCount.values().stream().mapToInt(Integer::intValue).sum();

    // Sort the candidates by votes in descending order
    voteCount.entrySet().stream()
        .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Descending order
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