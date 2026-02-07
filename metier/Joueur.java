package metier;

public class Joueur extends Entities {
    private final static int MAX_PLAYER_HEALTH = 100;

    private int armor;

    public Joueur(int health, int armor, int x, int y, int damage, int attackSpeed) {
        super(MAX_PLAYER_HEALTH, health, x, y, damage, attackSpeed);
        this.armor = armor;
    }

    public Joueur() {
        this(MAX_PLAYER_HEALTH, 0, 0, 0, 1, 1); // Valeurs par défaut
    }
}
