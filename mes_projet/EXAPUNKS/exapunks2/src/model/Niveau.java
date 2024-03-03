package model;
import java.util.ArrayList;

public class Niveau{

    private ArrayList<Instruction> solution;
    private ArrayList<Robot> robotsLancement;
    private ArrayList<Fichier> fichiersLancement;


    public Niveau() {
        
        robotsLancement = new ArrayList<>();
        fichiersLancement = new ArrayList<>();
        solution = new ArrayList<>();
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

    public ArrayList<Robot> getListRobotsLancemet() {
        ArrayList<Robot> result = new ArrayList<>(robotsLancement);
        return result;
    }

    public ArrayList<Fichier> getListFichiersLancement() {
        ArrayList<Fichier> result = new ArrayList<>(fichiersLancement);
        return result;
    }


}
