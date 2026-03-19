package src.metier;

public class Ressources extends Entity {

    private String name;          // Nom de la ressource
    private typeRessource type; // Type de ressource
    private int resAmount;       // Quantité de ressource

    public Ressources() {
        super(100,100, 0, 0,  0, 1); // Valeurs par défaut
        this.name = "Ressource"; // Nom de ressource par défaut
        this.type = typeRessource.Stone; // Type de ressource par défaut
        this.resAmount = 10; // Quantité de ressource par défaut
    }

    public Ressources(String name, typeRessource type, int maxHealth, int health, int x, int y, int resAmount) {
        super(maxHealth, health, x, y, 0, 1);
        this.name = name;
        this.type = type;
        this.resAmount = resAmount;
    }

    public Ressources(String name, typeRessource type, int maxHealth, int health, int x, int y, int damage, int attackSpeed, int resAmount) {
        this(name, type, maxHealth, health, x, y, resAmount); // Quantité de ressource par défaut
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    public String         getName     () { return this.name     ; }
    public int            getResAmount() { return this.resAmount; }
    public typeRessource getType     () { return this.type     ; }
}
