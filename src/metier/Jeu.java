package src.metier;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Jeu {

    private Joueur player;
    private Set<Ressources> ressources;

    private Set<Point> occupied;

    private static final int[][] DIRECTIONS = {
        {-1, -1}, {0, -1}, {1, -1},
        {-1,  0},          {1,  0},
        {-1,  1}, {0,  1}, {1,  1}
    };


    public Jeu() {
        this.player = new Joueur();
        this.ressources = new HashSet<Ressources>();
        this.occupied = new HashSet<Point>();

        chargerRessources("src/metier/map/test10x10.csv");
    }

    public Set<Ressources> getRessources() {
        return ressources;
    }

    public int getCurrentHP() { return this.player.getCurrentHP(); }

    public void reducePlayerHP(int amount) {
        this.player.reduceHP(amount);
        if( this.player.getCurrentHP() <= 0) {
            System.out.println("Le joueur est mort !");
        }
    }

    public void movePlayer(int targetX, int targetY) {

        ArrayList<Point> path = bfsToTarget(targetX, targetY);

        if (path == null || path.size() < 2) return;

        Point next = path.get(1); 

        this.player.setX(next.x);
        this.player.setY(next.y);
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && y >= 0 && x < 10 && y < 10; 
    }

    private boolean isNextToTarget(int x, int y, int tx, int ty) {
        return (Math.abs(x - tx) == 1 && y == ty)
            || (Math.abs(y - ty) == 1 && x == tx);
    }

    public ArrayList<Point> bfsToTarget(int targetX, int targetY) {

        int startX = this.player.getX();
        int startY = this.player.getY();

        boolean[][] visited = new boolean[10][10]; // taille de ta map
        java.util.Queue<Node> queue = new java.util.LinkedList<>();

        queue.add(new Node(startX, startY, null));
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (isNextToTarget(current.x, current.y, targetX, targetY)) {
                return buildPath(current);
            }

            for (int[] d : DIRECTIONS) {
                int nx = current.x + d[0];
                int ny = current.y + d[1];

                if (!isInside(nx, ny)) continue;
                if (visited[nx][ny]) continue;

                if (!playerCanMove(current.x, current.y, nx, ny)) continue;

                visited[nx][ny] = true;
                queue.add(new Node(nx, ny, current));
            }
        }

        return null; // aucun chemin trouvé
    }

    private ArrayList<Point> buildPath(Node end) {
        ArrayList<Point> path = new ArrayList<>();
        Node current = end;

        while (current != null) {
            path.add(new Point(current.x, current.y));
            current = current.parent;
        }

        java.util.Collections.reverse(path);
        return path;
    }

    private boolean isBlocked(int x, int y) {
        return occupied.contains(new Point(x, y));
    }

    private boolean playerCanMove(int fromX, int fromY, int toX, int toY) {

        int dx = toX - fromX;
        int dy = toY - fromY;

        // case d'arrivée bloquée
        if (isBlocked(toX, toY)) return false;

        // déplacement diagonal ?
        if (dx != 0 && dy != 0) {

            // cases adjacentes
            if (isBlocked(fromX + dx, fromY)
                && isBlocked(fromX, fromY + dy)) {
                return false;
            }
        }
        return true;
    }

    public void chargerRessources(String filename) {
        ressources = new HashSet<Ressources>();
        occupied = new java.util.HashSet<Point>();

        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File(filename))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 6) {
                    
                    String name = parts[0].trim();
                    typeRessource type = typeRessource.valueOf(parts[1].trim());
                    int maxHealth = Integer.parseInt(parts[2].trim());
                    int health = Integer.parseInt(parts[3].trim());
                    int x = Integer.parseInt(parts[4].trim());
                    int y = Integer.parseInt(parts[5].trim());

                    if (parts.length >= 8) {
                        int damage = Integer.parseInt(parts[6].trim());
                        int attackSpeed = Integer.parseInt(parts[7].trim());

                        ressources.add(new Ressources(name, type, maxHealth, health, x, y, damage, attackSpeed, (int) (Math.random() * 10) )); // resAmount est fixé à 10 pour l'instant
                    } 
                    else {
                        ressources.add(new Ressources(name, type, maxHealth, health, x, y, (int) (Math.random() * 10) )); // damage et attackSpeed sont fixés à 0 et 1 pour l'instant
                    }

                    occupied.add(new Point(x, y)); // Marquer la position comme occupée
                }
            }
            scanner.close();
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class Node {
        int x, y;
        Node parent;

        Node(int x, int y, Node parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }
}