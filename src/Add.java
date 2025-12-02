import javax.swing.*;
import java.awt.event.*;

public class Add {
    public static void main(String[] args) {

        //Frame
        JFrame frame = new JFrame("Add Two Numbers");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Labels
        JLabel labelX = new JLabel("Input x:");
        labelX.setBounds(50, 30, 100, 25);
        frame.add(labelX);

        JLabel labelY = new JLabel("Input y:");
        labelY.setBounds(50, 70, 100, 25);
        frame.add(labelY);

        JLabel labelResult = new JLabel("x + y =");
        labelResult.setBounds(50, 150, 100, 25);
        frame.add(labelResult);

        // Text fields
        JTextField textX = new JTextField();  
        textX.setBounds(150, 30, 150, 25);
        frame.add(textX);

        JTextField textY = new JTextField();
        textY.setBounds(150, 70, 150, 25);
        frame.add(textY);

        JTextField resultField = new JTextField();
        resultField.setBounds(150, 150, 150, 25);
        resultField.setEditable(false);                                                                                                                                                                                                                                                                                            
        frame.add(resultField);

        // Button
        JButton addButton = new JButton("ADD");
        addButton.setBounds(150, 110, 100, 25);
        frame.add(addButton);

        // Button action
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = Integer.parseInt(textX.getText());
                int y = Integer.parseInt(textY.getText());
                int sum = x + y;
                resultField.setText(String.valueOf(sum));
        }
    });
       
        frame.setVisible(true);
    }
}
