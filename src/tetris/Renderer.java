package tetris;

import javax.swing.JPanel;
import java.awt.*;

public class Renderer extends JPanel {

    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tetris.tetris.render((Graphics2D) g);
    }
}