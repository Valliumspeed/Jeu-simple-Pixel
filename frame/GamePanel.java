import javax.swing.JPanel;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {

    // Monde logique
    private static final int TILE_SIZE = 8;
    private static final int TILE_COUNT_X = 13;
    private static final int TILE_COUNT_Y = 11;

    // Zoom (pixel art)
    private static final int SCALE = 6;

    // Taille écran
    private static final int SCREEN_TILE_SIZE = TILE_SIZE * SCALE;
    private static final int WIDTH  = TILE_COUNT_X * SCREEN_TILE_SIZE;
    private static final int HEIGHT = TILE_COUNT_Y * SCREEN_TILE_SIZE;

    private Image grassTile;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        try {
            grassTile = ImageIO.read(new File("./images/herbe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g); // couche 0
        try {
            drawEntities(g);   // couche 1
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== COUCHES ==================

    private void drawBackground(Graphics g) {
        for (int x = 0; x < TILE_COUNT_X; x++) {
            for (int y = 0; y < TILE_COUNT_Y; y++) {
                g.drawImage(
                    grassTile,
                    x * SCREEN_TILE_SIZE,
                    y * SCREEN_TILE_SIZE,
                    SCREEN_TILE_SIZE,
                    SCREEN_TILE_SIZE,
                    this
                );
            }
        }
    }

    private void drawEntities(Graphics g) throws Exception {
        // Exemple : notre joueur au centre de l'écran
       g.drawImage(
        ImageIO.read(new File("./images/perso.png")),
        6 * SCREEN_TILE_SIZE,
        5 * SCREEN_TILE_SIZE,
        SCREEN_TILE_SIZE,
        SCREEN_TILE_SIZE,
        this
        );

        g.drawImage(
        ImageIO.read(new File("./images/iron.png")),
        5 * SCREEN_TILE_SIZE,
        5 * SCREEN_TILE_SIZE,
        SCREEN_TILE_SIZE,
        SCREEN_TILE_SIZE,
        this
        );
    }
}
