import java.util.ArrayList;

// TODO: implement Individual class
public class Individual {

    private Population population;
    private Grid grid;

    private int pathCost;
    private ArrayList<int[]> path;

    private double comfort;

    private Individual(int pathCost, int pathLength, double comfort, ArrayList<int[]> path, Population population, Grid grid) {
        this.pathCost = pathCost;
        this.path = path;
        this.comfort = comfort;

        this.population = population;
        this.grid = grid;
    }

    Individual(Population population, Grid grid) {
        this(0, 0, 0, new ArrayList<>(), population, grid);

        this.path.add(new int[] {grid.getStartCoordinates()[0], grid.getStartCoordinates()[1]});
        this.comfort = getComfort();
    }

    public boolean isAlive() {
        return population.containsIndividual(this);
    }

    private int getDistToFinish() {
        return Math.abs(path.getLast()[0] - grid.getEndCoordinates()[0])
        + Math.abs(path.getLast()[1] - grid.getEndCoordinates()[1]);
    }

    private double getComfort() {
        return Math.pow(1 - ((pathCost - getPathSize() + 2.0) / ((grid.getCmax() - 1.0) * getPathSize() + 3.0)), population.getK()) *
                Math.pow(1 - getDistToFinish() / (grid.getSize() + 1.0), population.getK());
    }

    private int getPathSize() {
        return path.size();
    }

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

    public double getDeathTime() {
        return (1 - Math.log(1 - comfort)) * population.getMu();
    }

    public double getMoveTime() {
        return (1 - Math.log(comfort)) * population.getDelta();
    }

    public double getReproductionTime() {
        return (1 - Math.log(comfort)) * population.getRo();
    }


    public void death() {
        population.removeIndividual(this);
    }

    public void move() {
        ArrayList<int[]> validMoves = grid.getValidMoves(path.getLast()[0], path.getLast()[1]);

        int randomIndex = (int) (Math.random() * validMoves.size());
        int[] nextMove = validMoves.get(randomIndex);
        // TODO se nao se mexer (validMoves tamanho 0) morre?
        int[] newPos = new int[] {nextMove[0], nextMove[1]};

        if (path.contains(newPos)) {
            // If the new position is already in the path, delete cycle
            chronoBreak(newPos[0], newPos[1]);
        }
        else {
            pathCost += grid.getCost(path.getLast()[0], path.getLast()[1], nextMove[0], nextMove[1]);
            path.add(new int[] {newPos[0], newPos[1]});
        }

        comfort = getComfort();

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
                population.setBestComfort(comfort);
            }
            if (pathCost < population.getBestPathCost() || population.getBestPath().isEmpty()) {
                population.setBestPathCost(pathCost);
                population.setBestPath(path);
                population.setBestComfort(comfort);
            }
        }
    }

    public Individual reproduce() {
        Individual child = new Individual(pathCost, getPathSize(), comfort, new ArrayList<int[]>(path), population, grid);

        int lastPosIndex = (int) Math.ceil(getPathSize() * (0.9 + this.comfort * 0.1));

        child.chronoBreak(path.get(lastPosIndex - 1)[0], path.get(lastPosIndex - 1)[1]);
        population.addIndividual(child);

        return child;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "pathCost=" + pathCost +
                ", pathLength=" + getPathSize() +
                ", comfort=" + comfort +
                ", path=" + path +
                '}';
    }
}
