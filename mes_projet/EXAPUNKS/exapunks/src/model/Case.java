package model;


public class Case {
    private int x;
    private int y;
    private TypeCase type;
    private boolean hasRobot; 
    private Fichier fichier; // Ajout de l'attribut pour stocker le fichier

    public Case(int x, int y, TypeCase type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public TypeCase getType() {
        return type;
    }

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }

    public Fichier getFichier() {
        return fichier;
    }
    
        public int getX() {
            return x;
        }
    
        public int getY() {
            return y;
        }
        public boolean hasRobot() {
            return hasRobot;
        }
    
        public void setHasRobot(boolean hasRobot) {
            this.hasRobot = hasRobot;
        }
    
    public  enum TypeCase {
        LIBRE, OCCUPEE, PORTE
    }
}
    

