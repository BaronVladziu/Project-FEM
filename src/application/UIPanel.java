package application;

import javax.swing.*;
import java.awt.*;

class UIPanel extends JComponent {

    private static final int _NAME_LENGTH = 130;
    private static final int _INPUT_LENGTH = 30;
    private static final int _BAR_HEIGHT = 25;
    private static final int _GAP_LENGTH = 10;

    private final SettingInputPanel _metrePanel = new SettingInputPanel("Metrum:", "4/4");
    private final SettingInputPanel _lengthPanel = new SettingInputPanel("Liczba taktów:", "4");
    private final SettingInputPanel _startSoundPanel = new SettingInputPanel("Dżwięk początkowy:", "a'");
    private final SettingInputPanel _endSoundPanel = new SettingInputPanel("Dżwięk końcowy:", "a'");
    private final SettingInputPanel _lowestSoundPanel = new SettingInputPanel("Dżwięk najniższy:", "a");
    private final SettingInputPanel _highestSoundPanel = new SettingInputPanel("Dżwięk najwyższy:", "d'''");
    private final SettingInputPanel _tempoPanel = new SettingInputPanel("Tempo [bpm]:", "90");
    private final JButton _generateButton = new JButton("Generuj");
    private final JButton _playButton = new JButton("Graj");

    UIPanel() {
        JComponent _comp1 = new JPanel();
        _comp1.setPreferredSize(new Dimension(_NAME_LENGTH + _GAP_LENGTH + _INPUT_LENGTH,
                6 * (_BAR_HEIGHT + _GAP_LENGTH)));
        _comp1.setLayout(new GridLayout(6, 0));
        _comp1.add(_metrePanel);
        _comp1.add(_lengthPanel);
        _comp1.add(_startSoundPanel);
        _comp1.add(_endSoundPanel);
        _comp1.add(_lowestSoundPanel);
        _comp1.add(_tempoPanel);
        _comp1.setVisible(true);

        _generateButton.setPreferredSize(new Dimension(_comp1.getPreferredSize().width,
                (900 - _comp1.getPreferredSize().height - 2*_GAP_LENGTH)/2));
        _playButton.setPreferredSize(new Dimension(_comp1.getPreferredSize().width,
                (900 - _comp1.getPreferredSize().height - 2*_GAP_LENGTH)/2));
        setViewToDone();
        JComponent _comp3 = new JPanel();
        _comp3.setPreferredSize(new Dimension(_comp1.getPreferredSize().width,
                900));
        _comp3.setLayout(new FlowLayout());
        _comp3.add(_comp1);
        _comp3.add(_generateButton);
        _comp3.add(_playButton);
        _comp3.setVisible(true);

        setPreferredSize(new Dimension( _comp3.getPreferredSize().width + _GAP_LENGTH,
                900));
        setLayout(new FlowLayout());
        add(_comp3);
        setVisible(true);
    }

    void setViewToWorkInProgress() {
        _generateButton.setEnabled(false);
        _generateButton.setText("Pracuję...");
        _generateButton.setBackground(Color.GRAY);
        _playButton.setEnabled(false);
        _playButton.setBackground(Color.GRAY);
    }

    void setViewToDone() {
        _generateButton.setBackground(Color.WHITE);
        _generateButton.setText("Generuj");
        _generateButton.setEnabled(true);
        _playButton.setBackground(Color.WHITE);
        _playButton.setEnabled(true);
    }

}