import java.util.PriorityQueue;
import java.util.ArrayList;

public class Pec {

    private PriorityQueue<Event> que;

    private int tau;
    private double time;
    private double nextUpdateTime;
    private double timeDiv;

    private int events;

    public Pec(int tau) {
        this.tau = tau;
        this.timeDiv = tau / 20.0;
        this.nextUpdateTime = this.timeDiv;
        this.time = 0.0;
        this.events = 0;

        que = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getTime(), e2.getTime()));
    }

    public void addEvent(Event e) {
        if (e.getTime() >= 0 && e.getTime() <= tau)
            que.add(e);
        
    }

    public int next() {
        if (que.isEmpty()) {
            // sim.print()
            // sim.end()
            return -1;
        }

        if (peekNextEvent().getTime() >= nextUpdateTime) {
            // sim.print()
            time = nextUpdateTime;
            nextUpdateTime += timeDiv;
            return 1;
        } else {
            Event e = getNextEvent();

            e.execute();
            events++;

            time = e.getTime();
            return 0;
        }
    }

    public double getTime() {
        return time;
    }

    public int getEventsCount() {
        return events;
    }

    private Event peekNextEvent() {
        return que.peek();
    }

    private Event getNextEvent() {
        return que.poll();
    }

    @Override
    public String toString() {
        return "Pec{" +
                "tau=" + tau +
                ", time=" + time +
                ", nextUpdateTime=" + nextUpdateTime +
                ", timeDiv=" + timeDiv +
                ", events=" + events +
                ", que=" + que +
                '}';
    }

    //TESTING
    public static void main(String[] args) {
        // Example usage of Pec class
        Pec pec = new Pec(10);
        int [][] obstacles = {};
        int [][] specialZones = {};
        System.out.println(obstacles + " " + specialZones);
        Grid grid = new Grid(1, 1, 10, 10, 10, 10, specialZones, obstacles);
        Population population = new Population(20, 3, 5, 1, 1);
        Individual individual = new Individual(population, grid);

        System.out.println(pec.toString());
        System.out.println(grid.toString());
        System.out.println(population.toString());
        System.out.println(individual.toString());


        pec.addEvent(new Death(0.5 , individual, pec));
        pec.addEvent(new Reproduction(0.2,individual, pec));
        pec.addEvent(new Move(0.1, individual, pec));

        System.out.println(pec.toString());
        
        System.out.println(pec.next());
        System.out.println(pec.toString());
        System.out.println("Events count: " + pec.getEventsCount());
        System.out.println("Next Event: " + pec.peekNextEvent().toString());

        while (true) {
            // Continue processing events until the queue is empty
            int result = pec.next();
            System.out.println(pec);
            System.out.println("Next event result: " + result);
            if (result == -1) {
                break;
            }
        }

        System.out.println("Final Events count: " + pec.getEventsCount());
        System.out.println("Final time: " + pec.getTime());
        System.out.println("Final Pec state: " + pec.toString());

        
    }
}


