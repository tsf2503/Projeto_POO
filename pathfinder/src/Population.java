import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class Population {

    private HashSet<Individual> individuals;

    private int maxSize;

    private boolean isPathComplete;
    private int bestPathCost;
    private ArrayList<int[]> bestPath;
    private double bestComfort;

    private int k, mu, delta, ro;

    public Population(int maxSize, int k, int mu, int delta, int ro) {
        this.maxSize = maxSize;
        this.k = k;
        this.mu = mu;
        this.delta = delta;
        this.ro = ro;

        individuals = new HashSet<>();
        isPathComplete = false;
        bestPathCost = Integer.MAX_VALUE;
        bestPath = new ArrayList<>();
        bestComfort = 0;

    }

    public int getSize() {
        return individuals.size();
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
        this.bestPath = new ArrayList<>(bestPath);
    }

    public void setBestComfort(double bestComfort) {
        this.bestComfort = bestComfort;
    }

    public boolean containsIndividual(Individual individual) {
        return individuals.contains(individual);
    }

    public void addIndividual(Individual individual) {
        individuals.add(individual);
        if (individuals.size() > maxSize) {
            epidemic(); // Remove individuals based on the epidemic logic
        }
    }

    public void removeIndividual(Individual individual) {
        if (individuals.remove(individual)) {
            // Nothing to do here
        } else {
            // TODO Handle case where individual is not found
            throw new IllegalArgumentException("Individual not found in population.");
        }
    }

    private HashSet<Individual> getKFittestIndividuals(int k) {
        PriorityQueue<Individual> kFittestIndividuals = new PriorityQueue<>(
                (i1, i2) -> Double.compare(i2.getComfort(), i1.getComfort())
        );

        for (Individual individual : individuals) {
            kFittestIndividuals.offer(individual);
            if (kFittestIndividuals.size() > k) {
                kFittestIndividuals.poll(); // Remove the least fit individual
            }
        }

        return new HashSet<>(kFittestIndividuals);

    }

    private void epidemic() {

        HashSet<Individual> kFittest = getKFittestIndividuals(5);
        Random random = new Random();

        Iterator<Individual> iterator = individuals.iterator();
        while (iterator.hasNext()) {
            Individual individual = iterator.next();
            if (!kFittest.contains(individual) && random.nextDouble() > individual.getComfort()) {
                iterator.remove(); // Remove the individual if not in the k-fittest
            }
        }

    }

    @Override
    public String toString() {
        return "Population{" +
                "size=" + individuals.size() +
                ", maxSize=" + maxSize +
                ", isPathComplete=" + isPathComplete +
                ", bestPathCost=" + bestPathCost +
                ", bestPath=" + bestPath +
                ", bestComfort=" + bestComfort +
                ", k=" + k +
                ", mu=" + mu +
                ", delta=" + delta +
                ", ro=" + ro +
                ", individuals=" + individuals +
                "}";
    }

}