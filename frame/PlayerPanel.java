import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel{
    
    public FrameJeu frame;

    public PlayerPanel(FrameJeu frame) {
        this.frame = frame;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            this.drawPlayer(g);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawPlayer(Graphics g) throws Exception {
        // Exemple : notre joueur au centre de l'écran
        g.drawImage(
        ImageIO.read(new File("./images/perso.png")),
        6 * GamePanel.SCREEN_TILE_SIZE,
        5 * GamePanel.SCREEN_TILE_SIZE,
        GamePanel.SCREEN_TILE_SIZE,
        GamePanel.SCREEN_TILE_SIZE,
        this
        );
    }
}
