import java.util.ArrayList;

public class Pec {
    private ArrayList<ArrayList<Event>> que;
    private int tau;
    private int index = 0;  

    public Pec(int tau) {
        this.tau = tau;
        que = new ArrayList<ArrayList<Event>>(tau);
        for (int i = 0; i < tau; i++) {
            que.add(new ArrayList<Event>(1));
        }
        index = 0;
    }
    public void addEvent(Event e, int time) {
        if (time < 0 || time >= tau) {
            throw new IllegalArgumentException("Time must be between 0 and " + (tau - 1));
        }
        if (e == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        que.get(time).add(e);
    }

    public ArrayList<Event> getEvents() {
        return que.get(index++);
    }

    //TESTING
    public static void main(String[] args) {
        // Example usage of Pec class
        Pec pec = new Pec(10);
        pec.addEvent(new Death(), 0);
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


