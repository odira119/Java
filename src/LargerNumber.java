import javax.swing.*;

public class LargerNumber{
    public static void main(String[]args){
        JFrame frame=new JFrame("Larder Number");
        JTextField Num1 = new JTextField(10);
        JTextField Num2 = new JTextField(10);
        JButton compare = new JButton("compare"); 
        JLabel result = new JLabel("Result will be displayed here");

        compare.addActionListener(e -> {
            int a = Integer.parseInt(Num1.getText());
            int b = Integer.parseInt(Num2.getText());
            if(a > b){
                result.setText(a + " is larger than " + b);
            } else if(b > a){
                result.setText(b + " is larger than " + a);
            } else{
                result.setText("Both numbers are equal");
            }
        });
        JPanel panel = new JPanel();
        panel.add(Num1);
        panel.add(Num2);
        panel.add(compare);
        panel.add(result);

        frame.add(panel);
        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}