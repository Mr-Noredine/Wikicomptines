package model;
import java.util.ArrayList;

public class Niveau1 extends Niveau{

 
    public Niveau1() {
        super();

        solution = new ArrayList<Instruction>();
        ajouterSolution();

        fichiers = new ArrayList<>();
        InitialiserFichier();
        
        robots = new ArrayList<>();
        InitialiserRobot();
    }







    public void ajouterSolution() {
        solution.add(new Instruction("LINK", "800", null, null));
        // a continuer ....
    }

    public void InitialiserFichier() {
        fichiers.add(new Fichier(null, 201 ,4, 0));

        // a continuer .... 
    }

    public void InitialiserRobot() {
        robots.add(new Robot(1, 0, 0, null));  
     // a contiuer ....
        
    }



}
