package model;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Pile<T> {

 public  File file; 

 public Pile(String filePath) {
        this.file = new File(filePath);
        }
        
        
 // Écrit un nouvel élément en fin de fichier
    public void push(String element) {
        try (FileWriter fw = new FileWriter(this.file, true);
             PrintWriter out = new PrintWriter(fw)) {
            out.println(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
   // Lit les éléments, retire le dernier, et réécrit le fichier
    public String pop() {
        LinkedList<String> lines = new LinkedList<>();
        String line;
        String lastElement = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            if (!lines.isEmpty()) {
                lastElement = lines.removeLast();
                // Réécrit le fichier sans le dernier élément
                try (PrintWriter writer = new PrintWriter(this.file)) {
                    for (String l : lines) {
                        writer.println(l);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastElement;
    }




  
    // Vérifie si le fichier est vide
    public boolean isEmpty() {
        return this.file.length() == 0;
    }

}
   

