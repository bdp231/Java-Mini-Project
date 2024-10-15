import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoanManagementSystem extends JFrame {
    private JTextField amountField, durationField, rateField, salaryField, eligibilityField, estimateField;
    private JButton estimateButton;

    public LoanManagementSystem() {
        setTitle("Loan Management System");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        //background color
        getContentPane().setBackground(new Color(28, 52, 106));

        //ui for taking inout from user
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(41, 64, 129));

        //section for loan amount
        JLabel amountLabel = new JLabel("Loan Amount:");
        amountLabel.setForeground(Color.WHITE);
        inputPanel.add(amountLabel);
        amountField = new JTextField(10);
        inputPanel.add(amountField);

        //section for annual salary
        JLabel salaryLabel = new JLabel("Annual Salary:");
        salaryLabel.setForeground(Color.WHITE);
        inputPanel.add(salaryLabel);
        salaryField = new JTextField(10);
        inputPanel.add(salaryField);

        //section for loan duration
        JLabel durationLabel = new JLabel("Duration (Years):");
        durationLabel.setForeground(Color.WHITE);
        inputPanel.add(durationLabel);
        durationField = new JTextField(10);
        inputPanel.add(durationField);

        //section for roi
        JLabel rateLabel = new JLabel("Interest Rate (%):");
        rateLabel.setForeground(Color.WHITE);
        inputPanel.add(rateLabel);
        rateField = new JTextField(10);
        inputPanel.add(rateField);

        
        add(inputPanel, BorderLayout.CENTER);

        //ui of buttons
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(5, 1, 10, 10));
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        outputPanel.setBackground(new Color(41, 64, 129));

        //eligibility button
        JButton eligibilityButton = new JButton("Check Eligibility");
        eligibilityButton.setBackground(new Color(100, 149, 237));
        eligibilityButton.setForeground(Color.WHITE);
        eligibilityButton.addActionListener(new Eligibility());
        outputPanel.add(eligibilityButton);

        
        eligibilityField = new JTextField(15);
        eligibilityField.setEditable(false);
        eligibilityField.setBackground(Color.WHITE);
        eligibilityField.setForeground(new Color(28, 52, 106));
        outputPanel.add(eligibilityField);

        //estimate loan button
        estimateButton = new JButton("Estimate Loan");
        estimateButton.setBackground(new Color(100, 149, 237));
        estimateButton.setForeground(Color.WHITE);
        estimateButton.setEnabled(false);  // Initially disabled
        estimateButton.addActionListener(new Estimate());
        outputPanel.add(estimateButton);

        
        estimateField = new JTextField(15);
        estimateField.setEditable(false);
        estimateField.setBackground(Color.WHITE);
        estimateField.setForeground(new Color(28, 52, 106));
        outputPanel.add(estimateField);

        //clear fields button
        JButton clearButton = new JButton("Clear Fields");
        clearButton.setBackground(new Color(220, 20, 60)); // Red Button
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> clearFields());
        outputPanel.add(clearButton);

        // Adding the output panel to the south of the frame
        add(outputPanel, BorderLayout.SOUTH);

        // Adding a footer label
        JLabel footerLabel = new JLabel("© 2024 Swarajya", JLabel.CENTER);
        footerLabel.setForeground(new Color(173, 216, 230));
        add(footerLabel, BorderLayout.NORTH);
    }

    private void clearFields() {
        amountField.setText("");
        salaryField.setText("");
        durationField.setText("");
        rateField.setText("");
        eligibilityField.setText("");
        estimateField.setText("");
        estimateButton.setEnabled(false);  // Disable estimate button when fields are cleared
    }

    // Eligibility
    private class Eligibility implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                double salary = Double.parseDouble(salaryField.getText());

                // Eligibility check: Loan amount should not exceed 5 times the annual salary
                if (amount <= salary * 5) {
                    eligibilityField.setText("Eligible for loan");
                    estimateButton.setEnabled(true);  // Enable estimate button if eligible
                } else {
                    eligibilityField.setText("Not eligible for loan");
                    estimateButton.setEnabled(false); // Disable estimate button if not eligible
                }
            } catch (NumberFormatException ex) {
                eligibilityField.setText("Invalid input");
                estimateButton.setEnabled(false); // Disable estimate button on invalid input
            }
        }
    }

    // Estimate
    private class Estimate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                int duration = Integer.parseInt(durationField.getText());
                double rate = Double.parseDouble(rateField.getText());

                double monthlyRate = rate / 100 / 12;
                int totalPayments = duration * 12;

                // EMI calculation
                double emi = (amount * monthlyRate * Math.pow(1 + monthlyRate, totalPayments)) /
                             (Math.pow(1 + monthlyRate, totalPayments) - 1);

                estimateField.setText(String.format("Estimated EMI: %.2f", emi));
            } catch (NumberFormatException ex) {
                estimateField.setText("Invalid input");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoanManagementSystem app = new LoanManagementSystem();
            app.setVisible(true);
        });
    }
}
