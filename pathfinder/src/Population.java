import java.util.ArrayList;
import java.util.HashSet;

public class Population {

    private HashSet<Individual> individuals;

    private int size;
    private int maxSize;

    private boolean isPathComplete;
    private int bestPathCost;
    private ArrayList<int[]> bestPath;
    private double bestComfort;

    private int k, mu, delta, ro;

    public Population(int size, int maxSize, int k, int mu, int delta, int ro, Grid grid) {
        this.size = size;
        this.maxSize = maxSize;
        this.k = k;
        this.mu = mu;
        this.delta = delta;
        this.ro = ro;

        individuals = new HashSet<>(size);
        isPathComplete = false;
        bestPathCost = Integer.MAX_VALUE;
        bestPath = new ArrayList<>();
        bestComfort = 0;

    }

    public int getSize() {
        return size;
    }

    public boolean isPathComplete() {
        return isPathComplete;
    }

    public int getBestPathCost() {
        return bestPathCost;
    }

    public ArrayList<int[]> getBestPath() {
        return bestPath;
    }

    public double getBestComfort() {
        return bestComfort;
    }


    public int getK() {
        return k;
    }

    public int getMu() {
        return mu;
    }

    public int getDelta() {
        return delta;
    }

    public int getRo() {
        return ro;
    }

    public void setIsPathComplete(boolean isPathComplete) {
        this.isPathComplete = isPathComplete;
    }

    public void setBestPathCost(int bestPathCost) {
        this.bestPathCost = bestPathCost;
    }

    public void setBestPath(ArrayList<int[]> bestPath) {
        this.bestPath = bestPath;
    }

    public void setBestComfort(double bestComfort) {
        this.bestComfort = bestComfort;
    }

    public void addIndividual(Individual individual) {
        if (individuals.size() < maxSize) {
            individuals.add(individual);
            size++;
        } else {
            // TODO Epidemic Event: Remove the worst individuals
        }
    }

    public void removeIndividual(Individual individual) {
        if (individuals.remove(individual)) {
            size--;
        } else {
            // TODO Handle case where individual is not found
            throw new IllegalArgumentException("Individual not found in population.");
        }
    }

}