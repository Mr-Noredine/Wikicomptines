package model;


public class Robot {
    private int id; // Identifiant du robot
    private int positionX; // Position en X du robot sur la grille
    private int positionY; // Position en Y du robot sur la grille
    private Fichier fichierEnPossession;
    private Object[] casesMemoire; // Tableau pour les cases mémoire, en effet ici la case 0 represente X et l'autre represente T
    private  Object registreM ; // il est global entre les deux robots




    public Robot(int id, int positionX, int positionY) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.fichierEnPossession = null; // Initialise le fichier actuel à null
        

      
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
    public void deplacerRobotVersPorte(int porteNumero) {
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

    public int getPremierNombre() {

        String premierNombreStr = getFichierEnMain().retirerContenu(); // Utilise dequeue pour obtenir la première ligne
        int premierNombre = 0; // Initialisation à 0
    
        if (premierNombreStr != null && !premierNombreStr.isEmpty()) {
            try {
                premierNombre = Integer.parseInt(premierNombreStr.split("\\s+")[0]); // Convertit le premier mot de la ligne en entier
                // Remettre la ligne au début du fichier si nécessaire, dépend de la logique métier
            } catch (NumberFormatException e) {
                System.err.println("Erreur lors de la conversion du premier nombre : " + e.getMessage());
                // Gérer l'erreur ou initialiser premierNombre à une valeur par défaut
            }
        } else {
            System.out.println("Le fichier est vide ou la ligne est invalide.");
        }
    
        return premierNombre;
    }
   
    public void setRegistreM(Object o) {
        registreM = o;
    }

}
