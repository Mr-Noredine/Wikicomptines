package view;


import model.Niveau1;

public class MainView{
    public static void main(String[] args) {
        Niveau1 niveau1 = new Niveau1();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameWindow gameWindow = new GameWindow(niveau1); // Assurez-vous que GameWindow a un constructeur public sans argument ou ajustez selon votre implémentation
                gameWindow.setVisible(true); // Rend la fenêtre visible
            }
        });
    }
}