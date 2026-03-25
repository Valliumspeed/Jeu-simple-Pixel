package src.metier;

public class Entity {

    protected int maxHealth;
    protected int health;

    protected double x;
    protected double y;

    protected int damage;
    protected double attackSpeed;

    protected Entity() {
        this(100,100, 0, 0, 0, 1.0); // Valeurs par défaut
    }

    protected Entity(int maxHealth, int health, int x, int y, int damage, double attackSpeed) {
        this.maxHealth   = maxHealth;
        this.health      = health;
        this.x           = x;
        this.y           = y;
        this.damage      = damage;
        this.attackSpeed = attackSpeed;
    }

    public int getMaxHP         () { return this.maxHealth  ; }
    public int getCurrentHP     () { return this.health     ; }
    public double getX          () { return this.x          ; }
    public double getY          () { return this.y          ; }
    public int getDamage        () { return this.damage     ; }
    public double getAttackSpeed() { return this.attackSpeed; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setDamage(int damage) { this.damage = damage; }
    public void setAttackSpeed(double attackSpeed) { this.attackSpeed = attackSpeed; }
    public void setMaxHP(int maxHealth) { this.maxHealth = maxHealth; }
    public void setCurrentHP(int health) { this.health = health; }

    protected void reduceHP(int amount) {
        this.health -= amount;
        if (this.health < 0) {
            this.health = 0;
        }
    }
}
