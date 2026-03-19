package src.metier;

public class Entity {

    protected int maxHealth;
    protected int health;

    protected int x;
    protected int y;
    
    protected int damage;
    protected int attackSpeed;

    protected Entity() {
        this(100,100, 0, 0, 0, 1); // Valeurs par défaut
    }

    protected Entity(int maxHealth, int health, int x, int y, int damage, int attackSpeed) {
        this.maxHealth   = maxHealth;
        this.health      = health;
        this.x           = x;
        this.y           = y;
        this.damage      = damage;
        this.attackSpeed = attackSpeed;
    }

    public int getMaxHP      () { return this.maxHealth  ; }
    public int getCurrentHP  () { return this.health     ; }
    public int getX          () { return this.x          ; }
    public int getY          () { return this.y          ; }
    public int getDamage     () { return this.damage     ; }
    public int getAttackSpeed() { return this.attackSpeed; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setDamage(int damage) { this.damage = damage; }
    public void setAttackSpeed(int attackSpeed) { this.attackSpeed = attackSpeed; }
    public void setMaxHP(int maxHealth) { this.maxHealth = maxHealth; }
    public void setCurrentHP(int health) { this.health = health; }

    protected void reduceHP(int amount) {
        this.health -= amount;
        if (this.health < 0) {
            this.health = 0; // Empêche les HP de devenir négatifs
        }
    }
}
