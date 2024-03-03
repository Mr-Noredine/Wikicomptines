package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

import model.*;
import view.*;
public class GameController implements ActionListener {
    private GrilleVue grilleVue;
    private TextZone textZone;
    private Robot robot;
    private Grille grille;
    private RobotVue robotVue;
    private AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique();
    private Random random = new Random();

    public GameController(GrilleVue grilleVue, RobotVue robotVue, TextZone textZone, Robot robot, Grille grille) {
        this.grilleVue = grilleVue;
        this.robotVue = robotVue;
        this.textZone = textZone;
        this.robot = robot;
        this.grille = grille;

        textZone.getAvancerButton().addActionListener(this);
        textZone.getStopButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textZone.getAvancerButton()) {
            executeNextCommand();
        } else if (e.getSource() == textZone.getStopButton()) {
            // Ajoutez ici le code à exécuter lorsque le bouton "Stop" est cliqué
        }
    }

    private void executeNextCommand() {
        String code = textZone.getTextArea().getText();
        String[] lines = code.split("\n");
        for (String line : lines) {
            if (!line.trim().isEmpty()) { // Ignore les lignes vides
                Instruction instruction = analyseurSyntaxique.getCommande(line.trim());
                if (instruction != null) {
                    // Exécutez l'instruction ici selon votre logique
                    // Par exemple, vous pouvez appeler une méthode de votre modèle pour exécuter l'instruction
                    executeInstruction(robot,instruction);
                    // Attendez un peu entre chaque exécution (facultatif)
                    try {
                        Thread.sleep(1000); // Attendez 1 seconde entre chaque exécution
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
private void executeInstruction(Robot robot, Instruction instruction) {
        String commande = instruction.getMotCommande();
        String[] arguments = instruction.getArguments(); 
        
        switch (commande.toUpperCase()) {
            case "LINK":
                if (arguments.length > 0) {
                    robot.traiterCommandeLink(arguments[0]); 
                    robotVue.move(robot.getPositionX(), robot.getPositionY());
                }
                break;
            case "GRAB":
                robot.GRAB(0); 
                break;
            case "DROP":
                robot.DROP();
                break;
            case "ADD":
                if (arguments.length >= 2) {
                    String sourceIndex = arguments[0];
                    String destinationIndex = arguments[2];
                    robot.add(sourceIndex, destinationIndex);
                }
                break;
            case "SUB":
                if (arguments.length >= 2) {
                    String sourceIndex = arguments[0];
                    String destinationIndex = arguments[2];
                    robot.sub(sourceIndex, destinationIndex);
                }
                break;
            case "MULT":
                if (arguments.length >= 2) {
                    String sourceIndex = arguments[0];
                    String destinationIndex = arguments[2];
                    robot.mult(sourceIndex, destinationIndex);
                }
                break;
            /*case "JUMP":
                if (arguments.length > 0) {
                    robot.jump(arguments[0]);
                }
                break;
            case "FJUMP":
                if (arguments.length > 0) {
                    boolean condition = true; 
                    robot.fjump(arguments[0], condition);
                }
                break;*/
            case "TESTEOF":
                robot.traiterCommandeTestEOF();
                break;
            case "COPY":
                if (arguments.length >= 2) {
                    int idFichier = Integer.parseInt(arguments[0]);
                    int caseMemoire = Integer.parseInt(arguments[1]);
                    robot.copy(idFichier, caseMemoire);
                }
                break;
            default:
                System.out.println("Commande non reconnue : " + commande);
                break;
        }
    }

}
