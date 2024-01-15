import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class StudentGradeCalculator extends JFrame implements ActionListener {
    private static final int SUBJECT_COUNT = 5;

    private JLabel headingLabel;
    private JLabel[] subjectLabels;
    private JTextField[] subjectTextFields;
    private JLabel resultLabel;
    private JButton calculateButton;

    public StudentGradeCalculator() {
        setTitle("Student Grade Calculator");
        setSize(1000, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();

        // Add components to the panel
        JPanel panel = createPanel();
        add(panel);
    }

    private void initializeComponents() {
        headingLabel = new JLabel("Student Grade Calculator");
        headingLabel.setForeground(new Color(102, 0, 0));
        headingLabel.setFont(new Font("Cambria", Font.BOLD, 30));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        subjectLabels = new JLabel[SUBJECT_COUNT];
        subjectTextFields = new JTextField[SUBJECT_COUNT];

        for (int i = 0; i < SUBJECT_COUNT; i++) {
            subjectLabels[i] = new JLabel("Subject " + (i + 1) + " Marks  :",JLabel.CENTER);
            subjectTextFields[i] = new JTextField(10);
        }

        resultLabel = new JLabel("Total Marks: 0   Average Percentage: 0%   Grade: F");
        resultLabel.setFont(new Font("Cambria", Font.BOLD, 15));
        resultLabel.setForeground(Color.BLUE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add heading with a gap
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(headingLabel, gbc);

        // Add subject components with adjusted insets
        gbc.gridwidth = 1;
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            panel.add(subjectLabels[i], gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            panel.add(subjectTextFields[i], gbc);
        }

        // Add result label with a gap
        gbc.gridx = 0;
        gbc.gridy = SUBJECT_COUNT + 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(resultLabel, gbc);

        // Add calculate button with a gap
        gbc.gridx = 0;
        gbc.gridy = SUBJECT_COUNT + 2;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            calculateTotalAndAverage();
        }
    }

    private void calculateTotalAndAverage() {
        int totalMarks = 0;
        int totalSubjects = 0;

        for (int i = 0; i < SUBJECT_COUNT; i++) {
            try {
                int marks = Integer.parseInt(subjectTextFields[i].getText());

                // Validate marks range (0-100)
                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(this, "Invalid marks for subject " + (i + 1) + ". Marks should be between 0 and 100.");
                    return;
                }

                totalMarks += marks;
                totalSubjects++;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for subject " + (i + 1));
                return;
            }
        }

        if (totalSubjects == 0) {
            JOptionPane.showMessageDialog(this, "Please enter marks for at least one subject.");
            return;
        }

        double averagePercentage = (double) totalMarks / (totalSubjects * 100);

        updateResultLabel(totalMarks, averagePercentage);
    }

    private void updateResultLabel(int totalMarks, double averagePercentage) {
        String grade = calculateGrade(averagePercentage);
        resultLabel.setText("Total Marks: " + totalMarks +
                "   Average Percentage: " + (averagePercentage * 100) +
                "%   Grade: " + grade);
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 0.9) return "A+";
        if (percentage >= 0.8) return "A";
        if (percentage >= 0.7) return "B";
        if (percentage >= 0.6) return "C";
        if (percentage >= 0.5) return "D";
        return "F";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentGradeCalculator calculator = new StudentGradeCalculator();
            calculator.setVisible(true);
        });
    }
}
