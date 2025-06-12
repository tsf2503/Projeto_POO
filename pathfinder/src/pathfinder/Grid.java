/*
 * POO - Instituto Superior Técnico
 *
 * Guilherme Dias
 * Francisco Coelho
 * João Oliveira
 * Tiago Ferreira
 */

package pathfinder;

import java.util.ArrayList;

/**
 * Represents a grid for pathfinding, including start/end points, obstacles, and special cost zones.
 */
public class Grid {
    private int m; // Number of columns
    private int n; // Number of rows
    private int xi, yi; // Start coordinates
    private int xf, yf; // End coordinates
    private int[][] scz; // Special Cost Zones: each zone is [x1, y1, x2, y2, cost]
    private int[][] obs; // Obstacles: each obstacle is [x, y]
    private int cmax; // Maximum cost among all special cost zones

    /**
     * Constructs a Grid with specified start/end coordinates, size, special cost zones, and obstacles.
     *
     * @param xi  Start x-coordinate
     * @param yi  Start y-coordinate
     * @param xf  End x-coordinate
     * @param yf  End y-coordinate
     * @param n   Number of rows
     * @param m   Number of columns
     * @param scz Special cost zones
     * @param obs Obstacles
     */
    Grid(int xi, int yi, int xf, int yf, int n, int m, int[][] scz, int[][] obs) {
        this.xi = xi;
        this.yi = yi;
        this.xf = xf;
        this.yf = yf;
        this.n = n;
        this.m = m;
        this.scz = scz;
        this.obs = obs;

        this.cmax = 0;
        if (scz.length == 0) {
            cmax = 1;
        } else {
            for (int[] zone : scz) {
                if (zone[4] > cmax) {
                    cmax = zone[4];
                }
            }
        }
    }

    /**
     * Returns the sum of the number of rows and columns.
     *
     * @return n + m
     */
    public int getSize() {
        return n + m;
    }

    /**
     * Returns the maximum cost among all special cost zones.
     *
     * @return Maximum cost
     */
    public int getCmax() {
        return cmax;
    }

    /**
     * Returns a list of valid moves (adjacent cells) from the given position, excluding obstacles.
     *
     * @param x Current x-coordinate
     * @param y Current y-coordinate
     * @return List of valid moves as int[] {x, y}
     */
    public ArrayList<int[]> getValidMoves(int x, int y) {
        ArrayList<int[]> validMoves = new ArrayList<>();

        if (x > 1 && !isObstacle(x - 1, y)) {
            validMoves.add(new int[] {x - 1, y});
        }
        if (x < n && !isObstacle(x + 1, y)) {
            validMoves.add(new int[] {x + 1, y});
        }
        if (y > 1 && !isObstacle(x, y - 1)) {
            validMoves.add(new int[] {x, y - 1});
        }
        if (y < m && !isObstacle(x, y + 1)) {
            validMoves.add(new int[] {x, y + 1});
        }

        return validMoves;
    }

    /**
     * Checks if the given position is an obstacle.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if (x, y) is an obstacle, false otherwise
     */
    private boolean isObstacle(int x, int y) {
        if (obs == null) return false;

        for (int[] obstacle : obs) {
            if (obstacle[0] == x && obstacle[1] == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the cost of moving from (xi, yi) to (xf, yf), considering special cost zones.
     *
     * @param xi Start x-coordinate
     * @param yi Start y-coordinate
     * @param xf End x-coordinate
     * @param yf End y-coordinate
     * @return Movement cost
     */
    public int getCost(int xi, int yi, int xf, int yf) {
        if (scz == null) return 1;

        // Ensure xi <= xf and yi <= yf for comparison
        if (xi > xf) {
            xi = xi ^ xf;
            xf = xi ^ xf;
            xi = xi ^ xf;
        }
        if (yi > yf) {
            yi = yi ^ yf;
            yf = yi ^ yf;
            yi = yi ^ yf;
        }

        int max = 1;
        for (int[] zone : scz) {
            if ( zone[4] > max && ( ((xi == zone[0] || xi == zone[2]) && zone[1] <= yi && yi <= zone[3]) ||
                                    ((yi == zone[1] || yi == zone[3]) && zone[0] <= xi && xi <= zone[2]) ) ) {
                max = zone[4];
            }
        }
        return max;
    }

    /**
     * Returns the start coordinates as an array [xi, yi].
     *
     * @return Start coordinates
     */
    public int[] getStartCoordinates() {
        return new int[]{xi, yi};
    }

    /**
     * Returns the end coordinates as an array [xf, yf].
     *
     * @return End coordinates
     */
    public int[] getEndCoordinates() {
        return new int[]{xf, yf};
    }

    /**
     * Returns the number of columns.
     *
     * @return Number of columns
     */
    public int getM() {
        return m;
    }

    /**
     * Returns the number of rows.
     *
     * @return Number of rows
     */
    public int getN() {
        return n;
    }

    /**
     * Returns the special cost zones.
     *
     * @return Special cost zones
     */
    public int[][] getScz() {
        return scz;
    }

    /**
     * Returns the obstacles.
     *
     * @return Obstacles
     */
    public int[][] getObs() {
        return obs;
    }

    /**
     * Returns a string representation of the grid.
     *
     * @return String describing the grid
     */
    @Override
    public String toString() {
        return "Grid{" +
                "m=" + m +
                ", n=" + n +
                ", start=(" + xi + ", " + yi + ")" +
                ", end=(" + xf + ", " + yf + ")" +
                ", cmax=" + cmax +
                '}';
    }
}