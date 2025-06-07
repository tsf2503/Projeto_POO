import java.util.ArrayList;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Simulator {
    Grid grid;
    Pec pec;
    ArrayList<Individual> individuals;
    
    int vmax;
    
    Simulator(String[] args)
    {
        int n = 0, m = 0, xi = 0, yi = 0, xf = 0, yf = 0, n_scz = 0, n_obs = 0, tau = 0, v = 0, k = 0, mu = 0, delta = 0, ro = 0;
        int[][] scz = null, obs = null;
        
        if (args[0] == "-r") {
            try {
                n = Integer.parseInt(args[1]);
                m = Integer.parseInt(args[2]);
                xi = Integer.parseInt(args[3]);
                yi = Integer.parseInt(args[4]);
                xf = Integer.parseInt(args[5]);
                yf = Integer.parseInt(args[6]);
                n_scz = Integer.parseInt(args[7]);
                n_obs = Integer.parseInt(args[8]);
                tau = Integer.parseInt(args[9]);
                v = Integer.parseInt(args[10]);
                vmax = Integer.parseInt(args[11]);
                k = Integer.parseInt(args[12]);
                mu = Integer.parseInt(args[13]);
                delta = Integer.parseInt(args[14]);
                ro = Integer.parseInt(args[15]);
                
            } catch (NumberFormatException e) {
                System.out.println("Error: Incorrect arguments");
                printHelp();
                System.exit(1);
            }

            // Create random Special Cost Zones and Obstacles
            scz = new int[n_scz][5];
            obs = new int[n_obs][2];

            Random r = new Random();
            for (int i = 0; i < n_scz; i++) {
                scz[i][0] = r.nextInt(m) + 1; // xn
                scz[i][1] = r.nextInt(n) + 1; // yn

                scz[i][2] = r.nextInt(m) + 1; // xn'
                scz[i][3] = r.nextInt(n) + 1; // yn'

                scz[i][4] = r.nextInt(10) + 1; // cost
            }

            for (int i = 0; i < n_obs; i++) {
                obs[i][0] = r.nextInt(m) + 1; // x
                obs[i][1] = r.nextInt(n) + 1; // y
            }
        }
        else if (args[0] == "-f") {
            File inputFile = new File(args[1]);
            if (!inputFile.exists()) {
                System.out.println("Error: File does not exist");
                printHelp();
                System.exit(1);
            }

            try {
                Scanner scanner = new Scanner(inputFile);
                n = scanner.nextInt();
                m = scanner.nextInt();
                xi = scanner.nextInt();
                yi = scanner.nextInt();
                xf = scanner.nextInt();
                yf = scanner.nextInt();
                n_scz = scanner.nextInt();
                n_obs = scanner.nextInt();
                tau = scanner.nextInt();
                v = scanner.nextInt();
                vmax = scanner.nextInt();
                k = scanner.nextInt();
                mu = scanner.nextInt();
                delta = scanner.nextInt();
                ro = scanner.nextInt();

                scz = new int[n_scz][5];
                obs = new int[n_obs][2];

                scanner.nextLine(); 
                scanner.nextLine(); 

                for (int i = 0; i < n_scz; i++) {
                    scz[i][0] = scanner.nextInt(); // xn
                    scz[i][1] = scanner.nextInt(); // yn
                    scz[i][2] = scanner.nextInt(); // xn'
                    scz[i][3] = scanner.nextInt(); // yn'
                    scz[i][4] = scanner.nextInt(); // cost
                }

                scanner.nextLine(); 
                scanner.nextLine();

                for (int i = 0; i < n_obs; i++) {
                    obs[i][0] = scanner.nextInt(); // x
                    obs[i][1] = scanner.nextInt(); // y
                }
                scanner.close();
            } catch (Exception e) {
                System.out.println("Error: Unable to read file");
                printHelp();
                System.exit(1);
            }
        }
        else{
            printHelp();
            System.exit(1);
        }
        
        printConfig(n, m, xi, yi, xf, yf, n_scz, n_obs, tau, v, k, mu, delta, ro, scz, obs);
        
        // Initialize the grid, pec, and individuals
        grid = new Grid(n, m, scz, obs);
        pec = new Pec(tau);
        individuals = new ArrayList<Individual>();
        for (int i = 0; i < v; i++) {
            individuals.add(new Individual(xi, yi, xf, yf, k, mu, delta, ro));
        }
    }

    private static void printConfig(int n, int m, int xi, int yi, int xf, int yf, int n_scz, int n_obs, int tau, int v, int k, int mu, int delta, int ro, int[][] scz, int[][] obs)
    {
        System.out.printf("%d %d %d %d %d %d %d %d %d %d %d %d %d %d\n", n, m, xi, yi, xf, yf, n_scz, n_obs, tau, v, k, mu, delta, ro);
        System.out.println("Special Cost Zones:");
        for (int[] zone : scz) {
            System.out.printf("%d %d %d %d %d\n", zone[0], zone[1], zone[2], zone[3], zone[4]);
        }
        System.out.println("Obstacles:");
        for (int[] obstacle : obs) {
            System.out.printf("%d %d\n", obstacle[0], obstacle[1]);
        }

    }

    private static void printHelp()
    {
        System.out.println("Usage: pathfinder [-r <n> <m> <xi> <yi> <xf> <yf> <n_scz> <n_obs> <tau> <v> <vmax> <k> <mu> <delta> <ro>] | [-f <input_file>]");
        System.out.println("Options:");
        System.out.println("  -r: Run with random parameters");
        System.out.println("  -f: Run with parameters from a file");
        System.out.println("Parameters:");
        System.out.println("  <n>: Grid rows");
        System.out.println("  <m>: Grid columns");
        System.out.println("  <xi>: Initial x-coordinate of the individual");
        System.out.println("  <yi>: Initial y-coordinate of the individual");
        System.out.println("  <xf>: Final x-coordinate of the individual");
        System.out.println("  <yf>: Final y-coordinate of the individual");
        System.out.println("  <n_scz>: Number of special cost zones");
        System.out.println("  <n_obs>: Number of obstacles");
        System.out.println("  <tau>: Time horizon");
        System.out.println("  <v>: Number of individuals");
        System.out.println("  <vmax>: Maximum speed of the individuals");
        System.out.println("  <k>: Parameter k");
        System.out.println("  <mu>: Parameter mu");
        System.out.println("  <delta>: Parameter delta");
        System.out.println("  <ro>: Parameter ro");
        System.out.println("File format:");
        System.out.println("  <n> <m> <xi> <yi> <xf> <yf> <n_scz> <n_obs> <tau> <v> <vmax> <k> <mu> <delta> <ro>");
        System.out.println("  Special Cost Zones:");
        System.out.println("    <xn> <yn> <xn'> <yn'> <cost>");
        System.out.println("  Obstacles:");
        System.out.println("    <x> <y>");
        System.out.println();
    }
    
    public static void main(String[] args) throws Exception 
    {
        if (args.length < 1) {
            printHelp();
        }            

        Simulator simulator = new Simulator(args);
    }
}
