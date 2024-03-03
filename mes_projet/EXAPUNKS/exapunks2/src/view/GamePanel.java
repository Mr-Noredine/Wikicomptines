package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.*;
import model.*;

public class GamePanel extends JPanel{
private BufferedImage robotImage;
private BufferedImage fileImage;
private Niveau1 niveau1;



final int originalTileize = 25;
final int scale = 3;

final int tileSize = originalTileize * scale;   // taille de la celleule
final int maxGrilleCol = 5;
final int maxGrilleRow = 5;
final int panelWidth = tileSize * maxGrilleCol;
final int panelHeight = tileSize * maxGrilleRow;

public GamePanel(Niveau1 niveau1) {

    this.setPreferredSize(new Dimension(panelWidth, panelHeight));
    this.setBackground(Color.DARK_GRAY);
    this.setDoubleBuffered(true);
    this.niveau1 = niveau1;

    try {
            robotImage = ImageIO.read(new File("src/assets/images/robot.png")); // Remplacez par le chemin réel
        } catch (IOException e) {
            e.printStackTrace();
        }
    

    try {
        fileImage = ImageIO.read(new File("src/assets/images/fileImage.png")); // Remplacez par le chemin réel
    } catch (IOException e) {
        e.printStackTrace();
    }

}

protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.RED); // Définir la couleur des cases de la grille

    for (int row = 0; row < maxGrilleRow; row++) {
        for (int col = 0; col < maxGrilleCol; col++) {
            int x = col * tileSize;
            int y = row * tileSize;
            g2d.fillRect(x, y, tileSize, tileSize); // Remplir la case avec la couleur rouge
        }
    }

    g2d.setColor(Color.BLACK); // Définir la couleur du contour

    for (int row = 0; row < maxGrilleRow; row++) {
        for (int col = 0; col < maxGrilleCol; col++) {
            int x = col * tileSize;
            int y = row * tileSize;
            g2d.drawRect(x, y, tileSize, tileSize); // Dessiner le contour de chaque case
        }
    }
    for (Robot robot : niveau1.getGrille().getListeRobots()) {
        int x = robot.getPositionX();
        int y = robot.getPositionY();
        g.drawImage(robotImage,  x * tileSize, y * tileSize + tileSize/4 , tileSize/2, tileSize/2, this);
    }

    for (Fichier fichier : niveau1.getGrille().getListFichiers()) {
        int x = fichier.getPosX();
        int y = fichier.getPosY();
        g.drawImage(fileImage, x * tileSize + tileSize/2, y * tileSize + tileSize/4, tileSize/2, tileSize/2, this);
    }
}
}