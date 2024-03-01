package model;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.LinkedList;

public class FileF{
    private File file;

    public FileF(String filePath) {
        this.file = new File(filePath);
    }
     // Ajouter  un élément en fin de notre fichier
    public void enqueue(String element) {
        try (FileWriter fw = new FileWriter(this.file, true);
             PrintWriter out = new PrintWriter(fw)) {
            out.println(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  // Retirer et retourner le premier élément de notre fichier
    public String dequeue() {
        LinkedList<String> lines = new LinkedList<>();
        String firstElement = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            if (!lines.isEmpty()) {
                firstElement = lines.removeFirst();
            }

            // on va Réécrir notre  fichier sans le premier élément
            try (PrintWriter writer = new PrintWriter(this.file)) {
                for (String l : lines) {
                    writer.println(l);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstElement;
    }


     // on va verifier si notre  fichier est vide
    public boolean Test_EOF() {
        return this.file.length() == 0;
    }
}
    
