import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

// Premium themes (needs flatlaf + flatlaf-intellij-themes jars)
import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;

public class SimpleCalculator {

    public static void main(String[] args) {
    	
    	System.setProperty("flatlaf.uiScale", "1.25");
    	
        SwingUtilities.invokeLater(() -> {

            // ----- Premium Look (FlatLaf Carbon) -----
            try {
                FlatCarbonIJTheme.setup();

                // Optional: small modern polish
                UIManager.put("Button.arc", 14);
                UIManager.put("Component.arc", 14);
                UIManager.put("TextComponent.arc", 14);
                UIManager.put("Component.focusWidth", 1);
                UIManager.put("ScrollBar.width", 12);

                // Optional: nicer font
                UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
            } catch (Exception e) {
                // fallback if jars not added
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) {}
            }

            Calculator c = new Calculator();
            c.setVisible(true);
        });
    }
}

class Calculator extends JFrame {
	
	private final Dimension designSize = new Dimension(460, 360); // your original design size
	private final Map<Component, Rectangle> designBounds = new HashMap<>();
	private final Map<Component, Font> designFonts = new HashMap<>();
	
	private void captureDesignBounds(Container container) {
	    for (Component c : container.getComponents()) {
	        designBounds.put(c, c.getBounds());
	        if (c.getFont() != null) designFonts.put(c, c.getFont());
	        if (c instanceof Container) {
	            captureDesignBounds((Container) c);
	        }
	    }
	}

	private void scaleUI() {
	    double sx = getWidth() / (double) designSize.width;
	    double sy = getHeight() / (double) designSize.height;

	    // Use min scale for fonts so text looks consistent
	    float fontScale = (float) Math.min(sx, sy);

	    for (Map.Entry<Component, Rectangle> entry : designBounds.entrySet()) {
	        Component c = entry.getKey();
	        Rectangle r = entry.getValue();

	        int nx = (int) Math.round(r.x * sx);
	        int ny = (int) Math.round(r.y * sy);
	        int nw = (int) Math.round(r.width * sx);
	        int nh = (int) Math.round(r.height * sy);

	        c.setBounds(nx, ny, nw, nh);

	        Font baseFont = designFonts.get(c);
	        if (baseFont != null) {
	            c.setFont(baseFont.deriveFont(baseFont.getSize2D() * fontScale));
	        }
	    }

	    revalidate();
	    repaint();
	}

    JLabel l1, l2;
    JTextField t1, t2;
    JButton b1, b2, b3, b4, clearButton;

    Calculator() {
        setTitle("Calculator (Java Swing/JFC)");
        setSize(650, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Important for dark theme: use theme background (don’t force light color)
        Color bg = UIManager.getColor("Panel.background");
        if (bg != null) getContentPane().setBackground(bg);

        // Heading
        l1 = new JLabel("Calculator (Java Swing/JFC)", SwingConstants.CENTER);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        l1.setForeground(new Color(140, 200, 255)); // premium accent for dark theme
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

        t1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        t2.setFont(new Font("Segoe UI", Font.PLAIN, 15));

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
        l2.setFont(new Font("Segoe UI", Font.BOLD, 18));
        l2.setForeground(new Color(120, 230, 170));
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
        
     // Capture original bounds based on your designed (460x360) UI
        captureDesignBounds(getContentPane());

        // Scale once for the current window size (important if you set a bigger size)
        scaleUI();

        // If you want user to be able to resize and still keep proportional UI:
        setResizable(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleUI();
            }
        });

        // Addition
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    double no1 = readNumber(t1);
                    double no2 = readNumber(t2);
                    l2.setText("Addition: " + (no1 + no2));
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
                    l2.setText("Subtraction: " + (no1 - no2));
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
                    l2.setText("Multiplication: " + (no1 * no2));
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

                    l2.setText("Division: " + (no1 / no2));
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
