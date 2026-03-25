package src.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import src.controleur.Controleur;
import src.metier.Ressources;

public class FrameJeu extends JFrame {
    
    private Controleur ctrl;

    private JLayeredPane layeredPanel;

    private GamePanel gamePanel;
    private UIPanel uiPanel;

    public FrameJeu(Controleur ctrl) {

        this.ctrl = ctrl;

        setTitle("Frame Jeu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false); // La fenêtre ne dépasse jamais la taille visible de la map

        this.layeredPanel = new JLayeredPane();

        int panelWidth = GamePanel.getPreferredWidth(this.ctrl.getMetier());
        int panelHeight = GamePanel.getPreferredHeight(this.ctrl.getMetier());

        this.gamePanel = new GamePanel(this, this.ctrl.getMetier());
        this.gamePanel.setBounds(0, 0, panelWidth, panelHeight);
        this.gamePanel.initialiserCamera();

        this.uiPanel = new UIPanel(this);
        this.uiPanel.setBounds(0, 0, panelWidth, panelHeight);

        this.layeredPanel.add(this.gamePanel  , Integer.valueOf(0));
        this.layeredPanel.add(this.uiPanel, Integer.valueOf(1));

        this.layeredPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.layeredPanel.setBackground(Color.BLACK);

        setContentPane(this.layeredPanel);

        pack(); // utilise la taille du panel
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public int getCurrentHP() {
        return this.ctrl.getCurrentHP(); // Valeur temporaire, à remplacer par la logique réelle
    }

    public void refresh() {
        this.gamePanel.repaint();
        this.uiPanel.repaint();
    }
}
