public class Controleur {
    
    public FrameJeu frame;
    public Metier metier;
    
    public Controleur() {
        demarrerJeu();
    }

    public void demarrerJeu() {
        this.frame = new FrameJeu(this);
        this.metier = new Metier();
    }

    public static void main(String[] args) {
        Controleur controleur = new Controleur();
        controleur.frame.move();
    }

    public int getCurrentHP() {
        return this.metier.getCurrentHP(); // Valeur temporaire, à remplacer par la logique réelle
    }
}
