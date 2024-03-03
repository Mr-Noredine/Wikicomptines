package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Fichier {
    private int id;
    private int posX;
    private int posY;
    private FileF gestionFichier; // Utilisez FileF pour la gestion du fichier

    public Fichier(String cheminFichier, int id, int posX, int posY) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.gestionFichier = new FileF(cheminFichier);
    }

       public void ajouterContenu(String data) {
        gestionFichier.enqueue(data);
    }

    public String retirerContenu() {
        String contenu = gestionFichier.dequeue();
        if (contenu == null) {
            throw new NoSuchElementException("Le fichier est vide.");
        }
        return contenu;
    }


    public boolean estVide() {
        return gestionFichier.Test_EOF();
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



    public void setPosition(int newX, int newY) {
        this.posX = newX;
        this.posY = newY;
    }

}
