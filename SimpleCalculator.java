import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator {

public static void main(String[] args) {
        // Create the Swing window on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            Calculator c = new Calculator();
            c.setVisible(true);
        });
    }
}

class Calculator extends JFrame {

JLabel l1, l2;
    JTextField t1, t2;
    JButton b1, b2, b3, b4, clearButton;

Calculator() {
        setTitle("Calculator (inJFC)");
        setSize(460, 360);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

getContentPane().setBackground(new Color(240, 245, 250));

// Heading
        l1 = new JLabel("Calculator (inJFC)", SwingConstants.CENTER);
        l1.setFont(new Font("SansSerif", Font.BOLD, 26));
        l1.setForeground(new Color(35, 75, 130));
        l1.setBounds(20, 15, 405, 40);

// Input labels
        JLabel firstLabel = new JLabel("First number:");
        firstLabel.setBounds(45, 75, 130, 30);

JLabel secondLabel = new JLabel("Second number:");
        secondLabel.setBounds(45, 115, 130, 30);

// Input fields
        t1 = new JTextField();
        t2 = new JTextField();

t1.setBounds(180, 75, 210, 30);
        t2.setBounds(180, 115, 210, 30);

t1.setFont(new Font("SansSerif", Font.PLAIN, 16));
        t2.setFont(new Font("SansSerif", Font.PLAIN, 16));

// Operation buttons
        b1 = new JButton("Add");
        b2 = new JButton("Sub");
        b3 = new JButton("Mul");
        b4 = new JButton("Div");

b1.setBounds(45, 165, 80, 35);
        b2.setBounds(135, 165, 80, 35);
        b3.setBounds(225, 165, 80, 35);
        b4.setBounds(315, 165, 80, 35);

// Result
        l2 = new JLabel("Result: —", SwingConstants.CENTER);
        l2.setFont(new Font("SansSerif", Font.BOLD, 18));
        l2.setForeground(new Color(25, 100, 65));
        l2.setBounds(25, 215, 405, 35);

clearButton = new JButton("Clear");
        clearButton.setBounds(170, 265, 110, 30);

add(l1);
        add(firstLabel);
        add(secondLabel);
        add(t1);
        add(t2);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(l2);
        add(clearButton);

// Addition
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    double no1 = readNumber(t1);
                    double no2 = readNumber(t2);
                    l2.setText("Result: " + (no1 + no2));
                } catch (NumberFormatException ex) {
                    showInputError();
                }
            }
        });

// Subtraction
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    double no1 = readNumber(t1);
                    double no2 = readNumber(t2);
                    l2.setText("Result: " + (no1 - no2));
                } catch (NumberFormatException ex) {
                    showInputError();
                }
            }
        });

// Multiplication
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    double no1 = readNumber(t1);
                    double no2 = readNumber(t2);
                    l2.setText("Result: " + (no1 * no2));
                } catch (NumberFormatException ex) {
                    showInputError();
                }
            }
        });

// Division
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    double no1 = readNumber(t1);
                    double no2 = readNumber(t2);

if (no2 == 0) {
                        l2.setText("Result: —");
                        JOptionPane.showMessageDialog(
                            Calculator.this,
                            "Cannot divide by zero.",
                            "Calculation Error",
                            JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }

l2.setText("Result: " + (no1 / no2));
                } catch (NumberFormatException ex) {
                    showInputError();
                }
            }
        });

// Clear inputs and result
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                t1.setText("");
                t2.setText("");
                l2.setText("Result: —");
                t1.requestFocusInWindow();
            }
        });

// Pressing Enter performs addition.
        getRootPane().setDefaultButton(b1);
    }

// Read a decimal number and reject invalid/special values.
    private double readNumber(JTextField field) {
        double number = Double.parseDouble(field.getText().trim());

if (Double.isNaN(number) || Double.isInfinite(number)) {
            throw new NumberFormatException();
        }

return number;
    }

// Show the same friendly message for any invalid input.
    private void showInputError() {
        l2.setText("Result: —");

JOptionPane.showMessageDialog(
            this,
            "Please enter valid numbers in both fields.\nExample: 10 or 12.5",
            "Invalid Input",
            JOptionPane.WARNING_MESSAGE
        );
    }
}
