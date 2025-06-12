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
 * Represents a Move event in the simulation.
 * Handles the logic for moving an individual and scheduling the next move.
 */
public class Move extends Event {

    /**
     * Constructs a Move event.
     *
     * @param time       The time at which the event occurs.
     * @param individual The individual involved in the event.
     * @param pec        The event controller (PEC) managing events.
     */
    Move(double time, Individual individual, Pec pec) {
        super(time, individual, pec);
    }

    /**
     * Executes the move event.
     * Moves the individual if alive and schedules the next move event.
     */
    @Override
    public void execute() {
        // Implementation of the move event logic
        if (individual.isAlive()) {
            individual.move();
            pec.addEvent(new Move(individual.getMoveTime() + time, individual, pec));
        }
    }

}
