package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class RobotVue extends JPanel {
    private static final int CELL_SIZE = 45;

    private BufferedImage robotImage;
    private int x; // Position x du robot en termes de cases de la grille
    private int y; // Position y du robot en termes de cases de la grille

    public RobotVue() {
        // Charger l'image du robot
       
            try {
                robotImage = ImageIO.read(new File("src/assets/images/robot.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
       
        
        // Positionner le robot dans la première case de la grille
        x = 0;
        y = 0;
    }

    public void move(int newX, int newY) {
        // Mettre à jour la position du robot
        x = newX;
        y = newY;
        BufferedImage image = new BufferedImage(CELL_SIZE, CELL_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
    
       // Appeler la méthode draw pour dessiner le robot à sa nouvelle position
        draw(g2d);
        System.out.println("Nouvelles coordonnées du robot : x = " + x + ", y = " + y);
    }

    public void draw(Graphics2D g2d) {
        // Dessiner le robot à sa position actuelle
        int robotX = x * CELL_SIZE;
        int robotY = y * CELL_SIZE;
        g2d.drawImage(robotImage, robotX, robotY, CELL_SIZE, CELL_SIZE, null);
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    // Dessiner le robot à sa position actuelle
    int robotX = x * CELL_SIZE;
    int robotY = y * CELL_SIZE;
    if (robotImage != null) {
        g2d.drawImage(robotImage, robotX, robotY, CELL_SIZE, CELL_SIZE, this);
    }
}
}