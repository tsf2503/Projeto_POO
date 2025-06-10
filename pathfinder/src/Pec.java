import java.util.PriorityQueue;
import java.util.ArrayList;

public class Pec {

    private Simulator sim;
    private PriorityQueue<Event> que;

    private int tau;
    private int time;
    private int timeDiv;

    private int events = 0;

    public Pec(int tau, Simulator sim) {
        this.tau = tau;
        this.timeDiv = (int) Math.floor(tau / 20);

        this.sim = sim;
        que = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.getTime(), e2.getTime()));
    }

    public void addEvent(Event e) {
        if (e.getTime() >= 0 && e.getTime() < tau) {
            que.add(e);
        } else {
            throw new IllegalArgumentException("Event time must be between 0 and " + tau);
        }
    }

    public void next() {
        if (que.isEmpty()) {
            sim.end();
            return;
        }

        if (peekNextEvent().getTime() == time) {
            getNextEvent().execute();
            events++;
        } else {
            if (time % timeDiv == 0) {
                sim.update();
                sim.print();
            }
            time++;
        }
    }

    public int getTime() {
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

    //TESTING
    public static void main(String[] args) {
        // Example usage of Pec class
        Pec pec = new Pec(10);
        pec.addEvent(new Death(new Individual(0,0,0,0,0,0,0,0)), 0);
        pec.addEvent(new Reproduction(), 1);
        pec.addEvent(new Move(), 2);

        ArrayList<Event> eventsAtTime0 = pec.getEvents();
        System.out.println("Events at time 0: " + eventsAtTime0.size());
        eventsAtTime0.get(0).execute();
        
        ArrayList<Event> eventsAtTime1 = pec.getEvents();
        System.out.println("Events at time 1: " + eventsAtTime1.size());
        eventsAtTime1.get(0).execute();
        
        ArrayList<Event> eventsAtTime2 = pec.getEvents();
        System.out.println("Events at time 2: " + eventsAtTime2.size());
        eventsAtTime2.get(0).execute();
    }
}


