/*
 * POO - Instituto Superior Técnico
 *
 * Guilherme Dias
 * Francisco Coelho
 * João Oliveira
 * Tiago Ferreira
 * 
 * Pathfinder is a stochastic simulation project that tries to achieve the optimal path
 * in a given grid, with constraints: obstacles, special cost zones and its own size.
 * It achieves this by simulating individuals that can randomly (given their comfort/fitness)
 * move, reproduce and die, creating a population that evolves over time. The fittest individuals
 * are more likely to survive, leading to an optimal path being found.
 * 
 * This is the entry point.
 */

/**
 * Main class for the Pathfinder project.
 * Handles command-line argument parsing, random/file input, and simulation startup.
 */
package main;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

import pathfinder.Simulator;

public class Main {

    /**
     * Prints usage instructions and parameter descriptions for the program.
     */
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

    /**
     * Main entry point for the Pathfinder program.
     * Parses command-line arguments, initializes simulation parameters (randomly or from file),
     * and starts the simulation.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Simulation parameters
        int n = 0, m = 0, xi = 0, yi = 0, xf = 0, yf = 0, n_scz = 0, n_obs = 0, tau = 0, v = 0, k = 0, mu = 0, delta = 0, ro = 0, vmax = 0;
        int[][] scz = null, obs = null;

        // Handle random parameter mode
        if (args[0].equals("-r")) {
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
        // Handle file input mode
        else if (args[0].equals("-f")) {
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

                scanner.nextLine(); // Skip to next line
                scanner.nextLine(); // Skip to next line

                for (int i = 0; i < n_scz; i++) {
                    scz[i][0] = scanner.nextInt(); // xn
                    scz[i][1] = scanner.nextInt(); // yn
                    scz[i][2] = scanner.nextInt(); // xn'
                    scz[i][3] = scanner.nextInt(); // yn'
                    scz[i][4] = scanner.nextInt(); // cost
                }

                scanner.nextLine(); // Skip to next line
                scanner.nextLine(); // Skip to next line

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
        // Handle invalid arguments
        else{
            printHelp();
            System.exit(1);
        }

        // Initialize the simulator with the provided parameters
        Simulator simulator = new Simulator(n, m, xi, yi, xf, yf, scz, obs, tau, v, vmax, k, mu, delta, ro);
        // Print initial configuration
        simulator.printConfig();
        // Start the simulation
        simulator.run();
    }
}
