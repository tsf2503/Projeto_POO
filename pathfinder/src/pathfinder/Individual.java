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
import java.util.stream.Collectors;

/**
 * Represents an individual in the pathfinding population.
 * Each individual maintains its own path, cost, and comfort metrics,
 * and interacts with the population and grid to evolve solutions.
 */
public class Individual {

    private Population population;
    private Grid grid;

    private int pathCost;
    private ArrayList<int[]> path;

    private double comfort;

    /**
     * Constructs an Individual with specified parameters.
     * Used internally for cloning and reproduction.
     *
     * @param pathCost   The total cost of the path so far.
     * @param pathLength The length of the path.
     * @param comfort    The comfort value of the individual.
     * @param path       The path taken as a list of coordinates.
     * @param population The population this individual belongs to.
     * @param grid       The grid on which the individual moves.
     */
    private Individual(int pathCost, int pathLength, double comfort, ArrayList<int[]> path, Population population, Grid grid) {
        this.pathCost = pathCost;
        this.path = path;
        this.comfort = comfort;

        this.population = population;
        this.grid = grid;
    }

    /**
     * Constructs a new Individual at the start position.
     *
     * @param population The population this individual belongs to.
     * @param grid       The grid on which the individual moves.
     */
    Individual(Population population, Grid grid) {
        this(0, 0, 0, new ArrayList<>(), population, grid);

        this.path.add(new int[] {grid.getStartCoordinates()[0], grid.getStartCoordinates()[1]});
        updateComfort();
    }

    /**
     * Checks if the individual is still alive (present in the population).
     *
     * @return true if alive, false otherwise.
     */
    public boolean isAlive() {
        return population.containsIndividual(this);
    }

    /**
     * Calculates the Manhattan distance from the current position to the finish.
     *
     * @return Distance to the finish.
     */
    private int getDistToFinish() {
        return Math.abs(path.getLast()[0] - grid.getEndCoordinates()[0])
        + Math.abs(path.getLast()[1] - grid.getEndCoordinates()[1]);
    }

    /**
     * Updates the comfort value based on the current path and cost.
     */
    private void updateComfort() {
        comfort = Math.pow(1 - ((pathCost - getPathSize() + 2.0) / ((grid.getCmax() - 1.0) * getPathSize() + 3.0)), population.getK()) *
                Math.pow(1 - getDistToFinish() / (grid.getSize() + 1.0), population.getK());
    }

    /**
     * Returns the current comfort value.
     *
     * @return Comfort value.
     */
    public double getComfort() {
        return comfort;
    }

    /**
     * Returns the current path size.
     *
     * @return Path size.
     */
    private int getPathSize() {
        return path.size();
    }

    /**
     * Removes cycles in the path by breaking at the specified coordinates.
     *
     * @param x X coordinate to break at.
     * @param y Y coordinate to break at.
     */
    private void chronoBreak(int x, int y) {
        int cycleStartIndex;

        for (cycleStartIndex = 0; cycleStartIndex < path.size(); cycleStartIndex++) {
            if (path.get(cycleStartIndex)[0] == x && path.get(cycleStartIndex)[1] == y) {
                break;
            }
        }

        for (int i = path.size() - 1; i > cycleStartIndex; i--) {
            pathCost -= grid.getCost(path.get(i)[0], path.get(i)[1], path.get(i - 1)[0], path.get(i - 1)[1]);
            path.remove(i);
        }
    }

    /**
     * Calculates the time until this individual dies.
     *
     * @return Death time.
     */
    public double getDeathTime() {
        return (1 - Math.log(1 - comfort)) * population.getMu();
    }

    /**
     * Calculates the time until this individual moves.
     *
     * @return Move time.
     */
    public double getMoveTime() {
        return (1 - Math.log(comfort)) * population.getDelta();
    }

    /**
     * Calculates the time until this individual reproduces.
     *
     * @return Reproduction time.
     */
    public double getReproductionTime() {
        return (1 - Math.log(comfort)) * population.getRo();
    }

    /**
     * Removes this individual from the population (kills it).
     */
    public void death() {
        if (isAlive())
            population.removeIndividual(this);
    }

    /**
     * Moves the individual to a new position, updates path and comfort,
     * and updates the population's best path if necessary.
     */
    public void move() {
        ArrayList<int[]> validMoves = grid.getValidMoves(path.getLast()[0], path.getLast()[1]);

        int randomIndex = (int) (Math.random() * validMoves.size());
        int[] nextMove = validMoves.get(randomIndex);
        int[] newPos = new int[] {nextMove[0], nextMove[1]};

        if (path.contains(newPos)) {
            // If the new position is already in the path, delete cycle
            chronoBreak(newPos[0], newPos[1]);
        }
        else {
            pathCost += grid.getCost(path.getLast()[0], path.getLast()[1], nextMove[0], nextMove[1]);
            path.add(new int[] {newPos[0], newPos[1]});
        }

        updateComfort();

        if (comfort > population.getBestComfort()) {
            population.setBestPathCost(pathCost);
            population.setBestPath(path);
            population.setBestComfort(comfort);
        }

        if (path.getLast()[0] == grid.getEndCoordinates()[0] && path.getLast()[1] == grid.getEndCoordinates()[1]) {
            if (!population.isPathComplete()) {
                population.setIsPathComplete(true);
                population.setBestPathCost(pathCost);
                population.setBestPath(path);
            }
            else if (pathCost < population.getBestPathCost()) {
                population.setBestPathCost(pathCost);
                population.setBestPath(path);
            }
        }
    }

    /**
     * Creates a child individual by cloning and truncating the path.
     *
     * @return The new child individual.
     */
    public Individual reproduce() {
        Individual child = new Individual(pathCost, getPathSize(), comfort, new ArrayList<int[]>(path), population, grid);

        int lastPosIndex = (int) Math.ceil(getPathSize() * (0.9 + this.comfort * 0.1));

        child.chronoBreak(path.get(lastPosIndex - 1)[0], path.get(lastPosIndex - 1)[1]);
        population.addIndividual(child);

        return child;
    }

    /**
     * Returns a string representation of the individual, including path and metrics.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        String pathString = path.stream()
            .map(coord -> "(" + coord[0] + ", " + coord[1] + ")")
            .collect(Collectors.joining(", ", "[", "]"));
        return "Individual{" +
                "pathCost=" + pathCost +
                ", pathLength=" + path.size() +
                ", comfort=" + comfort +
                ", path=" + pathString +
                '}';
    }
}
