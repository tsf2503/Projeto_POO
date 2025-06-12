/*
 * POO - Instituto Superior Técnico
 *
 * Guilherme Dias
 * Francisco Coelho
 * João Oliveira
 * Tiago Ferreira
 */

/**
 * Abstract base class representing an event in the simulation.
 * Each event is associated with a time, an individual, and a PEC (event queue).
 */
package pathfinder;

abstract class Event {

    /**
     * Reference to the event queue (PEC) where this event is managed.
     */
    protected Pec pec;

    /**
     * The scheduled time for this event to occur.
     */
    protected double time;

    /**
     * The individual associated with this event.
     */
    protected Individual individual;

    /**
     * Constructs an Event with the specified time, individual, and event queue.
     *
     * @param time       the time at which the event is scheduled
     * @param individual the individual involved in the event
     * @param pec        the event queue managing this event
     */
    public Event(double time, Individual individual, Pec pec) {
        this.time = time;
        this.individual = individual;
        this.pec = pec;
    }

    /**
     * Returns the scheduled time of the event.
     *
     * @return the event time
     */
    public double getTime() {
        return time;
    }

    /**
     * Returns the individual associated with the event.
     *
     * @return the individual
     */
    public Individual getIndividual() {
        return individual;
    }

    /**
     * Returns a string representation of the event, including its type, time, and individual.
     *
     * @return a string describing the event
     */
    @Override
    public String toString() {
        return "Event{" +
                "type=" + getClass().getSimpleName() +
                ", time=" + time +
                ", individual=" + individual +
                '}';
    }

    /**
     * Executes the event's logic.
     * This method must be implemented by subclasses to define specific event behavior.
     */
    abstract void execute();
}
