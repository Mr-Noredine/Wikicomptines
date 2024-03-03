package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class TextZone extends JPanel {
    private JTextArea textArea;
    private JTextArea memoryArea1;
    private JTextArea memoryArea2;
    JButton avancerButton;
    JButton stopButton;


    public TextZone() {
        setLayout(new BorderLayout());
    
        // Zone de texte principale
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 250)); // Diminuer la hauteur de la zone de texte
        add(scrollPane, BorderLayout.PAGE_START);
    
        // Panneau pour les zones de mémoire
        JPanel memoryPanel = new JPanel(new GridLayout(4 , 1));
        memoryPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(memoryPanel, BorderLayout.CENTER);
        // Première zone de mémoire
        memoryArea1 = new JTextArea();
        memoryArea1.setEditable(false);
        JScrollPane memoryScrollPane1 = new JScrollPane(memoryArea1);
        memoryScrollPane1.setPreferredSize(new Dimension(50, 30));
        memoryPanel.add(memoryScrollPane1);
    
        // Deuxième zone de mémoire
        memoryArea2 = new JTextArea();
        memoryArea2.setEditable(false);
        JScrollPane memoryScrollPane2 = new JScrollPane(memoryArea2);
        memoryScrollPane2.setPreferredSize(new Dimension(50, 30));
        memoryPanel.add(memoryScrollPane2);
    
        add(memoryPanel, BorderLayout.CENTER);
    
        // Création du panneau pour les boutons Stop et Avancer
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
        // Bouton Stop
        stopButton = new JButton("Stop"); // Modification ici
        stopButton.addActionListener(e -> {
            // Code à exécuter lorsque le bouton "Stop" est cliqué
            // Par exemple, vous pouvez réinitialiser le contenu de la zone de texte principale
            textArea.setText("");
        });
        buttonPanel.add(stopButton);
    
        // Ajout d'un espace entre les boutons
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    
        // Bouton Avancer
        avancerButton = new JButton("Avancer"); // Modification ici
        avancerButton.addActionListener(e -> {
            // Code à exécuter lorsque le bouton "Avancer" est cliqué
            // Par exemple, vous pouvez afficher un message dans la zone de texte principale
            textArea.append("Le bouton Avancer a été cliqué!\n");
        });
        
        buttonPanel.add(avancerButton);
    
        // Ajout du panneau de boutons en bas
        add(buttonPanel, BorderLayout.SOUTH);
    }
    // Getters et Setters

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextArea getMemoryArea1() {
        return memoryArea1;
    }

    public JTextArea getMemoryArea2() {
        return memoryArea2;
    }

    public JButton getAvancerButton() {
        return avancerButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public void setAvancerButton(JButton avancerButton) {
        this.avancerButton = avancerButton;
    }

    public void setStopButton(JButton stopButton) {
        this.stopButton = stopButton;
    }
}