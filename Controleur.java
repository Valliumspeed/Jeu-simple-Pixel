public class Controleur {
    
    public Frame frame;
    public Metier metier;
    
    public Controleur() {
        demarrerJeu();
    }

    public void demarrerJeu() {
        this.frame = new Frame();
        this.metier = new Metier();
    }

    public static void main(String[] args) {
        Controleur controleur = new Controleur();
    }
}
