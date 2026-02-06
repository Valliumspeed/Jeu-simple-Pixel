import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            this.drawBackground(g); 
            this.drawEntities(g);   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== COUCHES ==================

    private void drawBackground(Graphics g) throws Exception {
        for (int x = 0; x < TILE_COUNT_X; x++) {
            for (int y = 0; y < TILE_COUNT_Y; y++) {
                g.drawImage(
                    ImageIO.read(new File("./images/herbe.png")),
                    x * SCREEN_TILE_SIZE,
                    y * SCREEN_TILE_SIZE,
                    SCREEN_TILE_SIZE,
                    SCREEN_TILE_SIZE,
                    this.backgroundLayer
                );
            }
        }
    }

    private void drawEntities(Graphics g) throws Exception {
        // Exemple : notre joueur au centre de l'écran
        g.drawImage(
        ImageIO.read(new File("./images/iron.png")),
        5 * SCREEN_TILE_SIZE,
        5 * SCREEN_TILE_SIZE,
        SCREEN_TILE_SIZE,
        SCREEN_TILE_SIZE,
        this.entityLayer
        );  
    }
}
