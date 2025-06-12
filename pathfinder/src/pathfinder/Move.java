package pathfinder;
//TODO: Implement the Move event logic

public class Move extends Event {

    Move(double time, Individual individual, Pec pec) {
        super(time, individual, pec);
    }

    @Override
    public void execute() {
        // Implementation of the move event logic
        if (individual.isAlive()) {
            individual.move();
            pec.addEvent(new Move(individual.getMoveTime() + time, individual, pec));
        }
    }

}
