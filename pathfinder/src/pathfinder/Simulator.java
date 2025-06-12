package pathfinder;
import java.util.stream.Collectors;

public class Simulator {
    private Grid grid;
    private Pec pec;
    private Population population;
    private int observation;
    
    public Simulator(int n, int m, int xi, int yi, int xf, int yf, int[][] scz, int[][] obs, int tau, int v, int vmax, int k, int mu, int delta, int ro)
    {
        // Initialize the grid, pec, and individuals
        grid = new Grid(xi, yi, xf, yf, n, m, scz, obs);
        pec = new Pec(tau);
        population = new Population(vmax, k, mu, delta, ro);
        for (int i = 0; i < v; i++) {
            Individual individual = new Individual(population, grid);
            population.addIndividual(individual);
            pec.addEvent(new Move(individual.getMoveTime(), individual, pec));
            pec.addEvent(new Reproduction(individual.getReproductionTime(), individual, pec));
            pec.addEvent(new Death(individual.getDeathTime(), individual, pec));
        }

        observation = 0;
    }

    public void run()
    {
        int result;
        while ((result = pec.next()) != -1) {
            if (result == 1) {
                outputMidRun();
                observation++;
            }
        }
        outputMidRun();
        outputResults();
    }

    private void outputMidRun() {
        boolean isCost = population.isPathComplete();
        String bestPathString = population.getBestPath().stream()
            .map(coord -> "(" + coord[0] + ", " + coord[1] + ")")
            .collect(Collectors.joining(", ", "[", "]"));

        String output =
            "Observation " + observation + ":\n\t\t" +
                    "Present time:\t\t\t" + pec.getTime() + "\n\t\t" +
                    "Number of realized events:\t" + pec.getEventsCount() + "\n\t\t" +
                    "Population size:\t\t" + population.getSize() + "\n\t\t" +
                    "Final point has been hit:\t" + (isCost ? "yes" : "no") + "\n\t\t" +
                    "Path of the best fit:\t\t" + bestPathString + "\n\t\t" +
                    "Best path cost:\t\t\t" + (isCost ? population.getBestPathCost() : population.getBestComfort()) + "\n\t\t";

        System.out.println(output);
    }

    private void outputResults()
    {
        boolean isCost = population.isPathComplete();
        String bestPathString = population.getBestPath().stream()
            .map(coord -> "(" + coord[0] + ", " + coord[1] + ")")
            .collect(Collectors.joining(", ", "[", "]"));

        String output =
            "Best fit individual:\t" + bestPathString + " " +
            "with cost: " + (isCost ? population.getBestPathCost() : population.getBestComfort()) + "\n";

        System.out.println(output);
    }

    public void printConfig()
    {
        int[][] scz = grid.getScz();
        int[][] obs = grid.getObs();
        System.out.printf("%d %d %d %d %d %d %d %d %d %d %d %d %d %d\n", grid.getN(), grid.getM(), grid.getStartCoordinates()[0], grid.getStartCoordinates()[1],
                                                grid.getEndCoordinates()[0], grid.getEndCoordinates()[1], scz.length, obs.length, pec.getTau(),
                                                population.getSize(), population.getK(), population.getMu(), population.getDelta(), population.getRo());
        System.out.println("Special Cost Zones:");
        for (int[] zone : scz) {
            System.out.printf("%d %d %d %d %d\n", zone[0], zone[1], zone[2], zone[3], zone[4]);
        }
        System.out.println("Obstacles:");
        for (int[] obstacle : obs) {
            System.out.printf("%d %d\n", obstacle[0], obstacle[1]);
        }

        System.out.print("\n\n");

    }
}
