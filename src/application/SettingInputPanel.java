package application;

import javax.swing.*;
import java.awt.*;

class SettingInputPanel extends JComponent {

    private final JFormattedTextField _inputField = new JFormattedTextField();

    SettingInputPanel(String name, String defaultValue) {
        setLayout(new FlowLayout());
        JFormattedTextField _nameField = new JFormattedTextField();
        _nameField.setPreferredSize(new Dimension(130, 25));
        _nameField.setEditable(false);
        _nameField.setBackground(Color.LIGHT_GRAY);
        _nameField.setText(name);
        add(_nameField);
        _inputField.setPreferredSize(new Dimension(30, 25));
        _inputField.setText(defaultValue);
        add(_inputField);
        setVisible(true);
    }

    String getInput() {
        return _inputField.getText();
    }

}