package application;

import drawing.DrawingSheet;

import javax.swing.*;

public class Window extends JFrame {

    public Window() {
        super("Project-FEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new DrawingSheet();
        add(panel);
        pack();

        setVisible(true);
    }

}
