package view;

import javax.swing.*;
import java.awt.*;

public class GrilleVue extends JPanel {
    private static final int ROWS = 5;
    private static final int COLS = 5;
    private static final int CELL_SIZE = 50;
    private RobotVue robot;

    public GrilleVue() {
        setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE * ROWS));
        robot = new RobotVue();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED); // Définir la couleur des cases de la grille

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE); // Remplir la case avec la couleur rouge
            }
        }

        g2d.setColor(Color.BLACK); // Définir la couleur du contour

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE); // Dessiner le contour de chaque case
            }
        }

        int x1 = 0;
        int y1 = ROWS * CELL_SIZE - 1;
        int x2 = COLS * CELL_SIZE - 1;
        int y2 = ROWS * CELL_SIZE - 1;
        g2d.drawLine(x1, y1, x2, y2);
        robot.draw(g2d); // Dessiner le robot
    }
}