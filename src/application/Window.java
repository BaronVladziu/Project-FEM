package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class Window extends JFrame implements ActionListener {

    
    private final DrawPanel _picturePanel = new DrawPanel();
    private final UIPanel _uiPanel = new UIPanel();

    public Window() {
        super("Project-FEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        add(_picturePanel);
        add(_uiPanel);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        _uiPanel.setViewToWorkInProgress();
        System.out.println("Generating melody settings...");
        boolean ifNoExceptionHappened = true;

        if (ifNoExceptionHappened) {
            System.out.println("Melody settings generated");
            System.out.println("Generating melody...");
            System.out.println("Melody generated");
        }
        else {
            System.out.println("Generating melody settings failed!");
        }
        _uiPanel.setViewToDone();
    }

}