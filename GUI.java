
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A Class that models the GUI for the Energy game.
 *
 * @author Marcus Sosa
 * @version 1.0.0
 *
 */
public class GUI implements ActionListener {

    // Parts of the GUI
    private JLabel label;
    private JFrame frame;
    private JPanel panel;

    /**
     * Constructs a GUI
     *
     */
    public GUI(LinkedList<electronic> electronics) {

        frame = new JFrame();

        JButton button = new JButton("Button");
        button.addActionListener(this);
        label = new JLabel("Number of times clicked: 0");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(400, 400, 400, 400));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Energy app test");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Ensures only 1 program arguement, reminds user to use a .csv fle for it.
        if (args.length != 1) {
            System.out.printf("Error : 1 program argument expected, %d given instead.", args.length);
            System.out.println("Please give a .csv file for the program argument.\nClosing program...");
            System.exit(1);
        }
        // Instantiates a Linkedstack
        LinkedList<electronic> electronics = new LinkedList<>();
        // Creates a file object based off file provided from program arguement.
        File inFile = new File(args[0]);
        // Initializes two readers to read off program argument.
        Scanner Heathcliff = null;
        Scanner Catherine = null;

        // Temporary variable to store lines when retrieving line by line.
        String line = "";
        // Storage variables
        String inputName = "";
        double convertedEnergyUsage = 0.0;
        double convertedIncome = 0.0;
        int convertedAmount = 0;
        // Temporary variables to store doubles and integers before converting.
        String inputEnergyUsage = "";
        String inputIncome = "";
        String inputAmount = "";

        // If the filepath is invalid/file doesn't exist.
        try {
            // Connects Catherine to the program arguement file.
            Catherine = new Scanner(inFile);
        } catch (FileNotFoundException fnf) {
            System.out.printf("Error: Cannot open file: %s%n", inFile.getAbsolutePath());
            System.exit(1);
        }
        //Skips first line due to it being useless
        Catherine.nextLine();
        
        // Runs while file still has a next line.
        while (Catherine.hasNext()) {
            // reads next line.
            line = Catherine.nextLine();
            // Makes Heathcliff read off line read by Catherine
            Heathcliff = new Scanner(line);
            Heathcliff.useDelimiter(",");
            // Reads off the line and adds read values as variables.
            inputName = Heathcliff.next();
            inputEnergyUsage = Heathcliff.next();
            inputIncome = Heathcliff.next();
            inputAmount = Heathcliff.next();
            // attempts to convert Energy Usage into a double.
            // uses a try-catch just incase it doesn't.
            try {
                convertedEnergyUsage = Double.parseDouble(inputEnergyUsage);
                // If successful, uses round() to prevent floating point errors.
                convertedEnergyUsage = Math.round(convertedEnergyUsage * 100.00) / 100.00;
            } catch (NumberFormatException nfe) {
                // tells user what failed to convert.
                System.out.printf("Error : %s could not be converted into a double.", inputEnergyUsage);
            }
            // attempts to convert income into a double.
            // uses a try-catch just incase it doesn't.
            try {
                convertedIncome = Double.parseDouble(inputIncome);
                // If successful, uses round() to prevent floating point errors.
                convertedIncome = Math.round(convertedIncome * 100.00) / 100.00;
            } catch (NumberFormatException nfe) {
                // tells user what failed to convert.
                System.out.printf("Error : %s could not be converted into a double.", inputIncome);
            }
            // attempts to convert income into a double.
            // uses a try-catch just incase it doesn't.
            try {
                convertedAmount = Integer.parseInt(inputAmount);
            } catch (NumberFormatException nfe) {
                // tells user what failed to convert.
                System.out.printf("Error : %s could not be converted into a int.", inputAmount);
            }
            // Adds electronic to the linkedlist.
            electronics.add(new electronic(inputName, convertedEnergyUsage, convertedIncome, convertedAmount));
        }
        Catherine.close();
        // Initializes the GUI, adds the list that was imported via loops earlier.
        new GUI (electronics);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
