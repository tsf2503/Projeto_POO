import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Simulator {
    Grid grid;
    Pec pec;
    ArrayList<Individual> individuals;
    
    
    Simulator(int n, int m, int xi, int yi, int xf, int yf, int[][] scz, int[][] obs, int tau, int v, int vmax, int k, int mu, int delta, int ro){

    }

    private static void printHelp(){
        //TODO: printHelp
    }
    
    public static void main(String[] args) throws Exception {
        
        if (args.length < 1) {
            printHelp();
        }            

        try {
            if (args[0] == "-r") {

                int n = Integer.parseInt(args[1]);
                int m = Integer.parseInt(args[2]);

                // Create random Special Cost Zones and Obstacles
                int n_scz = Integer.parseInt(args[7]);
                int n_obs = Integer.parseInt(args[8]);

                int[][] scz = new int[n_scz][5];
                int[][] obs = new int[n_obs][2];

                Random r = new Random();
                for (int i = 0; i < n_scz; i++) {
                    scz[i][0] = r.nextInt(m) + 1; // xi
                    scz[i][1] = r.nextInt(n) + 1; // yi

                    scz[i][2] = r.nextInt(m) + 1; // xf
                    scz[i][3] = r.nextInt(n) + 1; // yf

                    scz[i][4] = r.nextInt(10) + 1; // cost
                }

                for (int i = 0; i < n_obs; i++) {
                    obs[i][0] = r.nextInt(m) + 1; // x
                    obs[i][1] = r.nextInt(n) + 1; // y
                }
                
                Simulator sim = new Simulator(
                    n, m,
                    Integer.parseInt(args[3]), Integer.parseInt(args[4]),
                    Integer.parseInt(args[5]), Integer.parseInt(args[6]),
                    scz, obs,
                    Integer.parseInt(args[9]), // tau
                    Integer.parseInt(args[10]), // v
                    Integer.parseInt(args[11]), // vmax
                    Integer.parseInt(args[12]), // k
                    Integer.parseInt(args[13]), // mu
                    Integer.parseInt(args[14]), // delta
                    Integer.parseInt(args[15])  // ro
                );
            }
            else if (args[0] == "-f") {
                File inputFile = new File(args[1]);
                if (!inputFile.exists()) {
                    System.out.println("Error: File does not exist");
                    printHelp();
                    return;
                }
            }
            else{
                printHelp();
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Incorrect arguments");
            printHelp();
        }
    }
}
