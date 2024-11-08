import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A Class that models the GUI for the Energy game.
 *
 * @author Marcus Sosa
 * @version 1.0.0
 */
public class GUI implements ActionListener {

    // Parts of the GUI
    private JLabel label;
    private BackgroundPanel panel;
    private double currentEnergyUsage;
    private LinkedList<electronic> electronics;
    private JButton button;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private Image image;
    private boolean counter = false;

    /**
     * Constructs a GUI
     *
     * @param electronics A linked list that has the values of electronic objects stored inside.
     */
    public GUI(LinkedList<electronic> electronics, Image image) {
        //Initializes variables.
        this.electronics = electronics;
        this.currentEnergyUsage = getEnergyUsed(electronics);

        //Creates the JFrame for the window.
        JFrame frame = new JFrame();

        //Creates button 1, adds action listener to make it function.
        button = new JButton("Button");
        button.addActionListener(this);

        //Creates button 2, adds action listener to make it function.
        button2 = new JButton("Button2");
        button2.addActionListener(this);

        //Creates button 3, adds action listener to make it function.
        button3 = new JButton("Button3");
        button3.addActionListener(this);

        //Creates button 4, adds action listener to make it function.
        button4 = new JButton("Button4");
        button4.addActionListener(this);

        button5 = new JButton("Button5");
        button5.addActionListener(this);

        //Creates label for energy being used.
        label = new JLabel("Energy currently being used : " + currentEnergyUsage);

        panel = new BackgroundPanel(image);
        panel.setBorder(BorderFactory.createEmptyBorder(200, 400, 200, 400));
        panel.setLayout(new GridLayout(0,1));
        panel.add(button);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Energy app test");
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Converts a file in .csv format into a Linked Stack of electronic objects.
     *
     * @param inFile is the .csv file being read from.
     * @return a linked stack of electronic objects created from the read file.
     */
    public static LinkedList<electronic> convertToLinkedList(File inFile) {
        // Instantiates a Linked stack
        LinkedList<electronic> electronics = new LinkedList<>();
        // Initializes two readers to read off of the file.
        Scanner Heathcliff;
        Scanner Catherine = null;

        // Temporary variable to store lines when retrieving line by line.
        String line;
        // Storage variables
        String inputName;
        double convertedEnergyUsage = 0.0;
        double convertedIncome = 0.0;
        int convertedAmount = 0;
        // Temporary variables to store doubles and integers before converting.
        String inputEnergyUsage;
        String inputIncome;
        String inputAmount;

        // If the filepath is invalid/file doesn't exist.
        try {
            // Connects Catherine to the file.
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
            // Makes Heathcliff read off of the line read by Catherine
            Heathcliff = new Scanner(line);
            Heathcliff.useDelimiter(",");
            // Reads off the line and adds read values as variables.
            inputName = Heathcliff.next();
            inputEnergyUsage = Heathcliff.next();
            inputIncome = Heathcliff.next();
            inputAmount = Heathcliff.next();
            // attempts to convert Energy Usage into a double.
            // uses a try-catch just in-case it doesn't.
            try {
                convertedEnergyUsage = Double.parseDouble(inputEnergyUsage);
                // If successful, uses round() to prevent floating point errors.
                convertedEnergyUsage = Math.round(convertedEnergyUsage * 100.00) / 100.00;
            } catch (NumberFormatException nfe) {
                // tells user what failed to convert.
                System.out.printf("Error : %s could not be converted into a double.", inputEnergyUsage);
            }
            // attempts to convert income into a double.
            // uses a try-catch just in-case it doesn't.
            try {
                convertedIncome = Double.parseDouble(inputIncome);
                // If successful, uses round() to prevent floating point errors.
                convertedIncome = Math.round(convertedIncome * 100.00) / 100.00;
            } catch (NumberFormatException nfe) {
                // tells user what failed to convert.
                System.out.printf("Error : %s could not be converted into a double.", inputIncome);
            }
            // attempts to convert income into a double.
            // uses a try-catch just in-case it doesn't.
            try {
                convertedAmount = Integer.parseInt(inputAmount);
            } catch (NumberFormatException nfe) {
                // tells user what failed to convert.
                System.out.printf("Error : %s could not be converted into a int.", inputAmount);
            }
            // Adds electronic to the linked list.
            electronics.add(new electronic(inputName, convertedEnergyUsage, convertedIncome, convertedAmount));
        }
        Catherine.close();
        return electronics;
    }

    /**
     * Calculates energy consumed from a linked stack of electronics.
     *
     * @param electronics is the linked stack.
     * @return is the energy being used by all objects in the stack.
     */
    public static double getEnergyUsed(LinkedList<electronic> electronics) {
        double energyUsage = 0.0;
        for (electronic electronic : electronics) {
            energyUsage += electronic.getEnergyUsage() * electronic.getAmount();
        }
        return energyUsage;
    }
    /**
     * Processes events and does an action based off what event happened.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            electronics.getFirst().setAmount(electronics.getFirst().getAmount()+1);
            currentEnergyUsage = getEnergyUsed(electronics);
            label.setText("Energy currently being used : " + currentEnergyUsage);
        } else if (e.getSource() == button2) {
            electronics.get(1).setAmount(electronics.get(1).getAmount()+1);
            currentEnergyUsage = getEnergyUsed(electronics);
            label.setText("Energy currently being used : " + currentEnergyUsage);
        } else if (e.getSource() == button3) {
            electronics.get(2).setAmount(electronics.get(2).getAmount()+1);
            currentEnergyUsage = getEnergyUsed(electronics);
            label.setText("Energy currently being used : " + currentEnergyUsage);
        } else if (e.getSource() == button4) {
            electronics.get(3).setAmount(electronics.get(3).getAmount()+1);
            currentEnergyUsage = getEnergyUsed(electronics);
            label.setText("Energy currently being used : " + currentEnergyUsage);
        } else if (e.getSource() == button5) {
            if (counter) {
                try {
                    image = ImageIO.read(new File("C:\\Users\\277Student\\Documents\\Marcus Sosa Project\\HACC-2024-Submission\\test2.png"));
                } catch (IOException ioe) {
                    System.out.println("Unable to find file");
                }
                panel.setImage(image);
                counter = false;
            } else {
                try {
                    image = ImageIO.read(new File("C:\\Users\\277Student\\Documents\\Marcus Sosa Project\\HACC-2024-Submission\\test.png"));
                } catch (IOException ioe) {
                    System.out.println("Unable to find file");
                }
                panel.setImage(image);
                counter = true;
            }
        }
    }
    //Launches the GUI.
    public static void main(String[] args) {
        // Ensures only 1 program argument, reminds user to use a .csv fle for it.
        if (args.length != 1) {
            System.out.printf("Error : 1 program argument expected, %d given instead.", args.length);
            System.out.println("Please give a .csv file for the program argument.\nClosing program...");
            System.exit(1);
        }
        // Creates a file object based off file provided from program argument.
        File inFile = new File(args[0]);
        //Creates an image file object for the background.
        Image image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\277Student\\Documents\\Marcus Sosa Project\\HACC-2024-Submission\\test.png"));
        } catch (IOException ioe) {
            System.out.println("Unable to find file");
        }
        // Initializes the GUI, uses a method to convert to linked list and sends to gui.
        new GUI(convertToLinkedList(inFile), image);
    }
}
