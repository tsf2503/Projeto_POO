import java.util.PriorityQueue;

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
            que.offer(e);
        
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
}


