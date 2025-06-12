/*
 * POO - Instituto Superior Técnico
 *
 * Guilherme Dias
 * Francisco Coelho
 * João Oliveira
 * Tiago Ferreira
 */

package pathfinder;

import java.util.PriorityQueue;

/**
 * The Pec class manages a priority event queue for a simulation,
 * handling event scheduling and time progression.
 */
public class Pec {

    // Priority queue to store events ordered by their scheduled time
    private PriorityQueue<Event> que;

    // Maximum simulation time
    private int tau;
    // Current simulation time
    private double time;
    // Time for the next scheduled update
    private double nextUpdateTime;
    // Time interval between updates
    private double timeDiv;

    // Number of executed events
    private int events;

    /**
     * Constructs a Pec object with a given maximum simulation time.
     * @param tau The maximum simulation time.
     */
    public Pec(int tau) {
        this.tau = tau;
        this.timeDiv = tau / 20.0;
        this.nextUpdateTime = this.timeDiv;
        this.time = 0.0;
        this.events = 0;

        que = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getTime(), e2.getTime()));
    }

    /**
     * Adds an event to the queue if its scheduled time is within bounds.
     * @param e The event to add.
     */
    public void addEvent(Event e) {
        if (e.getTime() >= 0 && e.getTime() <= tau)
            que.offer(e);
    }

    /**
     * Processes the next event or update in the simulation.
     * @return 0 if an event was executed, 1 if an update occurred, -1 if no events remain.
     */
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

    /**
     * Gets the current simulation time.
     * @return The current time.
     */
    public double getTime() {
        return time;
    }

    /**
     * Gets the number of executed events.
     * @return The event count.
     */
    public int getEventsCount() {
        return events;
    }

    /**
     * Peeks at the next event in the queue without removing it.
     * @return The next event.
     */
    private Event peekNextEvent() {
        return que.peek();
    }

    /**
     * Retrieves and removes the next event from the queue.
     * @return The next event.
     */
    private Event getNextEvent() {
        return que.poll();
    }

    /**
     * Gets the maximum simulation time.
     * @return The tau value.
     */
    public int getTau() {
        return tau;
    }

    /**
     * Returns a string representation of the Pec object.
     * @return String describing the Pec state.
     */
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
