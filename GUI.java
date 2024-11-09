import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * A Class that models the GUI for the Energy game.
 *
 * @author Marcus Sosa
 */
public class GUI implements ActionListener {

    // Parts of the GUI
    private JLabel energyText;
    private JLabel incomeText;
    private BackgroundPanel panel;
    private double currentEnergyUsage;
    private double currentIncome;
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
        this.currentIncome = getIncome(electronics);

        //Creates the JFrame for the window.
        JFrame frame = new JFrame();

        //Creates button 1, adds action listener to make it function.
        button = new JButton("Increment Computer (0.2 energy usage, 0.08 income)");
        button.addActionListener(this);

        //Creates button 2, adds action listener to make it function.
        button2 = new JButton("Increment Washing Machine (1 energy usage, 0.42 income)");
        button2.addActionListener(this);

        //Creates button 3, adds action listener to make it function.
        button3 = new JButton("Increment Ceiling Fan (0.075 energy usage, 0.03 income)");
        button3.addActionListener(this);

        //Creates button 4, adds action listener to make it function.
        button4 = new JButton("Increment Air Conditioner (3.5 energy usage, 1.47 income)");
        button4.addActionListener(this);

        //Creates button 5, adds action listener to make it function.
        button5 = new JButton("Change Background");
        button5.addActionListener(this);

        //Creates label for energy being used and income gained from it.
        energyText = new JLabel("Energy currently being used : " + currentEnergyUsage);
        incomeText = new JLabel("Income gained : " + currentIncome);

        panel = new BackgroundPanel(image);
        panel.setBorder(BorderFactory.createEmptyBorder(200, 400, 200, 400));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(energyText);
        panel.add(incomeText);

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
        //closes reader and returns converted linked list.
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
        //initializes return variable.
        double energyUsage = 0.0;
        //runs for all entries in electronics array
        for (electronic electronic : electronics) {
            //Adds the energy used by the electronic read to energyUsage
            energyUsage += electronic.getEnergyUsage() * electronic.getAmount();
        }
        //Makes sure that there's 3 decimal places, whilst removing floating point errors
        energyUsage = Math.round(energyUsage * 100.000) / 100.000;
        return energyUsage;
    }

    /**
     * Calculates the total income generated from a list of electronic devices.
     *
     * @param electronics A linked list of electronic objects from which the income will be calculated.
     * @return The total income generated from all electronic devices in the list.
     */
    public static double getIncome(LinkedList<electronic> electronics) {
        //initializes return variable.
        double income = 0.0;
        //runs for all entries in electronics array
        for (electronic electronic : electronics) {
            //adds the income gained from the electronic read to income.
            income += electronic.getIncome() * electronic.getAmount();
        }
        //makes sure there are 3 decimal places while getting rid of floating point errors
        income = Math.round(income * 100.000) / 100.000;
        return income;
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
            //Tells user that background 1 is missing then closes program.
            JOptionPane.showMessageDialog(null,"Background Image 1 was not found, closing program.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        // Initializes the GUI, uses a method to convert to linked list and sends to gui.
        new GUI(convertToLinkedList(inFile), image);
    }

    /**
     * Processes events and does an action based off what event happened.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Checks which button was pressed.
        if (e.getSource() == button) {
            //Adds 1 to computer amount then re-calculates and re-displays energy being used and income being gained
            electronics.getFirst().setAmount(electronics.getFirst().getAmount() + 1);
            currentEnergyUsage = getEnergyUsed(electronics);
            energyText.setText("Energy currently being used : " + currentEnergyUsage);
            currentIncome = getIncome(electronics);
            incomeText.setText("Income currently being gained : " + currentIncome);
        } else if (e.getSource() == button2) {
            //Adds 1 to washing machine amount then re-calculates and re-displays energy being used and income being gained
            electronics.get(1).setAmount(electronics.get(1).getAmount() + 1);
            currentEnergyUsage = getEnergyUsed(electronics);
            energyText.setText("Energy currently being used : " + currentEnergyUsage);
            currentIncome = getIncome(electronics);
            incomeText.setText("Income currently being gained : " + currentIncome);
        } else if (e.getSource() == button3) {
            //Adds 1 to ceiling fan amount then re-calculates and re-displays energy being used and income being gained
            electronics.get(2).setAmount(electronics.get(2).getAmount() + 1);
            currentEnergyUsage = getEnergyUsed(electronics);
            energyText.setText("Energy currently being used : " + currentEnergyUsage);
            currentIncome = getIncome(electronics);
            incomeText.setText("Income currently being gained : " + currentIncome);
        } else if (e.getSource() == button4) {
            //Adds 1 to air conditioner amount then re-calculates and re-displays energy being used and income being gained
            electronics.get(3).setAmount(electronics.get(3).getAmount() + 1);
            currentEnergyUsage = getEnergyUsed(electronics);
            energyText.setText("Energy currently being used : " + currentEnergyUsage);
            currentIncome = getIncome(electronics);
            incomeText.setText("Income currently being gained : " + currentIncome);
        } else if (e.getSource() == button5) {
            //Changes background on button press.
            if (counter) {
                try {
                    //Reads background two and saves it to image.
                    image = ImageIO.read(new File("C:\\Users\\277Student\\Documents\\Marcus Sosa Project\\HACC-2024-Submission\\test2.png"));
                } catch (IOException ioe) {
                    //Tells user that background 2 is missing then closes program.
                    JOptionPane.showMessageDialog(null,"Background Image 2 was not found, closing program.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                //sets background to image
                panel.setImage(image);
                //switches counter for next time button is pressed
                counter = false;
            } else {
                try {
                    //reads background one and saves it to image.
                    image = ImageIO.read(new File("C:\\Users\\277Student\\Documents\\Marcus Sosa Project\\HACC-2024-Submission\\test.png"));
                } catch (IOException ioe) {
                    //Tells user that background 2 is missing then closes program.
                    JOptionPane.showMessageDialog(null,"Background Image 2 was not found, closing program.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                //sets background to image
                panel.setImage(image);
                //switches counter for next time button is pressed
                counter = true;
            }
        }
    }
}
