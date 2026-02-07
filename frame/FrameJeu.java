import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import metier.Ressources;

public class FrameJeu extends JFrame {
    
    private Controleur controleur;

    private JLayeredPane layeredPanel;

    private PlayerPanel playerPanel;
    private GamePanel gamePanel;
    private UIPanel uiPanel;

    public FrameJeu(Controleur controleur) {

        this.controleur = controleur;

        setTitle("Frame Jeu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setResizable(false); // Rendre la fenêtre non changeable

        this.layeredPanel = new JLayeredPane();

        this.playerPanel = new PlayerPanel(this);
        this.playerPanel.setBounds(GamePanel.SCREEN_TILE_SIZE * 6, GamePanel.SCREEN_TILE_SIZE * 5, GamePanel.SCREEN_TILE_SIZE, GamePanel.SCREEN_TILE_SIZE);
        this.gamePanel = new GamePanel(this);
        this.gamePanel.setBounds(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        this.uiPanel = new UIPanel(this);
        this.uiPanel.setBounds(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        this.layeredPanel.add(this.gamePanel  , Integer.valueOf(0));
        this.layeredPanel.add(this.playerPanel, Integer.valueOf(1));
        this.layeredPanel.add(this.uiPanel, Integer.valueOf(2));

        this.layeredPanel.setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
        this.layeredPanel.setBackground(Color.BLACK);

        setContentPane(this.layeredPanel);

        pack(); // utilise la taille du panel
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public int getCurrentHP() {
        return this.controleur.getCurrentHP(); // Valeur temporaire, à remplacer par la logique réelle
    }

    public void drawBackground(Graphics g, String path) {
        try {
            this.gamePanel.drawBackground(g, path);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void drawRessources(Graphics g, Set<Ressources> ressources) {
        try {
            this.gamePanel.drawRessources(g, ressources);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

        public void drawPlayer(Graphics g) {
        try {
            this.playerPanel.drawPlayer(g);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }


    public void drawPlayerHealth(Graphics g, int health) {
        this.uiPanel.drawPlayerHealth(g, health);
    }

    public void movePlayer(int dx, int dy) {
        this.playerPanel.setLocation(this.playerPanel.getX() + dx * GamePanel.SCREEN_TILE_SIZE, this.playerPanel.getY() + dy * GamePanel.SCREEN_TILE_SIZE);
        this.playerPanel.repaint(); // Redessiner le joueur à sa nouvelle position
        //play animation, check collisions, etc.
    }
}