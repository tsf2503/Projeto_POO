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
 * Represents a Death event in the simulation.
 * Handles the logic for when an individual dies.
 */
public class Death extends Event {

    /**
     * Constructs a Death event.
     *
     * @param time       The time at which the event occurs.
     * @param individual The individual involved in the event.
     * @param pec        The event controller or manager.
     */
    Death(double time, Individual individual, Pec pec) {
        super(time, individual, pec);
    }

    /**
     * Executes the death event logic.
     * If the individual is alive, marks them as dead.
     */
    @Override
    public void execute() {
        // Implementation of the death event logic
        if (individual.isAlive())
            individual.death();
    }
}
