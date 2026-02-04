import javax.swing.JFrame;

public class FrameJeu extends JFrame {

    public FrameJeu() {
        setTitle("Frame Jeu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Rendre la fenêtre non changeable

        GamePanel panel = new GamePanel();
        setContentPane(panel);

        pack(); // utilise la taille du panel
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FrameJeu();
    }
}