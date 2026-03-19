package src.controleur;

import java.awt.Graphics;

import src.frame.FrameJeu;
import src.metier.Jeu;

public class Controleur {
    
    private FrameJeu frame;
    private Jeu metier;
    
    public Controleur() {
        this.metier = new Jeu();
        this.frame = new FrameJeu(this);
    }

    public int getCurrentHP() {
        return this.metier.getCurrentHP();
    }

    public static void main(String[] args) {
        Controleur controleur = new Controleur();

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = "";
        input = scanner.nextLine();
        while (!input.equals("close")) {
            try {
                System.out.println("Traitement de la commande : " + input);
                Thread.sleep(2000); // Attendre 3 secondes
                if (input.equals("M")) {
                    controleur.metier.movePlayer(5, 5);
                    controleur.refreshFrame();
                }   
                System.out.println("Entrez une commande (ou 'close' pour quitter) :");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            input = scanner.nextLine();
        }
        scanner.close();

        System.out.println("Fermeture du jeu...");
        return;
    }

    public Jeu getMetier() {
        return this.metier;
    }

    public void refreshFrame() {
        this.frame.repaint(); 
    }
}
