package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Solver implements ActionListener {

    private Matrix _matrix;

    public Solver() {}

    @Override
    public void actionPerformed(ActionEvent event) {
        _matrix = new Matrix(4, 3);
        _matrix.randomize();
        _matrix.print();
        System.out.println(_matrix.isEasilySolvable());
        try {
            _matrix.solve();
        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }
        _matrix.print();
    }

}