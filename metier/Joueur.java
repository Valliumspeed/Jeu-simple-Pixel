package metier;

public class Joueur {
    private final static int MAX_HEALTH = 100;

    private int health;
    private int armor;

    private int x;
    private int y;
    
    private int damage;
    private int attackSpeed;

    public Joueur(int armor, int x, int y, int damage) {
        this.health = MAX_HEALTH;
        this.armor = armor;
        this.x = x;
        this.y = y;
        this.damage = damage;
    }
}
