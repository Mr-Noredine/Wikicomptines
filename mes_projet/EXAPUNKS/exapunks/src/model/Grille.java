package model;

import java.util.ArrayList;

import model.Case.TypeCase;

public class Grille {
    private int[][] territoires;
    private Case[][] cases;
    private ArrayList<Robot>  robots;
    private ArrayList<Fichier> fichiers;
    
    public Grille(ArrayList<Robot> robots, ArrayList<Fichier> fichiers) {
        territoires = new int[][] {
            {0, 2, 1, 2, 0},
            {0, 5, 1, 3, 5},
            {1, 0, 1, 1, 4},
            {3, 0, 0, 0, 1},
            {0, 0, 1, 0, 4},
        };
        cases = new Case[5][5];
        initTerritoires();
        this.robots = new ArrayList<>(robots);
        this.fichiers = new ArrayList<>(fichiers);
    }

    private void initTerritoires() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int territoire = getTerritoire(i, j);
                TypeCase type;
                if (territoire == 0) {
                    type = TypeCase.LIBRE;
                } else if (territoire == 1) {
                    type = TypeCase.OCCUPEE;
                } else {
                    type = TypeCase.PORTE;
                }
                cases[i][j] = new Case(i, j, type);
            }
        }
    }
    public void ajouterFichier(Fichier fichier, int posX, int posY) {
        fichier.setPosition(posX, posY); 
        fichiers.add(fichier);
        cases[posX][posY].setFichier(fichier); 
    }
    
// Méthode pour récupérer un fichier par son ID
public Fichier getFichierParId(int id) {
    for (Fichier fichier : fichiers) {
        if (fichier.getId() == id) {
            return fichier;
        }
    }
    return null;
}


    public boolean contientFichier(int x, int y) {
        return cases[x][y].getFichier() != null;
    }

    public Fichier getFichier(int x, int y) {
        return cases[x][y].getFichier();
    }

    public void retirerFichier(Fichier fichier) {
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[i].length; j++) {
                if (cases[i][j].getFichier() == fichier) {
                    cases[i][j].setFichier(null);
                    return; 
                }
            }
        }
    }
    public void retirerFichier(int fichierId) {
        Fichier fichier = getFichierParId(fichierId);
        if (fichier != null) {
            retirerFichier(fichier);
        } else {
            System.out.println("Le fichier avec l'ID " + fichierId + " n'existe pas dans la grille.");
        }
    }
    
    // Méthode pour associer un fichier à un robot
    public void associerFichierRobot(Fichier fichier, Robot robot) {
        if (!fichierEstTransporte(fichier) && !robot.aFichier()) {
            robot.setFichierEnMain(fichier);
            retirerFichier(fichier);
        } else {
            System.out.println("Impossible d'associer le fichier au robot.");
        }
    }
   

    // Méthode pour récupérer la valeur d'un territoire à une position donnée dans une grille spécifique
    public int getTerritoire( int x, int y) {
      
            return territoires[x][y];
    }

    

    // Méthode pour vérifier si une position dans la grille est une zone accessible
    public boolean estZoneAccessible(int x, int y) {
        return cases[x][y].getType() == TypeCase.LIBRE;
    }
    
    public boolean estZoneOccupee(int x, int y) {
        return cases[x][y].getType() == TypeCase.OCCUPEE;
    }
    
    public boolean estPorteNumerotee(int x, int y) {
        TypeCase type = cases[x][y].getType();
        return type == TypeCase.PORTE ;
    }
   //Méthode pour déplacer un fichier d'une position à une autre dans la grille
    public void deplacerFichier(int origX, int origY, int destX, int destY) {
        if (contientFichier(origX, origY) && estZoneAccessible(destX, destY)) {
            Fichier fichier = getFichier(origX, origY);
            cases[origX][origY].setFichier(null);
            cases[destX][destY].setFichier(fichier);
            fichier.setPosition(destX, destY);
        } else {
            System.out.println("Déplacement de fichier impossible.");
        }
    }

   

    // Méthode pour déposer un fichier sur une case de la grille
    public void deposerFichierSurCase(Fichier fichier, int posX, int posY) {
        if (!contientFichier(posX, posY) && estZoneAccessible(posX, posY)) {
            cases[posX][posY].setFichier(fichier);
            fichier.setPosition(posX, posY);
        } else {
            System.out.println("Impossible de déposer le fichier sur cette case.");
        }
    }

    // Méthode pour vérifier si un fichier est en cours de transport par un robot
    public boolean fichierEstTransporte(Fichier fichier) {
        for (Robot robot : robots) {
            if (robot.aFichier() && robot.getFichierEnMain() == fichier) {
                return true;
            }
        }
        return false;
    }
}