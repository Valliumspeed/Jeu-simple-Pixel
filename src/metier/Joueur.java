package src.metier;

import java.awt.Point;
import java.util.ArrayList;

public class Joueur extends Entity {
    private final static int MAX_PLAYER_HEALTH = 100;

    // --- Variables pour les caractéristiques du joueur ---
    private int armor;

    // --- Variables pour le déplacement ---
    private ArrayList<Point> cheminActuel = new ArrayList<Point>();
    private int destX;
    private int destY;
    private double speed; // Vitesse de déplacement en cases par seconde
    private boolean enMouvement;

    private Ressources cibleInteractive;
    
    public Joueur(int health, int armor, int x, int y, int damage, double attackSpeed, double speed) {
        super(MAX_PLAYER_HEALTH, health, x, y, damage, attackSpeed);

        this.armor = armor;

        this.destX = x;
        this.destY = y;
        this.speed = speed;
        this.enMouvement = false;
        this.cibleInteractive = null;
    }

    public Joueur() {
        this(MAX_PLAYER_HEALTH, 0, 0, 0, 1, 1.0, 2.5);

        this.armor = 0;

        this.destX = 0;
        this.destY = 0;
        this.enMouvement = false;
        this.cibleInteractive = null;
    }

    public void setNouvelleDestination(int x, int y, Ressources cible) {
        this.destX = x;
        this.destY = y;
        this.cibleInteractive = cible;
        this.enMouvement = true;
    }

    public int              getArmor           () { return this.armor           ; }
    public ArrayList<Point> getChemin          () { return this.cheminActuel    ; }
    public int              getDestX           () { return this.destX           ; }
    public int              getDestY           () { return this.destY           ; }
    public double           getSpeed           () { return this.speed           ; }
    public Ressources       getCibleInteractive() { return this.cibleInteractive; }
    public boolean          isEnMouvement      () { return this.enMouvement     ; }

    public void setArmor      (int armor)             { this.armor = armor            ; }
    public void setDestX      (int destX)             { this.destX = destX            ; }
    public void setDestY      (int destY)             { this.destY = destY            ; }
    public void setSpeed      (double speed)          { this.speed = speed            ; }
    public void setEnMouvement(boolean mouvement)     { this.enMouvement = mouvement  ; }
    public void setCibleInteractive(Ressources cible) { this.cibleInteractive = cible ; }
    public void setChemin(ArrayList<Point> chemin) {
        this.cheminActuel = chemin;
        if (chemin != null && !chemin.isEmpty()) {
            this.setEnMouvement(true);
        }
    }
}