package model;
import java.util.ArrayList;

public class Niveau2 extends Niveau {

    private ArrayList<Instruction> solution;
    private ArrayList<Robot> robotsLancement;
    private ArrayList<Fichier> fichiersLancement;
    private Grille grille;

    public Niveau2() {

        robotsLancement = new ArrayList<>();
        fichiersLancement = new ArrayList<>();
        solution = new ArrayList<>();

        ajouterSolution();

        InitialiserFichierLancement();
        InitialiserRobotLancement();

        grille = new Grille(robotsLancement, fichiersLancement);
    }


    public Grille getGrille() {
        return grille;
    }

    public void ajouterSolution() {
        solution.add(new Instruction("LINK", "800", null, null));
        // a continuer ....
    }

    public void InitialiserFichierLancement() {
        fichiersLancement.add(new Fichier("/home/noureddine/" + "test.txt", 201 ,4, 0));
        fichiersLancement.add(new Fichier("/home/noureddine/" + "test.txt", 201 ,0, 3));


        // a continuer .... 
    }

    public void InitialiserRobotLancement() {
        robotsLancement.add(new Robot(1, 0, 0, null));  
     // a contiuer ....
        
    }


    public ArrayList<Instruction> getSolution() {
        ArrayList<Instruction> result = new ArrayList<>(solution);
        return result;
    }

    public ArrayList<Robot> getListRobotsLancemet() {
        ArrayList<Robot> result = new ArrayList<>(robotsLancement);
        return result;
    }

    public ArrayList<Fichier> getListFichiersLancement() {
        ArrayList<Fichier> result = new ArrayList<>(fichiersLancement);
        return result;
    }


}
