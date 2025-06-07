import java.util.ArrayList;

public class Grid {
    int m;
    int n;
    int[][] scz; // Special Cost Zones
    int[][] obs; // Obstacles

    Grid(int n, int m, int[][] scz, int[][] obs) {
        this.n = n; // Number of rows
        this.m = m; // Number of columns
        this.scz = scz; // Special Cost Zones
        this.obs = obs; // Obstacles
    }
    
    public ArrayList<int[]> getValidMoves(int x, int y) {
        // Returns a list of valid moves from position (x, y)
        ArrayList<int[]> validMoves = new ArrayList<>();
        
        if (x > 0 && !isObstacle(x - 1, y)) {
            validMoves.add(new int[] {x - 1, y});
        }
        if (x < n - 1 && !isObstacle(x + 1, y)) {
            validMoves.add(new int[] {x + 1, y});
        }
        if (y > 0 && !isObstacle(x, y - 1)) {
            validMoves.add(new int[] {x, y - 1});
        }
        if (y < m - 1 && !isObstacle(x, y + 1)) {
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

    public int getCost(int x, int y) {
        // Returns the cost of moving to (x, y)
        if (scz == null) return 1; // Default cost if no special zones

        for (int[] zone : scz) {
            if (zone[0] <= x && x <= zone[2] && zone[1] <= y && y <= zone[3]) {
                return zone[4]; // Return the cost from the special cost zone
            }
        }
        return 1; // Default cost if not in any special zone
    }
}
    