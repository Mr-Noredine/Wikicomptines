package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel {
    private RobotVue robotVue; // Instance unique de RobotVue
    public GamePanel() {
        this.robotVue = new RobotVue(); 
        fenetre_avant_jeu();
    }

    public void fenetre_avant_jeu() {
         SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Avant le jeu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("src/assets/images/im.jpg")); // Ensure this is the correct path
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Create a layered pane to manage z-index
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(800, 600));

            JLabel backgroundLabel = new JLabel(new ImageIcon(image));
            backgroundLabel.setBounds(0, 0, image.getWidth(), image.getHeight());
            layeredPane.add(backgroundLabel, Integer.valueOf(1));

            // Create a transparent panel for buttons
            JPanel buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.setOpaque(false);
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(10, 10, 10, 10);

            // Add the "Jouer" button
            JButton jouerButton = createButton("Jouer", Color.LIGHT_GRAY, e -> afficherFenetreJeu());
            constraints.gridx = 0;
            constraints.gridy = 0;
            buttonPanel.add(jouerButton, constraints);

            // Add the "Paramètre" button
            JButton parametreButton = createButton("Paramètre", Color.LIGHT_GRAY, e -> {
                // Your parameter settings action here
            });
            constraints.gridy = 1;
            buttonPanel.add(parametreButton, constraints);

            // Add the "Quitter" button
            JButton quitterButton = createButton("Quitter", Color.LIGHT_GRAY, e -> {
                JOptionPane.showMessageDialog(null, "Au revoir !");
                System.exit(0);
            });
            constraints.gridy = 2;
            buttonPanel.add(quitterButton, constraints);

            // Position des bouttons dans les fentres
            buttonPanel.setBounds(100, 100, 400, 400); // Set this as needed
            layeredPane.add(buttonPanel, Integer.valueOf(2));

            // Set the layered pane as the content pane
            frame.setContentPane(layeredPane);
            frame.setVisible(true);
        });
    }

    
    private static JButton createButton(String text, Color color, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(color);
        button.addActionListener(actionListener);
        return button;
    }

  
    public static void afficherFenetreJeu() {
        JFrame frame = new JFrame("Jeu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().setBackground(Color.BLACK); // Définit la couleur de fond de la fenêtre en noir
    
        TextZone textZone = new TextZone();
        GrilleVue grille = new GrilleVue();
        
    
        JPanel grillePanel = new JPanel(new GridBagLayout());
        grillePanel.setBackground(Color.DARK_GRAY); // Définit la couleur de fond du panneau de la grille en noir
        grillePanel.add(grille);
    
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(0, 20, 5, 0);
        grillePanel.add(grille, constraints);
    
       
    
        frame.add(textZone, BorderLayout.LINE_START);
       
        frame.add(grillePanel, BorderLayout.CENTER);
    
        frame.setVisible(true);
    }
    // Méthode pour déplacer le robot à de nouvelles coordonnées
    public void deplacerRobot(int newX, int newY) {
        // Mettez à jour les coordonnées du robot en appelant la méthode move de l'instance unique de RobotVue
        robotVue.move(newX, newY);
        // Appelez repaint pour mettre à jour l'affichage
        robotVue.repaint();
}
}