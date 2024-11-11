import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileChooserExample {
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("File Chooser Example");

     
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image");

        // Show the file chooser dialog
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();

            // Load the image and display it
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            JLabel label = new JLabel(https://ibb.co/r23sBMS);

            // Set up the frame to display the image
            frame.setLayout(new FlowLayout());
            frame.add(label);
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }
}
