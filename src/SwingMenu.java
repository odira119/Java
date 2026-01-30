import javax.swing.*;

public class SwingMenu{
    public static void main(String[]args){
        SwingMenu s=new SwingMenu();
    }

    public SwingMenu(){
        JFrame frame = new JFrame("Creating a  JMenuBar,JMenu,JMenuItem and separator componnents");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JSeparator());
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(new JSeparator());
        JMenuItem fileItem1 = new JMenuItem("New");
        JMenuItem fileItem2 = new JMenuItem("Open");
        JMenuItem fileItem3 = new JMenuItem("Close");
        fileMenu.add(fileItem1);
        fileMenu.add(fileItem2);
        fileMenu.add(fileItem3);
        fileMenu.add(new JSeparator());
        JMenuItem fileItem4 = new JMenuItem("Save");
        fileMenu.add(fileItem4);

        JMenuItem editItem1 = new JMenuItem("Cut");
        JMenuItem editItem2 = new JMenuItem("Copy");
        editMenu.add(editItem1);
        editMenu.add(editItem2);
        editMenu.add(new JSeparator());
        JMenuItem editItem3 = new JMenuItem("Paste");
        editMenu.add(editItem3);
        JMenuItem editItem4 = new JMenuItem("Insert");
        editMenu.add(editItem4);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        frame.setJMenuBar(menuBar);
        frame.setSize(400,400);
        frame.setVisible(true);
        
    }
}