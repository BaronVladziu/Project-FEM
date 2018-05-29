package application;

import javax.swing.*;
import java.awt.*;

public class ConsolePanel extends JPanel {

    private JTextArea _textArea = new JTextArea();

    public ConsolePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        _textArea.setPreferredSize(new Dimension(width - 20, height - 20));
        _textArea.setEditable(false);
        add(_textArea);
    }

    public void display(String text) {
        _textArea.append(text + "\n");
    }

    public void clear() {
        _textArea.setText("");
    }

}