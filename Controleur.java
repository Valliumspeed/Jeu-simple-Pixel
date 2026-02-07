import java.awt.Graphics;

import metier.Jeu;

public class Controleur {
    
    private FrameJeu frame;
    private Jeu metier;
    
    public Controleur() {
        this.frame = new FrameJeu(this);
        this.metier = new Jeu();
    }

    public int getCurrentHP() {
        return this.metier.getCurrentHP();
    }

    private void drawBackground(String path) {
        this.frame.drawBackground(this.frame.getGraphics(), path);
    }

    public void drawRessources() {
        this.frame.drawRessources(this.frame.getGraphics(), this.metier.getRessources());
    }

    public static void main(String[] args) {
        Controleur controleur = new Controleur();


        controleur.drawBackground("./images/Background/herbe.png");
        controleur.drawRessources();
        controleur.drawPlayer();
        controleur.drawUI();
        /* 
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = "";
        while (!input.equals("close")) {
            try {
                Thread.sleep(5000); // Attendre 5 secondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }  
        }
        scanner.close();
        */
    }

    private void drawPlayer() {
        this.frame.drawPlayer(this.frame.getGraphics());
    }

    private void drawUI() {
        this.frame.drawPlayerHealth(this.frame.getGraphics(), this.getCurrentHP());
    }
}
