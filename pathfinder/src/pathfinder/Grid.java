package pathfinder;
import java.util.ArrayList;

public class Grid {
    private int m;
    private int n;
    private int xi, yi; // Start coordinates
    private int xf, yf; // End coordinates
    private int[][] scz; // Special Cost Zones
    private int[][] obs; // Obstacles

    private int cmax;

    Grid(int xi, int yi, int xf, int yf, int n, int m, int[][] scz, int[][] obs) {
        this.xi = xi; // Start x-coordinate
        this.yi = yi; // Start y-coordinate
        this.xf = xf; // End x-coordinate
        this.yf = yf; // End y-coordinate
        this.n = n; // Number of rows
        this.m = m; // Number of columns
        this.scz = scz; // Special Cost Zones
        this.obs = obs; // Obstacles

        this.cmax = 0; // Maximum cost, initialized to 0
        if (scz.length == 0) {
            cmax = 1;
        } else {
            for (int[] zone : scz) {
                if (zone[4] > cmax) {
                    cmax = zone[4]; // Update maximum cost based on special zones
                }
            }
        }
    }

    public int getSize() {
        return n + m;
    }

    public int getCmax() {
        return cmax; // Returns the maximum cost from special zones
    }
    
    public ArrayList<int[]> getValidMoves(int x, int y) {
        // Returns a list of valid moves from position (x, y)
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
    
    private boolean isObstacle(int x, int y) {
        // Check if (x, y) is an obstacle
        if (obs == null) return false; // No obstacles

        for (int[] obstacle : obs) {
            if (obstacle[0] == x && obstacle[1] == y) {
                return true; // It's an obstacle
            }
        }
        return false; // Not an obstacle
    }

    public int getCost(int xi, int yi, int xf, int yf) {
        // Returns the cost of moving from (xi, yi) to (xf, yf)
        if (scz == null) return 1; // Default cost if no special zones

        if (xi > xf) {
            xi = xi ^ xf;
            xf = xi ^ xf;
            xi = xi ^ xf; // Swap xi and xf
        }
        if (yi > yf) {
            yi = yi ^ yf;
            yf = yi ^ yf;
            yi = yi ^ yf; // Swap yi and yf
        }

        int max = 1;
        for (int[] zone : scz) {
            if ( zone[4] > max && ( ((xi == zone[0] || xi == zone[2]) && zone[1] <= yi && yi <= zone[3]) ||
                                    ((yi == zone[1] || yi == zone[3]) && zone[0] <= xi && xi <= zone[2]) ) ) {
                max = zone[4];
            }
        }
        return max; // Default cost if not in any special zone
    }

    public int[] getStartCoordinates() {
        return new int[]{xi, yi};
    }

    public int[] getEndCoordinates() {
        return new int[]{xf, yf};
    }

    public int getM() {
        return m; // Returns the number of rows
    }

    public int getN() {
        return n; // Returns the number of columns
    }

    public int[][] getScz() {
        return scz; // Returns the special cost zones
    }

    public int[][] getObs() {
        return obs; // Returns the obstacles
    }

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
    