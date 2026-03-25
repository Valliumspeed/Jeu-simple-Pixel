package src.metier;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Jeu {

    private int mapWidth;
    private int mapHeight;

    private Joueur player;
    private Set<Ressources> ressources;

    private Set<Point> occupied;

    private int timerAttaqueJoueur = 0;
    private int timerAttaqueEnnemi = 0; 
    private int timerRegenJoueur = 0;   

    private static final int[][] DIRECTIONS = {
        {-1, -1}, {0, -1}, {1, -1},
        {-1,  0},          {1,  0},
        {-1,  1}, {0,  1}, {1,  1}
    };


    public Jeu() {
        this.mapWidth = 10;
        this.mapHeight = 10;
        this.player = new Joueur();
        this.ressources = new HashSet<Ressources>();
        this.occupied = new HashSet<Point>();

        chargerRessources("src/metier/map/test10x10.csv");
        placerJoueurAuCentre();
    }

    public Set<Ressources> getRessources() {
        return ressources;
    }

    public int getCurrentHP() { return this.player.getCurrentHP(); }

    public Joueur getPlayer() {
        return this.player;
    }

    public double getCameraX() {
        return player.getX();
    }

    public double getCameraY() {
        return player.getY();
    }

    public int getMapWidth() {
        return this.mapWidth;
    }

    public int getMapHeight() {
        return this.mapHeight;
    }

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
        return x >= 0 && y >= 0 && x < this.mapWidth && y < this.mapHeight;
    }

    private boolean isNextToTarget(int x, int y, int tx, int ty) {
        return (Math.abs(x - tx) == 1 && y == ty)
            || (Math.abs(y - ty) == 1 && x == tx);
    }

    public ArrayList<Point> bfsToTarget(int targetX, int targetY) {
        int startX = (int) this.player.getX();
        int startY = (int) this.player.getY();

        boolean stopNextTo = (this.player.getCibleInteractive() != null);

        int[][] distance = new int[this.mapWidth][this.mapHeight];
        for (int x = 0; x < this.mapWidth; x++) {
            for (int y = 0; y < this.mapHeight; y++) {
                distance[x][y] = Integer.MAX_VALUE;
            }
        }

        java.util.PriorityQueue<Node> queue = new java.util.PriorityQueue<>(
            java.util.Comparator.comparingInt(n -> n.cost)
        );

        queue.add(new Node(startX, startY, null, 0));
        distance[startX][startY] = 0;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.cost > distance[current.x][current.y]) continue;

            if (stopNextTo) {
                if (isNextToTarget(current.x, current.y, targetX, targetY)) {
                    return buildPath(current);
                }
            } else {
                if (current.x == targetX && current.y == targetY) {
                    return buildPath(current);
                }
            }

            for (int[] d : DIRECTIONS) {
                int nx = current.x + d[0];
                int ny = current.y + d[1];

                if (!isInside(nx, ny)) continue;
                if (!playerCanMove(current.x, current.y, nx, ny)) continue;

                int moveCost = (d[0] != 0 && d[1] != 0) ? 14 : 10;
                int newCost = current.cost + moveCost;

                if (newCost >= distance[nx][ny]) continue;

                distance[nx][ny] = newCost;
                queue.add(new Node(nx, ny, current, newCost));
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

        // déplacement diagonal :
        // on interdit de "couper un coin" si une des deux cases latérales est bloquée
        if (dx != 0 && dy != 0) {
            if (isBlocked(fromX + dx, fromY) || isBlocked(fromX, fromY + dy)) {
                return false;
            }
        }
        return true;
    }

    private void placerJoueurAuCentre() {
        int centreX = this.mapWidth / 2;
        int centreY = this.mapHeight / 2;

        this.player.setX(centreX);
        this.player.setY(centreY);
        this.player.setDestX(centreX);
        this.player.setDestY(centreY);
        this.player.setEnMouvement(false);
        this.player.setCibleInteractive(null);
        this.player.setChemin(new ArrayList<Point>());
    }

    public void chargerRessources(String filename) {
        int maxWidth = 0;
        int maxHeight = 0;

        this.ressources = new HashSet<Ressources>();
        this.occupied = new java.util.HashSet<Point>();

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

                    if (x >= maxWidth) maxWidth = x + 1; 
                    if (y >= maxHeight) maxHeight = y + 1;

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
            this.mapWidth  = maxWidth;
            this.mapHeight = maxHeight;

            scanner.close();
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cliquerSurCase(int targetX, int targetY, Ressources cible) {
        // 1. On mémorise la destination et la cible (ennemi/arbre ou null)
        this.player.setDestX(targetX);
        this.player.setDestY(targetY);
        this.player.setEnMouvement(true);
        this.player.setCibleInteractive(cible);

        // 2. On calcule le chemin
        ArrayList<Point> path = bfsToTarget(targetX, targetY);

        if (path != null && path.size() > 1) {
            path.remove(0); // On retire la case actuelle du joueur
            this.player.setChemin(path); // Le joueur se met en mouvement
        } else {
            System.out.println("Chemin impossible !");
        }
    }

    public void update(int deltaTime) {
        // --- 1. DÉPLACEMENT DU JOUEUR ---
        this.movePlayer(deltaTime);

        // --- 2. LOGIQUE DE COMBAT / INTERACTION ---
        this.updateCombat(deltaTime);

        // --- RÉGÉNÉRATION DU JOUEUR ---
        this.updatePlayerRegen(deltaTime);
    }

    private void movePlayer(int deltaTime) {

        if (!this.player.isEnMouvement() || this.player.getChemin().isEmpty()) {
            this.player.setEnMouvement(false);
            return;
        }

        // On récupère la prochaine étape du chemin
        Point nextStep = this.player.getChemin().get(0);
        
        double speed = this.player.getSpeed(); 
    
        // LA CORRECTION EST ICI : on convertit en secondes
        double distanceAfaire = speed * (deltaTime / 1000.0);

        double currentX = this.player.getX();
        double currentY = this.player.getY();
        
        double targetX = nextStep.x;
        double targetY = nextStep.y;

        // --- Déplacement horizontal ---
        if (Math.abs(targetX - currentX) > distanceAfaire) {
            if (currentX < targetX) this.player.setX(currentX + distanceAfaire);
            else this.player.setX(currentX - distanceAfaire);
        } else {
            this.player.setX(targetX);
        }

        // --- Déplacement vertical ---
        if (Math.abs(targetY - currentY) > distanceAfaire) {
            if (currentY < targetY) this.player.setY(currentY + distanceAfaire);
            else this.player.setY(currentY - distanceAfaire);
        } else {
            this.player.setY(targetY);
        }

        // --- Arrivé à la case intermédiaire ? ---
        if (this.player.getX() == targetX && this.player.getY() == targetY) {
            // On a atteint cette case, on la retire de la liste pour passer à la suivante
            this.player.getChemin().remove(0);
            
            // Si c'était la dernière case, on s'arrête
            if (this.player.getChemin().isEmpty()) {
                this.player.setEnMouvement(false);
            }
        }
    }

    private void updateCombat(int deltaTime) {
        Ressources cible = this.player.getCibleInteractive();

        if (!this.player.isEnMouvement() && cible != null) {

            // Timer d'attaque du Player
            this.timerAttaqueJoueur += deltaTime;
            if (this.timerAttaqueJoueur >= this.player.getAttackSpeed()) {
                cible.reduceHP(this.player.getDamage());
                if (cible.getCurrentHP() <= 0) {
                // 1. On récupère les coordonnées EXACTES en entiers
                int gridX = (int) Math.round(cible.getX());
                int gridY = (int) Math.round(cible.getY());

                // 2. On retire la ressource de la liste
                this.ressources.remove(cible);

                // 3. On libère la case dans la grille de collision
                // On utilise une boucle pour être SÛR de trouver le point qui correspond
                this.occupied.removeIf(p -> p.x == gridX && p.y == gridY);

                // 4. Reset des états
                this.player.setCibleInteractive(null);
                this.timerAttaqueJoueur = 0;
                this.timerAttaqueEnnemi = 0;
                
                System.out.println("Ressource détruite en " + gridX + "," + gridY + ". Case libérée !");
            }
                this.timerAttaqueJoueur -= this.player.getAttackSpeed();
            }

            // Timer d'attaque de l'Ennemi
            if (cible.getType() == typeRessource.Enemy && cible.getCurrentHP() > 0) {
                this.timerAttaqueEnnemi += deltaTime;
                    
                if (this.timerAttaqueEnnemi >= 1000) { 
                    this.player.reduceHP(cible.getDamage());
                    this.timerAttaqueEnnemi -= 1000;
                }
            }
        }
        else {
            this.timerAttaqueJoueur = 0;
            this.timerAttaqueEnnemi = 0;
        }
    }

    private void updatePlayerRegen(int deltaTime) {
        if (this.player.getCurrentHP() < 100) {
            this.timerRegenJoueur += deltaTime;
            
            if (this.timerRegenJoueur >= 1000) {
                int nouveauxHp = Math.min(100, this.player.getCurrentHP() + 5);
                this.player.setCurrentHP(nouveauxHp);
                
                this.timerRegenJoueur -= 1000; 
            }
        } else {
            this.timerRegenJoueur = 0;
        }
    }

    private static class Node {
        int x, y;
        int cost;
        Node parent;

        Node(int x, int y, Node parent, int cost) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.cost = cost;
        }
    }
}