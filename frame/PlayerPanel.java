import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel{
    
    public FrameJeu frame;

    public PlayerPanel(FrameJeu frame) {
        this.frame = frame;

        setOpaque(false); // rendre le panneau transparent
    }

    public void drawPlayer(Graphics g) throws Exception {
        // Exemple : notre joueur au centre de l'écran
        g.drawImage(
        ImageIO.read(new File("./images/perso.png")),
        0,
        0,
        GamePanel.SCREEN_TILE_SIZE,
        GamePanel.SCREEN_TILE_SIZE,
        this
        );
    }
}
