import metier.Joueur;

public class Metier {
    
    public Joueur joueur;

    public Metier() {
        this.joueur = new Joueur();
    }
    
    public int getCurrentHP() {
        return this.joueur.getCurrentHP(); // Valeur temporaire, à remplacer par la logique réelle
    }

    public void reducePlayerHP(int amount) {
        this.joueur.reduceHP(amount);
        if( this.joueur.getCurrentHP() <= 0) {
            System.out.println("Le joueur est mort !");
        }
    }
}
