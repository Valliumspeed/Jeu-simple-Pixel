package src.frame;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class UIPanel extends JPanel {

    private FrameJeu parent;

    public UIPanel(FrameJeu parent) {
        this.parent = parent;

        setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
        setOpaque(false); // rendre le panneau transparent
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void drawUI(Graphics g, int health) throws Exception {
        drawPlayerHealth(g, health);
    }

    public void drawPlayerHealth(Graphics g,int hp) {
        /// HP Bar - centered bottom
        int barWidth = 100;
        int barHeight = 12;
        int x = (GamePanel.WIDTH - barWidth) / 2;
        int y = GamePanel.HEIGHT - 30;
        
        int maxHP = 100;
        int currentHP = hp;
        int hpWidth = (int) ((currentHP / (float) maxHP) * barWidth);
        
        // Draw red background
        g.setColor(Color.RED);
        g.fillRect(x, y, barWidth, barHeight);
        
        // Draw green HP (only if < 100%)
        if (currentHP < maxHP) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, hpWidth, barHeight);
        }
        
        // Draw border
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
        
        // Draw text
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 8));
        String text = currentHP + "/" + maxHP;
        int textX = x + (barWidth - g.getFontMetrics().stringWidth(text)) / 2;
        int textY = y + 10;
        
        g.setColor(Color.BLACK);
        g.drawString(text, textX - 1, textY);
        g.drawString(text, textX + 1, textY);
        g.drawString(text, textX, textY - 1);
        g.drawString(text, textX, textY + 1);
        
        g.setColor(Color.WHITE);
        g.drawString(text, textX, textY);
    }
}