package model;

import java.util.ArrayList;

import model.Case.TypeCase;

public class Grille {
    private int[][] territoires;
    private Case[][] cases;
    private ArrayList<Robot> robots;
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
    ////////////ajout d'un fichier dans la grille/////////////////
    public void ajouterFichier(Fichier fichier, int posX, int posY) {
        fichier.setPosition(posX, posY); 
        fichiers.add(fichier);
        cases[posX][posY].setFichier(fichier); 
    }
    
    public ArrayList<Robot> getListeRobots() {
        ArrayList<Robot> result = new ArrayList<>(robots);
        return result;
    }

    public ArrayList<Fichier> getListFichiers() {
        ArrayList<Fichier> result = new ArrayList<>(fichiers);
        return result;
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

public Robot getRobotId(int id) {
    for(Robot r : getListeRobots()) {
        if (r.getId() == id) {
            return r;
        }
    }
    return null;
}

////////verifier si la case contient un fichier/////////
    public boolean contientFichier(int x, int y) {
        return cases[x][y].getFichier() != null;
    }

    /////////recuperer un  fichier depuis une case////////
    public Fichier getFichier(int x, int y) {
        return cases[x][y].getFichier();
    }

    /////////supprimer un fichier+///////////
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
    //////////:supprimer un fichier par son id///////////
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

    public void ajouterRobot(Robot r) {
        robots.add(r);
    }

    public void ajouterFichier(Fichier f) {
        fichiers.add(f);
    }

    /************************************************************************************************* */

     
    ////////////////////LINK //////////////////////////
    public void traiterCommandeLink(int id, String direction) {
        Robot r = getRobotId(id);
        int newX = r.getPositionX();
        int newY = r.getPositionY(); 
        // Calculer les nouvelles coordonnées en fonction de la direction
        switch (direction.toLowerCase()) {
            case "up":
                newX -= 1;
                break;
            case "down":
                newX += 1;
                break;
            case "right":
                newY += 1;
                break;
            case "left":
                newY -= 1;
                break;
            default:
                System.out.println("Direction invalide.");
                return; 
        }
    
        // Vérifier si le déplacement est possible
        if (this.estZoneAccessible(newX, newY)) {
            System.out.println("Le robot a passé par la porte " + getTerritoire(r.getPositionX(), r.getPositionY()) + ".");
            r.moveTo(newX, newY); // Mettre à jour la position du robot
        } else if (estPorteNumerotee(newX, newY)) {
            // Si la nouvelle case est une porte numérotée, déplacez le robot en conséquence
            int porteNumero = getTerritoire(newX, newY);
            r.deplacerRobotVersPorte(porteNumero);
        } else {
            System.out.println("Le robot ne peut pas passer dans cette direction à cause d'un obstacle.");
        }
    }
    
    ///////////////////////GRAB///////////////
    public void GRAB(int id, int fichierId) {
        Robot r = getRobotId(id);
        if (r.getPositionX() != r.getFichierEnMain().getPosX() || r.getPositionY() != r.getFichierEnMain().getPosY()) {
            System.out.println("Le robot doit être sur la même case que le fichier pour le saisir.");
            return;
        }
    
        if (r.getFichierEnMain() == null) {
            r.setFichierEnMain(getFichierParId(fichierId));
            if (r.getFichierEnMain() == null) {
                System.out.println("Le fichier avec l'identifiant " + fichierId + " n'existe pas.");
                return;
            }
            
            retirerFichier(r.getFichierEnMain());
            System.out.println("Le fichier avec l'identifiant " + fichierId + " a été saisi par le robot.");
        } else {
            System.out.println("Le robot détient déjà un fichier.");
        }
    }

    ///////////////la commande DROP/////////
    public void DROP(int id) {
        Robot r = getRobotId(id);
        // Vérifier si le robot est sur une case valide pour déposer le fichier
        if (!estZoneAccessible(r.getPositionX(), r.getPositionY())|| estPorteNumerotee(r.getPositionX(), r.getPositionY())) {
            System.out.println("Le robot ne peut pas déposer de fichier sur cette case.");
            return;
        }
    
        if (r.getFichierEnMain() != null) {
            ajouterFichier(r.getFichierEnMain(), r.getPositionX(), r.getPositionY());
            r.setFichierEnMain(null); // Réinitialiser pour indiquer que le robot ne détient plus de fichier
            System.out.println("Le fichier a été déposé sur la case (" + r.getPositionX() + ", " + r.getPositionY() + ").");
        } else {
            System.out.println("Le robot ne détient aucun fichier à déposer.");
        }
    }

  
    
    //////////// add /////////////////// 
    public void add(int id, String caseMemoireSource, String caseMemoireDestination) {
        Robot r = getRobotId(id);
        int indexSource = convertirLettreEnIndice(caseMemoireSource);
        int indexDestination = convertirLettreEnIndice(caseMemoireDestination);
    
        if (r.getFichierEnMain() == null) {
            System.out.println("Aucun fichier en possession.");
            return;
        }
    
        int premierNombre = r.getPremierNombre();
    
        if (indexSource == -1 || indexDestination == -1) {
            System.out.println("Indices de case mémoire invalides.");
            return;
        }
    
        int valeurMemoire = (Integer) r.getCaseMemoire(indexSource);
        int resultat = premierNombre + valeurMemoire;
    
        r.setCaseMemoire(indexDestination, resultat);
    
        System.out.println("Résultat de l'addition: " + resultat);
    }
    
    // Méthode pour convertir une lettre en indice numérique correspondant
    private int convertirLettreEnIndice(String lettre) {
        lettre = lettre.toUpperCase();
        switch (lettre) {
            case "X":
                return 0;
            case "T":
                return 1;
            default:
                return -1; 
        }
    }

  ////////////////////////////SUBI/////////////////////////////
public void sub(int id, String caseMemoireSource, String caseMemoireDestination) {
    Robot r = getRobotId(id);
    if (r.getFichierEnMain() != null) {

        int indexSource = convertirLettreEnIndice(caseMemoireSource);
        int indexDestination = convertirLettreEnIndice(caseMemoireDestination);

        if (indexSource == -1 || indexDestination == -1) {
            System.out.println("Indices de case mémoire invalides.");
            return;
        }

        int chiffreFichier = r.getPremierNombre();
        int valeurMemoire = (Integer) r.getCaseMemoire(indexSource);
        int resultat = valeurMemoire - chiffreFichier;

        r.setCaseMemoire(indexDestination, resultat);

        System.out.println("Résultat de la soustraction: " + resultat);
    } else {
        System.out.println("Aucun fichier en possession.");
    }
}


////////////////////////MULTI///////////////////
public void mult(int id, String caseMemoireSource, String caseMemoireDestination) {
    Robot r = getRobotId(id);
    if (r.getFichierEnMain() != null) {
        int indexSource = convertirLettreEnIndice(caseMemoireSource);
        int indexDestination = convertirLettreEnIndice(caseMemoireDestination);

        if (indexSource == -1 || indexDestination == -1) {
            System.out.println("Indices de case mémoire invalides.");
            return;
        }

        int chiffreFichier = r.getPremierNombre();
        int valeurMemoire = (Integer) r.getCaseMemoire(indexSource);
        int resultat = valeurMemoire * chiffreFichier;

        r.setCaseMemoire(indexDestination, resultat);

        System.out.println("Résultat de la multiplication: " + resultat);
    } else {
        System.out.println("Aucun fichier en possession.");
    }
}




// Méthode pour traiter la commande TEST EOF
public void traiterCommandeTestEOF(int id) {
    Robot r = getRobotId(id);
    if (r.getFichierEnMain() != null) {
        // Utiliser estVide pour vérifier si le contenu du fichier a été entièrement lu
        if (r.getFichierEnMain().estVide()) {
            System.out.println("Fin de fichier atteinte.");
        } else {
            System.out.println("Encore des données à lire.");
        }
    } else {
        System.out.println("Aucun fichier en possession.");
    }
}


////////////////////////COPY//////////////////////
// Méthode pour copier le contenu d'une case mémoire ou du registre M dans une autre case mémoire ou dans le fichier
public void copy(int id, Object source, Object destination) {
    Robot r = getRobotId(id);
    try {
        int valeur;

        if (source instanceof Integer) {
            // La source est une case mémoire
            int sourceMemoire = (int) source;
            valeur = (Integer) r.getCaseMemoire(sourceMemoire);
        } else if (source instanceof Object && source.equals("M")) {
            // La source est le registre M
            valeur = (Integer) r.getregistreM();
        } else {
            System.out.println("Source invalide.");
            return;
        }

        if (destination instanceof Integer) {
            // La destination est une case mémoire
            int destinationMemoire = (int) destination;
            r.setCaseMemoire(destinationMemoire, valeur);
            System.out.println("Valeur copiée dans la case mémoire " + destinationMemoire + ": " + valeur);
        } else if (destination instanceof Object && destination.equals("M")) {
            // La destination est le registre M
            r.setRegistreM(valeur);
            System.out.println("Valeur copiée dans le registre M: " + valeur);
        } else if (destination instanceof Fichier) {
            // La destination est un fichier
            Fichier fichierDestination = (Fichier) destination;
            fichierDestination.ajouterContenu(String.valueOf(valeur));
            System.out.println("Valeur copiée dans le fichier ID: " + fichierDestination.getId() + ": " + valeur);
        } else {
            System.out.println("Destination invalide.");
        }
    } catch (Exception e) {
        System.out.println("Erreur lors de la copie : " + e.getMessage());
    }
}

}