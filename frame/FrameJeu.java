import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class FrameJeu extends JFrame {
    
    public Controleur controleur;

    public JLayeredPane layeredPanel;

    public GamePanel gamePanel;
    public UIPanel uiPanel;

    public FrameJeu(Controleur controleur) {

        this.controleur = controleur;

        setTitle("Frame Jeu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setResizable(false); // Rendre la fenêtre non changeable

        this.layeredPanel = new JLayeredPane();

        this.gamePanel = new GamePanel(this);
        this.gamePanel.setBounds(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        this.uiPanel = new UIPanel(this);
        this.uiPanel.setBounds(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        this.layeredPanel.add(this.gamePanel  , Integer.valueOf(0));
        this.layeredPanel.add(this.uiPanel, Integer.valueOf(1));

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

    public void drawPlayerHealth(Graphics g) {
        this.uiPanel.drawPlayerHealth(g);
    }

    public void move() {
        this.gamePanel.setLocation(- GamePanel.SCREEN_TILE_SIZE,0);
    }
}