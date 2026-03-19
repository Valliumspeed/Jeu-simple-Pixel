package src.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.metier.Jeu;
import src.metier.Ressources;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class GamePanel extends JPanel {

    // Référence à la fenêtre parente (pour communication future)
    private JFrame parent;

    private Jeu metier;

    // Monde logique
    private static final int TILE_SIZE = 8;
    private static final int TILE_COUNT_X = 13;
    private static final int TILE_COUNT_Y = 11;

    // Zoom (pixel art)
    private static final int SCALE = 6;

    // Taille écran
    public static final  int SCREEN_TILE_SIZE = TILE_SIZE * SCALE;
    public static final  int WIDTH  = TILE_COUNT_X * SCREEN_TILE_SIZE;
    public static final  int HEIGHT = TILE_COUNT_Y * SCREEN_TILE_SIZE;

    private BufferedImage backgroundImage;

    public GamePanel(JFrame parent, Jeu metier) {
        this.parent = parent;
        this.metier = metier;
    }

    public void setMetier(Jeu metier) {
        this.metier = metier;
    }

    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            if (this.backgroundImage == null) {
                this.backgroundImage = ImageIO.read(new File("src/images/Background/grass.png"));
            }
            this.drawBackground(g, this.backgroundImage);
            this.drawRessources(g, this.metier.getRessources());
            this.drawPlayer(g);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawBackground(Graphics g, BufferedImage image) throws Exception {
        for (int x = 0; x < TILE_COUNT_X; x++) {
            for (int y = 0; y < TILE_COUNT_Y; y++) {
                g.drawImage(
                    image,
                    x * SCREEN_TILE_SIZE,
                    y * SCREEN_TILE_SIZE,
                    SCREEN_TILE_SIZE,
                    SCREEN_TILE_SIZE,
                    this
                );
            }
        }
    }

    public void drawRessources(Graphics g, Set<Ressources> ressources) throws Exception {
        for (Ressources ressource : ressources) {
            BufferedImage image = ImageIO.read(new File("src/images/" + ressource.getType().name() +"/" + ressource.getName() + ".png"));
            g.drawImage(
                image,
                ressource.getX() * SCREEN_TILE_SIZE,
                ressource.getY() * SCREEN_TILE_SIZE,
                image.getWidth() * SCALE,
                image.getHeight() * SCALE,
                this
            );
        }
    }

    public void drawPlayer(Graphics g) throws Exception {
        // Exemple : notre joueur au centre de l'écran
        g.drawImage(
        ImageIO.read(new File("src/images/perso.png")),
        0,
        0,
        GamePanel.SCREEN_TILE_SIZE,
        GamePanel.SCREEN_TILE_SIZE,
        this
        );
    }
}
