package controller;

import model.*;
import view.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Création des listes de robots et de fichiers
        ArrayList<Robot> robots = new ArrayList<>();
        ArrayList<Fichier> fichiers = new ArrayList<>();
        
        // Création de la grille avec les listes de robots et de fichiers
        Grille grille = new Grille(robots, fichiers);
        
        // Création du robot
        Robot robot = new Robot(1, 0, 0, grille);
        
        // Ajout du robot à la liste de robots
        robots.add(robot);
        
        // Création de la vue pour la grille
        GrilleVue grilleVue = new GrilleVue();
        RobotVue robotVue=new RobotVue();
        // Créer une instance de GamePanel
        GamePanel gamePanel = new GamePanel();
        
        // Création de la vue pour la zone de texte
        TextZone textZone = new TextZone();

        // Création du contrôleur de jeu
        GameController gameController = new GameController(grilleVue,robotVue, textZone, robot, grille);
    }
}
