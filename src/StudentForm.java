import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;

public class StudentForm extends JFrame {

    public StudentForm() {
        setTitle("Students Information Form");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1, 5, 5)); 

        // Name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Name"));
        JTextField nameField = new JTextField(20);
        namePanel.add(nameField);
        add(namePanel);

         // Date of Birth
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dobPanel.add(new JLabel("Date of Birth (dd/mm/yyyy)"));
        JTextField dobField = new JTextField(10);
        dobPanel.add(dobField);
        add(dobPanel);

        // Year of study
        JPanel yearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        yearPanel.add(new JLabel("Year of Study"));
        JRadioButton year1 = new JRadioButton("1st Year");
        JRadioButton year2 = new JRadioButton("2nd Year");
        JRadioButton year3 = new JRadioButton("3rd Year");
        JRadioButton year4 = new JRadioButton("4th Year");
        ButtonGroup yearGroup = new ButtonGroup();
        yearGroup.add(year1);
        yearGroup.add(year2);
        yearGroup.add(year3);
        yearGroup.add(year4);
        yearPanel.add(year1);
        yearPanel.add(year2);
        yearPanel.add(year3);
        yearPanel.add(year4);
        add(yearPanel);

        // Course
        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coursePanel.add(new JLabel("Course"));
        String[] courses = {"BSc Computer Science", "BSc Information Technology", "BSc Library Management", "BSc Data Science"};
        JComboBox<String> courseBox = new JComboBox<>(courses);
        coursePanel.add(courseBox);
        add(coursePanel);

        // Number of Units
        JPanel unitsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        unitsPanel.add(new JLabel("Number of Units"));
        JSpinner unitSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        unitsPanel.add(unitSpinner);
        add(unitsPanel); 

        // Residential status
        JPanel residencePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        residencePanel.add(new JLabel("Residential Status"));
        String[] status = {"Resident", "Non-Resident", "International"};
        JComboBox<String> resCombo = new JComboBox<>(status);
        residencePanel.add(resCombo);
        add(residencePanel);

        // Gender
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(new JLabel("Gender"));
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderPanel.add(male);
        genderPanel.add(female);
        add(genderPanel);

        // Common units
        JPanel commonUnitsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        commonUnitsPanel.add(new JLabel("Common Units"));
        JCheckBox ucu100 = new JCheckBox("UCU 100");
        JCheckBox ucu111 = new JCheckBox("UCU 111");
        commonUnitsPanel.add(ucu100);
        commonUnitsPanel.add(ucu111);
        add(commonUnitsPanel);

        // Submit Button
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> {
            String name = nameField.getText();
            String year = "";
            if (year1.isSelected()) year = "1st Year";
            else if (year2.isSelected()) year = "2nd Year";
            else if (year3.isSelected()) year = "3rd Year";
            else if (year4.isSelected()) year = "4th Year";

            String course = (String) courseBox.getSelectedItem();
            int units = (int) unitSpinner.getValue();
            String residence = (String) resCombo.getSelectedItem();
            String gender = male.isSelected() ? "Male" : (female.isSelected() ? "Female" : "Not selected");

            String dobText = dobField.getText();
            String dobDisplay = dobText.isEmpty() ? "Not selected" : dobText;

            StringBuilder commonUnits = new StringBuilder();
            if (ucu100.isSelected()) commonUnits.append("UCU 100 ");
            if (ucu111.isSelected()) commonUnits.append("UCU 111 ");

            JOptionPane.showMessageDialog(this,
                    "Name: " + name +
                    "\nDate of Birth: " + dobDisplay +
                    "\nYear of Study: " + year +
                    "\nCourse: " + course +
                    "\nUnits Done: " + units +
                    "\nResidential Status: " + residence +
                    "\nGender: " + gender +
                    "\nCommon Units: " + (commonUnits.length() > 0 ? commonUnits.toString() : "None")
            );
        });
        add(submitBtn);

        setVisible(true);
    }

    public static void main(String args[]){
        SwingUtilities.invokeLater(StudentForm::new);
    }
}
