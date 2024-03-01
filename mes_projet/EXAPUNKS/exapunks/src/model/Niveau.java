package model;
import java.util.ArrayList;

public class Niveau{

    ArrayList<Instruction> solution;
    ArrayList<Fichier> fichiers;
    ArrayList<Robot> robots;


    public Niveau() {
        
    }



    public boolean est_solution_valide(ArrayList<Instruction> userSolution){
        if (!(getSolution().size() == userSolution.size())) {
            return false;
        }
        for (int i = 0 ; i < getSolution().size() ; i++) {
            if (!getSolution().get(i).equals(userSolution.get(i))) {
                return false;
            }
        }

        return true;
    }


    public ArrayList<Instruction> getSolution() {
        ArrayList<Instruction> result = new ArrayList<>(solution);
        return result;
    }

    public ArrayList<Robot> getListRobots() {
        ArrayList<Robot> result = new ArrayList<>(robots);
        return result;
    }

    public ArrayList<Fichier> getListFichiers() {
        ArrayList<Fichier> result = new ArrayList<>(fichiers);
        return result;
    }


}
