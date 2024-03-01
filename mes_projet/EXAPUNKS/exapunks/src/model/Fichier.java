package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Fichier {
    private int id;
    private int posX;
    private int posY;
    private Queue<String> contenu; 
    private int ligneCourante; 

    public Fichier(String cheminFichier,int id, int posX, int posY) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.contenu = new LinkedList<>();
        this.ligneCourante = 0; 
        chargerContenu(cheminFichier);
    }

    private void chargerContenu(String cheminFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                contenu.offer(ligne); // Ajoutez chaque ligne au contenu du fichier
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du fichier : " + e.getMessage());
        }
    }

    public void ajouterContenu(String data) {
        contenu.offer(data);
     }

    public String retirerContenu() {
        return contenu.poll(); // poll retire et retourne la tête de la queue, ou retourne null si la queue est vide
    }

    public String voirSommet() {
        return contenu.peek(); // peek retourne la tête de la queue sans la retirer
    }

    public boolean estVide() {
        return contenu.isEmpty();
    }


    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getId() {
        return id;
    }

    public Queue<String> getContenu() {
         return contenu;
    }

    public void setPosition(int newX, int newY) {
        this.posX = newX;
        this.posY = newY;
    }

    public int getPremierNombre() {
        try {
            if (contenu.isEmpty()) {
                System.out.println("Le fichier est vide.");
                return 0;
            }
    
            String premiereLigne = contenu.peek(); // Récupérer la première ligne de la file sans la retirer
    
            if (premiereLigne.isEmpty()) {
                System.out.println("La première ligne est vide.");
                return 0;
            }
    
            int premierNombre = Character.getNumericValue(premiereLigne.charAt(0)); // Extraire le premier chiffre de la ligne actuelle
            passerALaLigneSuivante();
            return premierNombre;
        } catch (NumberFormatException e) {
            System.out.println("Erreur lors de la conversion du premier nombre.");
            return 0; 
        }
    }
    

    public void passerALaLigneSuivante() {
        if (!contenu.isEmpty()) {
            contenu.poll(); // Retirez la première ligne actuelle de la file pour passer à la ligne suivante
            ligneCourante++; // Incrémentez la variable de ligne courante
        }
    }
}
