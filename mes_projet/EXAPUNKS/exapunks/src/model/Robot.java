package model;


public class Robot {
    private int id; // Identifiant du robot
    private int positionX; // Position en X du robot sur la grille
    private int positionY; // Position en Y du robot sur la grille
    private Fichier fichierEnPossession;
    private Object[] casesMemoire; // Tableau pour les cases mémoire, en effet ici la case 0 represente X et l'autre represente M
    private  Object registreM ;
    private Grille grille; 



    public Robot(int id, int positionX, int positionY,Grille grille) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.fichierEnPossession = null; // Initialise le fichier actuel à null
        this.grille=grille;

      
        casesMemoire = new Object[2]; //supposons que l'indice 0 c'est la memoire X et l'autre c'est la memoire T
        registreM = null;
    }
   
    public Object getCaseMemoire(int indice) {
        return casesMemoire[indice];
    }


    public void setCaseMemoire(int indice, Object valeur) {
        casesMemoire[indice] = valeur;
    }


    public boolean aFichier() {
        return fichierEnPossession!= null;
    }


    public void moveTo(int newX, int newY) {
        this.positionX = newX;
        this.positionY = newY;
    }

    public Fichier getFichierEnMain() {
        return fichierEnPossession;
    }

    public void setFichierEnMain(Fichier fichierEnMain) {
        this.fichierEnPossession = fichierEnMain;
    }
    
    public int getId() {
        return id;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
    public Object getregistreM (){
        return registreM;
    }
    public Object getRegistreX(){
        return casesMemoire[0];
    }
    public Object getRegistreT(){
        return casesMemoire[1];
    }

    // Méthode pour déplacer le robot vers une porte numérotée
    private void deplacerRobotVersPorte(int porteNumero) {
        System.out.println("Le robot se déplace vers la porte numéro " + porteNumero + ".");
    
        switch (porteNumero) {
            case 2:
                
                moveTo(0,3 );
                break;
            case 3:
              moveTo(3, 0); 
                break;
            case 4:
            
              moveTo(4, 4); 
                break;
            case 5:
                
                moveTo(1, 4); 
                break;
            default:
                break;
        }
    }
    
    
    ////////////////////LINK //////////////////////////
    public void traiterCommandeLink( String direction) {
        int newX = getPositionX();
        int newY = getPositionY(); 
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
        if (grille.estZoneAccessible(newX, newY)) {
            System.out.println("Le robot a passé par la porte " + grille.getTerritoire(getPositionX(), getPositionY()) + ".");
            moveTo(newX, newY); // Mettre à jour la position du robot
        } else if (grille.estPorteNumerotee(newX, newY)) {
            // Si la nouvelle case est une porte numérotée, déplacez le robot en conséquence
            int porteNumero = grille.getTerritoire(newX, newY);
            deplacerRobotVersPorte( porteNumero);
        } else {
            System.out.println("Le robot ne peut pas passer dans cette direction à cause d'un obstacle.");
        }
    }
    
    ///////////////////////GRAB///////////////
    public void GRAB(int fichierId) {
        if (this.positionX != fichierEnPossession.getPosX() || this.positionY != fichierEnPossession.getPosY()) {
            System.out.println("Le robot doit être sur la même case que le fichier pour le saisir.");
            return;
        }
    
        if (this.fichierEnPossession == null) {
            this.fichierEnPossession = grille.getFichierParId(fichierId);
            if (this.fichierEnPossession == null) {
                System.out.println("Le fichier avec l'identifiant " + fichierId + " n'existe pas.");
                return;
            }
            
            grille.retirerFichier(this.fichierEnPossession);
            System.out.println("Le fichier avec l'identifiant " + fichierId + " a été saisi par le robot.");
        } else {
            System.out.println("Le robot détient déjà un fichier.");
        }
    }

    ///////////////la commande DROP/////////
    public void DROP() {
        // Vérifier si le robot est sur une case valide pour déposer le fichier
        if (!grille.estZoneAccessible(this.positionX, this.positionY)) {
            System.out.println("Le robot ne peut pas déposer de fichier sur cette case.");
            return;
        }
    
        if (this.fichierEnPossession != null) {
            grille.ajouterFichier(this.fichierEnPossession, this.positionX, this.positionY);
            this.fichierEnPossession = null; // Réinitialiser pour indiquer que le robot ne détient plus de fichier
            System.out.println("Le fichier a été déposé sur la case (" + this.positionX + ", " + this.positionY + ").");
        } else {
            System.out.println("Le robot ne détient aucun fichier à déposer.");
        }
    }

    //////////// add /////////////////// 
    public void add(String caseMemoireSource, String caseMemoireDestination) {
        int indexSource = convertirLettreEnIndice(caseMemoireSource);
        int indexDestination = convertirLettreEnIndice(caseMemoireDestination);
    
        if (this.fichierEnPossession == null) {
            System.out.println("Aucun fichier en possession.");
            return;
        }
    
        int premierNombre = fichierEnPossession.getPremierNombre();
    
        if (indexSource == -1 || indexDestination == -1) {
            System.out.println("Indices de case mémoire invalides.");
            return;
        }
    
        int valeurMemoire = (Integer) casesMemoire[indexSource];
        int resultat = premierNombre + valeurMemoire;
    
        casesMemoire[indexDestination] = resultat;
    
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
public void sub(String caseMemoireSource, String caseMemoireDestination) {
    if (this.fichierEnPossession != null) {

        int indexSource = convertirLettreEnIndice(caseMemoireSource);
        int indexDestination = convertirLettreEnIndice(caseMemoireDestination);

        if (indexSource == -1 || indexDestination == -1) {
            System.out.println("Indices de case mémoire invalides.");
            return;
        }

        int chiffreFichier = fichierEnPossession.getPremierNombre();
        int valeurMemoire = (Integer) casesMemoire[indexSource];
        int resultat = valeurMemoire - chiffreFichier;

        casesMemoire[indexDestination] = resultat;

        System.out.println("Résultat de la soustraction: " + resultat);
    } else {
        System.out.println("Aucun fichier en possession.");
    }
}


////////////////////////MULTI///////////////////
public void mult(String caseMemoireSource, String caseMemoireDestination) {
    if (this.fichierEnPossession != null) {
        int indexSource = convertirLettreEnIndice(caseMemoireSource);
        int indexDestination = convertirLettreEnIndice(caseMemoireDestination);

        if (indexSource == -1 || indexDestination == -1) {
            System.out.println("Indices de case mémoire invalides.");
            return;
        }

        int chiffreFichier = fichierEnPossession.getPremierNombre();
        int valeurMemoire = (Integer) casesMemoire[indexSource];
        int resultat = valeurMemoire * chiffreFichier;

        casesMemoire[indexDestination] = resultat;

        System.out.println("Résultat de la multiplication: " + resultat);
    } else {
        System.out.println("Aucun fichier en possession.");
    }
}




// Méthode pour traiter la commande TEST EOF
public void traiterCommandeTestEOF() {
    if (this.fichierEnPossession != null) {
        // Utiliser estVide pour vérifier si le contenu du fichier a été entièrement lu
        if (fichierEnPossession.estVide()) {
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
public void copy(Object source, Object destination) {
    try {
        int valeur;

        if (source instanceof Integer) {
            // La source est une case mémoire
            int sourceMemoire = (int) source;
            valeur = (Integer) casesMemoire[sourceMemoire];
        } else if (source instanceof Object && source.equals("M")) {
            // La source est le registre M
            valeur = (Integer) registreM;
        } else {
            System.out.println("Source invalide.");
            return;
        }

        if (destination instanceof Integer) {
            // La destination est une case mémoire
            int destinationMemoire = (int) destination;
            casesMemoire[destinationMemoire] = valeur;
            System.out.println("Valeur copiée dans la case mémoire " + destinationMemoire + ": " + valeur);
        } else if (destination instanceof Object && destination.equals("M")) {
            // La destination est le registre M
            registreM = valeur;
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
