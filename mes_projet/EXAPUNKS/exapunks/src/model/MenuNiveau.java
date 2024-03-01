package model;
import java.util.Scanner;

public class MenuNiveau {
    private Scanner scanner;

    public MenuNiveau() {
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenu() {
        System.out.println("Bienvenue dans le menu des niveaux !");
        System.out.println("Veuillez choisir un niveau :");
        System.out.println("1. Niveau 1");
        System.out.println("2. Niveau 2");
        System.out.println("3. Niveau 3");
        System.out.println("4. Quitter");

        int choix = scanner.nextInt();

        switch (choix) {
            case 1:
                Niveau1 niveau1 = new Niveau1();
                break;
            case 2:
                Niveau2 niveau2 = new Niveau2();
                break;
            case 3:
                Niveau3 niveau3 = new Niveau3();
                break;
            case 4:
                System.out.println("Merci d'avoir joué !");
                System.exit(0);
                break;
            default:
                System.out.println("Choix invalide, veuillez réessayer.");
                afficherMenu();
                break;
        }
    }

    
    public static void main(String[] args) {
        MenuNiveau menu = new MenuNiveau();
        while (true) {
            menu.afficherMenu();
        }
    }
}