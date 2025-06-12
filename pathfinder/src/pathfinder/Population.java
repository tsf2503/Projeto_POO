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
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Represents a population of Individuals for a genetic algorithm.
 * Manages population size, selection, and epidemic removal.
 */
public class Population {

    // Set of individuals in the population
    private HashSet<Individual> individuals;

    // Maximum allowed population size
    private int maxSize;

    // Best path and comfort metrics found so far
    private boolean isPathComplete;
    private int bestPathCost;
    private ArrayList<int[]> bestPath;
    private double bestComfort;

    // Genetic algorithm parameters
    private int k, mu, delta, ro;

    /**
     * Constructs a Population with given parameters.
     * @param maxSize Maximum population size
     * @param k Number of fittest individuals to retain during epidemic
     * @param mu Mutation rate parameter
     * @param delta Delta parameter for algorithm
     * @param ro Ro parameter for algorithm
     */
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

    /**
     * Returns the current size of the population.
     * @return Number of individuals
     */
    public int getSize() {
        return individuals.size();
    }

    /**
     * Checks if a complete path has been found.
     * @return True if path is complete
     */
    public boolean isPathComplete() {
        return isPathComplete;
    }

    /**
     * Gets the cost of the best path found.
     * @return Best path cost
     */
    public int getBestPathCost() {
        return bestPathCost;
    }

    /**
     * Gets the best path found.
     * @return List of coordinates representing the best path
     */
    public ArrayList<int[]> getBestPath() {
        return bestPath;
    }

    /**
     * Gets the comfort value of the best individual.
     * @return Best comfort value
     */
    public double getBestComfort() {
        return bestComfort;
    }

    /**
     * Gets the k parameter.
     * @return k value
     */
    public int getK() {
        return k;
    }

    /**
     * Gets the mu parameter.
     * @return mu value
     */
    public int getMu() {
        return mu;
    }

    /**
     * Gets the delta parameter.
     * @return delta value
     */
    public int getDelta() {
        return delta;
    }

    /**
     * Gets the ro parameter.
     * @return ro value
     */
    public int getRo() {
        return ro;
    }

    /**
     * Gets the maximum size of the population.
     * @return Maximum population size
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Sets whether a complete path has been found.
     * @param isPathComplete True if path is complete
     */
    public void setIsPathComplete(boolean isPathComplete) {
        this.isPathComplete = isPathComplete;
    }

    /**
     * Sets the cost of the best path.
     * @param bestPathCost Best path cost
     */
    public void setBestPathCost(int bestPathCost) {
        this.bestPathCost = bestPathCost;
    }

    /**
     * Sets the best path found.
     * @param bestPath List of coordinates representing the best path
     */
    public void setBestPath(ArrayList<int[]> bestPath) {
        this.bestPath = new ArrayList<>(bestPath);
    }

    /**
     * Sets the best comfort value found.
     * @param bestComfort Best comfort value
     */
    public void setBestComfort(double bestComfort) {
        this.bestComfort = bestComfort;
    }

    /**
     * Checks if the population contains a given individual.
     * @param individual The individual to check
     * @return True if present
     */
    public boolean containsIndividual(Individual individual) {
        return individuals.contains(individual);
    }

    /**
     * Adds an individual to the population.
     * Triggers epidemic if population exceeds maxSize.
     * @param individual The individual to add
     */
    public void addIndividual(Individual individual) {
        individuals.add(individual);
        if (individuals.size() > maxSize) {
            epidemic(); // Remove individuals based on the epidemic logic
        }
    }

    /**
     * Removes an individual from the population.
     * @param individual The individual to remove
     */
    public void removeIndividual(Individual individual) {
        individuals.remove(individual);
    }

    /**
     * Returns the k fittest individuals in the population.
     * @param k Number of individuals to select
     * @return Set of k fittest individuals
     */
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

    /**
     * Performs an epidemic event, removing less fit individuals
     * not in the k fittest, based on their comfort value.
     */
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

    /**
     * Returns a string representation of the population.
     * @return String describing the population
     */
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