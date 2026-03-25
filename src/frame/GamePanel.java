package src.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.metier.Jeu;
import src.metier.Ressources;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GamePanel extends JPanel {

    // Référence à la fenêtre parente (pour communication future)
    private JFrame parent;

    private Jeu metier;

    // Monde logique
    private static final int TILE_SIZE = 8;

    // Zoom (pixel art)
    private static final int SCALE = 6;

    // Taille écran
    public static final int SCREEN_TILE_SIZE = TILE_SIZE * SCALE;

    // Gestion de la caméra
    private double camX = 0;
    private double camY = 0;
    private static final int MAX_VIEW_SIZE = 10;

    private Map<String, BufferedImage> imageCache = new HashMap<>();
    private BufferedImage playerImage = null; // Cache pour l'image du joueur

    private BufferedImage backgroundImage;

    public GamePanel(JFrame parent, Jeu metier) {
        this.parent = parent;
        this.metier = metier;

        // --- AJOUT DE L'ÉCOUTEUR DE CLIC ---
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gererClicSouris(e.getX(), e.getY());
            }
        });
    }

    public void setMetier(Jeu metier) {
        this.metier = metier;
    }

    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.updateCamera();

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
        int startX = (int) Math.floor(camX);
        int startY = (int) Math.floor(camY);
        int endX = startX + getViewWidthInTiles() + 1;
        int endY = startY + getViewHeightInTiles() + 1;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                // Utiliser Math.floor pour un glissement parfait
                int drawX = (int) Math.floor((x - camX) * SCREEN_TILE_SIZE);
                int drawY = (int) Math.floor((y - camY) * SCREEN_TILE_SIZE);
                
                g.drawImage(image, drawX, drawY, SCREEN_TILE_SIZE, SCREEN_TILE_SIZE, this);
            }
        }
    }

    public void drawRessources(Graphics g, Set<Ressources> ressources) throws Exception {
        for (Ressources r : ressources) {
            if (r.getX() >= camX - 1 && r.getX() <= camX + getViewWidthInTiles() + 1 &&
                r.getY() >= camY - 1 && r.getY() <= camY + getViewHeightInTiles() + 1) {

                BufferedImage img = this.getImage(r);
                if (img != null) {
                    // CALCUL UNIFIÉ (Math.floor au lieu de Math.round)
                    int tileX = (int) Math.floor((r.getX() - camX) * SCREEN_TILE_SIZE);
                    int tileY = (int) Math.floor((r.getY() - camY) * SCREEN_TILE_SIZE);
                    
                    int imgWidth = img.getWidth() * SCALE;
                    int imgHeight = img.getHeight() * SCALE;
                    
                    int drawX = tileX + (SCREEN_TILE_SIZE - imgWidth) / 2;
                    int drawY = tileY + SCREEN_TILE_SIZE - imgHeight;

                    g.drawImage(img, drawX, drawY, imgWidth, imgHeight, this);
                }
            }   
        }
    }

    public void drawPlayer(Graphics g) {
        try {
            // 1. Chargement unique (Cache)
            if (this.playerImage == null) {
                this.playerImage = ImageIO.read(new File("src/images/perso.png"));
            }

            int imgWidth  = this.playerImage.getWidth()  * SCALE;
            int imgHeight = this.playerImage.getHeight() * SCALE;

            // Calcul en double puis Math.floor
            int tileX = (int) Math.floor((this.metier.getPlayer().getX() - camX) * SCREEN_TILE_SIZE);
            int tileY = (int) Math.floor((this.metier.getPlayer().getY() - camY) * SCREEN_TILE_SIZE);

            int drawX = tileX + (SCREEN_TILE_SIZE - imgWidth) / 2;
            int drawY = tileY + (SCREEN_TILE_SIZE - imgHeight) / 2;

            g.drawImage(playerImage, drawX, drawY, imgWidth, imgHeight, this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage getImage(Ressources ressource) {
        // On crée une clé unique pour cette image (ex: "ENEMY_Loup")
        String key = ressource.getType().name() + "_" + ressource.getName();

        // Si l'image n'est pas encore dans le cache, on la charge
        if (!this.imageCache.containsKey(key)) {
            try {
                String path = "src/images/" + ressource.getType().name() + "/" + ressource.getName() + ".png";
                BufferedImage img = ImageIO.read(new File(path));
                this.imageCache.put(key, img);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        // On retourne l'image stockée en mémoire
        return imageCache.get(key);
    }

    public static int getPreferredWidth(Jeu metier) {
        return Math.min(metier.getMapWidth(), MAX_VIEW_SIZE) * SCREEN_TILE_SIZE;
    }

    public static int getPreferredHeight(Jeu metier) {
        return Math.min(metier.getMapHeight(), MAX_VIEW_SIZE) * SCREEN_TILE_SIZE;
    }

    private int getViewWidthInTiles() {
        return Math.min(this.metier.getMapWidth(), MAX_VIEW_SIZE);
    }

    private int getViewHeightInTiles() {
        return Math.min(this.metier.getMapHeight(), MAX_VIEW_SIZE);
    }

    public void initialiserCamera() {
        this.updateCamera();
    }

    private void updateCamera() {
        this.camX = this.metier.getCameraX() - getViewWidthInTiles() / 2.0;
        this.camY = this.metier.getCameraY() - getViewHeightInTiles() / 2.0;

        this.fixerLimitesCamera();
    }

    private void fixerLimitesCamera() {
        double maxCamX = Math.max(0, this.metier.getMapWidth() - getViewWidthInTiles());
        double maxCamY = Math.max(0, this.metier.getMapHeight() - getViewHeightInTiles());

        this.camX = Math.max(0, this.camX);
        this.camY = Math.max(0, this.camY);

        this.camX = Math.min(this.camX, maxCamX);
        this.camY = Math.min(this.camY, maxCamY);
    }

    private void gererClicSouris(int mouseX, int mouseY) {
        // 1. Conversion Pixels -> Case de l'écran
        int screenTileX = mouseX / SCREEN_TILE_SIZE;
        int screenTileY = mouseY / SCREEN_TILE_SIZE;

        // 2. Conversion Case Écran -> Case Monde (en ajoutant la caméra)
        int worldTileX = screenTileX + (int) Math.floor(camX);
        int worldTileY = screenTileY + (int) Math.floor(camY);

        // 3. Vérification pour ne pas cliquer hors de la carte
        if (worldTileX >= 0 && worldTileX < metier.getMapWidth() &&
            worldTileY >= 0 && worldTileY < metier.getMapHeight()) {

            System.out.println("Clic sur la case Monde : " + worldTileX + ", " + worldTileY);

            // 4. A-t-on cliqué sur une ressource/ennemi ?
            Ressources cible = trouverRessourceSurCase(worldTileX, worldTileY);

            // 5. On envoie l'ordre au métier (qui lancera le BFS)
            metier.cliquerSurCase(worldTileX, worldTileY, cible);
        }
    }

    private Ressources trouverRessourceSurCase(int x, int y) {
        for (Ressources r : metier.getRessources()) {
            // Si les coordonnées de la ressource correspondent au clic
            if (r.getX() == x && r.getY() == y) {
                return r;
            }
        }
        return null; // Il n'y a rien sur cette case (c'est de l'herbe)
    }
}
