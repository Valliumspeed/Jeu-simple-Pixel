package src.controleur;

import java.awt.Graphics;

import javax.swing.Timer;

import src.frame.FrameJeu;
import src.metier.Jeu;

public class Controleur {
    
    private FrameJeu vue;
    private Jeu metier;
    private Timer gameLoop;
    
    private static final int UPDATE_INTERVAL = 16; //temps en ms de rafraichissement du jeu (environ 60 FPS)

    public Controleur() {
        this.metier = new Jeu();
        this.vue = new FrameJeu(this);

        this.gameLoop = new Timer(16, e -> {
            this.updateGame();
        });
        this.gameLoop.start();
    }

    public void updateGame() {
        this.metier.update(16); // Met à jour la logique du jeu
        this.vue.repaint(); // Redessine la fenêtre
    }

    public int getCurrentHP() {
        return this.metier.getCurrentHP();
    }

    public static void main(String[] args) {
        new Controleur();
    }

    public Jeu getMetier() {
        return this.metier;
    }

    public void refreshFrame() {
        this.vue.repaint(); 
    }
}
