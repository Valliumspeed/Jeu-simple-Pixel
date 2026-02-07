import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import metier.Ressources;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class GamePanel extends JLayeredPane {

    // Référence à la fenêtre parente (pour communication future)
    private JFrame parent;

    private JPanel backgroundLayer;
    private JPanel entityLayer;

    // Monde logique
    private static final int TILE_SIZE = 8;
    private static final int TILE_COUNT_X = 13;
    private static final int TILE_COUNT_Y = 11;

    // Zoom (pixel art)
    private static final int SCALE = 6;

    // Taille écran
    static final  int SCREEN_TILE_SIZE = TILE_SIZE * SCALE;
    static final  int WIDTH  = TILE_COUNT_X * SCREEN_TILE_SIZE;
    static final  int HEIGHT = TILE_COUNT_Y * SCREEN_TILE_SIZE;

    public GamePanel(JFrame parent ) {
        this.parent = parent;

        this.backgroundLayer = new JPanel();
        this.entityLayer = new JPanel();
        this.entityLayer.setOpaque(false); // Rendre la couche d'entités transparente

        this.add(this.backgroundLayer, Integer.valueOf(0));
        this.add(this.entityLayer, Integer.valueOf(1));
    }

    // ================== COUCHES ==================

    public void drawBackground(Graphics g, String path) throws Exception {
        for (int x = 0; x < TILE_COUNT_X; x++) {
            for (int y = 0; y < TILE_COUNT_Y; y++) {
                g.drawImage(
                    ImageIO.read(new File(path)),
                    x * SCREEN_TILE_SIZE,
                    y * SCREEN_TILE_SIZE,
                    SCREEN_TILE_SIZE,
                    SCREEN_TILE_SIZE,
                    this.backgroundLayer
                );
            }
        }
    }

    public void drawRessources(Graphics g, Set<Ressources> ressources) throws Exception {
        for (Ressources ressource : ressources) {
            BufferedImage image = ImageIO.read(new File("./images/" + ressource.getType().name() +"/" + ressource.getName() + ".png"));
            g.drawImage(
                image,
                ressource.getX() * SCREEN_TILE_SIZE,
                ressource.getY() * SCREEN_TILE_SIZE,
                image.getWidth() * SCALE,
                image.getHeight() * SCALE,
                this.entityLayer
            );
        }
    }
}
