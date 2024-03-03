package main;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private int instructionPointer; // Pointeur d'instruction
    private List<String> instructions; // Liste des instructions du programme
    private int programLength; // Longueur du programme

    public Program(List<String> instructions) {
        this.instructions = new ArrayList<>(instructions);
        this.programLength = instructions.size();
        this.instructionPointer = 0; // Initialiser le pointeur d'instruction à 0
    }

    // Méthode pour traiter l'instruction JUMP
    public void jump(int targetInstruction) {
        instructionPointer = targetInstruction;
    }

    // Méthode pour traiter l'instruction FJUMP
    public void fJump(boolean condition, int targetInstruction) {
        if (condition) {
            instructionPointer = targetInstruction;
        } else {
            instructionPointer++; // Avancer au prochain instruction si la condition n'est pas remplie
        }
    }

    // Méthode pour exécuter le programme
    public void run() {
        while (instructionPointer >= 0 && instructionPointer < programLength) {
            String instruction = instructions.get(instructionPointer); // Obtenir l'instruction à l'emplacement actuel
            // Exécuter l'instruction (traiter l'instruction ici)
            System.out.println("Executing instruction: " + instruction);
            // Incrémenter l'indice d'instruction ou le modifier en fonction des instructions JUMP ou FJUMP
            instructionPointer++;
        }
    }

    public static void main(String[] args) {
        List<String> instructions = new ArrayList<>();
        instructions.add("ADD X Y"); // Ajouter des instructions au programme (exemple)
        instructions.add("FJUMP true 2"); // Exemple d'instruction FJUMP

        Program program = new Program(instructions);
        program.run(); // Exécuter le programme
    }
}
