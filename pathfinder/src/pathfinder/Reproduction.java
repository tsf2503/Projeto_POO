/*
 * POO - Instituto Superior Técnico
 *
 * Guilherme Dias
 * Francisco Coelho
 * João Oliveira
 * Tiago Ferreira
 */

package pathfinder;

/**
 * Represents a reproduction event in the simulation.
 * Handles the logic for an individual reproducing and scheduling
 * subsequent events for the offspring.
 */
public class Reproduction extends Event {

    /**
     * Constructs a new Reproduction event.
     *
     * @param time       The time at which the event occurs.
     * @param individual The individual involved in the event.
     * @param pec        The event controller managing the simulation events.
     */
    Reproduction(double time, Individual individual, Pec pec) {
        super(time, individual, pec);
    }

    /**
     * Executes the reproduction event.
     * If the individual is alive, it creates a child and schedules
     * the child's move, death, and reproduction events, as well as
     * the parent's next reproduction event.
     */
    @Override
    public void execute() {
        // Implementation of the reproduction event logic
        if (individual.isAlive()) {
            Individual child = individual.reproduce();

            // Schedule the next reproduction event for the parent
            pec.addEvent(new Reproduction(individual.getMoveTime() + time, individual, pec));

            // Schedule the child's move, death, and reproduction events
            pec.addEvent(new Move(child.getMoveTime() + time, child, pec));
            pec.addEvent(new Death(child.getDeathTime() + time, child, pec));
            pec.addEvent(new Reproduction(child.getReproductionTime() + time, child, pec));
        }
    }

}
