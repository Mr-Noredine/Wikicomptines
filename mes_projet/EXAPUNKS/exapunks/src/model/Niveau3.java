package model;
import java.util.ArrayList;

public class Niveau3 extends Niveau{

    public Niveau3() {
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
        // a contiuer ....
    }

    public void InitialiserFichier() {
        fichiers.add(new Fichier(null, 201 ,4, 0));
        // a contiuer ....

    }

    public void InitialiserRobot() {
        robots.add(new Robot(1, 0, 0, null));  
        robots.add(new Robot(2, 0, 1, null));  
        // a contiuer ....
    }

}
