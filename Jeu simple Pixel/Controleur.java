public class Controleur {
    
    public FrameJeu frame;
    public Metier metier;
    
    public Controleur() {
        demarrerJeu();
    }

    public void demarrerJeu() {
        frame = new FrameJeu();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Controleur controleur = new Controleur();
    }
}
